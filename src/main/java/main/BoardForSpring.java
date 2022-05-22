package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx1;
import config.AppCtx2;
import spring.BoardDeleteService;
import spring.BoardInfoPrinter;
import spring.BoardListPrinter;
import spring.BoardNotFoundException;
import spring.BoardRegisterService;
import spring.ChangeBoardService;
import spring.DuplicateBoardException;
import spring.RegisterRequest;
import spring.WrongIdPasswordException;

public class BoardForSpring {

	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {
		ctx = new AnnotationConfigApplicationContext(AppCtx1.class, AppCtx2.class);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("명령어를 입력하세요:");
			String command = reader.readLine();
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			if (command.startsWith("write")) {
				processNewCommand(command.split(" "));

				continue;
			} else if (command.startsWith("modify")) {
				processChangeCommand(command.split(" "));
				continue;
			} else if (command.startsWith("list")) {
				processListCommand();
				continue;
			} else if (command.startsWith("read")) {
				processInfoCommand(command.split(" "));
				continue;

			} else if (command.startsWith("delete")) {
				processDeleteCommand(command.split(" "));
				continue;
			}
			printHelp();
		}
	}

	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			printHelp();
			return;
		}

		BoardRegisterService regSvc = ctx.getBean("boardRegSvc", BoardRegisterService.class);

		RegisterRequest req = new RegisterRequest();
		req.setTitle(arg[1]);
		req.setContent(arg[2]);
		req.setAuthor(arg[3]);
		req.setPassword(arg[4]);

		try {
			regSvc.regist(req);
			System.out.println("등록했습니다.\n");
		} catch (DuplicateBoardException e) {
			System.out.println("이미 존재하는 title 입니다.\n");
		}
	}

	private static void processChangeCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}
		ChangeBoardService changePwdSvc = ctx.getBean("changePwdSvc", ChangeBoardService.class);

		try {
			changePwdSvc.changeBoard(arg[1], arg[2], arg[3]);
			System.out.println("내용을 변경했습니다.\n");
		} catch (BoardNotFoundException e) {
			System.out.println("존재하지 않는 제목입니다.\n");
		} catch (WrongIdPasswordException e) {
			System.out.println("암호가 일치하지 않습니다.\n");
		}
	}

	public static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다.아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("write 제목 내용 작성자 비밀번호");
		System.out.println("list");
		System.out.println("read 제목 비밀번호");
		System.out.println("modify 제목 수정내용 비밀번호");
		System.out.println("delete 제목 비밀번호");
		System.out.println();
	}

	private static void processListCommand() {
		BoardListPrinter listPrinter = ctx.getBean("listPrinter", BoardListPrinter.class);
		listPrinter.printAll();
	}

	private static void processInfoCommand(String[] arg) {
		if (arg.length != 3) {
			printHelp();
			return;
		}

		try {
			BoardInfoPrinter infoPrinter = ctx.getBean("infoPrinter", BoardInfoPrinter.class);
			infoPrinter.printBoardInfo(arg[1], arg[2]);
		} catch (BoardNotFoundException e) {
			System.out.println("존재하지 않는 제목입니다.\n");
		} catch (WrongIdPasswordException e) {
			System.out.println("암호가 일치하지 않습니다.\n");

		}

	}

	private static void processDeleteCommand(String[] arg) {
		if (arg.length != 3) {
			printHelp();
			return;
		}
		BoardDeleteService deleteSvc = ctx.getBean("deleteSvc", BoardDeleteService.class);

		try {
			deleteSvc.deleteBoard(arg[1], arg[2]);
			System.out.println("내용을 삭제했습니다.\n");
		} catch (BoardNotFoundException e) {
			System.out.println("존재하지 않는 제목입니다.\n");
		} catch (WrongIdPasswordException e) {
			System.out.println("암호가 일치하지 않습니다.\n");

		}

	}

}
