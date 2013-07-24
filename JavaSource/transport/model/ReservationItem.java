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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import transport.customtype.CarType;
import transport.customtype.DataStatus;

@Entity
@Table(name="TRANRESERVATIONITEM")
@SequenceGenerator(name="SEQ_RESERVATIONITEM", sequenceName="GEN_RESERVATIONITEM", allocationSize=1)
public class ReservationItem implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_RESERVATIONITEM", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="RESERVATION_ID", referencedColumnName="ID")
	private Reservation reservation;
	
	@Column(name="USE_DATE")
	private Date useDate;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@OneToMany(mappedBy="reservationItem")
	private Set<Queue> queues;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus dataStatus = DataStatus.NORMAL;
	
	@Column(name="CAR_TYPE")
	@Enumerated(EnumType.STRING)
	private CarType carType;
	
	@Transient
	private List<Queue> listQueue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
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
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Set<Queue> getQueues() {
		return queues;
	}
	public void setQueues(Set<Queue> queues) {
		this.queues = queues;
	}
	public List<Queue> getListQueue() {
		return new ArrayList<Queue>(queues);
	}
	public void setListQueue(List<Queue> listQueue) {
		this.listQueue = listQueue;
	}
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	
}
