package spring;

public class RegisterRequest {

	private String title;
	private String content;
	private String author;
	private String password;

	public RegisterRequest() {
	};

	public RegisterRequest(String title, String content, String author, String password) {
		super();

		this.title = title;
		this.content = content;
		this.author = author;
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

}
