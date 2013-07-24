package transport.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import transport.customtype.DataStatus;
import transport.model.Car;
import transport.model.PersonalInfo;
import transport.model.Queue;
import transport.model.Reservation;
import transport.model.ReservationItem;
import transport.model.Routine;
import transport.utils.HibernateUtil;

public class ReservationController implements Serializable{

	private Date today = DateTime.now().toDateMidnight().toDate();
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservationList(String dep){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
				
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
					
			List<Reservation> reservations = session.createQuery(
					"SELECT DISTINCT reservation " +
					"FROM Reservation reservation " +
					"left join fetch reservation.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reservation.purpose " +
					"left join fetch reservation.parkingLocation " +
					"left join fetch reservation.reservationItems item " +
					"left join fetch item.queues queue " +
					"left join fetch reservation.staff " +
					"WHERE reservation.dataStatus = 'NORMAL' " +
					"AND reservation.endDate >= :ptoday " +
					"AND person.FLAGN = 'O' " +
					"AND (emp.DEPFINANCE = :pdep OR emp.DEPPERSON = :pdep) " +
					"AND (queue.dataStatus = 'NORMAL' OR queue = null) " +
					"ORDER BY reservation.requestNumber desc")
					.setParameter("ptoday", today)
					.setParameter("pdep", dep)
					.list();
			tx.commit();
			return reservations;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getLocalApprovedReservationList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Reservation> reservations = session.createQuery(
					"SELECT DISTINCT reservation " +
					"FROM Reservation reservation " +
					"left join fetch reservation.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reservation.purpose " +
					"left join fetch reservation.parkingLocation " +
					"left join fetch reservation.reservationItems item " +
					"left join fetch item.queues queue " +
					"left join fetch reservation.staff " +
					"WHERE reservation.dataStatus = 'NORMAL' " +
//					"AND reservation.formStatus in ('LOCAL_APPROVED','TRAN_APPROVED','NO_TRAN_APPROVE','CAR_ASSIGNED','NOT_AVAILABLE', 'CANCELED') " +
					"AND reservation.formStatus in ('REQUESTING','TRAN_APPROVED','NO_TRAN_APPROVE','CAR_ASSIGNED','NOT_AVAILABLE', 'CANCELED') " +
					"AND person.FLAGN = 'O' " +
					"AND reservation.endDate >= :ptoday " +
					"AND reservation.isEmergency = false " +
					"AND (queue.dataStatus = 'NORMAL' OR queue = null) " +
					"OR (reservation.isEmergency = true AND reservation.director != null) " +
					"ORDER BY reservation.requestNumber desc")
					.setParameter("ptoday", today)
					.list();
			tx.commit();
			
			return reservations;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public Reservation saveReservation(Reservation reservation){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(reservation);
			
			tx.commit();	
			
			return reservation;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void changeCarType(Reservation reservation){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(reservation);
			
			if(reservation.getReservationItems() != null){
				for(ReservationItem item : reservation.getReservationItems()){
					if(item.getQueues() != null){
						for(Queue queue : item.getQueues()){
							session.delete(queue);
						}
					}
					session.delete(item);
				}	
			}
					
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public Reservation saveReservationRoutine(Reservation reservation, Routine routine, Car car){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(reservation);
			
			ReservationItem reservationItem = new ReservationItem();
			reservationItem.setEndTime(reservation.getEndTime());
			reservationItem.setReservation(reservation);
			reservationItem.setStartTime(reservation.getStartTime());
			reservationItem.setUseDate(reservation.getEndDate());
			session.saveOrUpdate(reservationItem);
			
			Queue queue = new Queue();
			if(routine.getCar() != null){
				queue.setCar(routine.getCar());
				queue.setDriver(routine.getCar().getDriver());
			} else {
				queue.setCar(car);
				queue.setDriver(car.getDriver());
			}
			queue.setCarType(reservation.getCarType());
			queue.setReservationItem(reservationItem);
			session.saveOrUpdate(queue);

			tx.commit();
			
			return reservation;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public int getNextRequestNumber(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Integer maxRequestNumber = (Integer) session.createQuery(
					"SELECT MAX(reservation.requestNumber) " +
					"FROM Reservation reservation ")
					.uniqueResult();
			tx.commit();
			
			if(maxRequestNumber == null){
				maxRequestNumber = Integer.parseInt(Integer.toString(DateTime.now().getYear())+"0001");
			} else {
				if(!Integer.toString(DateTime.now().getYear()).equals(StringUtils.substring(Integer.toString(maxRequestNumber), 0, 4))){
					maxRequestNumber = Integer.parseInt(Integer.toString(DateTime.now().getYear())+"0001");
				} else {
					maxRequestNumber = maxRequestNumber+1;
				}
			}
			return maxRequestNumber;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return 0;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getEmergencyReservationList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Reservation> reservations = session.createQuery(
					"SELECT DISTINCT reservation " +
					"FROM Reservation reservation " +
					"left join fetch reservation.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reservation.purpose " +
					"left join fetch reservation.parkingLocation " +
					"left join fetch reservation.staff " +
					"WHERE reservation.dataStatus = 'NORMAL' " +
					"AND person.FLAGN = 'O' " +
					"AND reservation.isEmergency = true " +
					"AND reservation.endDate >= :ptoday " +
					"ORDER BY reservation.requestNumber desc")
					.setParameter("ptoday", today)
					.list();
			tx.commit();
			
			return reservations;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getReservationListForAssignment(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Reservation> reservations = session.createQuery(
					"SELECT DISTINCT reservation " +
					"FROM Reservation reservation " +
					"left join fetch reservation.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reservation.purpose " +
					"left join fetch reservation.parkingLocation " +
					"left join fetch reservation.reservationItems item " +
					"left join fetch item.queues queue " +
					"left join fetch reservation.staff " +
					"WHERE reservation.dataStatus = 'NORMAL' " +
					"AND person.FLAGN = 'O' " +
					"AND reservation.formStatus in ('TRAN_APPROVED') " +
					"AND reservation.dataStatus = 'NORMAL' " +
					"AND reservation.endDate >= :ptoday " +
					"AND (queue.dataStatus = 'NORMAL' OR queue = null) " +
					"ORDER BY reservation.requestNumber desc")
					.setParameter("ptoday", today)
					.list();
			tx.commit();
			
			return reservations;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getAssignedReservationList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Reservation> reservations = session.createQuery(
					"SELECT DISTINCT reservation " +
					"FROM Reservation reservation " +
					"left join fetch reservation.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reservation.purpose " +
					"left join fetch reservation.parkingLocation " +
					"left join fetch reservation.reservationItems item " +
					"left join fetch item.queues queue " +
					"left join fetch reservation.staff " +
					"WHERE reservation.dataStatus = 'NORMAL' " +
					"AND person.FLAGN = 'O' " +
					"AND reservation.formStatus in ('CAR_ASSIGNED', 'NOT_AVAILABLE', 'FINISHED') " +
					"AND reservation.dataStatus = 'NORMAL' " +
					"AND (queue.dataStatus = 'NORMAL' OR queue = null) " +
					"OR (reservation.formStatus = 'CANCELED' AND item != null) " +
					"ORDER BY reservation.requestNumber desc")
					.list();
			tx.commit();
		
			return reservations;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservationItem> getReservationItems(Reservation reservation){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
		
			List<ReservationItem> reservationItems = session.createQuery(
					"SELECT DISTINCT item " +
					"FROM ReservationItem item " +
					"left join fetch item.reservation reserve " +
					"left join fetch item.queues " +
					"left join fetch reserve.staff " +
					"WHERE item.reservation = :preservation " +
					"AND item.dataStatus = 'NORMAL' " +
					"ORDER BY item.useDate ")
					.setParameter("preservation", reservation)
					.list();
			tx.commit();
			
			return reservationItems;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public ReservationItem saveReservationItem(ReservationItem reservationItem){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(reservationItem);
			tx.commit();
			
			return reservationItem;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void deleteReservationItem(ReservationItem reservationItem){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.delete(reservationItem);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void deleteQueue(Queue queue){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
	
			session.delete(queue);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Queue> getQueueList(ReservationItem reservationItem){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Queue> queueList = session.createQuery(
					"SELECT DISTINCT queue " +
					"FROM Queue queue " +
					"WHERE queue.dataStatus = 'NORMAL' " +
					"AND queue.reservationItem = :pitem ")
					.setParameter("pitem", reservationItem)
					.list();
			
			tx.commit();
			return queueList;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Queue> getQueueListForCloseJob(Reservation reservation){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Queue> queueList = session.createQuery(
					"SELECT DISTINCT queue " +
					"FROM Queue queue " +
					"left join fetch queue.reservationItem item " +
					"left join fetch item.reservation reservation " +
					"WHERE queue.dataStatus = 'NORMAL' " +
					"AND reservation.id = :pid " +
					"ORDER BY item.useDate ")
					.setParameter("pid", reservation.getId())
					.list();
	
			tx.commit();
			return queueList;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void saveQueue(Queue queue){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(queue);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservationItem> getReservationItemForGivenDate(Date date){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<ReservationItem> itemList = session.createQuery(
					"SELECT DISTINCT item " +
					"FROM ReservationItem item " +
					"left join fetch item.reservation reservation " +
					"left join fetch item.queues queue " +
					"left join fetch queue.car " +
					"left join fetch reservation.staff " +
					"WHERE item.dataStatus = 'NORMAL' " +
					"AND reservation.formStatus != 'CANCELED' " +
					"AND item.useDate = :pdate " +
					"AND reservation.formStatus NOT IN ('FINISHED','NOT_AVAILABLE') ")
					.setParameter("pdate", date)
					.list();
			
			tx.commit();		
			return itemList;
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Reservation getMoreReservationDetail(Reservation reserve){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Reservation reservation = (Reservation) session.createQuery(
					"SELECT DISTINCT reservation " +
					"FROM Reservation reservation " +
					"left join fetch reservation.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reservation.purpose " +
					"left join fetch reservation.parkingLocation " +
					"left join fetch reservation.reservationItems item " +
					"left join fetch item.queues queue " +
					"left join fetch queue.driver " +
					"left join fetch reservation.staff " +
					"WHERE reservation = :preserve " +
					"AND person.FLAGN = 'O' " )
					.setParameter("preserve", reserve)
					.uniqueResult();
			tx.commit();
			
			return reservation;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Queue> getQueueListForViewPlan(Date date){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Queue> queueList = session.createQuery(
					"SELECT DISTINCT queue " +
					"FROM Queue queue " +
					"left join fetch queue.reservationItem item " +
					"left join fetch queue.car " +
					"left join fetch item.reservation reserve " +
					"left join fetch reserve.requester person " +
					"left join fetch person.employeeInfos emp " +
					"left join fetch reserve.staff " +
					"WHERE item.useDate = :pdate " +
					"AND queue.dataStatus = 'NORMAL' " +
					"AND person.FLAGN = 'O' " +
					"ORDER BY item.startTime")
					.setParameter("pdate", date)
					.list();
			tx.commit();
			
			return queueList;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPhoneNumberList(PersonalInfo personalInfo){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<String> phoneList = session.createQuery(
					"SELECT DISTINCT reservation.internalPhone " +
					"FROM Reservation reservation " +
					"WHERE reservation.requester = :prequest " +
					"AND reservation.dataStatus = 'NORMAL' ")
					.setParameter("prequest", personalInfo)
					.list()
					;
			tx.commit();
			
			return phoneList;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getStartPointList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<String> point = session.createQuery(
					"SELECT DISTINCT reservation.startingPoint " +
					"FROM Reservation reservation " +
					"WHERE reservation.dataStatus = 'NORMAL' " +
					"ORDER BY reservation.startingPoint asc")
					.list()
					;
			tx.commit();
			
			return point;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Queue> getQueueListForCar(Car car, Date date){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();

			List<Queue> queueList = session.createQuery(
					"SELECT queue " +
					"FROM Queue queue " +
					"left join fetch queue.reservationItem item " +
					"left join fetch item.reservation reservation " +
					"WHERE queue.car = :pcar " +
					"AND reservation.dataStatus = 'NORMAL' " +
					"AND reservation.formStatus in ('CAR_ASSIGNED','FINISHED') " +
					"AND item.useDate = :pdate ")
					.setParameter("pdate", date)
					.setParameter("pcar", car)
					.list();
//			List<Queue> queueList = session.createQuery(
//					"SELECT DISTINCT queue " +
//					"FROM Queue queue " +
//					"left join fetch queue.reservationItem item " +
//					"left join fetch item.reservation reservation " +
//					"WHERE queue.car = :pcar") 
//					.setParameter("pcar", car)
//					.list();
			tx.commit();
		
			return queueList;
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
}
