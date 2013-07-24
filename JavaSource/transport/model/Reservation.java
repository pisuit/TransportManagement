package transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import transport.customtype.CarType;
import transport.customtype.DataStatus;
import transport.customtype.FormStatus;
import transport.customtype.TravelType;

@Entity
@Table(name="TRANRESERVATION")
@SequenceGenerator(name="SEQ_RESERVATION", sequenceName="GEN_RESERVATION", allocationSize=1)
public class Reservation implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_RESERVATION")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="SERVICE_TYPE_ID", referencedColumnName="ID")
	private ServiceType serviceType;
	
	@Column(name="START_DATE")
	private Date startDate;
	
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@Column(name="REQUEST_NUMBER")
	private int requestNumber;
	
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="LOCAL_APPROVE_DATE")
	private Date localApproveDate;
	
	@Column(name="TRAN_APPROVE_DATE")
	private Date transportApproveDate;
	
	@Column(name="ASSIGN_DATE")
	private Date assignDate;
	
	@Column(name="STARTING_POINT")
	private String startingPoint;
	
	@Column(name="DIRECTOR_REMARK")
	private String directorRemark;
	
	@Column(name="SERVICE_REMARK")
	private String serviceRemark;
	
	@Column(name="FINISH_POINT")
	private String finishPoint;
	
	@Column(name="REF_DOCUMENT")
	private String refDocument;
	
	@Column(name="ISEMERGENCY")
	private boolean isEmergency = false;
	
	@ManyToOne
	@JoinColumn(name="DIRECTOR", referencedColumnName="STAFFCODE")
	private PersonalInfo director;
	
	@ManyToOne
	@JoinColumn(name="SERVICE_DIRECTOR", referencedColumnName="STAFFCODE")
	private PersonalInfo serviceDirector;
	
	@ManyToOne
	@JoinColumn(name="STAFF", referencedColumnName="STAFFCODE")
	private PersonalInfo staff;
		
	@ManyToOne
	@JoinColumn(name="STAFF_CODE", referencedColumnName="STAFFCODE")
	private PersonalInfo requester;
	
	@ManyToOne
	@JoinColumn(name="PURPOSE_ID", referencedColumnName="ID")
	private Purpose purpose;
	
	@ManyToOne
	@JoinColumn(name="PARKINGLOC_ID", referencedColumnName="ID")
	private ParkingLocation parkingLocation;
	
	@Column(name="INT_PHONE")
	private String internalPhone;
	
	@Column(name="CAR_TYPE")
	@Enumerated(EnumType.STRING)
	private CarType carType = CarType.VAN;
	
	@Column(name="TRAVEL_TYPE")
	@Enumerated(EnumType.STRING)
	private TravelType travelType = TravelType.TWOWAY;
	
	@OneToMany(mappedBy="reservation")
	private Set<ReservationItem> reservationItems;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="TRAVELLERS")
	private String travellers;
	
	@Column(name="NO_OF_TRAVELLER")
	private int numberOfTraveller;
	
	@Column(name="NO_OF_CAR")
	private int numberOfCars;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	@Column(name="FORM_STATUS")
	@Enumerated(EnumType.STRING)
	private FormStatus formStatus = FormStatus.REQUESTING;
	
	@ManyToOne
	@JoinColumn(name="MASTER_RESERVATION", referencedColumnName="ID")
	private Reservation master;
	
	@Transient
	private List<ReservationItem> listReservationItem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(String startingPoint) {
		this.startingPoint = startingPoint;
	}
	public String getFinishPoint() {
		return finishPoint;
	}
	public void setFinishPoint(String finishPoint) {
		this.finishPoint = finishPoint;
	}
	public Purpose getPurpose() {
		return purpose;
	}
	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}
	public ParkingLocation getParkingLocation() {
		return parkingLocation;
	}
	public void setParkingLocation(ParkingLocation parkingLocation) {
		this.parkingLocation = parkingLocation;
	}
	public String getInternalPhone() {
		return internalPhone;
	}
	public void setInternalPhone(String internalPhone) {
		this.internalPhone = internalPhone;
	}
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	public TravelType getTravelType() {
		return travelType;
	}
	public void setTravelType(TravelType travelType) {
		this.travelType = travelType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTravellers() {
		return travellers;
	}
	public void setTravellers(String travellers) {
		this.travellers = travellers;
	}
	public int getNumberOfTraveller() {
		return numberOfTraveller;
	}
	public void setNumberOfTraveller(int numberOfTraveller) {
		this.numberOfTraveller = numberOfTraveller;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public FormStatus getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(FormStatus formStatus) {
		this.formStatus = formStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLocalApproveDate() {
		return localApproveDate;
	}
	public void setLocalApproveDate(Date localApproveDate) {
		this.localApproveDate = localApproveDate;
	}
	public Date getTransportApproveDate() {
		return transportApproveDate;
	}
	public void setTransportApproveDate(Date transportApproveDate) {
		this.transportApproveDate = transportApproveDate;
	}
	public Date getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	public int getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}
	public PersonalInfo getRequester() {
		return requester;
	}
	public void setRequester(PersonalInfo requester) {
		this.requester = requester;
	}
	public int getNumberOfCars() {
		return numberOfCars;
	}
	public void setNumberOfCars(int numberOfCars) {
		this.numberOfCars = numberOfCars;
	}
	public boolean isEmergency() {
		return isEmergency;
	}
	public void setEmergency(boolean isEmergency) {
		this.isEmergency = isEmergency;
	}
	public Set<ReservationItem> getReservationItems() {
		return reservationItems;
	}
	public void setReservationItems(Set<ReservationItem> reservationItems) {
		this.reservationItems = reservationItems;
	}
	public String getDirectorRemark() {
		return directorRemark;
	}
	public void setDirectorRemark(String directorRemark) {
		this.directorRemark = directorRemark;
	}
	public String getServiceRemark() {
		return serviceRemark;
	}
	public void setServiceRemark(String serviceRemark) {
		this.serviceRemark = serviceRemark;
	}
	public List<ReservationItem> getListReservationItem() {
		return new ArrayList<ReservationItem>(reservationItems);
	}
	public void setListReservationItem(List<ReservationItem> listReservationItem) {
		this.listReservationItem = listReservationItem;
	}
	public PersonalInfo getDirector() {
		return director;
	}
	public void setDirector(PersonalInfo director) {
		this.director = director;
	}
	public PersonalInfo getServiceDirector() {
		return serviceDirector;
	}
	public void setServiceDirector(PersonalInfo serviceDirector) {
		this.serviceDirector = serviceDirector;
	}
	public PersonalInfo getStaff() {
		return staff;
	}
	public void setStaff(PersonalInfo staff) {
		this.staff = staff;
	}
	public String getRefDocument() {
		return refDocument;
	}
	public void setRefDocument(String refDocument) {
		this.refDocument = refDocument;
	}
	public Reservation getMaster() {
		return master;
	}
	public void setMaster(Reservation master) {
		this.master = master;
	}	
}
