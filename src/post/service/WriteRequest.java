package post.service;

import java.util.Map;

import post.model.*;

public class WriteRequest {

	private WriterEnt writerEnt;
	private String title;
	private String content;
	
	public WriteRequest(WriterEnt writerEnt, String title, String content) {
		this.writerEnt = writerEnt;
		this.title = title;
		this.content = content;
	}

	public WriterEnt getWriterEnt() {
		return writerEnt;
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
