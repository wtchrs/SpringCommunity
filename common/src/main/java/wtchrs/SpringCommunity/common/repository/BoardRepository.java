package wtchrs.SpringCommunity.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.SpringCommunity.common.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByName(String name);

    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"creator"})
    Page<Board> findAllWithUserBy(Pageable pageable);
}
