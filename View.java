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
 * Servlet implementation class View
 */
@WebServlet("/View")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected static void vv(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	// Set response content type
    	response.setContentType("text/html");
    	
    	
    	PrintWriter out = response.getWriter();
    	
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        
  
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");
        

        PreparedStatement ps = con.prepareStatement("select * from bookdatabase");
        
        ResultSet s = ps.executeQuery();
        
        if (s.next()) {
            
            out.println("<html><head>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 80%; margin: 20px auto; }");
            out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #f5f5f5; }");
            out.println("</style>");
            out.println("</head><body>");
            
            out.println("<h2 style='text-align:center;'>Comic Book Details</h2>");
            out.println("<table>");
            out.println("<tr><th>Name</th><th>Genre</th><th>Author</th><th>Price</th></tr>");
            
            do {
                String cname1 = s.getString("Name");
                String genre = s.getString("Genre");
                String author = s.getString("Author");
                String price = s.getString("Price");
                out.println("<tr><td>" + cname1 + "</td><td>" + genre + "</td><td>" + author + "</td><td>" + price + "</td></tr>");
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
		try {
			vv(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
