package transport.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import transport.customtype.CarType;
import transport.customtype.DataStatus;
import transport.customtype.TravelType;

@Entity
@Table(name="TRANROUTINE")
@SequenceGenerator(name="SEQ_ROUTINE", sequenceName="GEN_ROUTINE", allocationSize=1)
public class Routine implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_ROUTINE")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="CAR_ID", referencedColumnName="ID")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="STAFF_CODE", referencedColumnName="STAFFCODE")
	private PersonalInfo requester;
	
	@Column(name="INT_PHONE")
	private String phoneNumber;
	
	@Column(name="CAR_TYPE")
	@Enumerated(EnumType.STRING)
	private CarType carType = CarType.VAN;
	
	@Column(name="TRAVEL_TYPE")
	@Enumerated(EnumType.STRING)
	private TravelType travelType = TravelType.TWOWAY;
	
	@ManyToOne
	@JoinColumn(name="PURPOSE_ID", referencedColumnName="ID")
	private Purpose purpose;
	
	@ManyToOne
	@JoinColumn(name="PARKINGLOC_ID", referencedColumnName="ID")
	private ParkingLocation parkingLocation;
	
	@Column(name="USE_DATE")
	private Date useDate;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@Column(name="STARTING_POINT")
	private String startingPoint;
	
	@Column(name="FINISH_POINT")
	private String finishPoint;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public PersonalInfo getRequester() {
		return requester;
	}

	public void setRequester(PersonalInfo requester) {
		this.requester = requester;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public DataStatus getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
}
