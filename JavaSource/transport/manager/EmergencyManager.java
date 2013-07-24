package transport.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.primefaces.context.RequestContext;

import transport.application.TravellersAutocomplete;
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
import transport.model.Queue;
import transport.model.Reservation;
import transport.model.ReservationItem;
import transport.model.Routine;

@ManagedBean(name="emergencyManager")
@ViewScoped
public class EmergencyManager implements Serializable{
	private Date createDate;
	private Reservation reservation = new Reservation();
	private ArrayList<SelectItem> carTypeSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> travelTypeSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> purposeSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> parkingLocationSelectItemList = new ArrayList<SelectItem>();
	private String selectedTravelType = TravelType.TWOWAY.getID();
	private String selectedCarType = CarType.VAN.getID();
	private Long selectedPurpose = Long.valueOf(0);
	private Long selectedParkingLocation = Long.valueOf(0);
	private GeneralController generalController = new GeneralController();
	private ReservationController reservationController = new ReservationController();
	private EmployeeController employeeController = new EmployeeController();
	private List<PersonalInfo> selectedTravellers = new ArrayList<PersonalInfo>();
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private Reservation deletedReservation = new Reservation();
	private List<SelectItem> depSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> staffSelectItemList = new ArrayList<SelectItem>();
	private String selectedDep = "-1";
	private String selectedStaff = "-1";
	private boolean isRefresh = false;
	private List<SelectItem> carTypeNoVanSelectItemList = new ArrayList<SelectItem>();
	private String selectedCarTypeNoVan = CarType.VAN.getID();
	private List<SelectItem> formStatusFilterOptions = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeFilterOptions = new ArrayList<SelectItem>();
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public ArrayList<SelectItem> getCarTypeSelectItemList() {
		return carTypeSelectItemList;
	}
	public void setCarTypeSelectItemList(ArrayList<SelectItem> carTypeSelectItemList) {
		this.carTypeSelectItemList = carTypeSelectItemList;
	}
	public ArrayList<SelectItem> getTravelTypeSelectItemList() {
		return travelTypeSelectItemList;
	}
	public void setTravelTypeSelectItemList(
			ArrayList<SelectItem> travelTypeSelectItemList) {
		this.travelTypeSelectItemList = travelTypeSelectItemList;
	}
	public ArrayList<SelectItem> getPurposeSelectItemList() {
		return purposeSelectItemList;
	}
	public void setPurposeSelectItemList(ArrayList<SelectItem> purposeSelectItemList) {
		this.purposeSelectItemList = purposeSelectItemList;
	}
	public ArrayList<SelectItem> getParkingLocationSelectItemList() {
		return parkingLocationSelectItemList;
	}
	public void setParkingLocationSelectItemList(
			ArrayList<SelectItem> parkingLocationSelectItemList) {
		this.parkingLocationSelectItemList = parkingLocationSelectItemList;
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
	public List<SelectItem> getCarTypeNoVanSelectItemList() {
		return carTypeNoVanSelectItemList;
	}
	public void setCarTypeNoVanSelectItemList(
			List<SelectItem> carTypeNoVanSelectItemList) {
		this.carTypeNoVanSelectItemList = carTypeNoVanSelectItemList;
	}
	public String getSelectedCarTypeNoVan() {
		return selectedCarTypeNoVan;
	}
	public void setSelectedCarTypeNoVan(String selectedCarTypeNoVan) {
		this.selectedCarTypeNoVan = selectedCarTypeNoVan;
	}
	public List<SelectItem> getFormStatusFilterOptions() {
		return formStatusFilterOptions;
	}
	public void setFormStatusFilterOptions(List<SelectItem> formStatusFilterOptions) {
		this.formStatusFilterOptions = formStatusFilterOptions;
	}
	public List<SelectItem> getCarTypeFilterOptions() {
		return carTypeFilterOptions;
	}
	public void setCarTypeFilterOptions(List<SelectItem> carTypeFilterOptions) {
		this.carTypeFilterOptions = carTypeFilterOptions;
	}
	public boolean isRefresh() {
		return isRefresh;
	}
	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}
	public List<SelectItem> getStaffSelectItemList() {
		return staffSelectItemList;
	}
	public void setStaffSelectItemList(List<SelectItem> staffSelectItemList) {
		this.staffSelectItemList = staffSelectItemList;
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
	public Long getSelectedPurpose() {
		return selectedPurpose;
	}
	public void setSelectedPurpose(Long selectedPurpose) {
		this.selectedPurpose = selectedPurpose;
	}
	public Long getSelectedParkingLocation() {
		return selectedParkingLocation;
	}
	public void setSelectedParkingLocation(Long selectedParkingLocation) {
		this.selectedParkingLocation = selectedParkingLocation;
	}
	public List<SelectItem> getDepSelectItemList() {
		return depSelectItemList;
	}
	public void setDepSelectItemList(List<SelectItem> depSelectItemList) {
		this.depSelectItemList = depSelectItemList;
	}
	public List<PersonalInfo> getSelectedTravellers() {
		return selectedTravellers;
	}
	public void setSelectedTravellers(List<PersonalInfo> selectedTravellers) {
		this.selectedTravellers = selectedTravellers;
	}
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}
	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	public Reservation getDeletedReservation() {
		return deletedReservation;
	}
	public void setDeletedReservation(Reservation deletedReservation) {
		this.deletedReservation = deletedReservation;
	}
	
