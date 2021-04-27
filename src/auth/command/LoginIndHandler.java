package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.command.*;
import auth.service.*;
import mvc.CommandHandler;

public class LoginIndHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/loginIndForm.jsp";
	private LoginIndService loginIndService = new LoginIndService();
	
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
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 폼에서 전송한 id, password 파라미터 값을 구한다.
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password"));
		
		// 에러 정보를 담을 맴 객체 생성 후 errors 속성에 저장 
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		// id, password 값이 없는 경우 errors 속성에 저장 
		if (id == null || id.isEmpty())
			errors.put("id", Boolean.TRUE);
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);
		
		// 에러가 존재하면 폼 뷰를 리턴 
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			// 인증 수행 후 로그인에 성공하면 User 객체를 리턴 
			User user = loginIndService.loginInd(id, password);
			// User 객체를 authUser 속성에 저장 
			req.getSession().setAttribute("authUser", user);
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		} catch (LoginFailException e) {
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
			
	}
	
	private String trim(String str) {
		return str == null ? null : str.trim(); 
	}
}
