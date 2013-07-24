package transport.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import transport.controller.EmployeeController;
import transport.controller.GeneralController;
import transport.controller.ReservationController;
import transport.customtype.CarType;
import transport.customtype.DataStatus;
import transport.customtype.FormStatus;
import transport.customtype.TravelType;
import transport.model.Car;
import transport.model.Department;
import transport.model.EmployeeInfo;
import transport.model.ParkingLocation;
import transport.model.PersonalInfo;
import transport.model.Purpose;
import transport.model.Reservation;
import transport.model.Routine;
import transport.utils.SessionUtils;

@ManagedBean(name="routineManager")
@ViewScoped
public class RoutineManager implements Serializable{
	private Routine editRoutine;
	private List<Routine> routineList = new ArrayList<Routine>();
	private GeneralController generalController = new GeneralController();
	private EmployeeController employeeController = new EmployeeController();
	private List<SelectItem> carSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> purposeSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> parkSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> travelTypeSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> depSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> staffSelectItemList = new ArrayList<SelectItem>();
	private String selectedTravelType = TravelType.TWOWAY.getID();
	private String selectedCarType = CarType.VAN.getID();
	private Long selectedCar = Long.valueOf(0);
	private Long selectedPurpose = Long.valueOf(0);
	private Long selectedPark = Long.valueOf(0);
	private String selectedDep = "0";
	private String selectedStaff = "0";
	private Routine deletedRoutine = new Routine();
	private Routine selectedRoutine;
	private Date selectedDate;
	private List<PersonalInfo> selectedTravellers;
	private Long selectedCar2 = Long.valueOf(0);
	private ReservationController reservationController = new ReservationController();
	
	public Routine getDeletedRoutine(){
		return deletedRoutine;
	}
	public void setDeletedRoutine(Routine deletedRoutine){
		this.deletedRoutine = deletedRoutine;
	}	
	public Routine getEditRoutine() {
		return editRoutine;
	}
	public void setEditRoutine(Routine editRoutine) {
		this.editRoutine = editRoutine;
	}
	public List<Routine> getRoutineList() {
		return routineList;
	}
	public void setRoutineList(List<Routine> routineList) {
		this.routineList = routineList;
	}
	public String getSelectedTravelType() {
		return selectedTravelType;
	}
	public void setSelectedTravelType(String selectedTravelType) {
		this.selectedTravelType = selectedTravelType;
	}
	public String getSelectedCarType() {
		return selectedCarType;
	}
	public void setSelectedCarType(String selectedCarType) {
		this.selectedCarType = selectedCarType;
	}
	public Long getSelectedCar() {
		return selectedCar;
	}
	public void setSelectedCar(Long selectedCar) {
		this.selectedCar = selectedCar;
	}
	public Long getSelectedPurpose() {
		return selectedPurpose;
	}
	public void setSelectedPurpose(Long selectedPurpose) {
		this.selectedPurpose = selectedPurpose;
	}
	public Long getSelectedPark() {
		return selectedPark;
	}
	public void setSelectedPark(Long selectedPark) {
		this.selectedPark = selectedPark;
	}
	public Long getSelectedCar2() {
		return selectedCar2;
	}
	public void setSelectedCar2(Long selectedCar2) {
		this.selectedCar2 = selectedCar2;
	}
	public List<PersonalInfo> getSelectedTravellers() {
		return selectedTravellers;
	}
	public void setSelectedTravellers(List<PersonalInfo> selectedTravellers) {
		this.selectedTravellers = selectedTravellers;
	}
	public String getSelectedDep() {
		return selectedDep;
	}
	public void setSelectedDep(String selectedDep) {
		this.selectedDep = selectedDep;
	}
	public String getSelectedStaff() {
		return selectedStaff;
	}
	public void setSelectedStaff(String selectedStaff) {
		this.selectedStaff = selectedStaff;
	}
	public List<SelectItem> getCarSelectItemList() {
		return carSelectItemList;
	}
	public void setCarSelectItemList(List<SelectItem> carSelectItemList) {
		this.carSelectItemList = carSelectItemList;
	}
	public List<SelectItem> getPurposeSelectItemList() {
		return purposeSelectItemList;
	}
	public void setPurposeSelectItemList(List<SelectItem> purposeSelectItemList) {
		this.purposeSelectItemList = purposeSelectItemList;
	}
	public List<SelectItem> getParkSelectItemList() {
		return parkSelectItemList;
	}
	public void setParkSelectItemList(List<SelectItem> parkSelectItemList) {
		this.parkSelectItemList = parkSelectItemList;
	}
	public List<SelectItem> getCarTypeSelectItemList() {
		return carTypeSelectItemList;
	}
	public void setCarTypeSelectItemList(List<SelectItem> carTypeSelectItemList) {
		this.carTypeSelectItemList = carTypeSelectItemList;
	}
	public List<SelectItem> getTravelTypeSelectItemList() {
		return travelTypeSelectItemList;
	}
	public void setTravelTypeSelectItemList(
			List<SelectItem> travelTypeSelectItemList) {
		this.travelTypeSelectItemList = travelTypeSelectItemList;
	}
	public List<SelectItem> getDepSelectItemList() {
		return depSelectItemList;
	}
	public void setDepSelectItemList(List<SelectItem> depSelectItemList) {
		this.depSelectItemList = depSelectItemList;
	}
	public Routine getSelectedRoutine() {
		return selectedRoutine;
	}
	public void setSelectedRoutine(Routine selectedRoutine) {
		this.selectedRoutine = selectedRoutine;
	}
	public Date getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	public List<SelectItem> getStaffSelectItemList() {
		return staffSelectItemList;
	}
	public void setStaffSelectItemList(List<SelectItem> staffSelectItemList) {
		this.staffSelectItemList = staffSelectItemList;
	}
	
