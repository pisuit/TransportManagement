package transport.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import transport.customtype.Role;
import transport.model.Authorization;
import transport.model.Car;
import transport.model.Company;
import transport.model.Contract;
import transport.model.Driver;
import transport.model.FuelType;
import transport.model.ParkingLocation;
import transport.model.PersonalInfo;
import transport.model.Purpose;
import transport.model.Rental;
import transport.model.ServiceType;
import transport.model.User;
import transport.utils.HibernateUtil;

public class AdminController implements Serializable{
	
	@SuppressWarnings("unchecked")
	public List<Driver> getDriverList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Driver> driverList = session.createQuery(
					"SELECT driver " +
					"FROM Driver driver " +
					"left join fetch driver.company " +
					"WHERE driver.dataStatus = 'NORMAL' " +
					"ORDER BY driver.thaiFirstName")
					.list();
			tx.commit();
			
			return driverList;
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
	
	public Driver getDriver(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Driver driver = (Driver) session.createQuery(
					"SELECT driver " +
					"FROM Driver driver " +
					"left join fetch driver.company " +
					"WHERE driver.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return driver;
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
	
	public void saveDriver(Driver driver){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(driver);
			
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
	public List<Company> getCompanyList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Company> companyList = session.createQuery(
					"SELECT company " +
					"FROM Company company " +
					"left join fetch company.contract " +
					"WHERE company.dataStatus = 'NORMAL' " +
					"ORDER BY company.name")
					.list();
			tx.commit();
			
			return companyList;
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
	
	public Company getCompany(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Company company = (Company) session.createQuery(
					"SELECT company " +
					"FROM Company company " +
					"left join fetch company.contract " +
					"WHERE company.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return company;
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
	
	public void saveCompany(Company company){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(company);
			
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
	public List<Contract> getContractList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Contract> contractList = session.createQuery(
					"SELECT contract " +
					"FROM Contract contract " +
					"WHERE contract.dataStatus = 'NORMAL' " +
					"ORDER BY contract.name")
					.list();
			tx.commit();
			
			return contractList;
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
	
	public void saveContract(Contract contract){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(contract);
			
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
	
	public Contract getContract(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Contract contract = (Contract) session.createQuery(
					"SELECT contract " +
					"FROM Contract contract " +
					"WHERE contract.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return contract;
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
	public List<Car> getCarList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Car> carList = session.createQuery(
					"SELECT car " +
					"FROM Car car " +
					"left join fetch car.serviceType " +
					"left join fetch car.fuelType " +
					"left join fetch car.driver " +
					"WHERE car.dataStatus = 'NORMAL' " +
					"ORDER BY car.name")
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
	
	public void saveCar(Car car){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(car);
			
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
	
	public Contract getCar(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Contract contract = (Contract) session.createQuery(
					"SELECT car " +
					"FROM Car car " +
					"left join fetch car.serviceType " +
					"left join fetch car.fuelType " +
					"left join fetch car.driver " +
					"WHERE car.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return contract;
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
	public List<FuelType> getFuelTypeList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<FuelType> fuelList = session.createQuery(
					"SELECT fuel " +
					"FROM FuelType fuel " +
					"WHERE fuel.dataStatus = 'NORMAL' " +
					"ORDER BY fuel.name")
					.list();
			tx.commit();
			
			return fuelList;
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
	
	public void saveFuelType(FuelType fuelType){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(fuelType);
			
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
	
	public FuelType getFuelType(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			FuelType fuel = (FuelType) session.createQuery(
					"SELECT fuel " +
					"FROM FuelType fuel " +
					"WHERE fuel.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return fuel;
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
	public List<ServiceType> getServiceTypeList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<ServiceType> serviceList = session.createQuery(
					"SELECT service " +
					"FROM ServiceType service " +
					"WHERE service.dataStatus = 'NORMAL' " +
					"ORDER BY service.name")
					.list();
			tx.commit();
			
			return serviceList;
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
	
	public void saveServiceType(ServiceType serviceType){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(serviceType);
			
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
	
	public ServiceType getServiceType(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			ServiceType service = (ServiceType) session.createQuery(
					"SELECT service " +
					"FROM ServiceType service " +
					"WHERE service.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return service;
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
	
	public void savePurpose(Purpose purpose){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(purpose);
			
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
	
	public void saveParkingLocation(ParkingLocation park){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(park);
			
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
	public List<Purpose> getPurposeList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Purpose> purposeList = session.createQuery(
					"SELECT purpose " +
					"FROM Purpose purpose " +
					"WHERE purpose.dataStatus = 'NORMAL' " +
					"ORDER BY purpose.name")
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
	public List<ParkingLocation> getParkingLocationList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<ParkingLocation> parkList = session.createQuery(
					"SELECT park " +
					"FROM ParkingLocation park " +
					"WHERE park.dataStatus = 'NORMAL' " +
					"ORDER BY park.name")
					.list();
			tx.commit();
			
			return parkList;
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
					"SELECT purpose " +
					"FROM Purpose purpose " +
					"WHERE Purpose.id = :pid ")
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
			
			ParkingLocation park = (ParkingLocation) session.createQuery(
					"SELECT park " +
					"FROM ParkingLocation park " +
					"WHERE park.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return park;
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
	public List<User> getAllUser(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<User> userList = session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"left join fetch user.personalInfo person " +
					"WHERE user.dataStatus = 'NORMAL' " +
					"AND person.FLAGN = 'O' " +
					"ORDER BY person.TNAME ")
					.list();
			tx.commit();
			
			return userList;
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
	
	public void saveUser(User user, List<String> roles){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(user);
			
			if(user.getAuthorizations() != null){
				for(Authorization authorization : user.getAuthorizations()){
					session.delete(authorization);
				}
			}
			
			if(roles != null && roles.size() != 0){
				Authorization authorization;
				for(String role : roles){
					authorization = new Authorization();
					authorization.setUser(user);
					authorization.setRole(Role.find(role));
					session.saveOrUpdate(authorization);
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
	
	public User getUser(PersonalInfo personalInfo){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"WHERE user.personalInfo = :pperson " +
					"AND user.dataStatus = 'NORMAL' ")
					.setParameter("pperson", personalInfo)
					.uniqueResult();
			
			tx.commit();
			
			return user;
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
	
	public User getUser(String staffCode){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"left join fetch user.personalInfo person " +
					"WHERE person.STAFFCODE = :pperson " +
					"AND person.FLAGN = 'O' " +
					"AND user.dataStatus = 'NORMAL' ")
					.setParameter("pperson", staffCode)
					.uniqueResult();
			
			tx.commit();
			
			return user;
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
	
	public List<User> getOperators(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<User> users = session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +
					"left join fetch user.personalInfo person " +
					"left join fetch user.authorizations auth " +
					"WHERE user.dataStatus = 'NORMAL' " +
					"AND person.FLAGN = 'O' " +
					"AND auth.role = 'SERVICE_STAFF' ")
					.list();
			
			tx.commit();
			
			return users;
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
	public List<Rental> getRentalList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Rental> rentalList = session.createQuery(
					"SELECT rental " +
					"FROM Rental rental " +
					"WHERE rental.dataStatus = 'NORMAL' " +
					"ORDER BY rental.companyName")
					.list();
			tx.commit();
			
			return rentalList;
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
	
	public void saveRental(Rental rental){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(rental);
			
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
	public Rental getRental(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Rental rental = (Rental) session.createQuery(
					"SELECT rental " +
					"FROM Rental rental " +
					"WHERE rental.id = :pid ")
					.setParameter("pid", id)
					.uniqueResult();
			tx.commit();
			
			return rental;
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
