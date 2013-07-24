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
@Table(name="TRANCAR")
@SequenceGenerator(name="SEQ_CAR", sequenceName="GEN_CAR", allocationSize=1)
public class Car implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_CAR", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="COMPANY_ID", referencedColumnName="ID")
	private Company company;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SHORT_NAME")
	private String shortName;
	
	@Column(name="SEATS")
	private int seats;
	
	@ManyToOne
	@JoinColumn(name="FUEL_TYPE_ID", referencedColumnName="ID")
	private FuelType fuelType;
	
	@Column(name="REGISTER_NUMBER")
	private String registerNumber;
	
	@Column(name="ENGINE")
	private int engine;
	
	@Column(name="RENT")
	private BigDecimal rent = new BigDecimal("0.00");
	
	@Column(name="TOTAL_MILEAGE")
	private Long totalMileage = Long.valueOf(0);
	
	@ManyToOne
	@JoinColumn(name="SERVICE_TYPE_ID", referencedColumnName="ID")
	private ServiceType serviceType;
	
	@ManyToOne
	@JoinColumn(name="DRIVER_ID", referencedColumnName="ID")
	private Driver driver;
	
	@Column(name="CAR_TYPE")
	@Enumerated(EnumType.STRING)
	private CarType carType = CarType.VAN;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public int getEngine() {
		return engine;
	}
	public void setEngine(int engine) {
		this.engine = engine;
	}
	public BigDecimal getRent() {
		return rent;
	}
	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	public FuelType getFuelType() {
		return fuelType;
	}
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public Long getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(Long totalMileage) {
		this.totalMileage = totalMileage;
	}
	
}
