package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class BoardDao {
	private JdbcTemplate jdbcTemplate;

	private static long nextId = 0;

	private Map<String, Board> map = new HashMap<>();

	public BoardDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public class BoardRowMapper implements RowMapper<Board> {
		@Override
		public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
			Board board = new Board(rs.getString("title"), rs.getString("content"), rs.getString("author"),
					rs.getString("password"), rs.getTimestamp("createdAt").toLocalDateTime());
			board.setId(rs.getLong("id"));
			return board;
		}
	}

	public Board selectByTitle(String title) {
		List<Board> results = jdbcTemplate.query("select * from	board where	title = ?", new BoardRowMapper(), title); // 물음표
																														// 개수만큼
																														// 해당되는
																														// 값
																														// 전달

		return results.isEmpty() ? null : results.get(0);
	}

	public Board selectByPassword(String password) {
		return map.get(password);
	}

	public void insert(Board board) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//			파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
				PreparedStatement pstmt = con.prepareStatement(
						"insert	into	Board	(title,	content, author,	password, createdAt)	"
								+ "values	(?, ?, ?, ?, ?)",
						new String[] { "ID" });
//			인덱스 파라미터 값 설정
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getAuthor());
				pstmt.setString(4, board.getPassword());
				pstmt.setTimestamp(5, Timestamp.valueOf(board.getCreatedAt()));
//			생성한 PreparedStatement 객체 리턴
				return pstmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		board.setId(keyValue.longValue());
	}

	public void update(Board board) {
		jdbcTemplate.update("update	Board	set	author	=	?,	password	=	?	where	title	=	?",
				board.getAuthor(), board.getPassword(), board.getTitle());
	}

	public void delete(Board board) {
		map.remove(board.getTitle(), board);
	}

	public List<Board> selectAll() {
		List<Board> results = jdbcTemplate.query("select * from board", new RowMapper<Board>() {
			@Override
			public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
				Board board = new Board(rs.getString("title"), rs.getString("content"), rs.getString("author"),
						rs.getString("password"), rs.getTimestamp("createdAt").toLocalDateTime());
				board.setId(rs.getLong("id"));
				return board;
			}
		});

		return results;
	}

	public int count() {
		Integer count = jdbcTemplate.queryForObject("select	count(*) from board", Integer.class);
		return count;
	}

}
