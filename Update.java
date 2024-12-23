package Library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Update")
public class Update extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Update() {
        super();
    }

    protected static void Function(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, SQLException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String field = request.getParameter("fname");
        String upname = request.getParameter("up");
        String before = request.getParameter("down");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");

        String query = null;
        switch (field) {
            case "Name":
                query = "UPDATE bookdatabase SET Name = ? WHERE Name = ?";
                break;
            case "GenreOfthebook":
                query = "UPDATE bookdatabase SET Genre = ? WHERE Name = ?";
                break;
            case "AuthorName":
                query = "UPDATE bookdatabase SET Author = ? WHERE Name = ?";
                break;
            case "Price":
                query = "UPDATE bookdatabase SET Price = ? WHERE Name = ?";
                break;
            default:
                out.println("Wrong Input");
                return;
        }

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, upname);
            ps.setString(2, before);
            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("View");
            } else {
                out.println("Update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Function(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}