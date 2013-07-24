package transport.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.data.cache.EmptyColumnValues;
import net.sf.jasperreports.data.empty.EmptyDataAdapterImpl;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.bcel.generic.Select;
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
import transport.model.EmployeeInfo;
import transport.model.ParkingLocation;
import transport.model.PersonalInfo;
import transport.model.Purpose;
import transport.model.Queue;
import transport.model.Reservation;
import transport.model.ReservationItem;
import transport.model.Routine;
import transport.session.UserSession;
import transport.utils.ReportUtils;
import transport.utils.SessionUtils;

@ManagedBean(name="requestManager")
@ViewScoped
public class RequestManager implements Serializable{
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
	private PersonalInfo requester;
	private boolean isRefresh = false;
	private Reservation viewReservation = new Reservation();
	private List<String> travellerList = new ArrayList<String>();
	private List<String> phoneNumberList = new ArrayList<String>();
	private List<SelectItem> carTypeNoVanSelectItemList = new ArrayList<SelectItem>();
	private String selectedCarTypeNoVan = CarType.TAXI.getID();
	private Reservation canceledReservation = new Reservation();
	private List<SelectItem> formStatusFilterOptions = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeFilterOptions = new ArrayList<SelectItem>();
	private List<String> startPointList = new ArrayList<String>();
	
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

	public Reservation getCanceledReservation() {
		return canceledReservation;
	}

	public void setCanceledReservation(Reservation canceledReservation) {
		this.canceledReservation = canceledReservation;
	}

	public boolean isRefresh() {
		return isRefresh;
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public PersonalInfo getRequester() {
		return requester;
	}

	public void setRequester(PersonalInfo requester) {
		this.requester = requester;
	}

	public Reservation getDeletedReservation() {
		return deletedReservation;
	}

	public void setDeletedReservation(Reservation deletedReservation) {
		this.deletedReservation = deletedReservation;
	}

	public List<SelectItem> getCarTypeNoVanSelectItemList() {
		return carTypeNoVanSelectItemList;
	}

	public void setCarTypeNoVanSelectItemList(
			List<SelectItem> carTypeNoVanSelectItemList) {
		this.carTypeNoVanSelectItemList = carTypeNoVanSelectItemList;
	}

	public ArrayList<SelectItem> getCarTypeSelectItemList() {
		return carTypeSelectItemList;
	}

	public void setCarTypeSelectItemList(ArrayList<SelectItem> carTypeSelectItemList) {
		this.carTypeSelectItemList = carTypeSelectItemList;
	}

	public ArrayList<SelectItem> getParkingLocationSelectItemList() {
		return parkingLocationSelectItemList;
	}

	public void setParkingLocationSelectItemList(
			ArrayList<SelectItem> parkingLocationSelectItemList) {
		this.parkingLocationSelectItemList = parkingLocationSelectItemList;
	}

	public List<String> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<String> travellerList) {
		this.travellerList = travellerList;
	}

	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	public Reservation getViewReservation() {
		return viewReservation;
	}

	public void setViewReservation(Reservation viewReservation) {
		this.viewReservation = viewReservation;
	}

	public String getSelectedCarTypeNoVan() {
		return selectedCarTypeNoVan;
	}

	public void setSelectedCarTypeNoVan(String selectedCarTypeNoVan) {
		this.selectedCarTypeNoVan = selectedCarTypeNoVan;
	}

	public List<SelectItem> getCarTypeFilterOptions() {
		return carTypeFilterOptions;
	}

	public void setCarTypeFilterOptions(List<SelectItem> carTypeFilterOptions) {
		this.carTypeFilterOptions = carTypeFilterOptions;
	}

	public Long getSelectedParkingLocation() {
		return selectedParkingLocation;
	}

	public void setSelectedParkingLocation(Long selectedParkingLocation) {
		this.selectedParkingLocation = selectedParkingLocation;
	}

	public List<SelectItem> getFormStatusFilterOptions() {
		return formStatusFilterOptions;
	}

	public void setFormStatusFilterOptions(List<SelectItem> formStatusFilterOptions) {
		this.formStatusFilterOptions = formStatusFilterOptions;
	}

	public List<PersonalInfo> getSelectedTravellers() {
		return selectedTravellers;
	}

