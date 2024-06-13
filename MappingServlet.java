package website;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/website/login", "/website/register", "/website/contact"})
public class MappingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String jspPath = null;

        switch (path) {
            case "/website/login":
                jspPath = "/website/login.jsp";
                break;
            case "/website/register":
                jspPath = "/website/register.jsp";
                break;
            case "/website/contact":
                jspPath = "/website/contact.jsp";
                break;
            default:
                jspPath = "/website/404.jsp";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
