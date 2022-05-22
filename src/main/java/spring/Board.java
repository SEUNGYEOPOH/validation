package spring;

import java.time.LocalDateTime;

public class Board {
	private Long id;
	private String title;
	private String content;
	private String author;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Board(String title, String content, String author, String password, LocalDateTime createdAt) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.password = password;
		this.createdAt = createdAt;
	}

	void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	void setTilte(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void changeBoard(String newcontent, String Password) {
		if (!password.equals(Password))
			throw new WrongIdPasswordException();
		this.content = newcontent;
		this.createdAt = LocalDateTime.now();
	}

	public void deleteBoard(String Title, String Password) {
		if (!password.equals(Password))
			throw new WrongIdPasswordException();
		this.content = Title;
		this.password = Password;
	}
}
