package transport.manager;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import transport.utils.SessionUtils;

@ManagedBean(name="polling")
@RequestScoped
public class PollingManager implements Serializable{
	public PollingManager(){
		
	}
	
	public void startPolling(){
		System.out.println("Application : Transport");
		System.out.println("Polling interval : 30 mins");
		System.out.println("User : "+SessionUtils.getUserFromSession().getStaff().getSTAFFCODE());
	}
}
