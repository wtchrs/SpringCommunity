package wtchrs.SpringCommunity.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.common.entity.Board;
import wtchrs.SpringCommunity.common.entity.BoardAdmin;
import wtchrs.SpringCommunity.common.entity.User;
import wtchrs.SpringCommunity.common.repository.BoardAdminRepository;
import wtchrs.SpringCommunity.common.repository.BoardRepository;
import wtchrs.SpringCommunity.common.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardAdminRepository boardAdminRepository;
    private final UserRepository userRepository;

    /**
     * @param userId    creator.
     * @param boardName board name.
     * @return pk value of the board instance.
     * @throws IllegalStateException when the user id passed is not exist or the board name already exists.
     */
    @Transactional
    public Long createBoard(Long userId, String boardName) {
        if (boardRepository.existsByName(boardName)) throw new IllegalStateException("Already exist board name");

        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        Board board = new Board(boardName, user);
        boardRepository.save(board);

        return board.getId();
    }


    /**
     * @return first page of all board.
     */
    public Page<Board> getBoards() {
        return getBoards(PageRequest.of(0, 20));
    }

    /**
     * @param pageable page info to find.
     * @return page of all board.
     */
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<Board> getBoardsWithUser(Pageable pageable) {
        return boardRepository.findAllWithUserBy(pageable);
    }

    /**
     * @param boardId pk value of board.
     * @param userId  pk value of user.
     * @return pk value of BoardAdmin.
     * @throws IllegalStateException when {@code boardId} or {@code userId} do not exist.
     */
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

    /**
     * @param boardId pk value of board.
     * @param userId  pk value of user.
     * @throws IllegalStateException when not exist board admin.
     */
    @Transactional
    public void removeAdmin(Long boardId, Long userId) {
        Optional<BoardAdmin> findAdmin = boardAdminRepository.findByBoard_IdAndUser_Id(boardId, userId);
        BoardAdmin admin = findAdmin.orElseThrow(() -> new IllegalStateException("Not exist board admin"));
        boardAdminRepository.delete(admin);
    }

    /**
     * @param boardId pk value of board.
     * @return list of {@link BoardAdmin}. It can be an empty list when there is no admin of the board or there is no
     * board of the pk value.
     */
    public List<BoardAdmin> getAdmins(Long boardId) {
        return boardAdminRepository.findBoardAdminsByBoard_Id(boardId);
    }
}
