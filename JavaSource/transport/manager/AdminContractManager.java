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
import transport.model.Contract;

@ManagedBean(name="adminContractManager")
@ViewScoped
public class AdminContractManager implements Serializable{
	private List<Contract> contractList = new ArrayList<Contract>();
	private AdminController adminController = new AdminController();
	private Contract editContract;
	
	public List<Contract> getContractList() {
		return contractList;
	}
	public void setContractList(List<Contract> contractList) {
		this.contractList = contractList;
	}
	public Contract getEditContract() {
		return editContract;
	}
	public void setEditContract(Contract editContract) {
		this.editContract = editContract;
	}
	
	public AdminContractManager(){
		editContract = new Contract();
		createContractList();
	}
	
	private void createContractList(){
		if(contractList != null) contractList.clear();
		
		contractList.addAll(adminController.getContractList());
	}
	
	public void saveContract(){
		adminController.saveContract(editContract);
		createContractList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void deleteContract(){
		editContract.setDataStatus(DataStatus.DELETED);
		adminController.saveContract(editContract);
		createContractList();
		editContract = new Contract();
	}
	
	public void refreshData(){
		editContract = new Contract();
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editContract.getCode().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่เลขที่สัญญา"));
		}	
		if(editContract.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อสัญญา"));
		}
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveContract();
	}
	
	public void deleteDialogClosed(){
		editContract = new Contract();
	}
	
}
