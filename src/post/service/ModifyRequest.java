package post.service;

import java.util.Map;

public class ModifyRequest {

	private String userId;
	private int postNumber;
	private String title;
	private String content;
	
	public ModifyRequest(String userId, int postNumber, String title, String content) {
		this.userId = userId;
		this.postNumber = postNumber;
		this.title = title;
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public int getPostNumber() {
		return postNumber;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public void validate(Map<String, Boolean> errors) {
		if (title == null || title.trim().isEmpty()) {
			errors.put("title", Boolean.TRUE);
		}
	}
}
