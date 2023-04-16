package wtchrs.SpringCommunity.common.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardAdminRepository extends JpaRepository<BoardAdmin, Long> {

    List<BoardAdmin> findBoardAdminsByBoard_Id(Long boardId);

    Optional<BoardAdmin> findByBoard_IdAndUser_Id(Long boardId, Long userId);

    boolean existsByBoard_IdAndUser_Id(Long boardId, Long userId);
}
