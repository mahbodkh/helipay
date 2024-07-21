package app.helipay.um.service;


import app.helipay.um.constants.AuthoritiesConstants;
import app.helipay.um.constants.Constants;
import app.helipay.um.domain.Authority;
import app.helipay.um.domain.UserEntity;
import app.helipay.um.exeption.BadRequestException;
import app.helipay.um.exeption.EmailAlreadyUsedException;
import app.helipay.um.exeption.UsernameAlreadyUsedException;
import app.helipay.um.repository.AuthorityRepository;
import app.helipay.um.repository.UserRepository;
import app.helipay.um.service.dto.*;
import app.helipay.um.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CacheManager cacheManager;
    private final AuthorityRepository authorityRepository;
    //
    private static final int DEF_COUNT = 6;
    private static final int USER_COMPLETE_PASSWORD_MIN = 5;

    @Transactional(readOnly = true)
    public List<UserReply> getAllUsers(Pageable pageable) {
        return userMapper.toDto(userRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public UserReply getUser() {
        // get user from catchManager
//        final var user = SecurityContextHolder.getContext().getAuthentication().getName();
//        return userMapper.toDto(userRepository.findByUsername(user));
        return null;
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    //    @Transactional(readOnly = true)
//    public List<UserReply> getAdminUsers() {
//        final var userEntityAdmins
//                = userRepository.findAllByAuthoritiesIn(Set.of(new Authority("ADMIN")));
//        return userMapper.toDto(userEntityAdmins);
//    }


    @Transactional(readOnly = true)
    public List<UserReply> getUsersByRole(String role) {
        final var userEntitiesByRole
                = userRepository.findAllByAuthorities_NameInAndActivatedAndStatus(List.of(role), Boolean.TRUE, UserEntity.StatusType.ACTIVE);
        return userMapper.toDto(userEntitiesByRole);
    }

    public Optional<UserEntity> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
                .findOneByActivationKey(key)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);

                    final UserEntity saved = userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Activated user: {}", saved);
                    return saved;
                });
    }

    public Optional<UserEntity> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
                .findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minus(USER_COMPLETE_PASSWORD_MIN, ChronoUnit.MINUTES)))
                .map(user -> {
//                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setPassword(newPassword);
                    user.setResetKey(null);
                    user.setResetDate(null);

                    final UserEntity saved = userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Password reset is done for user: {}", saved);
                    return saved;
                });
    }

    public Optional<UserEntity> requestPasswordReset(String mail) {
        return userRepository
                .findOneByEmailIgnoreCase(mail)
                .filter(UserEntity::isActivated)
                .map(user -> {
                    user.setResetKey(RandomStringUtils.randomAlphanumeric(DEF_COUNT));
                    user.setResetDate(Instant.now());

                    final UserEntity saved = userRepository.save(user);
                    log.debug("Request password reset for user: {}", saved);
                    this.clearUserCaches(user);
                    return user;
                });
    }


    public UserReply createUser(UserRequest request) {

        final UserEntity userEntity = userMapper.toEntity(request);

//        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
//        userEntity.setPassword(encryptedPassword);

        userEntity.setResetDate(Instant.now());
        userEntity.setResetKey(RandomStringUtils.randomAlphanumeric(DEF_COUNT));
        userEntity.setPassword(RandomStringUtils.randomAlphanumeric(DEF_COUNT));
        userEntity.setActivated(false);

        final Set<Authority> authorities = request.authorities() == null
                ? Set.of(new Authority().name(AuthoritiesConstants.USER))
                : request.authorities()
                .stream()
                .map(authorityRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        userEntity.setAuthorities(authorities);

        final UserEntity saved = userRepository.save(userEntity);
        this.clearUserCaches(userEntity);
        log.debug("Created is done for this user: {}", saved);
        return userMapper.toDto(saved);
    }

    /**
     * register is create user by client!
     *
     * @param request dto is mostly should equal to UserDto.
     */
    // needed to handle file as well!
    public UserReply registerUser(RegisterRequest request, String password) {
        userRepository
                .findOneByUsername(request.username().toLowerCase())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                });

        userRepository
                .findOneByEmailIgnoreCase(request.email())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                });

        // check userByMobile as well

        final UserEntity newUser = userMapper.registerRequestToEntity(request);

