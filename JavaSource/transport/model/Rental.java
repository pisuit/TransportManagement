package transport.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import transport.customtype.DataStatus;

@Entity
@Table(name="TRANRENTAL")
@SequenceGenerator(name="SEQ_RENT", sequenceName="GEN_RENT", allocationSize=1)
public class Rental implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_RENT", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="COMPANY_NAME")
	private String companyName;
	
	@Column(name="DRIVER_NAME")
	private String driverName;
	
	@Column(name="DRIVER_PHONE")
	private String driverPhone;
	
	@Column(name="CAR_TYPE")
	private String carServiceType;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getCarServiceType() {
		return carServiceType;
	}
	public void setCarServiceType(String carServiceType) {
		this.carServiceType = carServiceType;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
}
