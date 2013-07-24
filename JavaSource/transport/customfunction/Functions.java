package transport.customfunction;

import java.util.Collection;
import java.util.Date;

import transport.customtype.Role;
import transport.model.Authorization;

public class Functions {
	private Functions(){
		
	}
	
	public static boolean containAuthorize(Collection<Authorization> authorizations, String role){
		for(Authorization authorization : authorizations){
			if(authorization.getRole().getID().equals(role)){
				return true;
			}		
		}
		return false;
	}
	
	public static boolean containRole(Collection<Role> roles, String rolestr){
		for(Role role : roles){
			if(role.getID().equals(rolestr)){
				return true;
			}		
		}
		return false;
	}
	
	public static boolean compareDate(Date end, Date today){
		if(end.compareTo(today) == 0){
			return false;
		} else if(end.compareTo(today) == -1){
			return false;
		} else {
			return true;
		}
	}
}
