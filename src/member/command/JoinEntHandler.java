package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.*;
import mvc.CommandHandler;

public class JoinEntHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/joinEntForm.jsp";
	private JoinEntService joinEntService = new JoinEntService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) {
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
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		JoinEntRequest joinEntReq = new JoinEntRequest();
		joinEntReq.setId(req.getParameter("id"));
		joinEntReq.setName(req.getParameter("name"));
		joinEntReq.setPassword(req.getParameter("password"));
		joinEntReq.setEmail(req.getParameter("email"));
		joinEntReq.setEntname(req.getParameter("entname"));
		joinEntReq.setConfirmPassword(req.getParameter("confirmPassword"));
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		joinEntReq.validate(errors);
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			joinEntService.join(joinEntReq);
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
}
