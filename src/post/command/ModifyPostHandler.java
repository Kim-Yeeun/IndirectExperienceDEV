package post.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import post.dao.*;
import post.model.*;
import post.service.*;
import auth.service.*;
import mvc.CommandHandler;


public class ModifyPostHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/modifyForm.jsp";
	
	private ReadPostService readService = new ReadPostService();
	private ModifyPostService modifyService = new ModifyPostService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	// 폼 요청을 처리 
	private String processForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			
			// 폼에 보여줄 게시글을 구한다.
			PostData postData = readService.getPost(no, false);
			
			// 현재 로그인한 사용자 정보를 구한다.
			User authUser = (User) req.getSession().getAttribute("authUser");
			if (!canModify(authUser, postData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			// 폼에 데이터를 보여줄 때 사용할 객체를 생성하고 속성에 저장한다.
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, postData.getPost().getTitle(), postData.getContent().getContent());
			
			req.setAttribute("modReq", modReq);
			return FORM_VIEW;
		} catch (PostNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	private boolean canModify(User authUser, PostData postData) {
		String writeUser = postData.getPost().getWriterEnt().getId();
		return authUser.getId().equals(writeUser);
	}
	
	// 폼 전송을 처리 
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 게시글 수정을 요청한 사용자 정보를 구한다.
		User authUser = (User) req.getSession().getAttribute("authUser");
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		// 요청 파라미터와 현재 사용자 정보를 이용해서 객체를 생성한다.
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, req.getParameter("title"), req.getParameter("content"));
		req.setAttribute("modReq", modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		try {
			modifyService.modify(modReq);
			return "/WEB-INF/view/modifySuccess.jsp";
		} catch (PostNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
}
