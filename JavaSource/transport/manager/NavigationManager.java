package transport.manager;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="navigationManager")
@SessionScoped
public class NavigationManager implements Serializable{
	private String targetPage = "request";

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
		
	}
	
	public void removeAllBeans(){
 
		 removeBeanFromSession("emergencyManager");
		 removeBeanFromSession("localApproveManager");
		 removeBeanFromSession("navigationManager");
		 removeBeanFromSession("requestManager");
		 removeBeanFromSession("transportApproveManager");	
		 removeBeanFromSession("carAssignManager");
	}
	
	private void removeBeanFromSession(String beanName){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.remove(beanName); // Removes the session scoped bean.
	}
	
}
