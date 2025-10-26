package src;

import java.io.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if(username.equals("admin") && password.equals("1234")) {
            out.println("<h2>Welcome, " + username + "!</h2>");
            out.println("<p>Login Successful.</p>");
        } else {
            out.println("<h3 style='color:red;'>Invalid Username or Password!</h3>");
            out.println("<a href='login.html'>Try Again</a>");
        }
    }
}
