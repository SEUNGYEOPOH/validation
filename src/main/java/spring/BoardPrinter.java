package spring;

public class BoardPrinter {

	public void print(Board board) {
		System.out.printf("-----게시글 정보-----\n아이디 = %d \n제목 = %s\n내용 = %s\n저자 = %s\n등록일= %tF %tT \n----------------\n",
				board.getId(), board.getTitle(), board.getContent(), board.getAuthor(), board.getCreatedAt(),
				board.getCreatedAt());

	}

}