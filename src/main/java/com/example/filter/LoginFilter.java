package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		// 將 ServletRequest 轉為 HttpServletRequest 才能使用 getSession 等方法
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 取得請求的 URI，例如 /eshop/cart、/login
        String uri = request.getRequestURI();

        // 從 Session 中取得登入資訊（currentCustomer 是你登入後存入的 key）
        HttpSession session = request.getSession();
        Object currentCustomer = session.getAttribute("currentCustomer");

        // ✅ 不需要驗證登入的路徑（放行）
        boolean isExcluded = uri.endsWith("index.jsp")           // ✅ 精準 index.jsp
		        	      || uri.endsWith("/")                   // ✅ 像 /eshop/ 根路徑
		        	      || uri.contains("/login")
		        	      || uri.contains("/needLogin.jsp")
		        	      || uri.contains("/register")
		        	      || uri.contains("/public/")
		        	      || uri.endsWith(".css") || uri.endsWith(".js")
		        	      || uri.endsWith(".png") || uri.endsWith(".jpg");

        // ✅ 需要登入驗證的路徑（你可以依需求再加）
        boolean isProtected = uri.contains("/eshop") || uri.contains("/cart") || uri.contains("/order");

        // ❗ 若請求的是保護頁 + 沒登入 + 又不是例外 ➜ 導向提示頁
        if (isProtected && !isExcluded && currentCustomer == null) {
            response.sendRedirect(request.getContextPath() + "/public/needLogin.jsp");
            return;
        }

        // ✅ 通過驗證，放行
        chain.doFilter(req, res);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
