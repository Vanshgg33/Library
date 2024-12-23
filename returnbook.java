package Library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class returnbook
 */
@WebServlet("/returnbook")
public class returnbook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public returnbook() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   protected void rbook(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,SQLException, ClassNotFoundException {
	   PrintWriter out = response.getWriter();
	   Class.forName("com.mysql.cj.jdbc.Driver");
	   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");
	   Cookie ck[] = request.getCookies();
	String book =    request.getParameter("bookName");
	   String n = ck[0].getValue();
	   String query = "DELETE FROM " + n + " WHERE books = ?";
       try (PreparedStatement ps = con.prepareStatement(query)) {
           ps.setString(1, book);

      
     int a =   ps.executeUpdate();
       if(a > 0) {
    	   response.sendRedirect("ProfileVeiw");
       }
       else {
    	   response.sendRedirect("Erorr.html");
       }
       }
       catch (SQLException e) {
           e.printStackTrace();
           out.println("<h3>Error processing request.</h3>");
       } finally {
    	   
           con.close();
       }
           // Close PrintWriter
       
      
   }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		 try {
			 rbook(request, response);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
