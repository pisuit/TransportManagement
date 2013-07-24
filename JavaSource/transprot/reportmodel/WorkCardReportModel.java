package transprot.reportmodel;

import java.util.Date;

public class WorkCardReportModel {
	private Long id;
	private String carName;
	private String driverName;
	private String register;
	private Date useDate;
	private String requesterName;
	private String requesterDep;
	private String startPoint;
	private String endPoint;
	private String travelType;
	private String meetingPoint;
	private String driverPhone;
	private String carType;
	private String time;
	private String requesterPhone;
	private String refDoc;
	private String travellers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public String getRequesterDep() {
		return requesterDep;
	}
	public void setRequesterDep(String requesterDep) {
		this.requesterDep = requesterDep;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getRequesterPhone() {
		return requesterPhone;
	}
	public void setRequesterPhone(String requesterPhone) {
		this.requesterPhone = requesterPhone;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public String getMeetingPoint() {
		return meetingPoint;
	}
	public void setMeetingPoint(String meetingPoint) {
		this.meetingPoint = meetingPoint;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRefDoc() {
		return refDoc;
	}
	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}
	public String getTravellers() {
		return travellers;
	}
	public void setTravellers(String travellers) {
		this.travellers = travellers;
	}
}
