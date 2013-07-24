package transport.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import transport.customtype.DataStatus;

@Entity
@Table(name="TRANUSER")
@SequenceGenerator(name="SEQ_USER", sequenceName="GEN_USER", allocationSize=1)
public class User implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_USER", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="STAFFCODE", referencedColumnName="STAFFCODE")
	private PersonalInfo personalInfo;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name="IP_ADDRESS")
	private String ipAddress;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	@OneToMany(mappedBy="user")
	@NotFound(action=NotFoundAction.IGNORE)
	List<Authorization> authorizations;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public List<Authorization> getAuthorizations() {
		return authorizations;
	}
	public void setAuthorizations(List<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
