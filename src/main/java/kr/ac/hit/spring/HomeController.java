package kr.ac.hit.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	@Autowired
	BasicDataSource dataSource;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.LONG, DateFormat.LONG, locale);
		
		String formettedDate = dateFormat.format(date);
		
		//request.setAttribute("serverTime", formettedDate);
		model.addAttribute("serverTime", formettedDate);		
		
		return "home"; //사용자에게 보여줄 jsp(뷰 경로)
	}
	
	@RequestMapping(value="/dbTest")
	public String dbTest(Model model) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select mem_id,mem_name,mem_email from tb_member where mem_name='test_name'");
			
			while(rs.next()) {
				model.addAttribute("mem_id", rs.getString("mem_id"));
				model.addAttribute("mem_name", rs.getString("mem_name"));
				model.addAttribute("mem_email", rs.getString("mem_email"));
			}
					
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
				if(stmt != null) stmt.close();
				if(rs != null) rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return "dbTest"; //   /WEB-INF/views/dbTest.jsp
	}
	

}