	public EmergencyManager(){
		createDate = DateTime.now().toDateMidnight().toDate();
		createCarTypeSelectItemList();
		createTravelTypeSelectItemList();
		createPurposeSelectItemList();
		createParkingLocationSelectItemList();
		createReservationList();
		createDepartmentSelectItemList();
		createStaffSelectItemList();
		createCarTypeNoVanSelectItemList();
		createFormStatusFilterOptions();
		createCarTypeFilterOptions();
	}
	
	private void createCarTypeSelectItemList(){
		if(carTypeSelectItemList != null) carTypeSelectItemList.clear();
		for(CarType type : CarType.values()){
			carTypeSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
		}
	}
	
	private void createFormStatusFilterOptions(){
		if(formStatusFilterOptions != null) formStatusFilterOptions.clear();
		
		formStatusFilterOptions.add(new SelectItem("", "เลือกทั้งหมด"));
		List<String> tempList = new ArrayList<String>();
		for(Reservation reservation : reservationList){
			if(!tempList.contains(reservation.getFormStatus().getValue())){
				tempList.add(reservation.getFormStatus().getValue());
			}
		}
		for(String str : tempList){
			formStatusFilterOptions.add(new SelectItem(str, str));
		}
	}
	
	private void createCarTypeFilterOptions(){
		if(carTypeFilterOptions != null) carTypeFilterOptions.clear();
		
		carTypeFilterOptions.add(new SelectItem("", "เลือกทั้งหมด"));
		List<String> tempList = new ArrayList<String>();
		for(Reservation reservation : reservationList){
			if(!tempList.contains(reservation.getCarType().getValue())){
				tempList.add(reservation.getCarType().getValue());
			}
		}
		for(String str : tempList){
			carTypeFilterOptions.add(new SelectItem(str, str));
		}
	}
	
	private void createDepartmentSelectItemList(){
		if(depSelectItemList != null) depSelectItemList.clear();		
		depSelectItemList.add(new SelectItem("-1", "กรุณาเลือกฝ่าย/กอง"));
		
		for(Department dep : employeeController.getDepartments()){
			depSelectItemList.add(new SelectItem(dep.getTDEPS(), dep.getTDEPS()));
		}
	}
	
	private void createStaffSelectItemList(){
		if(staffSelectItemList != null) staffSelectItemList.clear();
		staffSelectItemList.add(new SelectItem("-1", "กรุณาเลือกผู้ขอใช้รถ"));
		
		EmployeeInfo director = employeeController.getDirectorOfDepartment(selectedDep);
		if(director != null){
			staffSelectItemList.add(new SelectItem(director.getPersonalInfo().getSTAFFCODE(), director.getPersonalInfo().getTNAME()+" "+director.getPersonalInfo().getTSURNAME()));
		}
		for(EmployeeInfo employee : employeeController.getEmployeeForDepartment(selectedDep)){
			staffSelectItemList.add(new SelectItem(employee.getPersonalInfo().getSTAFFCODE(), employee.getPersonalInfo().getTNAME()+" "+employee.getPersonalInfo().getTSURNAME()));
		}
	}
	
	private void createTravelTypeSelectItemList(){
		if(travelTypeSelectItemList != null) travelTypeSelectItemList.clear();
		for(TravelType type : TravelType.values()){
			travelTypeSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
		}
	}
	
	private void createPurposeSelectItemList(){
		if(purposeSelectItemList != null) purposeSelectItemList.clear();
		purposeSelectItemList.add(new SelectItem(Long.valueOf(0), "กรุณาเลือกวัตถุประสงค์"));

		for(Purpose purpose : generalController.getAllPurposeList()){
			purposeSelectItemList.add(new SelectItem(purpose.getId(), purpose.getName(), purpose.getDescription()));
		}		
	}
	
