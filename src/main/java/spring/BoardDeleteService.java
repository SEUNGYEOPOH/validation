package spring;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardDeleteService {

	@Autowired
	private BoardDao boardDao;

	public void deleteBoard(String title, String password) {
		Board board = boardDao.selectByTitle(title);
		board.deleteBoard(title, password);
		boardDao.delete(board);

		// TODO Auto-generated method stub

	}

	public void setMemberDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

}