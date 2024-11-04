package app.helipay.me.repository;

import app.helipay.me.domain.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
//    Optional<MatchEntity> findByUserFromAndUserTo(long userFrom, long userTo);

//    @Query("select u from matchEntity u where u.id NOT IN " +
//            "(SELECT m.userToId FROM MatchEntity m WHERE m.userFromId = :userFrom)")
//    List<MatchEntity> findUsersNotMatchedBy(@Param("userFrom") long userFrom);
//
//
//    @Query("SELECT u.id FROM UserEntity u WHERE u.city = :city AND u.id != :userFrom " +
//            "AND u.id NOT IN (SELECT m.userToId FROM MatchEntity m WHERE m.userFromId = :userFrom)")
//    List<Long> findPotentialUserIdsByLocation(@Param("userFrom") long userFrom, @Param("city") String city);

}
