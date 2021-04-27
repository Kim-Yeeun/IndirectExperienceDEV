package post.service;

import post.model.*;

public class PostData {

	private Post post;
	private PostContent content;
	
	public PostData(Post post, PostContent content) {
		this.post = post;
		this.content = content;
	}

	public Post getPost() {
		return post;
	}

	public PostContent getContent() {
		return content;
	}
	
	
}