//        String encryptedPassword = passwordEncoder.encode(password);
//        newUser.setPassword(encryptedPassword);
        newUser.setPassword(password);
        newUser.setLangKey(request.langKey() != null ? request.langKey() : Constants.DEFAULT_LANGUAGE);
        newUser.setActivated(false);
        newUser.setActivationKey(RandomStringUtils.randomNumeric(DEF_COUNT));
        newUser.setAuthorities(Set.of(new Authority().name(AuthoritiesConstants.USER)));

        final UserEntity saved = userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Register for this User: {}", saved);
        return userMapper.toDto(saved);
    }


    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param request is the dto of user for update.
     * @return updated user.
     */
    public Optional<UserReply> updateUser(Long userId, UserRequest request) {
        return Optional
                .of(userRepository.findById(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user = userMapper.userRequestToEntity(request, user);

                    if (request.authorities() != null) {
                        final Set<Authority> updatedAuthorities = request.authorities()
                                .stream()
                                .map(authorityRepository::findByName)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toSet());
                        user.setAuthorities(updatedAuthorities);
                    }
                    final UserEntity saved = userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Updated for this User: {}", saved);
                    return saved;
                })
                .map(userMapper::toDto);
    }


    // warning
    public void deleteUser(String login) {
        userRepository
                .findByUsernameOrMobileOrEmail(login)
                .ifPresent(user -> {
                    userRepository.deleteById(user.getId());
                    log.debug("Deleted User: {}", user);
                });
    }


    // warning
    public void deleteUserByUsername(String username) {
        userRepository
                .findOneByUsername(username)
                .ifPresent(user -> {
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                    log.debug("Deleted User: {}", user);
                });
    }

    // warning
    public void deleteUserById(Long userId) {
        userRepository
                .findById(userId)
                .ifPresent(user -> {
                    userRepository.deleteById(user.getId());
                    log.debug("Deleted User: {}", user);
                });
    }

    public void safeDeleteUser(Long userId) {
        userRepository
                .findById(userId)
                .ifPresent(
                        entity -> {
                            entity.setStatus(UserEntity.StatusType.DELETED);
                            userRepository.save(entity);
                            log.debug("Safe deleted User: {}", entity);
                        }
                );
    }

    public void banUser(Long userId) {
        userRepository
                .findById(userId)
                .ifPresent(
                        entity -> {
                            entity.setStatus(UserEntity.StatusType.BANNED);
                            userRepository.save(entity);
                            log.debug("Banned User: {}", entity);
                        }
                );
    }

    public void frozenUser(Long userId) {
        userRepository
                .findById(userId)
                .ifPresent(
                        entity -> {
                            entity.setStatus(UserEntity.StatusType.FROZEN);
                            userRepository.save(entity);
                            log.debug("Frozen User: {}", entity);
                        }
                );
    }

    public UserReply getUserById(Long userId) {
        final var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(String.format("User id: %s not found.", userId)));
        return userMapper.toDto(userEntity);
    }

    public LoginReply login(LoginRequest request) {
        log.info("User has try to login by: ({}) and ip ({})", request.login(), request.ipAddress());

        return null;
        //        new AuthenticationController.LoginReply(reply.getFirst(), null,
        //                                                reply.getSecond().stream().map(String::valueOf).collect(Collectors.toUnmodifiableSet()));
    }


//      @Transactional
//      public void changePassword(String currentClearTextPassword, String newPassword) {
//        SecurityUtils
//            .getCurrentUserLogin()
//            .flatMap(userRepository::findOneByLogin)
//            .ifPresent(user -> {
//              String currentEncryptedPassword = user.getPassword();
//              if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
//                throw new InvalidPasswordException();
//              }
//              String encryptedPassword = passwordEncoder.encode(newPassword);
//              user.setPassword(encryptedPassword);
//              this.clearUserCaches(user);
//              log.debug("Changed password for User: {}", user);
//            });
//      }

    public void resetPassword(ResetPasswordRequest request) {
        log.debug("Reset user password for username key {}", request.login());
        userRepository
                .findOneByUsernameAndStatus(request.login(), UserEntity.StatusType.ACTIVE)
                .filter(status -> UserEntity.StatusType.ACTIVE.equals(status.getStatus()))
                .map(
                        user -> {
//                                            user.setPassword(bCryptPasswordEncoder.encode(request.newPassword()));
                            user.setPassword(request.newPassword());
                            return user;
                        }
                );
    }

//      public Optional<UserEntity> getUserByPrincipal() {
//        var principal = Objects
//            .requireNonNull(SecurityUtils.getAuthenticatedUsername()).getFirst();
//        return userRepository.findOneByUsername(principal);
//      }


    private boolean removeNonActivatedUser(UserEntity existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;

    }

    //    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getUsername());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                });
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).toList();
    }

    private void clearUserCaches(UserEntity userEntity) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_USERNAME_CACHE), "USERS_BY_USERNAME_CACHE is null").evict(userEntity.getUsername());
        if (userEntity.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE), "USERS_BY_EMAIL_CACHE is null").evict(userEntity.getEmail());
        }
    }


}
