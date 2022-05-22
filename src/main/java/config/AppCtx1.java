package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.BoardDao;
import spring.BoardPrinter;

@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({ AppCtx2.class })

@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("core.*")
public class AppCtx1 {

	@Bean
	public BoardPrinter boardPrinter() {
		return new BoardPrinter();
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource(); // DataSource 객체 생성
		ds.setDriverClassName("com.mysql.jdbc.Driver"); // JDBC 드라이버 클래스를 지정 (MySQL 사용)
		ds.setUrl("jdbc:mysql://localhost:3307/spring5test?characterEncoding=utf8"); // JDBC URL을 지정
		ds.setUsername("spring"); // 연결 계정과 암호 지정
		ds.setPassword("spring");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}

	@Bean
	public BoardDao boardDao() {
		return new BoardDao(dataSource());
	}
	// @Bean
	// public ExeTimeAspect exeTimeAspect() {
	// return new ExeTimeAspect();
	// }

	// @Bean
	// public Calculator calculator() {
	// return new RecCalculator();
	// }

	// @Bean
	// public CacheAspect cacheAspect() {
	// return new CacheAspect();
	// }

}
