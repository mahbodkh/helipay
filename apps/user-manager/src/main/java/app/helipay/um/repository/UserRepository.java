package app.helipay.um.repository;

import app.helipay.um.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByEmailIgnoreCase(String email);

    Optional<UserEntity> findOneByMobile(String mobile);

    Optional<UserEntity> findOneByUsername(String username);

    Optional<UserEntity> findOneByUsernameAndStatus(String username, UserEntity.StatusType status);

    //  Optional<UserEntity> findOneByUsernameOrMobileOrEmail(String login);

    //  @Query("select u from UserEntity u where u.authorities like %:authorities%")
    //  List<UserEntity> findAllByAuthoritiesIn(List<UserEntity.Authority> authorities);
//  List<UserEntity> findAllByAuthoritiesIn(Set<Authority> authorities);

    @Query("from UserEntity u where u.username =:login or u.mobile =:login or u.email =:login")
    Optional<UserEntity> findByUsernameOrMobileOrEmail(String login);
}