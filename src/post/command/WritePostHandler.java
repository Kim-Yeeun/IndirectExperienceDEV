package post.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import post.model.*;
import post.service.*;
import auth.service.*;
import mvc.CommandHandler;

public class WritePostHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/newPostForm.jsp";
	private WritePostService writePostService = new WritePostService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		// 세션에서 로그인한 사용자 정보를 구한다.
		User user = (User)req.getSession(false).getAttribute("authUser");
		WriteRequest writereq = createWriteRequest(user, req);
		writereq.validate(errors);
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		// 게시글을 등록하고 등록된 게시글의 ID를 리턴받는다.
		int newPostNo = writePostService.write(writereq);
		// 새 글의 ID를 newArticleID 속성에 저장한다.
		// 처리 결과를 보여주는 JSP 파일은 이 속성값을 이용하여 링크를 생성한다.
		req.setAttribute("newPostNo", newPostNo);
		
		return "/WEB-INF/view/newPostSuccess.jsp";
	}
	
	private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
		return new WriteRequest(
				new WriterEnt(user.getId(), user.getName()),
				req.getParameter("title"),
				req.getParameter("content"));
	}
}
