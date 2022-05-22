package spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardListPrinter {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private BoardPrinter printer;

	public BoardListPrinter() {
	}

	public void printAll() {
		Collection<Board> boards = boardDao.selectAll();
		boards.forEach(m -> printer.print(m));
	}
}
