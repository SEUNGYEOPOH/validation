package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import spring.BoardDao;
import spring.BoardDeleteService;
import spring.BoardInfoPrinter;
import spring.BoardListPrinter;
import spring.BoardPrinter;
import spring.BoardRegisterService;
import spring.ChangeBoardService;

@EnableAspectJAutoProxy
@ComponentScan("core.*")
@Configuration
public class AppCtx2 {

	@Autowired
	private BoardDao boardDao;

	@Bean
	public BoardRegisterService boardRegSvc() {
		return new BoardRegisterService();
	}

	@Bean
	public ChangeBoardService changePwdSvc() {
		ChangeBoardService pwdSvc = new ChangeBoardService();
		pwdSvc.setMemberDao(boardDao);
		return pwdSvc;
	}

	@Autowired
	private BoardPrinter boardPrinter;

	@Bean
	public BoardListPrinter listPrinter() {
		return new BoardListPrinter();
	}

	@Bean
	public BoardInfoPrinter infoPrinter() {
		BoardInfoPrinter infoPrinter = new BoardInfoPrinter();
		infoPrinter.setBoardDao(boardDao);
		infoPrinter.setPrinter(boardPrinter);
		return infoPrinter;
	}

	@Bean
	public BoardDeleteService deleteSvc() {
		BoardDeleteService deleteSvc = new BoardDeleteService();
		deleteSvc.setMemberDao(boardDao);
		return deleteSvc;
	}

}