	private void createParkingLocationSelectItemList(){
		if(parkingLocationSelectItemList != null) parkingLocationSelectItemList.clear();
		parkingLocationSelectItemList.add(new SelectItem(Long.valueOf(0), "กรุณาเลือกจุดขึ้นรถ"));	
		
		for(ParkingLocation location : generalController.getAllParkingLocationList()){
			parkingLocationSelectItemList.add(new SelectItem(location.getId(), location.getName()));
		}
	}
	
	public void createReservationList(){
		if(reservationList != null) reservationList.clear();
		
		List<Reservation> list = reservationController.getEmergencyReservationList();
		reservationList.addAll(sortReservationList(list));
	}
	
	private void createCarTypeNoVanSelectItemList(){
		if(carTypeNoVanSelectItemList != null) carTypeNoVanSelectItemList.clear();
		
		for(CarType type : CarType.values()){
//			if(!type.equals(CarType.VAN)){
				carTypeNoVanSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
//			}
		}
	}
	
	private List<Reservation> sortReservationList(List<Reservation> list){
		List<Reservation> sortedList = new ArrayList<Reservation>();
		if(list == null){
			return null;
		} else {
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.REQUESTING)){
					sortedList.add(reservation);
				}
			}
//			for(Reservation reservation : list){
//				if(reservation.getFormStatus().equals(FormStatus.LOCAL_APPROVED)){
//					sortedList.add(reservation);
//				}
//			}
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.TRAN_APPROVED)){
					sortedList.add(reservation);
				}
			}
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.CAR_ASSIGNED)){
					sortedList.add(reservation);
				}
			}
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.FINISHED)){
					sortedList.add(reservation);
				}
			}