	public RoutineManager(){
		selectedDate = DateTime.now().toDateMidnight().toDate();
		editRoutine = new Routine();
		createTravelTypeSelectItemList();
		createCarTypeSelectItemList();
		createPurposeSelectItemList();
		createParkingLocationSelectItemList();
		createDepartmentSelectItemList();
		createRoutineList();
		createCarSelectItemList();
		createStaffSelectItemList();
	}
	
	private void createCarSelectItemList(){
		if(carSelectItemList != null) carSelectItemList.clear();
		
		carSelectItemList.add(new SelectItem(Long.valueOf(0), "กรุณาเลือกรถ"));
		for(Car car : generalController.getAllCars()){
			carSelectItemList.add(new SelectItem(car.getId(), car.getName()));
		}
	}
	
	private void createTravelTypeSelectItemList(){
		if(travelTypeSelectItemList != null) travelTypeSelectItemList.clear();
		
		for(TravelType type : TravelType.values()){
			travelTypeSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
		}
	}
	
	private void createCarTypeSelectItemList(){
		if(carTypeSelectItemList != null) carTypeSelectItemList.clear();
		
		for(CarType type : CarType.values()){
			carTypeSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
		}
	}
	
	private void createPurposeSelectItemList(){
		if(purposeSelectItemList != null) parkSelectItemList.clear();
		
		purposeSelectItemList.add(new SelectItem(Long.valueOf(0), "กรุณาเลือกวัตถุประสงค์"));
		for(Purpose purpose : generalController.getAllPurposeList()){
			purposeSelectItemList.add(new SelectItem(purpose.getId(), purpose.getName()));
		}
	}
	
	private void createParkingLocationSelectItemList(){
		if(parkSelectItemList != null) parkSelectItemList.clear();
		parkSelectItemList.add(new SelectItem(Long.valueOf(0), "กรุณาเลือกจุดขึ้นรถ"));	
		
		for(ParkingLocation location : generalController.getAllParkingLocationList()){
			parkSelectItemList.add(new SelectItem(location.getId(), location.getName()));
		}
	}
	
	private void createDepartmentSelectItemList(){
		if(depSelectItemList != null) depSelectItemList.clear();		
		depSelectItemList.add(new SelectItem("0", "กรุณาเลือกฝ่าย/กอง"));
		
		for(Department dep : employeeController.getDepartments()){
			depSelectItemList.add(new SelectItem(dep.getTDEPS(), dep.getTDEPS()));
		}
	}
	
	private void createStaffSelectItemList(){
		if(staffSelectItemList != null) staffSelectItemList.clear();
		staffSelectItemList.add(new SelectItem("0", "กรุณาเลือกผู้ขอใช้รถ"));
		
		EmployeeInfo director = employeeController.getDirectorOfDepartment(selectedDep);
		if(director != null){
			staffSelectItemList.add(new SelectItem(director.getPersonalInfo().getSTAFFCODE(), director.getPersonalInfo().getTNAME()+" "+director.getPersonalInfo().getTSURNAME()));
		}
		for(EmployeeInfo employee : employeeController.getEmployeeForDepartment(selectedDep)){
			staffSelectItemList.add(new SelectItem(employee.getPersonalInfo().getSTAFFCODE(), employee.getPersonalInfo().getTNAME()+" "+employee.getPersonalInfo().getTSURNAME()));
		}
	}
	
	private void createRoutineList(){
		if(routineList != null) routineList.clear();
		
		routineList.addAll(generalController.getAllRoutines());
	}
	
	public void depSelected(){
		createStaffSelectItemList();
	}
	
