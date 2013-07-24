package transport.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import transport.customtype.CarType;
import transport.customtype.DataStatus;

@Entity
@Table(name="TRANQUEUE")
@SequenceGenerator(name="SEQ_QUEUE", sequenceName="GEN_QUEUE", allocationSize=1)
public class Queue implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_QUEUE", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="CAR_ID")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="DRIVER_ID", referencedColumnName="ID")
	private Driver driver;
	
	@ManyToOne
	@JoinColumn(name="RESERVATION_ITEM_ID", referencedColumnName="ID")
	private ReservationItem reservationItem;
	
	@Column(name="TOLLS")
	private BigDecimal tolls = new BigDecimal("0.00");
	
	@Column(name="PARK_COST")
	private BigDecimal parkCost = new BigDecimal("0.00");
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="START_MILEAGE")
	private Long startMileage = Long.valueOf(0);
	
	@Column(name="END_MILEAGE")
	private Long endMileage = Long.valueOf(0);
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	@Column(name="CAR_TYPE")
	@Enumerated(EnumType.STRING)
	private CarType carType = CarType.VAN;
	
	@ManyToOne
	@JoinColumn(name="RENT_ID", referencedColumnName="ID")
	private Rental rental;
	
	@Column(name="RENT_COST")
	private BigDecimal rentCost = new BigDecimal("0.00");
	
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
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public BigDecimal getTolls() {
		return tolls;
	}
	public void setTolls(BigDecimal tolls) {
		this.tolls = tolls;
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
	public ReservationItem getReservationItem() {
		return reservationItem;
	}
	public void setReservationItem(ReservationItem reservationItem) {
		this.reservationItem = reservationItem;
	}
	public BigDecimal getParkCost() {
		return parkCost;
	}
	public void setParkCost(BigDecimal parkCost) {
		this.parkCost = parkCost;
	}
	public Long getStartMileage() {
		return startMileage;
	}
	public void setStartMileage(Long startMileage) {
		this.startMileage = startMileage;
	}
	public Long getEndMileage() {
		return endMileage;
	}
	public void setEndMileage(Long endMileage) {
		this.endMileage = endMileage;
	}
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	public Rental getRental() {
		return rental;
	}
	public void setRental(Rental rental) {
		this.rental = rental;
	}
	public BigDecimal getRentCost() {
		return rentCost;
	}
	public void setRentCost(BigDecimal rentCost) {
		this.rentCost = rentCost;
	}
}
