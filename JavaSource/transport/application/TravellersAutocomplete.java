package transport.application;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.StringUtils;

import transport.controller.EmployeeController;
import transport.model.PersonalInfo;

@ManagedBean(name="travellersAutocomplete", eager=true)
@ApplicationScoped
public class TravellersAutocomplete {
//	private ArrayList<String> nameList = new ArrayList<String>();
	private EmployeeController employeeController = new EmployeeController();
	private List<PersonalInfo> employeeList = new ArrayList<PersonalInfo>();
	
//	public ArrayList<String> getNameList(String input) {
//		System.out.println("get: "+input+" processing auto complete....");
//		ArrayList<String> list = new ArrayList<String>();
//		
//		for(String str : nameList){
//			if(StringUtils.contains(str, input)){
//				if(list.size() != 0){
//					for(int i=0; i<list.size(); i++){
//						if(list.get(i).equals(str)){
//							list.remove(i);
//						}
//					}
//				}
//				list.add(str);
//			}
//		}
//		return list;
//	}
	
	public List<PersonalInfo> getEmployeeList(String input) {
		List<PersonalInfo> list = new ArrayList<PersonalInfo>();
		
		for(PersonalInfo person : employeeList){
			if(StringUtils.contains(person.toString(), input)){
//				if(list.size() != 0){
//					for(int i=0; i<list.size(); i++){
//						if(list.get(i).toString().equals(person.toString())){
//							list.remove(i);
//						}
//					}
//				}
				list.add(person);
			}
		}
		return list;
	}
		
	public TravellersAutocomplete() {
//		createTravellerList();
		createEmployeeList();
	}
	
//	private void createTravellerList(){
//		if(nameList != null) nameList.clear();
//		List<String> list = employeeController.getEmployeeNameList();
//		for(String str : list){
//			nameList.add(str);
//		}
//	}
	
	private void createEmployeeList(){
		if(employeeList != null) employeeList.clear();
		employeeList.addAll(employeeController.getEmployeeList());
	}

//	public ArrayList<String> getNameList() {
//		return nameList;
//	}
//
//	public void setNameList(ArrayList<String> nameList) {
//		this.nameList = nameList;
//	}

	public List<PersonalInfo> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<PersonalInfo> employeeList) {
		this.employeeList = employeeList;
	}
		
}
