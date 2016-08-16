import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ppl.Module.RouterMapConfig;
import org.ppl.common.CookieAction;
import org.ppl.common.PorG;
import org.ppl.common.SessionAction;
import org.ppl.common.ShowMessage;

@WebServlet(value = "/")
public class Index extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mutex = "";

	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("init http");
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		synchronized (mutex) {
			PorG porg = PorG.getInstance();
			porg.Init(req, res);
			porg.setContext_Path(req.getContextPath());
			porg.setMehtod(req.getMethod());

			ShowMessage sm = ShowMessage.getInstance();
			sm.Init(req, res);

			HttpSession session = req.getSession(true);
			SessionAction sa = SessionAction.getInstance();
			sa.init(session);

			CookieAction ca = CookieAction.getInstance();
			ca.init(req, res);

			RouterMapConfig rmc = new RouterMapConfig();

			rmc.map(req.getServletPath());
			rmc.setMehtod(req.getMethod());

			rmc.match();

			if (rmc.routing()) {
				res.getWriter().println("not class <br /> 404");
			} else {
				res.setContentType(rmc.setContentType());

				res.getWriter().println(rmc.getHtml());
			}
		}
		//SystemInfo(req, res);
	}

	private void SystemInfo(HttpServletRequest req, HttpServletResponse res) {
		PrintWriter out = null;
		try {
			out = res.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// out.println(getInitParameter("foo"));
		// out.println(getInitParameter("bar"));
		out.println(req.getParameter("id") + "<br />");
		System.out.println("id:" + req.getParameter("id"));

		// System.out.println("addr:" +req.getr);

		String url = req.getRequestURL().toString();
		String uri = req.getRequestURI();

		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int portNumber = req.getServerPort();
		String contextPath = req.getContextPath();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String query = req.getQueryString();
		String mehtod = req.getMethod();
		String ip = req.getRemoteHost();

		res.setContentType("text/html");

		out.print("Url: " + url + "<br />");
		out.print("Uri: " + uri + "<br />");
		out.print("Scheme: " + scheme + "<br />");
		out.print("Server Name: " + serverName + "<br />");
		out.print("Port: " + portNumber + "<br />");
		out.print("Context Path: " + contextPath + "<br />");
		out.print("Servlet Path: " + servletPath + "<br />");
		out.print("Path Info: " + pathInfo + "<br />");
		out.print("mehtod: " + mehtod + "<br />");
		out.print("Query: " + query);
		out.print("ip: " + ip);

		out.close();
	}

	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("destroy ");
	}
}