//			for(Reservation reservation : list){
//				if(reservation.getFormStatus().equals(FormStatus.NO_LOCAL_APPROVE)){
//					sortedList.add(reservation);
//				}
//			}	
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.NO_TRAN_APPROVE)){
					sortedList.add(reservation);
				}
			}		
			return sortedList;
		}
	}
	
	public void purposeSelected(){
		reservation.setPurpose(generalController.getPurpose(selectedPurpose));		
	}
	
	public void parkingLocationSelected(){
		reservation.setParkingLocation(generalController.getParkingLocation(selectedParkingLocation));	
	}
	
	public void depSelected(){
		createStaffSelectItemList();
	}
	
	public void staffSelected(){
		reservation.setRequester(employeeController.getStaff(selectedStaff));
	}
	
	public void carTypeSelected(){
		reservation.setCarType(CarType.find(selectedCarType));
	}
	
	public void travelTypeSelected(){
		reservation.setTravelType(TravelType.find(selectedTravelType));
	}
	
	public void reservationSelected(){
		createDate = reservation.getCreateDate();
		selectedCarType = reservation.getCarType().getID();
		if(reservation.getParkingLocation() != null) {
			selectedParkingLocation = reservation.getParkingLocation().getId();
		} else {
			selectedParkingLocation = Long.valueOf(0);
		}
		if(reservation.getPurpose() != null) {
			selectedPurpose = reservation.getPurpose().getId();
		} else {
			selectedPurpose = Long.valueOf(0);
		}
		selectedTravelType = reservation.getTravelType().getID();
		if(selectedTravellers != null) selectedTravellers.clear();
		if(reservation.getTravellers() != null)	{
//			selectedTravellers = Arrays.asList(StringUtils.split(reservation.getTravellers(), ','));
			List<String> nameList = Arrays.asList(StringUtils.split(reservation.getTravellers(), ','));
			FacesContext context = FacesContext.getCurrentInstance();
			TravellersAutocomplete travellers  =  (TravellersAutocomplete) context.getApplication().evaluateExpressionGet(context, "#{travellersAutocomplete}", TravellersAutocomplete.class);
			for(PersonalInfo personalInfo : travellers.getEmployeeList()){
				for(String string : nameList){
					if(personalInfo.toString().equals(string)){
						selectedTravellers.add(personalInfo);
					}
				}
			}
		}
		if(reservation.getRequester() != null) {
//			EmployeeInfo empInfo = employeeController.getEmployeeInfo(reservation.getRequester());
//			if(empInfo != null){
//				selectedDep = empInfo.getDEPFINANCE();
//			}
			if(reservation.getRequester().getEmployeeInfos().size() != 0){
				selectedDep = reservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE();
			}
			createStaffSelectItemList();
			selectedStaff = reservation.getRequester().getSTAFFCODE();
		}
	}
	
	public void saveReservation(String input){
//		reservation.setTravellers(StringUtils.join(selectedTravellers, ','));
		if(input.equals("novan")) reservation.setCarType(CarType.find(selectedCarTypeNoVan));
		if(selectedTravellers != null && selectedTravellers.size() != 0){
			List<String> stringListOfTravellers = new ArrayList<String>();
			for(PersonalInfo personalInfo : selectedTravellers){
				stringListOfTravellers.add(personalInfo.toString());
				reservation.setTravellers(StringUtils.join(stringListOfTravellers, ','));
			}
		} else {
			reservation.setTravellers(null);
		}	
		if(reservation.getId() == null) {
			reservation.setCreateDate(DateTime.now().toDateMidnight().toDate());
			reservation.setRequestNumber(reservationController.getNextRequestNumber());
		}
		reservation.setFormStatus(FormStatus.TRAN_APPROVED);
		reservation.setEmergency(true);
		reservationController.saveReservation(reservation);
		createReservationList();
		if(isRefresh == false){
			refreshData();
		} else {
			reservation.setId(null);
		}
	}
	
	public void deleteReservation(){
		deletedReservation.setDataStatus(DataStatus.DELETED);
		reservationController.saveReservation(deletedReservation);
		createReservationList();
		deletedReservation = new Reservation();
	}
	
	public void refreshData(){
		selectedTravelType = TravelType.TWOWAY.getID();
		selectedCarType = CarType.VAN.getID();
		selectedPurpose = Long.valueOf(0);
		selectedParkingLocation = Long.valueOf(0);
		reservation = new Reservation();
		deletedReservation = new Reservation();
		if(selectedTravellers != null) selectedTravellers.clear();
		selectedDep = "-1";
		selectedStaff = "-1";
		createDate = DateTime.now().toDateMidnight().toDate();
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();
		if(selectedStaff.equals("-1")){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกผู้ขอใช้รถ"));
		}
		if(reservation.getInternalPhone().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่เบอร์โทรศัพท์"));
		}
		if(selectedPurpose.equals(Long.valueOf(0))){	          
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกวัตถุประสงค์"));
		}
		if(selectedParkingLocation.equals(Long.valueOf(0))){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกจุดขึ้นรถ"));
		}
		if(reservation.getEndDate() == null){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกวันที่ต้องการใช้งาน"));
		}
		if(reservation.getStartTime() == null || reservation.getEndTime() == null){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกเวลาที่ต้องการใช้งาน"));
		}
		if(reservation.getStartingPoint().trim().length() == 0 || reservation.getFinishPoint().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกจุดเริ่มต้นและปลายทาง"));
		}
		if(reservation.getNumberOfCars() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาระบุจำนวนรถที่ต้องการ"));
		}
		if(reservation.getNumberOfTraveller() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาระบุจำนวนผู้โดยสาร"));
		}
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0){
			if(reservation.getCarType().equals(CarType.VAN)){
				if(checkAvailableCar() == true){
					saveReservation("van");
				} else {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("showNotAvailableDialogStaff()");
				}
			} else {
				saveReservation("van");
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
		}
	}
	
	public void deleteCancleButtonClicked(){
		deletedReservation = new Reservation();
	}
	
	private boolean checkAvailableCar(){
		List<ReservationItem> itemList = reservationController.getReservationItemForGivenDate(reservation.getEndDate());
		Interval selectedInterval = new Interval(new DateTime(reservation.getStartTime()), new DateTime(reservation.getEndTime()));
		List<Car> allCarList = generalController.getAllCars();
		List<Routine> routines = generalController.getAllRoutines();
		List<Car> tempList = new ArrayList<Car>();
		tempList.addAll(allCarList);
		List<Car> unavailableCarList = new ArrayList<Car>();
		Interval comparedInterval;
	
		for(ReservationItem item : itemList){
			comparedInterval = new Interval(new DateTime(item.getStartTime()), new DateTime(item.getEndTime()));
			if(selectedInterval.overlaps(comparedInterval) == true){
				for(Queue queue : item.getQueues()){				
					if(!queue.getDataStatus().equals(DataStatus.DELETED)){
						if(queue.getCarType().equals(CarType.VAN)){
							unavailableCarList.add(queue.getCar());	
						}
					}
				}
			}
		}
		
		for(Routine routine : routines){
			if(selectedInterval.overlaps(new Interval(new DateTime(routine.getStartTime()), new DateTime(routine.getEndTime())))){
				if(routine.getCar() != null){
					unavailableCarList.add(routine.getCar());
				}
			}
		}
		
		for(Car car : tempList){
			for(Car car2 : unavailableCarList){
				if(car.getId().equals(car2.getId())){
					allCarList.remove(car);
				}
			}
		}
		if(allCarList.size() == 0){
			return false;
		} else {
			return true;
		}
	}
}
