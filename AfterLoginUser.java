package Library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("AfterLoginUser")
public class AfterLoginUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AfterLoginUser() {
        super();
    }

    protected static void verify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        PreparedStatement ps = con.prepareStatement("SELECT * FROM userinfo WHERE Username = ? AND Password = ?");
        ps.setString(1, uname);
        ps.setString(2, password);
       
        Cookie c1 = new Cookie("uname", uname);
        Cookie c2 = new Cookie("pass", password);
        response.addCookie(c1);
        response.addCookie(c2);
        
        
        ResultSet s = ps.executeQuery();
        if (s.next()) {
            response.sendRedirect("Useroption.html");
        } else {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("body {");
            
            out.println("    font-family: Arial, sans-serif;");
            out.println("    background-color: #f0f0f0;");
            out.println("    display: flex;");
            out.println("    align-items: center;");
            out.println("    justify-content: center;");
            out.println("    height: 100vh;");
            out.println("    margin: 0;");
            out.println("}");
            out.println(".container {");
            out.println("    background-color: #ffffff;");
            out.println("    padding: 20px;");
            out.println("    border-radius: 8px;");
            out.println("    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);");
            out.println("    max-width: 400px;");
            out.println("    text-align: center;");
            out.println("}");
            out.println("h2 { color: #333333; }");
            out.println(".error { color: #ff0000; font-weight: bold; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Login Failed</h2>");
            out.println("<h1> " + uname + "</h1>");
            out.println("<p class='error'>Invalid username or password.</p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        

        out.close();
        con.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            verify(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection or SQL query failed.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
