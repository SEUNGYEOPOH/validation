package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ChangeBoardService {

	@Autowired
	private BoardDao boardDao;

	@Transactional
	public void changeBoard(String Title, String Content, String Password) {
		Board board = boardDao.selectByTitle(Title);
		if (board == null)
			throw new BoardNotFoundException();

		board.changeBoard(Content, Password);

		boardDao.update(board);
	}

	public void setMemberDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

}
