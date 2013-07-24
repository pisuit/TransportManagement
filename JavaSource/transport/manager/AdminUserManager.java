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
import transport.controller.EmployeeController;
import transport.customtype.DataStatus;
import transport.customtype.Role;
import transport.model.Authorization;
import transport.model.PersonalInfo;
import transport.model.User;

@ManagedBean(name="adminUserManager")
@ViewScoped
public class AdminUserManager implements Serializable{
	private EmployeeController employeeController = new EmployeeController();
	private AdminController adminController = new AdminController();
	private List<User> userList = new ArrayList<User>();
	private User editUser;
	private List<SelectItem> roleSelectItemList = new ArrayList<SelectItem>();
	private PersonalInfo selectedStaff;
	private List<String> selectedRole = new ArrayList<String>();
	
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<String> getSelectedRole() {
		return selectedRole;
	}
	public void setSelectedRole(List<String> selectedRole) {
		this.selectedRole = selectedRole;
	}
	public User getEditUser() {
		return editUser;
	}
	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}
	public List<SelectItem> getRoleSelectItemList() {
		return roleSelectItemList;
	}
	public void setRoleSelectItemList(List<SelectItem> roleSelectItemList) {
		this.roleSelectItemList = roleSelectItemList;
	}
	public PersonalInfo getSelectedStaff() {
		return selectedStaff;
	}
	public void setSelectedStaff(PersonalInfo selectedStaff) {
		this.selectedStaff = selectedStaff;
	}
	
	public AdminUserManager(){
		createUserList();
		createRoleSelectItemList();
		editUser = new User();
	}
	
	private void createUserList(){
		if(userList != null) userList.clear();
		
		userList.addAll(adminController.getAllUser());
	}
	
	private void createRoleSelectItemList(){
		if(roleSelectItemList != null) roleSelectItemList.clear();
		
		for(Role role : Role.values()){
			if(role != Role.NORMAL){
				roleSelectItemList.add(new SelectItem(role.getID(), role.getValue()));
			}
		}
	}
	
	public void userSelected(){
		if(selectedRole != null) selectedRole.clear();
		selectedStaff = editUser.getPersonalInfo();
		if(editUser.getAuthorizations() != null){
			for(Authorization authorization : editUser.getAuthorizations()){
				selectedRole.add(authorization.getRole().getID());
			}
		}
	}
	
	public void saveUser(){
		editUser.setPersonalInfo(selectedStaff);
		adminController.saveUser(editUser, selectedRole);
		createUserList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void refreshData(){
		editUser = new User();
		if(selectedRole != null) selectedRole.clear();
		selectedStaff = null;
	}
	
	public void deleteUser(){
		editUser.setDataStatus(DataStatus.DELETED);
		adminController.saveUser(editUser, null);
		createUserList();
		editUser = new User();
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(selectedStaff == null){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกผู้ใช้งาน"));
		}
		if(selectedRole.size() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกสิทธิการใช้งาน"));
		}		
		if(selectedStaff != null && editUser.getId() == null){
			if(adminController.getUser(selectedStaff) != null){
				messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "เกิดความผิดพลาด !!", "มีชื่อผู้ใช้งานนี้ในระบบแล้ว"));
			}
		}
		
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveUser();
	}
	
	public void deleteDialogClosed(){
		editUser = new User();
	}

}
