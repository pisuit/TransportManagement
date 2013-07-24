package transport.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

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
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.context.RequestContext;

import transport.application.TravellersAutocomplete;
import transport.controller.AdminController;
import transport.controller.GeneralController;
import transport.controller.ReservationController;
import transport.customtype.CarType;
import transport.customtype.DataStatus;
import transport.customtype.FormStatus;
import transport.model.Car;
import transport.model.PersonalInfo;
import transport.model.Purpose;
import transport.model.Queue;
import transport.model.Rental;
import transport.model.Reservation;
import transport.model.ReservationItem;
import transport.model.Routine;
import transport.utils.ReportUtils;
import transport.utils.SessionUtils;
import transprot.reportmodel.WorkCardReportModel;

@ManagedBean(name="carAssignManager")
@ViewScoped
public class CarAssignManager implements Serializable{
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private Reservation selectedReservation = new Reservation();
	private ReservationController reservationController = new ReservationController();
	private GeneralController generalController = new GeneralController();
	private ArrayList<ReservationItem> reservationItemList = new ArrayList<ReservationItem>();
	private Reservation editReservation = new Reservation();
	private ReservationItem editReservationItem = new ReservationItem();
	private List<String> travellerList = new ArrayList<String>();
	private ArrayList<Queue> queueItemList = new ArrayList<Queue>();
	private ArrayList<SelectItem> carSelectItemList = new ArrayList<SelectItem>();
	private ReservationItem selectedReservationItem = new ReservationItem();
	private boolean isReadyFlag = false;
	private Queue editQueue = new Queue();
	private Queue selectedQueue = new Queue();
	private Long selectedCarID = Long.valueOf(-1);
	private ArrayList<Reservation> assignedReservationList = new ArrayList<Reservation>();
	private List<SelectItem> formStatusFilterOptionsTable1 = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeFilterOptionsTable1 = new ArrayList<SelectItem>();
	private List<SelectItem> formStatusFilterOptionsTable2 = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeFilterOptionsTable2 = new ArrayList<SelectItem>();
	private Date today = DateTime.now().toDateMidnight().toDate();
	private List<SelectItem> rentalSelectItemList = new ArrayList<SelectItem>();
	private Long selectedRental = Long.valueOf(-1);
	private AdminController adminController = new AdminController();
	private List<SelectItem> carTypeSelectItemList = new ArrayList<SelectItem>();
	private List<PersonalInfo> selectedTravellers = new ArrayList<PersonalInfo>();
	
	public Reservation getSelectedReservation() {
		return selectedReservation;
	}

	public void setSelectedReservation(Reservation selectedReservation) {
		this.selectedReservation = selectedReservation;
	}

	public ArrayList<Queue> getQueueItemList() {
		return queueItemList;
	}

	public void setQueueItemList(ArrayList<Queue> queueItemList) {
		this.queueItemList = queueItemList;
	}

	public List<SelectItem> getRentalSelectItemList() {
		return rentalSelectItemList;
	}

	public void setRentalSelectItemList(List<SelectItem> rentalSelectItemList) {
		this.rentalSelectItemList = rentalSelectItemList;
	}

	public Long getSelectedRental() {
		return selectedRental;
	}

	public void setSelectedRental(Long selectedRental) {
		this.selectedRental = selectedRental;
	}

	public ReservationItem getSelectedReservationItem() {
		return selectedReservationItem;
	}

	public void setSelectedReservationItem(ReservationItem selectedReservationItem) {
		this.selectedReservationItem = selectedReservationItem;
	}

	public ArrayList<ReservationItem> getReservationItemList() {
		return reservationItemList;
	}

	public void setReservationItemList(
			ArrayList<ReservationItem> reservationItemList) {
		this.reservationItemList = reservationItemList;
	}

	public Reservation getEditReservation() {
		return editReservation;
	}

	public void setEditReservation(Reservation editReservation) {
		this.editReservation = editReservation;
	}

	public ArrayList<Reservation> getAssignedReservationList() {
		return assignedReservationList;
	}

	public void setAssignedReservationList(
			ArrayList<Reservation> assignedReservationList) {
		this.assignedReservationList = assignedReservationList;
	}

