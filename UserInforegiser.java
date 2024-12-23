package Library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserInforegiser")
public class UserInforegiser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserInforegiser() {
        super();
    }

    protected static void regis(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException {
        PrintWriter out = response.getWriter();
      
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH")) {

            String name = request.getParameter("name");
            String pass = request.getParameter("password");
            String email = request.getParameter("email");

            PreparedStatement ps = con.prepareStatement("INSERT INTO UserInfo (Username, Password, Email) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, email);

            int result = ps.executeUpdate();
            
            String sql = "CREATE TABLE " + name + " ("
                    + "    Books VARCHAR(40),"
                    + "    Date DATE,"
                    + "    Price varchar(40)"
                    + ");";

            Statement st = con.prepareStatement(sql);
            int r = st.executeUpdate(sql);

            if (result > 0 && r >= 0) {
                response.sendRedirect("LibraryMain.html");
            } else {
                out.println("<body><p>Failed to register user. Please try again.</p></body>");
            }

            ps.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection or SQL query failed.");
        }	
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            regis(request, response);
        } catch (ClassNotFoundException | SQLException | IOException | ServletException e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred. Please try again.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
