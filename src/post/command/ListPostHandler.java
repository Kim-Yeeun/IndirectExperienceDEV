package post.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import post.service.*;
import mvc.CommandHandler;

public class ListPostHandler implements CommandHandler {

	private ListPostService listService = new ListPostService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		PostPage postPage = listService.getPostPage(pageNo);
		req.setAttribute("postPage", postPage);
		return "/WEB-INF/view/listPost.jsp";
	}
}
