package transport.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import transport.customtype.Role;
import transport.model.PersonalInfo;
import transport.session.UserSession;
import transport.utils.SessionUtils;

@ManagedBean(name="menuManager")
@ViewScoped
public class MenuManager implements Serializable{
	private boolean isNormal = false;
	private boolean isDirector = false;
	private boolean isServiceDirector = false;
	private boolean isStaff = false;
	private boolean isAdmin = false;
	
	public boolean isNormal() {
		return isNormal;
	}

	public boolean isDirector() {
		return isDirector;
	}

	public boolean isServiceDirector() {
		return isServiceDirector;
	}

	public boolean isStaff() {
		return isStaff;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public MenuManager(){
		setValue();
	}
	
	private void setValue(){
		UserSession userSession = SessionUtils.getUserFromSession();
		if(userSession != null){
			for(Role role : userSession.getRoleList()){
				if(role.equals(Role.NORMAL)) this.isNormal = true;
//				if(role.equals(Role.DIRECTOR)) this.isDirector = true;
				if(role.equals(Role.SERVICE_DIRECTOR)) this.isServiceDirector = true;
				if(role.equals(Role.SERVICE_STAFF)) this.isStaff = true;
				if(role.equals(Role.ADMIN)) this.isAdmin = true;
			}
		}	
	}
}
