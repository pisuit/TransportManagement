package transport.model;

import java.io.Serializable;

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
@Table(name="TRANCOMPANY")
@SequenceGenerator(name="SEQ_COMPANY", sequenceName="GEN_COMPANY", allocationSize=1)
public class Company implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_COMPANY", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name="AUTHOR_FNAME")
	private String authorityFirstName;
	
	@Column(name="AUTHOR_LNAME")
	private String authorityLastName;
	
	@ManyToOne
	@JoinColumn(name="CONTRACT_ID", referencedColumnName="ID")
	private Contract contract;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAuthorityFirstName() {
		return authorityFirstName;
	}
	public void setAuthorityFirstName(String authorityFirstName) {
		this.authorityFirstName = authorityFirstName;
	}
	public String getAuthorityLastName() {
		return authorityLastName;
	}
	public void setAuthorityLastName(String authorityLastName) {
		this.authorityLastName = authorityLastName;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String toString(){
		return authorityFirstName+" "+authorityLastName; 
	}
	
}
