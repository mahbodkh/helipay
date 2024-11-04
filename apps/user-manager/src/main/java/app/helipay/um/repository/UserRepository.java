package app.helipay.um.repository;

import app.helipay.um.domain.Authority;
import app.helipay.um.domain.UserEntity;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    String USERS_BY_USERNAME_CACHE = "usersByUsername";

//    String USERS_BY_EMAIL_CACHE = "usersByEmail";

//    String USERS_BY_MOBILE_CACHE = "usersByMobile";

//    Optional<UserEntity> findOneByActivationKey(String activationKey);

//    List<UserEntity> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

//    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
//    Optional<UserEntity> findOneByEmailIgnoreCase(String email);

//    @Cacheable(cacheNames = USERS_BY_MOBILE_CACHE)
//    Optional<UserEntity> findOneByMobile(String mobile);

//    @Cacheable(cacheNames = USERS_BY_USERNAME_CACHE)
    Optional<UserEntity> findOneByUsername(String username);

//    Optional<UserEntity> findOneByUsernameAndStatus(String username, UserEntity.StatusType status);

//    Optional<UserEntity> findOneByResetKey(String resetKey);


//    @EntityGraph(attributePaths = "authorities")
//    @Cacheable(cacheNames = USERS_BY_USERNAME_CACHE)
//    Optional<UserEntity> findOneWithAuthoritiesByUsername(String login);

//    @EntityGraph(attributePaths = "authorities")
//    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
//    Optional<UserEntity> findOneWithAuthoritiesByEmailIgnoreCase(String email);

//    @EntityGraph(attributePaths = "authorities")
//    @Cacheable(cacheNames = USERS_BY_MOBILE_CACHE)
//    Optional<UserEntity> findOneWithAuthoritiesByMobile(String email);

//    Page<UserEntity> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

//      Optional<UserEntity> findOneByUsernameOrMobileOrEmail(String login);

//      List<UserEntity> findAllByAuthoritiesIn(Set<Authority> authorities);


//    List<UserEntity> findAllByAuthorities_NameInAndActivatedAndStatus(List<String> authority, boolean activated, UserEntity.StatusType status);
//
//
//    @Query("from UserEntity u where u.username =:login or u.mobile =:login or u.email =:login")
//    Optional<UserEntity> findByUsernameOrMobileOrEmail(String login);


//    List<UserEntity> findAllByCity(String city);
//
//    List<UserEntity> findAllByLanguage(String language);
//
//    List<UserEntity> findAllByNearby(long userId, double radius);

}