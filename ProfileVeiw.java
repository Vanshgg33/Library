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

/**
 * Servlet implementation class issueveiw
 */
@WebServlet("/ProfileVeiw")
public class ProfileVeiw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileVeiw() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected static void vv(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
    	// Set response content type
    	response.setContentType("text/html");
    	
    	
    	PrintWriter out = response.getWriter();
    	
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        
    //   Cookie[] cookies = request.getCookies();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "VANSH");
        Cookie ck[] = request.getCookies();
       String n = ck[0].getValue();
String query = ("select * from " + n);
        PreparedStatement ps = con.prepareStatement(query);
       //System.out.println(query);
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
            out.println("<h2> Welcome" + n + " Profile : ");
            out.println("<h2 style='text-align:center;'>Issued  Book Details</h2>");
            out.println("<table>");
            out.println("<tr><th>NameOfBook</th><th>Date Issued</th><th>Price</th></tr>");
            
         
            
            do {
                String book = s.getString("books");
                String name = s.getString("Date");
               String price = s.getString("Price");
                
                out.println("<tr><td>" + book + "</td><td>" + name + "</td><td>" + price + "</td>" );
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
	            vv(request, response);
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
