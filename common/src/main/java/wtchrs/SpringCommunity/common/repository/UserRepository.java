package wtchrs.SpringCommunity.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.SpringCommunity.common.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
