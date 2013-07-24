package transport.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import transport.controller.AdminController;
import transport.customtype.DataStatus;
import transport.model.Company;
import transport.model.Driver;

@ManagedBean(name="adminDriverManager")
@ViewScoped
public class AdminDriverManager implements Serializable{
	private List<Driver> driverList = new ArrayList<Driver>();
	private AdminController adminController = new AdminController();
	private Driver editDriver;
	private List<SelectItem> companySelectItemList = new ArrayList<SelectItem>();
	private Long selectedCompany = Long.valueOf(-1);
	
	public List<Driver> getDriverList() {
		return driverList;
	}

	public void setDriverList(List<Driver> driverList) {
		this.driverList = driverList;
	}

	public Driver getEditDriver() {
		return editDriver;
	}

	public void setEditDriver(Driver editDriver) {
		this.editDriver = editDriver;
	}

	public Long getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Long selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public List<SelectItem> getCompanySelectItemList() {
		return companySelectItemList;
	}

	public void setCompanySelectItemList(List<SelectItem> companySelectItemList) {
		this.companySelectItemList = companySelectItemList;
	}

	public AdminDriverManager() {
		editDriver = new Driver();
		createDriverList();
		createCompanySelectItemList();
	}
	
	private void createDriverList(){
		if(driverList != null) driverList.clear();
		
		driverList.addAll(adminController.getDriverList());
	}
	
	public void saveDriver(){
		editDriver.setCompany(adminController.getCompany(selectedCompany));
		adminController.saveDriver(editDriver);
		createDriverList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void deleteDriver(){
		editDriver.setDataStatus(DataStatus.DELETED);
		adminController.saveDriver(editDriver);
		createDriverList();
		refreshData();
	}
	
	private void createCompanySelectItemList(){
		if(companySelectItemList != null) companySelectItemList.clear();
		
		companySelectItemList.add(new SelectItem(Long.valueOf(-1), "บริษัท"));
		for(Company company : adminController.getCompanyList()){
			companySelectItemList.add(new SelectItem(company.getId(), company.getName()));
		}
	}
	
	public void refreshData(){
		selectedCompany = Long.valueOf(-1);
		editDriver = new Driver();
	}
	
	public void driverSelected(){
		if(editDriver.getCompany() != null){
			selectedCompany = editDriver.getCompany().getId();
		} else {
			selectedCompany = Long.valueOf(-1);
		}
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editDriver.getThaiFirstName().trim().length() == 0 || editDriver.getThaiLastName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อและนามสกุลผู้ขับรถ"));
		}	
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveDriver();
	}
	
	public void deleteDialogClosed(){
		editDriver = new Driver();
	}

}
