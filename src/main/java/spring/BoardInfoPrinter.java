package spring;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardInfoPrinter {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private BoardPrinter printer;

	public void printBoardInfo(String title, String password) {
		Board board = boardDao.selectByTitle(title);
		// Board board2 = boardDao.selectByPassword(password);
		if (board == null) {
			System.out.println("데이터 없음\n");
			return;
		} else if (!password.equals(password)) {
			throw new WrongIdPasswordException();
		}
		;
		printer.print(board);
		System.out.println();
	}

	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Autowired
	public void setPrinter(BoardPrinter printer) {
		this.printer = printer;
	}
}
