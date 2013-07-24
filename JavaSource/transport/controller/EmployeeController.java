package transport.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import transport.model.Department;
import transport.model.EmployeeInfo;
import transport.model.PersonalInfo;
import transport.utils.HibernateUtil;

public class EmployeeController implements Serializable{
	
	@SuppressWarnings({ "unchecked" })
	public List<String> getEmployeeNameList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		ArrayList<String> nameList = new ArrayList<String>();
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<PersonalInfo> personalList = session.createQuery(
					"SELECT personal " +
					"FROM PersonalInfo personal " +
					"WHERE personal.FLAGN = 'O' ")
					.list();
			tx.commit();
			
			for(PersonalInfo info : personalList){
				nameList.add(info.getTNAME()+" "+info.getTSURNAME());
			}
			return nameList;
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
	
	@SuppressWarnings({ "unchecked" })
	public List<PersonalInfo> getEmployeeList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<PersonalInfo> personalList = session.createQuery(
					"SELECT distinct personal " +
					"FROM PersonalInfo personal " +
					"left join fetch personal.employeeInfos emp " +
					"WHERE personal.FLAGN = 'O' ")
					.list();
			tx.commit();
			
			return personalList;
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
	
	public PersonalInfo getStaff(String staffCode){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			PersonalInfo person = (PersonalInfo) session.createQuery(
					"SELECT distinct person " +
					"FROM PersonalInfo person " +
					"left join fetch person.employeeInfos emp " +
					"WHERE person.STAFFCODE = :pstaffcode " +
					"AND person.FLAGN = 'O' ")
					.setParameter("pstaffcode", staffCode)
					.uniqueResult();
			
			tx.commit();
			
			return person;
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
	public List<Department> getDepartments(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Department> depList = session.createQuery(
					"SELECT dep " +
					"FROM Department dep " +
					"WHERE dep.PFLAG = 'C' " +
					"AND dep.FFLAG = 'C' " +
					"ORDER BY dep.TDEPS")
					.list();
			
			tx.commit();
			
			return depList;
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
	public List<EmployeeInfo> getEmployeeForDepartment(String dep){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<EmployeeInfo> empList = session.createQuery(
					"SELECT emp " +
					"FROM EmployeeInfo emp " +
					"left join fetch emp.personalInfo pers " +
					"WHERE emp.DEPFINANCE = :pdep " +
					"ORDER BY pers.TNAME ")
					.setParameter("pdep", dep)
					.list();
			
			tx.commit();
			
			return empList;
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
	public EmployeeInfo getDirectorOfDepartment(String dep){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			EmployeeInfo emp = (EmployeeInfo) session.createQuery(
					"SELECT emp " +
					"FROM EmployeeInfo emp " +
					"left join fetch emp.personalInfo pers " +
					"WHERE emp.DEPFINANCE = '½¨.' " +
					"AND emp.DEPPERSON = :pdep " +
					"ORDER BY pers.TNAME ")
					.setParameter("pdep", dep)
					.uniqueResult();
			
			tx.commit();
			
			return emp;
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
	public EmployeeInfo getEmployeeInfo(PersonalInfo linkp){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			EmployeeInfo emp = (EmployeeInfo) session.createQuery(
					"SELECT distinct emp " +
					"FROM EmployeeInfo emp " +
					"left join fetch emp.personalInfo pers " +
					"WHERE pers = :plinkp ")
					.setParameter("plinkp", linkp)
					.uniqueResult();
			
			tx.commit();
			
			return emp;
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
