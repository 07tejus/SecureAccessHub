package tj.proj;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uemail = req.getParameter("username");
		String upass = req.getParameter("password");
		HttpSession session = req.getSession();
		RequestDispatcher rd = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proj?useSSL=false", "root", "tejus123");
			PreparedStatement ps = con.prepareStatement("select * from users where uemail = ? and upass = ?");
			ps.setString(1, uemail);
			ps.setString(2, upass);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				rd = req.getRequestDispatcher("/index.jsp");
			}else {
				session.setAttribute("status", "failed");
				rd = req.getRequestDispatcher("/login.jsp");
			}
			rd.forward(req, res);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
