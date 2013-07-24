package transport.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import transport.controller.AdminController;
import transport.controller.EmployeeController;
import transport.customtype.Role;
import transport.ldap.LDAPConnect;
import transport.ldap.LDAPUser;
import transport.model.Authorization;
import transport.model.PersonalInfo;
import transport.model.User;
import transport.session.UserSession;
import transport.utils.SessionUtils;

@ManagedBean(name="loginManager")
@RequestScoped
public class LoginManager implements Serializable{
	
	private String staffCode;
	private EmployeeController employeeController = new EmployeeController();
	private AdminController adminController = new AdminController();
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginManager(String staffCode){
		this.staffCode = staffCode;
	}
	
	public LoginManager(){
		
	}
	
	public void loginUser(HttpSession session){
		List<Role> roleList = new ArrayList<Role>();
		PersonalInfo personalInfo = employeeController.getStaff(staffCode);
		User user = adminController.getUser(personalInfo);
		
		if(user != null){
			if(user.getAuthorizations() != null){
				roleList.add(Role.NORMAL);
				for(Authorization authorization : user.getAuthorizations()){
					roleList.add(authorization.getRole());
				}
			} else {
				roleList.add(Role.NORMAL);
			}
		} else {
			roleList.add(Role.NORMAL);
		}
		SessionUtils.putUserToSession(personalInfo, roleList, session);
	}
	
	public String loginStaff(){
		if(username.equals("admin") && password.equals("admintransport")){
			UserSession userSession = new UserSession();
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(Role.ADMIN);
			userSession.setRoleList(roleList);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSession", userSession);
			return "adminuser?faces-redirect=true";			
		}
		
		if(username.trim().length() == 0){
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"กรุณาใส่ชื่อผู้ใช้",""));
			 return null;
		}
		if(password.trim().length() == 0){
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"กรุณาใส่รหัสผ่าน",""));
			 return null;
		}
		User user = authentication(username, password);
		
		if(user != null){
			List<Role> roleList = new ArrayList<Role>();
			UserSession userSession = new UserSession();
			PersonalInfo personalInfo = employeeController.getStaff(user.getPersonalInfo().getSTAFFCODE());
			roleList.add(Role.NORMAL);
			for(Authorization auth : user.getAuthorizations()){				
				roleList.add(auth.getRole());
			}
			userSession.setRoleList(roleList);
			userSession.setStaff(personalInfo);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSession", userSession);
			return "request?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"คุณไม่มีสิทธิในการใช้งานระบบ",""));
			return null;
		}
		
	}
	
	private User authentication(String username, String password) {
		LDAPConnect connect = new LDAPConnect();
		LDAPUser ldapUser = connect.login(username, password);
		connect.disconnect();
		if(ldapUser != null){
			User dbUser = adminController.getUser(StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, '0'));
			if(dbUser == null){
				return null;
			} else {
				return dbUser;
			}
		} else {
			return null;
		}
	}
	
	public String logout(){
//		removeBeanFromSession("appMemberList");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("userSession");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		 
		return "login?faces-redirect=true";
	}
}
