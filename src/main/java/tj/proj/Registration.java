package tj.proj;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String upass = req.getParameter("pass");
		String umobile = req.getParameter("contact");
		RequestDispatcher rd = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proj?useSSL=false", "root", "tejus123");
			PreparedStatement ps = con.prepareStatement("insert into users (uname,upass,uemail,umobile) values (?,?,?,?)");
			ps.setString(1, uname);
			ps.setString(2, upass);
			ps.setString(3, uemail);
			ps.setString(4, umobile);
			
			int cnt = ps.executeUpdate();
			
			if(cnt > 0) {
				req.setAttribute("status", "success");
				rd = req.getRequestDispatcher("/registration.jsp");
			}else {
				req.setAttribute("status", "failed");
				rd = req.getRequestDispatcher("/registration.jsp");
			}
			rd.forward(req, res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
