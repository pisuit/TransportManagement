package transport.manager;

import java.io.Serializable;
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

import transport.controller.ReservationController;
import transport.customtype.FormStatus;
import transport.model.Reservation;
import transport.utils.SessionUtils;

@ManagedBean(name="localApproveManager")
@ViewScoped
public class LocalApproveManager implements Serializable{
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private ReservationController reservationController = new ReservationController();
	private Reservation selectedReservation = new Reservation();
	private List<String> travellerList = new ArrayList<String>();
	private List<SelectItem> formStatusFilterOptions = new ArrayList<SelectItem>();
	private List<SelectItem> carTypeFilterOptions = new ArrayList<SelectItem>();
	
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}
	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	public Reservation getSelectedReservation() {
		return selectedReservation;
	}
	public void setSelectedReservation(Reservation selectedReservation) {
		this.selectedReservation = selectedReservation;
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
	public List<String> getTravellerList() {
		return travellerList;
	}
	public void setTravellerList(List<String> travellerList) {
		this.travellerList = travellerList;
	}
	public LocalApproveManager(){
		createReservationList();
		createCarTypeFilterOptions();
		createFormStatusFilterOptions();
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
			for(Reservation reservation : list){
				if(reservation.getFormStatus().equals(FormStatus.REQUESTING)){
					sortedList.add(reservation);
				}
			}
//			for(Reservation reservation : list){
//				if(reservation.getFormStatus().equals(FormStatus.NO_LOCAL_APPROVE)){
//					sortedList.add(reservation);
//				}
//			}
//			for(Reservation reservation : list){
//				if(reservation.getFormStatus().equals(FormStatus.LOCAL_APPROVED)){
//					sortedList.add(reservation);
//				}
//			}	
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
//			if(argu.equals("approve")){
//				selectedReservation.setFormStatus(FormStatus.LOCAL_APPROVED);
//			} else {
//				selectedReservation.setFormStatus(FormStatus.NO_LOCAL_APPROVE);
//			}
		}
		selectedReservation.setLocalApproveDate(DateTime.now().toDateMidnight().toDate());
		selectedReservation.setDirector(SessionUtils.getUserFromSession().getStaff());
		reservationController.saveReservation(selectedReservation);
		createReservationList();
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
	
	public void clearData(){
		selectedReservation = new Reservation();
	}
}
