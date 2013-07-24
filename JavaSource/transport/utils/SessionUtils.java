package transport.utils;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import transport.customtype.Role;
import transport.model.PersonalInfo;
import transport.session.UserSession;


public class SessionUtils {
	
	public static UserSession getUserFromSession(){
		UserSession userSession = (UserSession) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userSession");
//		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		HttpSession session = request.getSession();
//		UserSession userSession = (UserSession) session.getAttribute("userSession");
		return userSession;
	}
	
	public static void putUserToSession(PersonalInfo person, List<Role> roleList, HttpSession session){
		UserSession userSession = new UserSession();		
		userSession.setStaff(person);
		userSession.setRoleList(roleList);
		
		session.setAttribute("userSession", userSession);
	}
}
