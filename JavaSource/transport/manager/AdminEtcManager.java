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
import transport.model.FuelType;
import transport.model.ParkingLocation;
import transport.model.Purpose;
import transport.model.ServiceType;

@ManagedBean(name="adminEtcManager")
@ViewScoped
public class AdminEtcManager implements Serializable{
	private List<Purpose> purposeList = new ArrayList<Purpose>();
	private List<ParkingLocation> parkList = new ArrayList<ParkingLocation>();
	private List<FuelType> fuelList = new ArrayList<FuelType>();
	private List<ServiceType> serviceList = new ArrayList<ServiceType>();
	private Purpose editPurpose;
	private ParkingLocation editPark;
	private FuelType editFuel;
	private ServiceType editService;
	private AdminController adminController = new AdminController();
	
	public List<Purpose> getPurposeList() {
		return purposeList;
	}

	public void setPurposeList(List<Purpose> purposeList) {
		this.purposeList = purposeList;
	}

	public List<ParkingLocation> getParkList() {
		return parkList;
	}

	public void setParkList(List<ParkingLocation> parkList) {
		this.parkList = parkList;
	}

	public List<FuelType> getFuelList() {
		return fuelList;
	}

	public void setFuelList(List<FuelType> fuelList) {
		this.fuelList = fuelList;
	}

	public List<ServiceType> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceType> serviceList) {
		this.serviceList = serviceList;
	}

	public Purpose getEditPurpose() {
		return editPurpose;
	}

	public void setEditPurpose(Purpose editPurpose) {
		this.editPurpose = editPurpose;
	}

	public ParkingLocation getEditPark() {
		return editPark;
	}

	public void setEditPark(ParkingLocation editPark) {
		this.editPark = editPark;
	}

	public FuelType getEditFuel() {
		return editFuel;
	}

	public void setEditFuel(FuelType editFuel) {
		this.editFuel = editFuel;
	}

	public ServiceType getEditService() {
		return editService;
	}

	public void setEditService(ServiceType editService) {
		this.editService = editService;
	}

	public AdminEtcManager(){
		editFuel = new FuelType();
		editPark = new ParkingLocation();
		editPurpose = new Purpose();
		editService = new ServiceType();
		createPurposeList();
		createFuelList();
		createParkList();
		createServiceList();
	}
	
	private void createPurposeList(){
		if(purposeList != null) purposeList.clear();
		purposeList.addAll(adminController.getPurposeList());
	}
	
	private void createParkList(){
		if(parkList != null) parkList.clear();
		parkList.addAll(adminController.getParkingLocationList());
	}
	
	private void createFuelList(){
		if(fuelList != null) fuelList.clear();
		fuelList.addAll(adminController.getFuelTypeList());
	}
	
	private void createServiceList(){
		if(serviceList != null) serviceList.clear();
		serviceList.addAll(adminController.getServiceTypeList());
	}
	
	public void savePurpose(){
		adminController.savePurpose(editPurpose);
		refreshData();
		createPurposeList();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void savePark(){
		adminController.saveParkingLocation(editPark);
		refreshData();
		createParkList();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void saveFuel(){
		adminController.saveFuelType(editFuel);
		refreshData();
		createFuelList();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void saveService(){
		adminController.saveServiceType(editService);
		refreshData();
		createServiceList();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void deletePurpose(){
		editPurpose.setDataStatus(DataStatus.DELETED);
		adminController.savePurpose(editPurpose);
		refreshData();
		createPurposeList();
	}
	
	public void deletePark(){
		editPark.setDataStatus(DataStatus.DELETED);
		adminController.saveParkingLocation(editPark);
		refreshData();
		createParkList();
	}
	public void deleteFuel(){
		editFuel.setDataStatus(DataStatus.DELETED);
		adminController.saveFuelType(editFuel);
		refreshData();
		createFuelList();
	}
	
	public void deleteService(){
		editService.setDataStatus(DataStatus.DELETED);
		adminController.saveServiceType(editService);
		refreshData();
		createServiceList();
	}
	
	public void refreshData(){
		editFuel = new FuelType();
		editPark = new ParkingLocation();
		editPurpose = new Purpose();
		editService = new ServiceType();
	}
	
	public void validateFuel(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editFuel.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อเชื้อเพลิง"));
		}
		
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveFuel();
	}
	
	public void validateServiceType(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editService.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อประเภทการให้บริการ"));
		}
		
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveService();
	}
	
	public void validatePurpose(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editPurpose.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อวัตถุประสงค์"));
		}
		
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) savePurpose();
	}
	
	public void validatePark(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();	
		
		if(editPark.getName().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่ชื่อจุดขึ้นรถ"));
		}
		
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) savePark();
	}
	
	public void deleteFuelDialogClosed(){
		editFuel = new FuelType();
	}
	
	public void deleteServiceDialogClosed(){
		editService = new ServiceType();
	}
	
	public void deletePurposeDialogClosed(){
		editPurpose = new Purpose();
	}
	
	public void deleteParkDialogClosed(){
		editPark = new ParkingLocation();
	}
}
