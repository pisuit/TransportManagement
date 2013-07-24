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
import transport.model.Contract;

@ManagedBean(name="adminCompanyManager")
@ViewScoped
public class AdminCompanyManager implements Serializable{
	private List<Company> companyList = new ArrayList<Company>();
	private Company editCompany;
	private AdminController adminController = new AdminController();
	private Long selectedContract = Long.valueOf(-1);
	private List<SelectItem> contractSelectItemList = new ArrayList<SelectItem>();
	
	public List<Company> getCompanyList() {
		return companyList;
	}
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}
	public Company getEditCompany() {
		return editCompany;
	}
	public void setEditCompany(Company editCompany) {
		this.editCompany = editCompany;
	}
	public Long getSelectedContract() {
		return selectedContract;
	}
	public void setSelectedContract(Long selectedContract) {
		this.selectedContract = selectedContract;
	}
	public List<SelectItem> getContractSelectItemList() {
		return contractSelectItemList;
	}
	public void setContractSelectItemList(List<SelectItem> contractSelectItemList) {
		this.contractSelectItemList = contractSelectItemList;
	}
	public AdminController getAdminController() {
		return adminController;
	}
	public void setAdminController(AdminController adminController) {
		this.adminController = adminController;
	}
	
	public AdminCompanyManager() {
		editCompany = new Company();
		createCompanyList();
		createContractSelectItemList();
	}
	
	private void createCompanyList(){
		if(companyList != null) companyList.clear();
		
		companyList.addAll(adminController.getCompanyList());
	}
	
	private void createContractSelectItemList(){
		if(contractSelectItemList != null) contractSelectItemList.clear();
		
		contractSelectItemList.add(new SelectItem(Long.valueOf(-1), "สัญญา"));
		for(Contract contract : adminController.getContractList()){
			contractSelectItemList.add(new SelectItem(contract.getId(), contract.getName()));
		}
	}
	
	public void saveCompany(){
		editCompany.setContract(adminController.getContract(selectedContract));
		adminController.saveCompany(editCompany);
		createCompanyList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void deleteComapny(){
		editCompany.setDataStatus(DataStatus.DELETED);
		adminController.saveCompany(editCompany);
		createCompanyList();
		refreshData();
	}
	
	public void refreshData(){
		editCompany = new Company();
		selectedContract = Long.valueOf(-1);
	}
	
	public void contractSelected(){
		if(editCompany.getContract() != null){
			selectedContract = editCompany.getContract().getId();
		} else {
			selectedContract = Long.valueOf(-1);
		}
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editCompany.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อบริษัท"));
		}	
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveCompany();
	}
	
	public void deleteDialogClosed(){
		editCompany = new Company();
	}
}
