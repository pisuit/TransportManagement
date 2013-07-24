package transport.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ssoclient.InvalidLoginException;
import ssoclient.SSOauthen;
import transport.customtype.Role;
import transport.manager.LoginManager;
import transport.utils.SessionUtils;

/**
 * Servlet implementation class SSOservlet
 */
@WebServlet("/SSOservlet")
public class SSOservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SSOservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		String encodedkey = request.getParameter("ss");
		SSOauthen ssOauthen = new SSOauthen();
				
		try {
			String result = ssOauthen.getEmployeeCode(encodedkey,request.getSession().getId());
//			String result = "384161";
//			System.out.println("Result : " + result);
			
			if(!result.equals("false")){
				LoginManager loginManager = new LoginManager(StringUtils.leftPad(result, 6, '0'));
				loginManager.loginUser(request.getSession());
				response.sendRedirect(response.encodeRedirectURL("pages/request.jsf"));
			}
			
//		} catch (InvalidLoginException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
