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
import transport.customtype.CarType;
import transport.customtype.DataStatus;
import transport.model.Car;
import transport.model.Company;
import transport.model.Driver;
import transport.model.FuelType;
import transport.model.ServiceType;

@ManagedBean(name="adminCarManager")
@ViewScoped
public class AdminCarManager implements Serializable{
	private List<Car> carList = new ArrayList<Car>();
	private Car editCar;
	private Long selectedFuelType = Long.valueOf(-1);
	private Long selectedServiceType = Long.valueOf(-1);
	private Long selectedDriver = Long.valueOf(-1);
	private Long selectedCompany = Long.valueOf(-1);
	private CarType selectedCarType = CarType.VAN;
	private AdminController adminController = new AdminController();
	private List<SelectItem> fuelTypeSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> serviceTypeSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> driverSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItemList = new ArrayList<SelectItem>();
	
	public List<Car> getCarList() {
		return carList;
	}
	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}
	public Car getEditCar() {
		return editCar;
	}
	public void setEditCar(Car editCar) {
		this.editCar = editCar;
	}
	public Long getSelectedFuelType() {
		return selectedFuelType;
	}
	public void setSelectedFuelType(Long selectedFuelType) {
		this.selectedFuelType = selectedFuelType;
	}
	public Long getSelectedServiceType() {
		return selectedServiceType;
	}
	public void setSelectedServiceType(Long selectedServiceType) {
		this.selectedServiceType = selectedServiceType;
	}
	public Long getSelectedDriver() {
		return selectedDriver;
	}
	public void setSelectedDriver(Long selectedDriver) {
		this.selectedDriver = selectedDriver;
	}
	public Long getSelectedCompany() {
		return selectedCompany;
	}
	public void setSelectedCompany(Long selectedCompany) {
		this.selectedCompany = selectedCompany;
	}
	public CarType getSelectedCarType() {
		return selectedCarType;
	}
	public void setSelectedCarType(CarType selectedCarType) {
		this.selectedCarType = selectedCarType;
	}
	public List<SelectItem> getFuelTypeSelectItemList() {
		return fuelTypeSelectItemList;
	}
	public void setFuelTypeSelectItemList(List<SelectItem> fuelTypeSelectItemList) {
		this.fuelTypeSelectItemList = fuelTypeSelectItemList;
	}
	public List<SelectItem> getCompanySelectItemList() {
		return companySelectItemList;
	}
	public void setCompanySelectItemList(List<SelectItem> companySelectItemList) {
		this.companySelectItemList = companySelectItemList;
	}
	public List<SelectItem> getServiceTypeSelectItemList() {
		return serviceTypeSelectItemList;
	}
	public void setServiceTypeSelectItemList(
			List<SelectItem> serviceTypeSelectItemList) {
		this.serviceTypeSelectItemList = serviceTypeSelectItemList;
	}
	public List<SelectItem> getDriverSelectItemList() {
		return driverSelectItemList;
	}
	public void setDriverSelectItemList(List<SelectItem> driverSelectItemList) {
		this.driverSelectItemList = driverSelectItemList;
	}
	public List<SelectItem> getCarTypeSelectItemList() {
		return carTypeSelectItemList;
	}
	public void setCarTypeSelectItemList(List<SelectItem> carTypeSelectItemList) {
		this.carTypeSelectItemList = carTypeSelectItemList;
	}
	
	public AdminCarManager(){
		editCar = new Car();
		createCarList();
		createCarTypeSelectItemList();
		createDriverSelectItemList();
		createFuelTypeSelectItemList();
		createServiceTypeSelectItemList();
		createCompanySelectItemList();
	}
	
	private void createCarList(){
		if(carList != null) carList.clear();
		
		carList.addAll(adminController.getCarList());
	}
	
	private void createFuelTypeSelectItemList(){
		if(fuelTypeSelectItemList != null) fuelTypeSelectItemList.clear();
		
		fuelTypeSelectItemList.add(new SelectItem(Long.valueOf(-1), "ประเภทเชื้อเพลิง"));
		for(FuelType fuelType : adminController.getFuelTypeList()){
			fuelTypeSelectItemList.add(new SelectItem(fuelType.getId(), fuelType.getName()));
		}
	}
	
	private void createServiceTypeSelectItemList(){
		if(serviceTypeSelectItemList != null) serviceTypeSelectItemList.clear();
		
		serviceTypeSelectItemList.add(new SelectItem(Long.valueOf(-1), "ประเภทการให้บริการ"));
		for(ServiceType serviceType : adminController.getServiceTypeList()){
			serviceTypeSelectItemList.add(new SelectItem(serviceType.getId(), serviceType.getName()));
		}
	}
	
	private void createDriverSelectItemList(){
		if(driverSelectItemList != null) driverSelectItemList.clear();
		
		driverSelectItemList.add(new SelectItem(Long.valueOf(-1), "ผู้ขับรถ"));
		for(Driver driver : adminController.getDriverList()){
			driverSelectItemList.add(new SelectItem(driver.getId(), driver.toString()));
		}
	}
	
	private void createCompanySelectItemList(){
		if(companySelectItemList != null) companySelectItemList.clear();
		
		companySelectItemList.add(new SelectItem(Long.valueOf(-1), "บริษัท"));
		for(Company company : adminController.getCompanyList()){
			companySelectItemList.add(new SelectItem(company.getId(), company.getName()));
		}
	}
	
	private void createCarTypeSelectItemList(){
		if(carTypeSelectItemList != null) carTypeSelectItemList.clear();
		
		for(CarType carType : CarType.values()){
			carTypeSelectItemList.add(new SelectItem(carType, carType.getValue()));
		}
	}
	
	public void saveCar(){
		editCar.setCompany(adminController.getCompany(selectedCompany));
		editCar.setDriver(adminController.getDriver(selectedDriver));
		editCar.setFuelType(adminController.getFuelType(selectedFuelType));
		editCar.setServiceType(adminController.getServiceType(selectedServiceType));
		adminController.saveCar(editCar);
		createCarList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void deleteCar(){
		editCar.setDataStatus(DataStatus.DELETED);
		adminController.saveCar(editCar);
		createCarList();
		refreshData();
	}
	
	public void refreshData(){
		editCar = new Car();
		selectedCarType = CarType.VAN;
		selectedCompany = Long.valueOf(-1);
		selectedDriver = Long.valueOf(-1);
		selectedFuelType = Long.valueOf(-1);
		selectedServiceType = Long.valueOf(-1);
	}
	
	public void carSelected(){
		selectedCarType = editCar.getCarType();
		if(editCar.getCompany() != null){
			selectedCompany = editCar.getCompany().getId();
		}
		if(editCar.getDriver() != null){
			selectedDriver = editCar.getDriver().getId();
		}
		if(editCar.getFuelType() != null){
			selectedFuelType = editCar.getFuelType().getId();
		}
		if(editCar.getServiceType() != null){
			selectedServiceType = editCar.getServiceType().getId();
		}
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editCar.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อรถ"));
		}	
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveCar();
	}
	
	public void deleteDialogClosed(){
		editCar = new Car();
	}
}
