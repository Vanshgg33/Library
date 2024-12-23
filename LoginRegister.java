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
 * Servlet implementation class LoginRegister
 */
@WebServlet("/LoginRegister")
public class LoginRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginRegister() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void verify(HttpServletRequest request, HttpServletResponse response)throws ClassNotFoundException, SQLException, IOException {
    	PrintWriter out = response.getWriter();
    	 Class.forName("com.mysql.cj.jdbc.Driver");
	    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","VANSH");
	    String uname =  request.getParameter("fname");
	    String password = request.getParameter("pass");
	    PreparedStatement ps = con.prepareStatement("SELECT * FROM LoginInfo WHERE Name LIKE ? AND pass LIKE ?");
	    ps.setString(1, uname);
	    ps.setString(2, password);
	    Cookie c1 = new Cookie("uname",uname);
	    Cookie c2 = new Cookie("pass",password);
	    response.addCookie(c1);
	    response.addCookie(c2);
	   
	    ResultSet s = ps.executeQuery();
	    if(s.next()) {
	    	response.sendRedirect("ViewEdit.html");
	    }
    
	    
	    else {
	    	out.println("Invalid user");
	    }
	
		out.close();
	
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 try {
	            verify(request, response);
	        } catch (ClassNotFoundException | SQLException | IOException e) {
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
