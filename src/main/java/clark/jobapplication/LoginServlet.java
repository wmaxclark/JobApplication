package clark.jobapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Home
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
  
    private static final Map<String, String> userDatabase = new Hashtable<>();

    public LoginServlet() {
        userDatabase.put("Marc", "password");
        userDatabase.put("Amy", "password");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("logout") != null) {
            session.invalidate();
            response.sendRedirect("login");
            return;
        } else if (session.getAttribute("username") != null) {
            response.sendRedirect("applications");
            return;
        }
        request.setAttribute("loginFailed", false);
        request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            response.sendRedirect("applications");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null || 
                !LoginServlet.userDatabase.containsKey(username) || 
                !password.equals(LoginServlet.userDatabase.get(username))) {
            request.setAttribute("loginFailed", true);
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
        } else {
            session.setAttribute("username", username);
            request.changeSessionId();
            response.sendRedirect("applications");
        }
    }

}