	public Date getToday() {
		return today;
	}

	public Queue getSelectedQueue() {
		return selectedQueue;
	}

	public void setSelectedQueue(Queue selectedQueue) {
		this.selectedQueue = selectedQueue;
	}

	public Queue getEditQueue() {
		return editQueue;
	}

	public void setEditQueue(Queue editQueue) {
		this.editQueue = editQueue;
	}

	public List<String> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<String> travellerList) {
		this.travellerList = travellerList;
	}

	public List<SelectItem> getFormStatusFilterOptionsTable1() {
		return formStatusFilterOptionsTable1;
	}

	public void setFormStatusFilterOptionsTable1(
			List<SelectItem> formStatusFilterOptionsTable1) {
		this.formStatusFilterOptionsTable1 = formStatusFilterOptionsTable1;
	}

	public List<SelectItem> getCarTypeFilterOptionsTable1() {
		return carTypeFilterOptionsTable1;
	}

	public void setCarTypeFilterOptionsTable1(
			List<SelectItem> carTypeFilterOptionsTable1) {
		this.carTypeFilterOptionsTable1 = carTypeFilterOptionsTable1;
	}

	public List<SelectItem> getFormStatusFilterOptionsTable2() {
		return formStatusFilterOptionsTable2;
	}

	public void setFormStatusFilterOptionsTable2(
			List<SelectItem> formStatusFilterOptionsTable2) {
		this.formStatusFilterOptionsTable2 = formStatusFilterOptionsTable2;
	}

	public List<SelectItem> getCarTypeFilterOptionsTable2() {
		return carTypeFilterOptionsTable2;
	}

	public void setCarTypeFilterOptionsTable2(
			List<SelectItem> carTypeFilterOptionsTable2) {
		this.carTypeFilterOptionsTable2 = carTypeFilterOptionsTable2;
	}

	public Long getSelectedCarID() {
		return selectedCarID;
	}

	public void setSelectedCarID(Long selectedCarID) {
		this.selectedCarID = selectedCarID;
	}

	public ArrayList<SelectItem> getCarSelectItemList() {
		return carSelectItemList;
	}

	public void setCarSelectItemList(ArrayList<SelectItem> carSelectItemList) {
		this.carSelectItemList = carSelectItemList;
	}

	public ReservationItem getEditReservationItem() {
		return editReservationItem;
	}

	public void setEditReservationItem(ReservationItem editReservationItem) {
		this.editReservationItem = editReservationItem;
	}

	public boolean isReadyFlag() {
		return isReadyFlag;
	}

	public void setReadyFlag(boolean isReadyFlag) {
		this.isReadyFlag = isReadyFlag;
	}

	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	public CarAssignManager(){
		createReservationList();
		createAssignedReservationList();
		createFormStatusFilterOptionsTable1();
		createCarTypeFilterOptionsTable1();
		createFormStatusFilterOptionsTable2();
		createCarTypeFilterOptionsTable2();
		createRentalSelectItemList();
		createCarTypeSelectItemList();
	}
	
	private void createRentalSelectItemList(){
		if(rentalSelectItemList != null) rentalSelectItemList.clear();
		
		rentalSelectItemList.add(new SelectItem(Long.valueOf(-1), "ผู้ให้เช่ารถ"));
		for(Rental rental : adminController.getRentalList()){
			rentalSelectItemList.add(new SelectItem(rental.getId(), rental.getCompanyName()));
		}
	}
	
	public void createReservationList(){
		if(reservationList != null) reservationList.clear();
		
		reservationList.addAll(reservationController.getReservationListForAssignment());
	}
	
	private void createFormStatusFilterOptionsTable1(){
		if(formStatusFilterOptionsTable1 != null) formStatusFilterOptionsTable1.clear();
		
		formStatusFilterOptionsTable1.add(new SelectItem("", "เลือกทั้งหมด"));
		List<String> tempList = new ArrayList<String>();
		for(Reservation reservation : reservationList){
			if(!tempList.contains(reservation.getFormStatus().getValue())){
				tempList.add(reservation.getFormStatus().getValue());
			}
		}
		for(String str : tempList){
			formStatusFilterOptionsTable1.add(new SelectItem(str, str));
		}
	}
	
	public void copyReservation(){
		Long tempID = new Long(editReservation.getId());
		Reservation tempReservation = new Reservation();
		tempReservation.setId(tempID);
		
		editReservation.setMaster(tempReservation);
		editReservation.setId(null);
		editReservation.setRequestNumber(reservationController.getNextRequestNumber());
		reservationController.saveReservation(editReservation);
		createReservationList();
		editReservation = new Reservation();
	}
	
	public void deleteReservation(){
		editReservation.setDataStatus(DataStatus.DELETED);
		reservationController.saveReservation(editReservation);
		createReservationList();
		editReservation = new Reservation();
	}
	
	public void createTravellersList(){
		selectedTravellers.clear();
		
		if(editReservation.getTravellers() != null){
			List<String> nameList = Arrays.asList(StringUtils.split(editReservation.getTravellers(), ','));
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
	
	public void updateTravellers(){
		if(selectedTravellers != null && selectedTravellers.size() != 0){
			List<String> stringListOfTravellers = new ArrayList<String>();
			for(PersonalInfo personalInfo : selectedTravellers){
				stringListOfTravellers.add(personalInfo.toString());
				editReservation.setTravellers(StringUtils.join(stringListOfTravellers, ','));
			}
			
			reservationController.saveReservation(editReservation);
			
			selectedTravellers.clear();
			editReservation = new Reservation();
		}
	}
	
	private void createCarTypeFilterOptionsTable1(){
		if(carTypeFilterOptionsTable1 != null) carTypeFilterOptionsTable1.clear();
		
		carTypeFilterOptionsTable1.add(new SelectItem("", "เลือกทั้งหมด"));
		List<String> tempList = new ArrayList<String>();
		for(Reservation reservation : reservationList){
			if(!tempList.contains(reservation.getCarType().getValue())){
				tempList.add(reservation.getCarType().getValue());
			}
		}
		for(String str : tempList){
			carTypeFilterOptionsTable1.add(new SelectItem(str, str));
		}
	}
	
	private void createCarTypeSelectItemList(){
		carTypeSelectItemList.clear();
		
		for(CarType type : CarType.values()){
			carTypeSelectItemList.add(new SelectItem(type.getID(), type.getValue()));
		}
	}
	
	public void printReport(){
		try {
			JasperReport report = null;
			report = (JasperReport)JRLoader.loadObject(this.getClass().getResource("/transport/reports/request.jasper"));
			HashMap parameters = new HashMap();
			
			String dep;
			if(editReservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE().equals("ฝจ.")){
				dep = editReservation.getRequester().getEmployeeInfos().get(0).getDEPPERSON();
			} else {
				dep = editReservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE();
			}
			parameters.put("requester", editReservation.getRequester().toString());
			parameters.put("department", dep);
			parameters.put("phone", editReservation.getInternalPhone());
			parameters.put("remark", editReservation.getRemark());
			parameters.put("startPoint", editReservation.getStartingPoint());
			parameters.put("endPoint", editReservation.getFinishPoint());
			parameters.put("useDate", editReservation.getEndDate());
			parameters.put("startTime", editReservation.getStartTime());
			parameters.put("endTime", editReservation.getEndTime());
			parameters.put("carType", editReservation.getCarType().getValue());
			parameters.put("formNumber", editReservation.getRequestNumber());
			if(editReservation.getDirector() != null) parameters.put("position", "ผ"+editReservation.getDirector().getEmployeeInfos().get(0).getDEPPERSON());
			if(editReservation.getCarType().equals(CarType.TAXI)){
				parameters.put("message", "ไม่สามารถจัดรถให้ได้เนื่องจากรถในภารกิจระหว่างวันเต็ม เห็นควรให้ใช้รถรับจ้างสาธารณะ");
			}
			if(editReservation.getCarType().equals(CarType.RENT_VAN) || editReservation.getCarType().equals(CarType.PICKUP)){
				parameters.put("message", "จัดจ้าง ผู้รับจ้าง..........................................................................ค่าเช่ารถ..........................ค่าเชื้อเพลิง..........................");
			}
			if(editReservation.getStaff() != null) parameters.put("staff", editReservation.getStaff().toString());
			
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
	
	private void createFormStatusFilterOptionsTable2(){
		if(formStatusFilterOptionsTable2 != null) formStatusFilterOptionsTable2.clear();
		
		formStatusFilterOptionsTable2.add(new SelectItem("", "เลือกทั้งหมด"));
		List<String> tempList = new ArrayList<String>();
		for(Reservation reservation : assignedReservationList){
			if(!tempList.contains(reservation.getFormStatus().getValue())){
				tempList.add(reservation.getFormStatus().getValue());
			}
		}
		for(String str : tempList){
			formStatusFilterOptionsTable2.add(new SelectItem(str, str));
		}
	}
	
	private void createCarTypeFilterOptionsTable2(){
		if(carTypeFilterOptionsTable2 != null) carTypeFilterOptionsTable2.clear();
		
		carTypeFilterOptionsTable2.add(new SelectItem("", "เลือกทั้งหมด"));
		List<String> tempList = new ArrayList<String>();
		for(Reservation reservation : assignedReservationList){
			if(!tempList.contains(reservation.getCarType().getValue())){
				tempList.add(reservation.getCarType().getValue());
			}
		}
		for(String str : tempList){
			carTypeFilterOptionsTable2.add(new SelectItem(str, str));
		}
	}
	
	public void createReservationItemList(){
		if(reservationItemList != null) reservationItemList.clear();
	
		reservationItemList.addAll(reservationController.getReservationItems(editReservation));
		
		editReservationItem.setUseDate(editReservation.getEndDate());
		editReservationItem.setStartTime(editReservation.getStartTime());
		editReservationItem.setEndTime(editReservation.getEndTime());
//		editReservationItem.setCarType(editReservation.getCarType());
		if(editReservation.getFormStatus().equals(FormStatus.CAR_ASSIGNED)){
			isReadyFlag = true;
		} else if(editReservation.getFormStatus().equals(FormStatus.NOT_AVAILABLE)){
			isReadyFlag = true;
		} else {
			isReadyFlag = false;
		} 
	}
	
	public void createAssignedReservationList(){
		if(assignedReservationList != null) assignedReservationList.clear();
		
		List<Reservation> list = reservationController.getAssignedReservationList();
//		assignedReservationList.addAll(sortReservationList(list));
		if(list != null){
			assignedReservationList.addAll(list);
		}
	}
	
	private List<Reservation> sortReservationList(List<Reservation> list){
		List<Reservation> sortedList = new ArrayList<Reservation>();
		if(list == null){
			return null;
		} else {			
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
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.CANCELED)){
					sortedList.add(reservation);
				}
			}
			return sortedList;
		}
	}
	
	public void saveReservationItem(){
		editReservationItem.setReservation(editReservation);
		reservationController.saveReservationItem(editReservationItem);
		createReservationItemList();
		editReservationItem = new ReservationItem();
		editReservationItem.setUseDate(editReservation.getEndDate());
		editReservationItem.setStartTime(editReservation.getStartTime());
		editReservationItem.setEndTime(editReservation.getEndTime());
	}
	
	public void createTravellerList(){
		if(travellerList != null) travellerList.clear();
		if(selectedReservation != null){
			if(selectedReservation.getTravellers() != null){
				for(String name : StringUtils.split(selectedReservation.getTravellers(), ',')){
					travellerList.add(name);
				}
			}
		}
	}
	
	public void createQueueList(){
		if(queueItemList != null) queueItemList.clear();
	
		queueItemList.addAll(reservationController.getQueueList(selectedReservationItem));
	}
	
	public void createQueueListForCloseJob(){
		if(queueItemList != null) queueItemList.clear();
	
		queueItemList.addAll(reservationController.getQueueListForCloseJob(editReservation));
	}
	
	public void deleteReservationItem(){
		if(selectedReservationItem.getQueues() != null){
			for(Queue queue : selectedReservationItem.getQueues()){
				reservationController.deleteQueue(queue);
			}
		}
		reservationController.deleteReservationItem(selectedReservationItem);
		createReservationItemList();
	}
	
	public void changeReservationCarType(){
		reservationController.changeCarType(editReservation);
		reservationItemList.clear();
	}
	
	public void setReservationStatus(String status){	
		editReservation.setStaff(SessionUtils.getUserFromSession().getStaff());
		editReservation.setAssignDate(DateTime.now().toDateMidnight().toDate());
		if(status.equals("ready")){		
			editReservation.setFormStatus(FormStatus.CAR_ASSIGNED);
			Reservation reservation = reservationController.saveReservation(editReservation);
			isReadyFlag = true;	
			
			if(reservation.getCarType().equals(CarType.RENT_VAN) || reservation.getCarType().equals(CarType.PICKUP)){
				ReservationItem item = new ReservationItem();
				item.setReservation(reservation);
				item.setUseDate(reservation.getEndDate());
				item.setStartTime(reservation.getStartTime());
				item.setEndTime(reservation.getEndTime());
				
				ReservationItem reservationItem = reservationController.saveReservationItem(item);
				
				Queue queue = new Queue();
				if(reservation.getCarType().equals(CarType.RENT_VAN)){
					queue.setCarType(CarType.RENT_VAN);
				} else {
					queue.setCarType(CarType.PICKUP);
				}
				queue.setReservationItem(reservationItem);
				
				reservationController.saveQueue(queue);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
		} else if(status.equals("notavailable")){
			editReservation.setFormStatus(FormStatus.NOT_AVAILABLE);
			reservationController.saveReservation(editReservation);
			isReadyFlag = true;
		} else {		
			editReservation.setFormStatus(FormStatus.TRAN_APPROVED);
			Reservation reservation = reservationController.saveReservation(editReservation);
			isReadyFlag = false;
			
			if(reservation.getCarType().equals(CarType.RENT_VAN) || reservation.getCarType().equals(CarType.PICKUP)){
				List<ReservationItem> items = reservationController.getReservationItems(reservation);
				List<Queue> queues = reservationController.getQueueList(items.get(0));
				
				reservationController.deleteReservationItem(items.get(0));
				reservationController.deleteQueue(queues.get(0));
			}
		}
		createReservationList();
		createAssignedReservationList();
		editReservation = new Reservation();
	}
	
	public void carSelected(){
		Car car = generalController.getCar(selectedCarID);
		
		if(car != null){
			editQueue.setCar(car);
			editQueue.setDriver(car.getDriver());
			editQueue.setReservationItem(selectedReservationItem);
		}
	}
	
	public void saveQueue(){
		reservationController.saveQueue(editQueue);
		findAvailableCars();
		refreshData();
	}
	
	public void saveQueueInfo(){
		selectedQueue.setRental(adminController.getRental(selectedRental));
		reservationController.saveQueue(selectedQueue);
		createQueueListForCloseJob();
		selectedQueue = new Queue();
		selectedRental = Long.valueOf(-1);
		
	}
	
	public void deleteQueue(){
		editQueue.setDataStatus(DataStatus.DELETED);
		reservationController.saveQueue(editQueue);
		findAvailableCars();
		refreshData();
	}
	
	private void refreshData(){
		editQueue = new Queue();
		selectedCarID = Long.valueOf(-1);
	}	
	
	public void findAvailableCars(){
		if(carSelectItemList != null) carSelectItemList.clear();
		carSelectItemList.add(new SelectItem(Long.valueOf(-1), "เลือกรถ"));
		List<ReservationItem> itemList = reservationController.getReservationItemForGivenDate(selectedReservationItem.getUseDate());
		Interval selectedInterval = new Interval(new DateTime(selectedReservationItem.getStartTime()), new DateTime(selectedReservationItem.getEndTime()));
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
			if(routine.getCar() != null){
				if(selectedInterval.overlaps(new Interval(new DateTime(routine.getStartTime()), new DateTime(routine.getEndTime())))){
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
		
		for(Car car : allCarList){
			carSelectItemList.add(new SelectItem(car.getId(), car.getName()));
		}
		createQueueList();
		refreshData();
	}
	
	public void closeJob(){
		editReservation.setFormStatus(FormStatus.FINISHED);
		reservationController.saveReservation(editReservation);
		createAssignedReservationList();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
	}
	
	public void printJob(){
		try {
			JasperReport report = null;
			DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
			List<WorkCardReportModel> reportModelList = new ArrayList<WorkCardReportModel>();
			Reservation fullDetailReservation = reservationController.getMoreReservationDetail(editReservation);
			report = (JasperReport)JRLoader.loadObject(this.getClass().getResource("/transport/reports/workcard.jasper"));
			HashMap parameters = new HashMap();
			WorkCardReportModel model;
			if(fullDetailReservation.getReservationItems() != null){
				for(ReservationItem item : fullDetailReservation.getReservationItems()){
					if(item.getQueues() != null){
						for(Queue queue : item.getQueues()){
							model = new WorkCardReportModel();
							if(queue.getCar() != null){
								model.setCarName(queue.getCar().getName());
								model.setRegister(queue.getCar().getRegisterNumber());
							}
							if(queue.getDriver() != null){
								model.setDriverName(queue.getDriver().toString());
								model.setDriverPhone(queue.getDriver().getPhoneNumber());
							}
							model.setEndPoint(editReservation.getFinishPoint());
							model.setId(queue.getId());							
							if(editReservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE().equals("ฝจ.")){
								model.setRequesterDep(editReservation.getRequester().getEmployeeInfos().get(0).getDEPPERSON());
							} else {
								model.setRequesterDep(editReservation.getRequester().getEmployeeInfos().get(0).getDEPFINANCE());
							}						
							model.setRequesterName(editReservation.getRequester().toString());
							model.setRefDoc(editReservation.getRefDocument());
							model.setRequesterPhone(editReservation.getInternalPhone());
							model.setStartPoint(editReservation.getStartingPoint());
							
							if(editReservation.getTravellers() != null && editReservation.getTravellers().trim().length() != 0){
								String[] travellers = editReservation.getTravellers().split(",");
								model.setTravellers(StringUtils.join(travellers, System.getProperty("line.separator")));
							}
							
							model.setTravelType(editReservation.getTravelType().getValue());
							model.setUseDate(editReservation.getEndDate());
							model.setTime(new DateTime(item.getStartTime()).toString(fmt)+" - "+new DateTime(item.getEndTime()).toString(fmt));
							if(editReservation.getParkingLocation() != null){
								model.setMeetingPoint(editReservation.getParkingLocation().getName());
							}
							if(queue.getCar() != null){
								model.setCarType(queue.getCar().getCarType().getValue());
							} else {
								model.setCarType(editReservation.getCarType().getValue());
							}
							reportModelList.add(model);
						}
					}
				}
					
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(reportModelList));
						
//				JasperExportManager.exportReportToPdfFile(jasperPrint,"C:/summary.pdf");
				byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
				ReportUtils.displayPdfReport(bytes);	
			}
													

			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void validateReservationItem(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();
		
		if(editReservationItem.getStartTime() == null || editReservationItem.getEndTime() == null){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกเวลา"));
		}
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveReservationItem();
	}
	
	public void validateQueue(){
		List<FacesMessage> messageList = new ArrayList<FacesMessage>();
		
		if(selectedCarID.equals(Long.valueOf(-1))){
			messageList.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "ข้อมูลไม่ครบถ้วน !!", "กรุณาเลือกรถตู้"));
		}
		for(FacesMessage message : messageList){
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(messageList.size() == 0) saveQueue();
	}
	
	public void infoDialogClosed(){
		selectedReservation = new Reservation();
	}
	
	public void carAssignDialogClosed(){
		editReservation = new Reservation();
	}

	public List<SelectItem> getCarTypeSelectItemList() {
		return carTypeSelectItemList;
	}

	public void setCarTypeSelectItemList(List<SelectItem> carTypeSelectItemList) {
		this.carTypeSelectItemList = carTypeSelectItemList;
	}

	public List<PersonalInfo> getSelectedTravellers() {
		return selectedTravellers;
	}

	public void setSelectedTravellers(List<PersonalInfo> selectedTravellers) {
		this.selectedTravellers = selectedTravellers;
	}
}
