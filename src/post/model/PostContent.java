package post.model;

public class PostContent {

	private Integer number;
	private String content;
	
	public PostContent(Integer number, String content) {
		this.number = number;
		this.content = content;
	}

	public Integer getNumber() {
		return number;
	}

	public String getContent() {
		return content;
	}
	
}

