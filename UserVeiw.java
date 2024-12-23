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
 * Servlet implementation class UserVeiw
 */
@WebServlet("/UserVeiw")
public class UserVeiw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserVeiw() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void veiw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ,ClassNotFoundException,SQLException{
    	response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");
String name =  request.getParameter("userName");

String query = ("select * from " + name);
PreparedStatement ps = con.prepareStatement(query);
response.sendRedirect(query);
ResultSet s = ps.executeQuery(query);

if (s.next()) {    
    out.println("<html><head>");
    out.println("<style>");
    
    out.println("table { border-collapse: collapse; width: 80%; margin: 20px auto; }");
    out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
    out.println("th { background-color: #f2f2f2; }");
    out.println("tr:hover { background-color: #f5f5f5; }");
  
    out.println("</style>");
    out.println("</head><body>");
   out.println("<h1> Welcome " + name + "</h1>");
   out.println("<br><br>");
    out.println("<h2 style='text-align:center;'>Issued  Book Details</h2>");
    out.println("<table>");
    out.println("<tr><th>NameOfBook</th><th>Date Issued</th></tr>");
    
    
    
    do {
        String book = s.getString("books");
        String date = s.getString("Date");
       // String date = s.getString("Price");
        
        out.println("<tr><td>" + book + "</td><td>" + date + "</td>");
    } while (s.next());
    
    out.println("</table>");
    out.println("</body></html>");
} else {
    out.println("<p style='text-align:center;'>No comics found in the database.</p>");
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
	            veiw(request, response);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            response.getWriter().println("Database connection or SQL query failed.");
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
