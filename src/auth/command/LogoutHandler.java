package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.CommandHandler;

public class LogoutHandler implements CommandHandler {

	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false);
		
		// 세션이 존재하면 세션을 종료 -> "authUser" 속성도 함께 삭제되어 로그아웃 처리 
		if (session != null) {
			session.invalidate();
		}
		res.sendRedirect(req.getContextPath() + "/index.jsp");
		return null;
	}
}
