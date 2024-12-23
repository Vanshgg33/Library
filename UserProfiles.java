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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserProfiles
 */
@WebServlet("/UserProfiles")
public class UserProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfiles() {
        super();
        // TODO Auto-generated constructor stub
    }
protected void pp(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,SQLException,ClassNotFoundException {
	 response.setContentType("text/html");

     PrintWriter out = response.getWriter();

     Class.forName("com.mysql.cj.jdbc.Driver");

     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");

     PreparedStatement ps = con.prepareStatement("select * from userinfo");

     ResultSet s = ps.executeQuery();
    

     if (s.next()) {
        // Cookie ck[] = request.getCookies();
         out.println("<html><head>");
         out.println("<style>");
         out.println("body { background-color: #f9f9f9; font-family: Arial, sans-serif; }");
         out.println("table { border-collapse: collapse; width: 80%; margin: 20px auto; }");
         out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
         out.println("th { background-color: #f2f2f2; }");
         out.println("tr:hover { background-color: #f5f5f5; }");
         out.println("</style>");
         out.println("</head><body>");
       
         out.println("<h2 style='text-align:center;'>All User Details</h2>");
         out.println("<table>");
         out.println("<tr><th>UserName</th><th>Password</th><th>Email</th></tr>");

         do {
             String cname1 = s.getString("Username");
             String pass = s.getString("Password");
             String email = s.getString("Email");
         
             out.println("<tr>");
             out.println("<td>" + cname1 + "</td>");
             out.println("<td>" + pass + "</td>");
             out.println("<td>" + email + "</td>");
             
             out.println("</tr>");

         } while (s.next());

         out.println("</table>");

             out.println("<form action=\"UserVeiw\" method=\"get\" style='text-align:center;'>");
         out.println("<label for='userName'>Which user's profile to veiw:</label>");
         out.println("<input type='text' name='userName' id='userName' required>");
         out.println("<input type=\"submit\" value=\"select user\">");
         out.println("</form>");

         out.println("</body></html>");
     } else {
         out.println("<p style='text-align:center;'>No books to issue !</p>");
     }

     // Close resources
     s.close();
     ps.close();
     con.close();
 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		  try {
	            pp(request, response);
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
