package transport.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import transport.controller.AdminController;
import transport.customtype.DataStatus;
import transport.model.Rental;

@ManagedBean(name="adminRentalManager")
@ViewScoped
public class AdminRentalManager implements Serializable{
	private List<Rental> rentalList = new ArrayList<Rental>();
	private Rental editRental;
	private AdminController adminController = new AdminController();
	
	public List<Rental> getRentalList() {
		return rentalList;
	}
	public void setRentalList(List<Rental> rentalList) {
		this.rentalList = rentalList;
	}
	public Rental getEditRental() {
		return editRental;
	}
	public void setEditRental(Rental editRental) {
		this.editRental = editRental;
	}
	
	public AdminRentalManager(){
		editRental = new Rental();
		createRentalList();
	}
	
	private void createRentalList(){
		if(rentalList != null) rentalList.clear();
		
		rentalList.addAll(adminController.getRentalList());
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editRental.getCompanyName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อบริษัท"));
		}	
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveRental();
	}
	
	private void saveRental(){
		adminController.saveRental(editRental);
		createRentalList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void deleteRental(){
		editRental.setDataStatus(DataStatus.DELETED);
		adminController.saveRental(editRental);
		createRentalList();
		refreshData();
	}
	
	public void refreshData(){
		editRental = new Rental();
	}
	
}
