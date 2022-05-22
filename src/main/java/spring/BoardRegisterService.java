package spring;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardRegisterService {
	@Autowired
	private BoardDao boardDao;

	public BoardRegisterService() {

	}

	public Long regist(RegisterRequest req) {

		Board board = boardDao.selectByTitle(req.getTitle());

		if (board != null) {
			throw new DuplicateBoardException("dup Title " + req.getTitle());
		}

		Board newBoard = new Board(req.getTitle(), req.getContent(), req.getAuthor(), req.getPassword(),
				LocalDateTime.now());

		boardDao.insert(newBoard);

		return newBoard.getId();
	}
}
