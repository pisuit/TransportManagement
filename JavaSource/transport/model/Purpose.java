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
@Table(name="TRANPURPOSE")
@SequenceGenerator(name="SEQ_PURPOSE", sequenceName="GEN_PURPOSE", allocationSize=1)
public class Purpose implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_PURPOSE", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="ISPRIOR")
	private boolean isPrior = false;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public boolean isPrior() {
		return isPrior;
	}
	public void setPrior(boolean isPrior) {
		this.isPrior = isPrior;
	}
}
