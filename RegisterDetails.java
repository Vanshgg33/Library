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

/**
 * Servlet implementation class RegisterDetails
 */
@WebServlet("/RegisterDetails")
public class RegisterDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterDetails() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected static void regis(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException,ServletException {
                  PrintWriter out =  response.getWriter();
		 Class.forName("com.mysql.cj.jdbc.Driver");
	    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","VANSH");
	    	
	    	String name =  request.getParameter("fname");
	    	String pass =  request.getParameter("pass");
	    	String phoneNo = request.getParameter("Phone");
	    	
	    	PreparedStatement ps = con.prepareStatement("INSERT INTO LoginInfo (Name, pass, phoneNo) VALUES (?, ?, ?)");
	    	
	    	ps.setString(1,name);
	    	ps.setString(2, pass);
	    	ps.setString(3, phoneNo);
	    	
	    int result = ps.executeUpdate();
	 	    if(result>0) {
	    	out.println("<body><p> Registration Successful"
	    			+ "<a href = 'LibrarianLogin.html'>LoginPage</a> "
	    			+ "</body>");
	    	}
	    else {
	    	out.println("<body> <p> Failed"
	    			+ "</body>");
	    	
	    }
	    con.close();
	    ps.close();
	    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		
		 try {
	            regis(request, response);
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
