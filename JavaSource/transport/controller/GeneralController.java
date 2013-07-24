package transport.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import transport.model.Car;
import transport.model.Driver;
import transport.model.ParkingLocation;
import transport.model.Photo;
import transport.model.Purpose;
import transport.model.Routine;
import transport.utils.HibernateUtil;

public class GeneralController implements Serializable{
	
	@SuppressWarnings("unchecked")
	public List<Purpose> getAllPurposeList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Purpose> purposeList = session.createQuery(
					"SELECT DISTINCT purpose " +
					"FROM Purpose purpose " +
					"WHERE purpose.dataStatus = 'NORMAL' " +
					"ORDER BY purpose.name ")
					.list();
			tx.commit();
			
			return purposeList;
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
	public List<ParkingLocation> getAllParkingLocationList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<ParkingLocation> locationList = session.createQuery(
					"SELECT DISTINCT parking " +
					"FROM ParkingLocation parking " +
					"WHERE parking.dataStatus = 'NORMAL' " +
					"ORDER BY parking.name ")
					.list();
			tx.commit();
			
			return locationList;
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
	
	public Purpose getPurpose(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Purpose purpose = (Purpose) session.createQuery(
					"SELECT DISTINCT purpose " +
					"FROM Purpose purpose " +
					"WHERE purpose.id = :pid")
					.setParameter("pid", id)
					.uniqueResult();
			
			tx.commit();
			return purpose;
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
	
	public ParkingLocation getParkingLocation(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			ParkingLocation location = (ParkingLocation) session.createQuery(
					"SELECT DISTINCT location " +
					"FROM ParkingLocation location " +
					"WHERE location.id = :pid")
					.setParameter("pid", id)
					.uniqueResult();
			
			tx.commit();
			return location;
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
	public List<Car> getAllCars(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Car> carList = session.createQuery(
					"SELECT DISTINCT car " +
					"FROM Car car " +
					"WHERE car.dataStatus = 'NORMAL' " +
					"ORDER BY car.shortName ")
					.list();
			
			tx.commit();
			return carList;
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
	
	public Car getCar(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Car car = (Car) session.createQuery(
					"SELECT DISTINCT car " +
					"FROM Car car " +
					"left join fetch car.driver " +
					"WHERE car.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			
			tx.commit();
			return car;
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
	
	public List<Routine> getAllRoutines(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Routine> routineList = session.createQuery(
					"SELECT distinct routine " +
					"FROM Routine routine " +
					"left join fetch routine.requester person " +
					"left join fetch person.employeeInfos emp " +
					"WHERE routine.dataStatus = 'NORMAL' " +
					"AND emp != null")
					.list();
			
			tx.commit();
			return routineList;
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
	public List<Routine> getRoutineForCar(Car car){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Routine> routine = session.createQuery(
					"SELECT distinct routine " +
					"FROM Routine routine " +
					"WHERE routine.dataStatus = 'NORMAL' " +
					"AND routine.car = :pcar")
					.setParameter("pcar", car)
					.list();
			
			tx.commit();
			return routine;
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
	
	public void saveRoutine(Routine routine){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(routine);
			
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
	
	public Photo getPhoto(String staffCode){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Photo photo = (Photo) session.createQuery(
					"SELECT photo " +
					"FROM Photo photo " +
					"WHERE photo.staffCode = :pstaffcode ")
					.setParameter("pstaffcode", staffCode)
					.uniqueResult();
			
			tx.commit();
			return photo;
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
}
