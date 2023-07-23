package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.entity.board.Board;
import wtchrs.SpringCommunity.entity.board.BoardAdmin;
import wtchrs.SpringCommunity.entity.board.BoardAdminRepository;
import wtchrs.SpringCommunity.entity.board.BoardRepository;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.entity.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardAdminRepository boardAdminRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createBoard(Long userId, String boardName, String description) {
        if (boardRepository.existsByName(boardName)) throw new IllegalStateException("Already exist board name");

        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        Board board = new Board(boardName, user, description);
        boardRepository.save(board);

        return board.getId();
    }


    public Page<Board> getBoards() {
        return getBoards(PageRequest.of(0, 20));
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<Board> getBoardsWithUser(Pageable pageable) {
        return boardRepository.findAllWithUserBy(pageable);
    }

    @Transactional
    public Long addAdmin(Long boardId, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.orElseThrow(() -> new IllegalStateException("Not exist board id"));

        if (boardAdminRepository.existsByBoard_IdAndUser_Id(boardId, userId)) {
            throw new IllegalStateException("Already an board admin");
        }

        BoardAdmin admin = new BoardAdmin(user, board);
        boardAdminRepository.save(admin);

        return admin.getId();
    }

    @Transactional
    public void removeAdmin(Long boardId, Long userId) {
        Optional<BoardAdmin> findAdmin = boardAdminRepository.findByBoard_IdAndUser_Id(boardId, userId);
        BoardAdmin admin = findAdmin.orElseThrow(() -> new IllegalStateException("Not exist board admin"));
        boardAdminRepository.delete(admin);
    }

    public List<BoardAdmin> getAdmins(Long boardId) {
        return boardAdminRepository.findBoardAdminsByBoard_Id(boardId);
    }
}
