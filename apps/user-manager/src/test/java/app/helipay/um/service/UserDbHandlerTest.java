package app.helipay.um.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import app.helipay.um.constants.AuthoritiesConstants;
import app.helipay.um.domain.Authority;
import app.helipay.um.domain.UserEntity;
import app.helipay.um.handler.UserDbHandler;
import app.helipay.um.repository.AuthorityRepository;
import app.helipay.um.repository.UserRepository;
import app.helipay.um.service.dto.RegisterRequest;
import app.helipay.um.service.dto.UserReply;
import app.helipay.um.service.dto.UserRequest;
import app.helipay.um.service.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class UserDbHandlerTest {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_USERNAME = "username";
    private static final String DEFAULT_EMAIL = "email@example_localhost.com";
    private static final String DEFAULT_FIRSTNAME = "john";
    private static final String DEFAULT_LASTNAME = "doe";
    private static final String DEFAULT_IMAGE_URL = "http://placehold.it/50x50";
    private static final String DEFAULT_LANG_KEY = "EN";
    private static final Set<Authority> authoritiesUser = Set.of(new Authority().name(AuthoritiesConstants.USER));

    @Mock
    private UserRepository userRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private UserDbHandler userDbHandler;

    private UserEntity user;
    private UserReply userReply;
    private Long numberOfUsers;

    @BeforeEach
    public void setUp() {
        user = buildUser();
        userReply = buildUserReply();

        assertThat(userRepository).isNotNull();
        assertThat(userDbHandler).isNotNull();
        assertThat(userMapper).isNotNull();

        numberOfUsers = userRepository.count();
    }


    @AfterEach
    public void cleanupAndCheck() {
        cacheManager
                .getCacheNames()
                .stream()
                .map(cacheName -> this.cacheManager.getCache(cacheName))
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
        userDbHandler.deleteUser(DEFAULT_USERNAME);
        assertThat(userRepository.count()).isEqualTo(numberOfUsers);
        numberOfUsers = null;
    }

    @Test
    @Transactional
    void testCreateUser() {
        setupCache();

        final UserRequest request = buildUserRequest();

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(user);

        when(authorityRepository.findByName(anyString())).thenReturn(Optional.of(new Authority().name(AuthoritiesConstants.USER)));

        setupUserSave();

        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userReply);


        final UserReply result = userDbHandler.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(user.getId());
        assertThat(result.username()).isEqualTo(user.getUsername());
        assertThat(result.firstName()).isEqualTo(user.getFirstName());
        assertThat(result.lastName()).isEqualTo(user.getLastName());
        assertThat(result.email()).isEqualTo(user.getEmail());
        assertThat(result.authorities()).isEqualTo(getAuthoritiesUser());

        verify(userRepository).save(any(UserEntity.class));

        verifyCache();
    }


    @Test
    @Transactional
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user), pageable, 1));

        List<UserReply> users = List.of(userReply);
        when(userMapper.toDto(any(Page.class))).thenReturn(users);

        List<UserReply> result = userDbHandler.getAllUsers(pageable);

        assertThat(users.size()).isGreaterThan(0);
        assertThat(users.size()).isEqualTo(result.size());
        verify(userRepository).findAll(pageable);
    }

    @Test
    @Transactional
    void testGetUser() {
        final Long userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        final Optional<UserEntity> result = userDbHandler.getUser(userId);

        assertThat(result).isPresent();
        assertThat(user).isEqualTo(result.get());
        verify(userRepository).findById(userId);
    }

    @Test
    @Transactional
    void testGetUsersByRole() {
        final List<UserEntity> users = List.of(user);
        when(userRepository.findAllByAuthorities_NameInAndActivatedAndStatus(List.of("ROLE_USER"), Boolean.TRUE, UserEntity.StatusType.ACTIVE))
                .thenReturn(users);

        when(userMapper.toDto(any(List.class))).thenReturn(List.of(userReply));

        final List<UserReply> result = userDbHandler.getUsersByRole("ROLE_USER");

        assertThat(result).isNotNull();
        assertThat(users.size()).isEqualTo(result.size());
        assertThat(result).containsExactly(userReply);

        verify(userRepository).findAllByAuthorities_NameInAndActivatedAndStatus(List.of("ROLE_USER"), Boolean.TRUE, UserEntity.StatusType.ACTIVE);
    }

    @Test
    @Transactional
    void testActivateRegistration() {
        setupCache();

        user.setActivated(false);
        final String key = user.getActivationKey();

        setupUserSave();

        when(userRepository.findOneByActivationKey(eq(key))).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userDbHandler.activateRegistration(key);

        assertThat(result).isPresent();
        assertThat(result.get().isActivated()).isTrue();
        assertThat(result.get().getActivationKey()).isNull();
        verify(userRepository).findOneByActivationKey(key);
        verify(userRepository).save(user);

        verifyCache();
    }


    @Test
    @Transactional
    void testIsNotActivatedUser_CanNotRequestPasswordReset() {
        user.setActivated(false);
        when(userRepository.findOneByEmailIgnoreCase(eq(user.getEmail()))).thenReturn(Optional.of(user));

        Optional<UserEntity> maybeUser = userDbHandler.requestPasswordReset(user.getEmail());
        assertThat(maybeUser).isNotPresent();
    }

    @Test
    @Transactional
    void testIsActivatedUserCanRequestPasswordReset() {
        setupCache();

        when(userRepository.findOneByEmailIgnoreCase(eq(user.getEmail()))).thenReturn(Optional.of(user));

        setupUserSave();

        Optional<UserEntity> result = userDbHandler.requestPasswordReset(user.getEmail());
        assertThat(result).isPresent();

        verifyCache();
    }

    @Test
    @Transactional
    void testUserMustExistToResetPassword() {
        setupCache();

        when(userRepository.findOneByEmailIgnoreCase("invalid.login@localhost")).thenReturn(Optional.empty());
        when(userRepository.findOneByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

        Optional<UserEntity> maybeUser = userDbHandler.requestPasswordReset("invalid.login@localhost");
        assertThat(maybeUser).isNotPresent();

        maybeUser = userDbHandler.requestPasswordReset(user.getEmail());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getEmail()).isEqualTo(user.getEmail());
        assertThat(maybeUser.orElse(null).getResetDate()).isNotNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNotNull();

        verifyCache();
    }

    @Test
    @Transactional
    void testUserCanResetPassword() {
        setupCache();

        final String oldPassword = user.getPassword();
        final Instant minusAgo = Instant.now().minus(3, ChronoUnit.MINUTES);
        final String resetKey = RandomStringUtils.randomAlphanumeric(6);
        user.setResetDate(minusAgo);
        user.setResetKey(resetKey);

        setupUserSave();

        when(userRepository.findOneByResetKey(eq(resetKey))).thenReturn(Optional.of(user));

        Optional<UserEntity> maybeUser = userDbHandler.completePasswordReset("newPassword", user.getResetKey());
        assertThat(maybeUser).isPresent();

        assertThat(maybeUser.get().isActivated()).isEqualTo(user.getActivated());
        assertThat(maybeUser.get().getResetDate()).isEqualTo(user.getResetDate());
        assertThat(maybeUser.get().getResetKey()).isEqualTo(user.getResetKey());

        assertThat(maybeUser.orElse(null).getResetDate()).isNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNull();
        assertThat(maybeUser.orElse(null).getPassword()).isNotEqualTo(oldPassword);

        verifyCache();
    }

    @Test
    @Transactional
    void testResetKeyMoreThan5Mints() {

        final Instant minusAgo = Instant.now().minus(25, ChronoUnit.MINUTES);
        final String resetKey = RandomStringUtils.randomAlphanumeric(6);
        user.setResetDate(minusAgo);
        user.setResetKey(resetKey);

        when(userRepository.findOneByResetKey(eq(resetKey))).thenReturn(Optional.empty());

        Optional<UserEntity> maybeUser = userDbHandler.completePasswordReset("newPassword", user.getResetKey());
        assertThat(maybeUser).isNotPresent();
    }

    @Test
    @Transactional
    void testGetAuthorities() {
        List<Authority> authorities = List.of(new Authority().name(AuthoritiesConstants.USER), new Authority().name("ROLE_ADMIN"));
        when(authorityRepository.findAll()).thenReturn(authorities);

        List<String> result = userDbHandler.getAuthorities();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(authorities.size());
        assertThat(result).contains("ROLE_USER", "ROLE_ADMIN");
    }

    @Test
    @Transactional
    void testRegisterUser() {
        setupCache();

        final RegisterRequest request = new RegisterRequest(DEFAULT_USERNAME, DEFAULT_FIRSTNAME, DEFAULT_LASTNAME, DEFAULT_EMAIL, DEFAULT_IMAGE_URL, DEFAULT_LANG_KEY);
        final String password = "password";

        when(userRepository.findOneByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.findOneByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(userMapper.registerRequestToEntity(any(RegisterRequest.class))).thenReturn(user);
        setupUserSave();
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userReply);


        final UserReply result = userDbHandler.registerUser(request, password);

        assertThat(result).isNotNull();
        verify(userRepository).save(any(UserEntity.class));

        verifyCache();
    }


    @Test
    @Transactional
    void testFrozenUser() {
        final Long userId = DEFAULT_ID;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));

        userDbHandler.frozenUser(userId);

        assertThat(user.getStatus()).isEqualTo(UserEntity.StatusType.FROZEN);
        verify(userRepository).save(user);
    }


    @Test
    void testGetUserById() {
        final Long userId = DEFAULT_ID;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userReply);

        final UserReply result = userDbHandler.getUserById(userId);


        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(userReply.id());
        assertThat(result.username()).isEqualTo(userReply.username());

        verify(userRepository).findById(userId);
    }


    @Test
    @Transactional
    void testUpdateUser() {
        setupCache();

        final Long userId = DEFAULT_ID;
        final UserRequest request = buildUserRequest();
        final UserEntity updated = UserEntity.builder()
                .id(userId)
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .langKey(request.langKey())
                .activated(request.isActivated())
                .authorities(request.authorities().stream().map(authority -> new Authority().name(authority)).collect(Collectors.toSet()))
                .build();

        final UserReply reply = new UserReply(updated.getId(),
                updated.getUsername(),
                updated.getFirstName(),
                updated.getLastName(),
                updated.getEmail(),
                updated.getImageUrl(),
                updated.getActivated(),
                updated.getLangKey(),
                updated.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authorityRepository.findByName(anyString())).thenReturn(Optional.of(new Authority().name(AuthoritiesConstants.USER)));
        when(userMapper.userRequestToEntity(any(UserRequest.class), any(UserEntity.class))).thenReturn(updated);
        when(userRepository.save(any(UserEntity.class))).thenReturn(updated);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(reply);

        // when
        final Optional<UserReply> result = userDbHandler.updateUser(userId, request);

        assertThat(result).isPresent();
        assertThat(request.username()).isEqualTo(result.get().username());

        verify(userRepository).save(updated);

        verify(cacheManager).getCache(UserRepository.USERS_BY_USERNAME_CACHE);
        verify(cacheManager).getCache(UserRepository.USERS_BY_EMAIL_CACHE);

        verify(cache).evict(request.username());
        verify(cache).evict(request.email());

        verify(cache, times(1)).evict(request.username());
        verify(cache, times(1)).evict(request.email());
    }


    @Test
    @Transactional
    void testDeleteUserByUsername() {
        setupCache();

        final String username = DEFAULT_USERNAME;
        when(userRepository.findOneByUsername(eq(username))).thenReturn(Optional.of(user));

        userDbHandler.deleteUserByUsername(username);

        verify(userRepository).delete(user);

        verifyCache();
    }

    @Test
    @Transactional
    void testDeleteUser() {
        final String login = "userLogin";
        when(userRepository.findByUsernameOrMobileOrEmail(eq(login))).thenReturn(Optional.of(user));

        userDbHandler.deleteUser(login);

        verify(userRepository).deleteById(user.getId());
    }


    @Test
    @Transactional
    void testSafeDeleteUser() {
        final Long userId = DEFAULT_ID;
        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));

        userDbHandler.safeDeleteUser(userId);

        assertThat(user.getStatus()).isEqualTo(UserEntity.StatusType.DELETED);
        verify(userRepository).save(user);
    }


    @Test
    @Transactional
    void testBanUser() {
        final Long userId = DEFAULT_ID;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));

        userDbHandler.banUser(userId);

        assertThat(user.getStatus()).isEqualTo(UserEntity.StatusType.BANNED);
        verify(userRepository).save(user);
    }


    private void setupUserSave() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
    }

    private void setupCache() {
        when(cacheManager.getCache(UserRepository.USERS_BY_USERNAME_CACHE)).thenReturn(cache);
        when(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).thenReturn(cache);
    }

    private void verifyCache() {
        verify(cacheManager).getCache(UserRepository.USERS_BY_USERNAME_CACHE);
        verify(cacheManager).getCache(UserRepository.USERS_BY_EMAIL_CACHE);

        verify(cache).evict(DEFAULT_USERNAME);
        verify(cache).evict(DEFAULT_EMAIL);

        verify(cache, times(1)).evict(DEFAULT_USERNAME);
        verify(cache, times(1)).evict(DEFAULT_EMAIL);
    }

    private UserEntity buildUser() {
        UserEntity user = new UserEntity();
        user.setId(DEFAULT_ID);
        user.setUsername(DEFAULT_USERNAME);
        user.setPassword("password");
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGE_URL);
        user.setLangKey(DEFAULT_LANG_KEY);
        user.setActivationKey("123key");
        return user;
    }


    private Set<String> getAuthoritiesUser() {
        return authoritiesUser.stream().map(Authority::getName).collect(Collectors.toSet());
    }

    private UserReply buildUserReply() {
        return new UserReply(DEFAULT_ID, DEFAULT_USERNAME, DEFAULT_FIRSTNAME, DEFAULT_LASTNAME, DEFAULT_EMAIL, DEFAULT_IMAGE_URL, true, DEFAULT_LANG_KEY, getAuthoritiesUser());
    }

    private @NotNull UserRequest buildUserRequest() {
        return new UserRequest(null, "usernameRequest", "firstRequest", "lastRequest", "email@example.com", "imageUrlRequest", false, "FA", Set.of("ROLE_ADMIN"));
    }

}