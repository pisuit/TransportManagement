package transport.manager;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import transport.controller.AdminController;
import transport.controller.ReservationController;
import transport.customtype.CarType;
import transport.customtype.FormStatus;
import transport.model.Reservation;
import transport.model.User;
import transport.push.Growl;
import transport.utils.SessionUtils;

@ManagedBean(name="transportApproveManager")
@ViewScoped
public class TransportApproveManager implements Serializable{
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private ReservationController reservationController = new ReservationController();
	private Reservation selectedReservation = new Reservation();
	private List<String> travellerList = new ArrayList<String>();
	private List<SelectItem> formStatusFilterOptions = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeFilterOptions = new ArrayList<SelectItem>();
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat tf = new SimpleDateFormat("HH:mm");
	private AdminController adminController = new AdminController();
	
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
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

	public Reservation getSelectedReservation() {
		return selectedReservation;
	}

	public void setSelectedReservation(Reservation selectedReservation) {
		this.selectedReservation = selectedReservation;
	}

	public List<String> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<String> travellerList) {
		this.travellerList = travellerList;
	}

	public TransportApproveManager(){
		createReservationList();
		createFormStatusFilterOptions();
		createCarTypeFilterOptions();
	}
	
	public void createReservationList(){
		if(reservationList != null) reservationList.clear();
		
		List<Reservation> list = reservationController.getLocalApprovedReservationList();
		reservationList.addAll(sortReservationList(list));
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
	
	private List<Reservation> sortReservationList(List<Reservation> list){
		List<Reservation> sortedList = new ArrayList<Reservation>();
		if(list == null){
			return null;
		} else {			
//			for(Reservation reservation : list){
//				if(reservation.getFormStatus().equals(FormStatus.LOCAL_APPROVED)){
//					sortedList.add(reservation);
//				}
//			}	
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.REQUESTING)){
					sortedList.add(reservation);
				}
			}
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.NO_TRAN_APPROVE)){
					sortedList.add(reservation);
				}
			}
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
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.CANCELED)){
					sortedList.add(reservation);
				}
			}
			return sortedList;
		}
	}
	
	public void approveReservation(String argu){
		if(!selectedReservation.isEmergency()){
			if(argu.equals("approve")) {
				selectedReservation.setFormStatus(FormStatus.TRAN_APPROVED);			
			} else {
				selectedReservation.setFormStatus(FormStatus.NO_TRAN_APPROVE);
			}
		}
		selectedReservation.setTransportApproveDate(DateTime.now().toDateMidnight().toDate());
		selectedReservation.setServiceDirector(SessionUtils.getUserFromSession().getStaff());
		reservationController.saveReservation(selectedReservation);
		createReservationList();
		
		if(argu.equals("approve")){
			try {
				List<User> operators = adminController.getOperators();
				if(operators != null){
					for(User user : operators){
						if(user.getIpAddress() != null &&  user.getIpAddress().trim().length() > 0){
							Growl.sendNotification("New Request !!", "ผู้ขอ: "+selectedReservation.getRequester().toString()+
									"\n"+"เลขที่ใบขอรถ: "+selectedReservation.getRequestNumber()+
									"\n"+"ประเภทรถ: "+selectedReservation.getCarType().getValue()+
									"\n"+"วันที่: "+df.format(selectedReservation.getEndDate())+
									"\n"+"เวลา: "+tf.format(selectedReservation.getStartTime())+" - "+tf.format(selectedReservation.getEndTime())+
									"\n"+"ต้นทาง: "+selectedReservation.getStartingPoint()+
									"\n"+"ปลายทาง: "+selectedReservation.getFinishPoint(), selectedReservation.getRequester().getSTAFFCODE(), user.getIpAddress());
						}
					}		
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
//		try {
//			if(argu.equals("approve")){
//				Growl.sendNotification("New Request !!", "ผู้ขอ: "+selectedReservation.getRequester().toString()+
//		        		"\n"+"เลขที่ใบขอรถ: "+selectedReservation.getRequestNumber()+
//		        		"\n"+"ประเภทรถ: "+selectedReservation.getCarType().getValue()+
//		        		"\n"+"วันที่: "+df.format(selectedReservation.getEndDate())+
//		        		"\n"+"เวลา: "+tf.format(selectedReservation.getStartTime())+" - "+tf.format(selectedReservation.getEndTime())+
//		        		"\n"+"ปลายทาง: "+selectedReservation.getFinishPoint(), selectedReservation.getRequester().getSTAFFCODE());
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
		selectedReservation = new Reservation();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "สำเร็จ !!", "บันทึกข้อมูลเรียบร้อยแล้ว"));
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
	
	public void refreshData(){
		selectedReservation = new Reservation();
	}
}