	public void saveRoutine(){
		editRoutine.setCar(generalController.getCar(selectedCar));
		editRoutine.setCarType(CarType.find(selectedCarType));
		editRoutine.setParkingLocation(generalController.getParkingLocation(selectedPark));
		editRoutine.setPurpose(generalController.getPurpose(selectedPurpose));
		editRoutine.setRequester(employeeController.getStaff(selectedStaff));
		editRoutine.setTravelType(TravelType.find(selectedTravelType));
		generalController.saveRoutine(editRoutine);
		createRoutineList();
		refreshData();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void refreshData(){
		editRoutine = new Routine();
		selectedCar = Long.valueOf(0);
		selectedCarType = CarType.VAN.getID();
		selectedDep = "0";
		selectedPark = Long.valueOf(0);
		selectedPurpose = Long.valueOf(0);
		selectedStaff = "0";
		selectedTravelType = TravelType.TWOWAY.getID();
	}
	
	public void routineSelected(){
		if(editRoutine.getCar() != null)
			selectedCar = editRoutine.getCar().getId();
		else
			selectedCar = Long.valueOf(0);
		
		if(editRoutine.getCarType() != null)
			selectedCarType = editRoutine.getCarType().getID();
		else
			selectedCarType = CarType.VAN.getID();
		
		if(editRoutine.getParkingLocation() != null)
			selectedPark = editRoutine.getParkingLocation().getId();
		else
			selectedPark = Long.valueOf(0);
		
		if(editRoutine.getPurpose() != null)
			selectedPurpose = editRoutine.getPurpose().getId();
		else
			selectedPurpose = Long.valueOf(0);
		
		if(editRoutine.getTravelType() != null)
			selectedTravelType = editRoutine.getTravelType().getID();
		else
			selectedTravelType = TravelType.TWOWAY.getID();
				
		if(editRoutine.getRequester() != null) {
			EmployeeInfo empInfo = employeeController.getEmployeeInfo(editRoutine.getRequester());
			selectedDep = empInfo.getDEPFINANCE();
			createStaffSelectItemList();
			selectedStaff = editRoutine.getRequester().getSTAFFCODE();
		}
	}
	
	public void deleteCancleButtonClicked(){
		deletedRoutine = new Routine();
	}
	
	public void deleteRoutine(){
		deletedRoutine.setDataStatus(DataStatus.DELETED);
		generalController.saveRoutine(deletedRoutine);
		createRoutineList();
		deletedRoutine = new Routine();
	}
	
	public void test(){
		System.out.println(selectedTravellers);
	}
	
	public void saveRequestForm(){
		Reservation reservation = new Reservation();
		reservation.setAssignDate(DateTime.now().toDateMidnight().toDate());
		reservation.setCarType(selectedRoutine.getCarType());
		reservation.setCreateDate(DateTime.now().toDateMidnight().toDate());
		reservation.setEndDate(selectedDate);
		reservation.setEndTime(selectedRoutine.getEndTime());
		reservation.setFinishPoint(selectedRoutine.getFinishPoint());
		reservation.setFormStatus(FormStatus.CAR_ASSIGNED);
		reservation.setInternalPhone(selectedRoutine.getPhoneNumber());
		reservation.setNumberOfCars(1);
		if(selectedTravellers != null){
			reservation.setNumberOfTraveller(selectedTravellers.size());
		} else {
			reservation.setNumberOfTraveller(1);
		}
		reservation.setParkingLocation(selectedRoutine.getParkingLocation());
		reservation.setPurpose(selectedRoutine.getPurpose());
		reservation.setRemark(selectedRoutine.getRemark());
		reservation.setRequester(selectedRoutine.getRequester());
		reservation.setRequestNumber(reservationController.getNextRequestNumber());
		reservation.setStaff(SessionUtils.getUserFromSession().getStaff());
		reservation.setStartDate(selectedRoutine.getEndTime());
		reservation.setStartingPoint(selectedRoutine.getStartingPoint());
		reservation.setStartTime(selectedRoutine.getStartTime());
		if(selectedTravellers != null && selectedTravellers.size() != 0){
			List<String> stringListOfTravellers = new ArrayList<String>();
			for(PersonalInfo personalInfo : selectedTravellers){
				stringListOfTravellers.add(personalInfo.toString());
				reservation.setTravellers(StringUtils.join(stringListOfTravellers, ','));
			}
		} else {
			reservation.setTravellers(null);
		}
		reservation.setTravelType(selectedRoutine.getTravelType());
		reservationController.saveReservationRoutine(reservation,selectedRoutine,generalController.getCar(selectedCar2));
	}
	
	public void createRequestDialogClosed(){
		selectedRoutine = new Routine();
	}
}
