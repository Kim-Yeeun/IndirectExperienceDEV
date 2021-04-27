package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.*;
import mvc.CommandHandler;

public class JoinIndHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/joinIndForm.jsp";
	private JoinIndService joinIndService = new JoinIndService();
	
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
		JoinIndRequest joinIndReq = new JoinIndRequest();
		joinIndReq.setId(req.getParameter("id"));
		joinIndReq.setName(req.getParameter("name"));
		joinIndReq.setPassword(req.getParameter("password"));
		joinIndReq.setEmail(req.getParameter("email"));
		joinIndReq.setConfirmPassword(req.getParameter("confirmPassword"));
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		joinIndReq.validate(errors);
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			joinIndService.join(joinIndReq);
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
}
