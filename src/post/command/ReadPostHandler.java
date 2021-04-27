package post.command;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import post.service.*;
import mvc.CommandHandler;

public class ReadPostHandler implements CommandHandler {

	private ReadPostService readService = new ReadPostService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("no");
		int postNum = Integer.parseInt(noVal);
		try {
			PostData postData = readService.getPost(postNum, true);
			req.setAttribute("postData", postData);
			return "/WEB-INF/view/readPost.jsp";
		} catch (PostNotFoundException | PostContentNotFoundException e) {
			req.getServletContext().log("no article", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
