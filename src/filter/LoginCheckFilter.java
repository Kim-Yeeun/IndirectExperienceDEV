package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		// 세션이 존재하지 않거나 "authUser" 속성이 없으면 login.do로 리다이렉트한다.
		if (session == null || session.getAttribute("authUser") == null) {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(request.getContextPath() + "/loginInd.do");
		} else {
			chain.doFilter(req, res);
		}
	}
	
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	public void destroy() {
		
	}
}
