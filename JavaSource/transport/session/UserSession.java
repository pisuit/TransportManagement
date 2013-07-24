package transport.session;

import java.util.ArrayList;
import java.util.List;

import transport.customtype.Role;
import transport.model.Authorization;
import transport.model.PersonalInfo;

public class UserSession {
	private PersonalInfo staff;
	private List<Role> roleList;
	
	public PersonalInfo getStaff() {
		return staff;
	}
	public void setStaff(PersonalInfo staff) {
		this.staff = staff;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