	public void setSelectedTravellers(List<PersonalInfo> selectedTravellers) {
		this.selectedTravellers = selectedTravellers;
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

	public Long getSelectedPurpose() {
		return selectedPurpose;
	}

	public void setSelectedPurpose(Long selectedPurpose) {
		this.selectedPurpose = selectedPurpose;
	}

	public RequestManager() {
		createDate = DateTime.now().toDateMidnight().toDate();
		createCarTypeSelectItemList();
		createTravelTypeSelectItemList();
		createPurposeSelectItemList();
		createParkingLocationSelectItemList();
		createReservationList();
		createPhoneNumberList();
		createFormStatusFilterOptions();
		createCarTypeFilterOptions();
		createStartPoint();
		
		if(SessionUtils.getUserFromSession() != null){
			requester = SessionUtils.getUserFromSession().getStaff();
		}		
	}
	
	private void createCarTypeSelectItemList(){
		if(carTypeSelectItemList != null) carTypeSelectItemList.clear();
		for(CarType type : CarType.values()){
//			if(!type.equals(CarType.RENT_VAN) && !type.equals(CarType.TAXI)){
				carTypeSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
//			}
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
	
	private void createCarTypeNoVanSelectItemList(String input){
		if(carTypeNoVanSelectItemList != null) carTypeNoVanSelectItemList.clear();
		
		if(input.equals("prior")) carTypeNoVanSelectItemList.add(new SelectItem(CarType.RENT_VAN.getID(), CarType.RENT_VAN.getValue()));
		carTypeNoVanSelectItemList.add(new SelectItem(CarType.TAXI.getID(), CarType.TAXI.getValue()));
//		for(CarType type : CarType.values()){
//			if(!type.equals(CarType.RENT_VAN) && !type.equals(CarType.PICKUP)){
//				carTypeNoVanSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
//			}
//		}
	}
	
	public void createReservationList(){
		if(reservationList != null) reservationList.clear();
		
		String department = SessionUtils.getUserFromSession().getStaff().getEmployeeInfos().get(0).getDEPFINANCE();
		if(department.equals("ฝจ.")){
			department = SessionUtils.getUserFromSession().getStaff().getEmployeeInfos().get(0).getDEPPERSON();
		}
		List<Reservation> list = reservationController.getAllReservationList(department);
		reservationList.addAll(sortReservationList(list));	
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
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.CANCELED)){
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
		reservation.setRequester(SessionUtils.getUserFromSession().getStaff());
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
		canceledReservation = new Reservation();
		if(selectedTravellers != null) selectedTravellers.clear();
		createDate = DateTime.now().toDateMidnight().toDate();;
	}
	
	public void printReport(){
		try {
			JasperReport report = null;
			report = (JasperReport)JRLoader.loadObject(this.getClass().getResource("/transport/reports/request.jasper"));
			HashMap parameters = new HashMap();
			
			String dep;
			if(viewReservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE().equals("ฝจ.")){
				dep = viewReservation.getRequester().getEmployeeInfos().get(0).getDEPPERSON();
			} else {
				dep = viewReservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE();
			}
			parameters.put("requester", viewReservation.getRequester().toString());
			parameters.put("department", dep);
			parameters.put("phone", viewReservation.getInternalPhone());
			parameters.put("remark", viewReservation.getRemark());
			parameters.put("startPoint", viewReservation.getStartingPoint());
			parameters.put("endPoint", viewReservation.getFinishPoint());
			parameters.put("useDate", viewReservation.getEndDate());
			parameters.put("startTime", viewReservation.getStartTime());
			parameters.put("endTime", viewReservation.getEndTime());
			parameters.put("carType", viewReservation.getCarType().getValue());
			parameters.put("formNumber", viewReservation.getRequestNumber());
			if(viewReservation.getDirector() != null) parameters.put("position", "ผ"+viewReservation.getDirector().getEmployeeInfos().get(0).getDEPPERSON());
			if(viewReservation.getCarType().equals(CarType.TAXI)){
				parameters.put("message", "ไม่สามารถจัดรถให้ได้เนื่องจากรถในภารกิจระหว่างวันเต็ม เห็นควรให้ใช้รถรับจ้างสาธารณะ");
			}
			if(viewReservation.getCarType().equals(CarType.RENT_VAN) || viewReservation.getCarType().equals(CarType.PICKUP)){
				parameters.put("message", "จัดจ้าง ผู้รับจ้าง................................ค่าเช่ารถ..........ค่าเชื้อเพลิง..........");
			}
			if(viewReservation.getServiceType() != null) parameters.put("staff", viewReservation.getStaff().toString());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			
//			JasperExportManager.exportReportToPdfFile(jasperPrint,"C:/summary.pdf");
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			ReportUtils.displayPdfReport(bytes);	
			
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void validateInput(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();
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
		if(reservation.getRefDocument().trim().length() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาใส่เอกสารอ้างอิง (กรณีที่ยังไม่ได้รับเอกสาร ให้พิมพ์ว่ายังไม่ได้รับเอกสาร)"));
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
		if(selectedCarType.equals(CarType.VAN.getID()) && reservation.getNumberOfCars() >= 2 && reservation.getNumberOfTraveller() < 12){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "จำนวนรถที่ขอมากกว่า 1 คัน ผู้ร่วมเดินทางต้องมากกว่า 12 คน"));
		}
		if(reservation.getNumberOfTraveller() > 1 && selectedTravellers != null){
			if(selectedTravellers.size() != reservation.getNumberOfTraveller()){
				messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "จำนวนรายชื่อผู้ร่วมเดินทางไม่ตรงกับจำนวนผู้โดยสาร"));
			}
		}
		if(reservation.getNumberOfTraveller() == 0){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาระบุจำนวนผู้โดยสาร"));
		}
//		if(selectedTravellers.size() != reservation.getNumberOfTraveller()){
//			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "จำนวนรายชื่อผู้ร่วมเดินทางกับจำนวนผู้โดยสารไม่เท่ากัน"));
//		}
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		if(messageList.size() == 0){
			if(reservation.getCarType().equals(CarType.VAN)){
				if(checkAvailableCar() == true){
					saveReservation("van");
				} else {
					if(reservation.getPurpose().isPrior() == true){
						createCarTypeNoVanSelectItemList("prior");
					} else {
						createCarTypeNoVanSelectItemList("noprior");
					}
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("showNotAvailableDialog()");
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
	
	public void cancleButtonClicked(){
		canceledReservation = new Reservation();
	}
	
	public void infoDialogClosed(){
		viewReservation = new Reservation();
	}
	
	public void createTravellerList(){
		if(travellerList != null) travellerList.clear();
		
		if(viewReservation != null){
			if(viewReservation.getTravellers() != null){
				for(String name : StringUtils.split(viewReservation.getTravellers(), ',')){
					travellerList.add(name);
				}
			}
		}
	}
	
	public void createPhoneNumberList(){
		phoneNumberList.addAll(reservationController.getPhoneNumberList(SessionUtils.getUserFromSession().getStaff()));
	}
	
	public void createStartPoint(){
		startPointList.addAll(reservationController.getStartPointList());
	}
	
	public List<String> startPointAutocomplete(String input){
		List<String> pointList = new ArrayList<String>();
		
		for(String str : startPointList){
			if(StringUtils.startsWith(str, input)){
				pointList.add(str);
			}
		}
		return pointList;
	}
	
	public List<String> phoneAutocomplete(String input){
		List<String> phoneList = new ArrayList<String>();

		for(String string : phoneNumberList){
			if(StringUtils.contains(string, input)){
				phoneList.add(string);
			}
		}
		return phoneList;
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
	
	public void calcelReservation(){
		canceledReservation.setFormStatus(FormStatus.CANCELED);
		reservationController.saveReservation(canceledReservation);
		reservation = new Reservation();
		createReservationList();
	}

	public List<String> getStartPointList() {
		return startPointList;
	}

	public void setStartPointList(List<String> startPointList) {
		this.startPointList = startPointList;
	}
	
//	public void validateNotAvailable(){
//		List<FacesMessage> messageList = new ArrayList<FacesMessage>();
//		System.out.println(selectedCarType);
//		if(selectedCarType.equals(CarType.VAN.getID())){
//			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกประเภทรถใหม่อีกครั้ง"));
//		}
//		for(FacesMessage message : messageList){
//			FacesContext.getCurrentInstance().addMessage(null, message);
//		}
//		if(messageList.size() == 0) saveReservation("novan");
//		
//	}
	
}
