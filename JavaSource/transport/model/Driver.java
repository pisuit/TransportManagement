package transport.model;

import java.io.Serializable;
import java.util.Date;

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

import transport.customtype.DataStatus;

@Entity
@Table(name="TRANDRIVER")
@SequenceGenerator(name="SEQ_DRIVER", sequenceName="GEN_DRIVER", allocationSize=1)
public class Driver implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_DRIVER", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="LICENSE_ID")
	private String driverLicenseID;
	
	@Column(name="TFIRSTNAME")
	private String thaiFirstName;
	
	@Column(name="TLASTNAME")
	private String thaiLastName;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name="COMPANY_ID", referencedColumnName="ID")
	private Company company;
	
	@Column(name="DATE_ISSUE")
	private Date dateIssue;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDriverLicenseID() {
		return driverLicenseID;
	}
	public void setDriverLicenseID(String driverLicenseID) {
		this.driverLicenseID = driverLicenseID;
	}
	public String getThaiFirstName() {
		return thaiFirstName;
	}
	public void setThaiFirstName(String thaiFirstName) {
		this.thaiFirstName = thaiFirstName;
	}
	public String getThaiLastName() {
		return thaiLastName;
	}
	public void setThaiLastName(String thaiLastName) {
		this.thaiLastName = thaiLastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Date getDateIssue() {
		return dateIssue;
	}
	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	public String toString(){
		return thaiFirstName + " " + thaiLastName;
	}
}
