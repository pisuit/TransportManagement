package transport.manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.primefaces.extensions.model.timeline.DefaultTimeLine;
import org.primefaces.extensions.model.timeline.DefaultTimelineEvent;
import org.primefaces.extensions.model.timeline.Timeline;
import org.primefaces.extensions.model.timeline.TimelineEvent;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import transport.controller.GeneralController;
import transport.controller.ReservationController;
import transport.customtype.CarType;
import transport.model.Car;
import transport.model.Queue;
import transport.model.Reservation;
import transport.model.ReservationItem;
import transport.model.Routine;
import transport.utils.ReportUtils;
import transprot.reportmodel.DailyReportModel;

@ManagedBean(name = "viewPlanManager")
@ViewScoped
public class ViewPlanManager implements Serializable {
	private Date selectedDate;
	private ReservationController reservationController = new ReservationController();
	private List<Queue> queueList = new ArrayList<Queue>();
	private GeneralController generalController = new GeneralController();
	private List<Timeline> timeline = new ArrayList<Timeline>();

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public List<Timeline> getTimeline() {
		return timeline;
	}

	public void setTimeline(List<Timeline> timeline) {
		this.timeline = timeline;
	}

	public List<Queue> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<Queue> queueList) {
		this.queueList = queueList;
	}

	public ViewPlanManager() {
		selectedDate = DateTime.now().toDateMidnight().toDate();
		createQueueList();
	}

	public void createQueueList() {
		if (queueList != null)
			queueList.clear();

		Reservation reservation;
		ReservationItem reservationItem;
		Queue queue;

		for (Routine routine : generalController.getAllRoutines()) {
			if (routine.getCar() != null) {
				reservation = new Reservation();
				reservation.setCarType(routine.getCarType());
				reservation.setEndDate(routine.getUseDate());
				reservation.setEndTime(routine.getEndTime());
				reservation.setInternalPhone(routine.getPhoneNumber());
				reservation.setParkingLocation(routine.getParkingLocation());
				reservation.setPurpose(routine.getPurpose());
				reservation.setRemark(routine.getRemark());
				reservation.setRequester(routine.getRequester());
				reservation.setStartingPoint(routine.getStartingPoint());
				reservation.setStartTime(routine.getStartTime());
				reservation.setTravelType(routine.getTravelType());
				reservation.setFinishPoint(routine.getFinishPoint());

				reservationItem = new ReservationItem();
				reservationItem.setStartTime(routine.getStartTime());
				reservationItem.setEndTime(routine.getEndTime());
				reservationItem.setUseDate(routine.getUseDate());
				reservationItem.setReservation(reservation);

				queue = new Queue();
				queue.setCar(routine.getCar());

				if (routine.getCar() != null)
					queue.setDriver(routine.getCar().getDriver());

				queue.setReservationItem(reservationItem);

				queueList.add(queue);
			}
		}
		List<Queue> qlist = reservationController.getQueueListForViewPlan(selectedDate);
		if(qlist != null){
			queueList.addAll(qlist);
		}
		
		createTimeLine();
	}

	private void createTimeLine() {
		timeline.clear();
		List<Car> carList = generalController.getAllCars();
		for (Car car : carList) {
			Timeline timeLine = new DefaultTimeLine(String.valueOf(car.getId()), car.getName());
			List<Queue> queueList = reservationController.getQueueListForCar(car, selectedDate);
			List<Routine> routines = generalController.getRoutineForCar(car);
	
			if (queueList.size() != 0) {
				for (Queue queue : queueList) {
					Calendar start = Calendar.getInstance();
					start.setTime(queue.getReservationItem().getUseDate());
					start.set(Calendar.HOUR, queue.getReservationItem().getStartTime().getHours());
					start.set(Calendar.MINUTE, queue.getReservationItem().getStartTime().getMinutes());
					Calendar end = Calendar.getInstance();
					end.setTime(queue.getReservationItem().getUseDate());
					end.set(Calendar.HOUR, queue.getReservationItem().getEndTime().getHours());
					end.set(Calendar.MINUTE, queue.getReservationItem().getEndTime().getMinutes());
					TimelineEvent timelineEvent = new DefaultTimelineEvent("ไม่ว่าง", start.getTime(), end.getTime());
					timelineEvent.setStyleClass("red");
					timeLine.addEvent(timelineEvent);
					timeline.add(timeLine);		
				}
			}  
			if(routines.size() != 0){
				for(Routine routine : routines){
					Calendar start = Calendar.getInstance();
					start.setTime(selectedDate);
					start.set(Calendar.HOUR, routine.getStartTime().getHours());
					start.set(Calendar.MINUTE, routine.getStartTime().getMinutes());
					Calendar end = Calendar.getInstance();
					end.setTime(selectedDate);
					end.set(Calendar.HOUR, routine.getEndTime().getHours());
					end.set(Calendar.MINUTE, routine.getEndTime().getMinutes());
					TimelineEvent timelineEvent = new DefaultTimelineEvent("ไม่ว่าง", start.getTime(), end.getTime());
					timelineEvent.setStyleClass("red");
					timeLine.addEvent(timelineEvent);
					timeline.add(timeLine);
				}
			} 
			if(queueList.size() == 0 && routines.size() == 0){
				Calendar starttime = Calendar.getInstance();
				starttime.setTime(selectedDate);
				starttime.set(Calendar.HOUR, 6);
				starttime.set(Calendar.MINUTE, 0);
				starttime.set(Calendar.SECOND, 0);
				Calendar endtime = Calendar.getInstance();
				endtime.setTime(selectedDate);
				endtime.set(Calendar.HOUR, 22);
				endtime.set(Calendar.MINUTE, 00);
				endtime.set(Calendar.SECOND, 00);
				TimelineEvent timelineEvent = new DefaultTimelineEvent("ว่าง", starttime.getTime(), endtime.getTime());
				timelineEvent.setStyleClass("green");
				timeLine.addEvent(timelineEvent);
				timeline.add(timeLine);
			}
		}
	}
	
	public void printReport() {
		try {
			JasperReport report = null;
			report = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/transport/reports/daily.jasper"));
			HashMap parameters = new HashMap();
			DailyReportModel dailyReportModel;
			List<DailyReportModel> dailyReportModels = new ArrayList<DailyReportModel>();

			parameters.put("date", selectedDate);

			for (Queue queue : queueList) {
				if (queue.getCar() != null) {
					dailyReportModel = new DailyReportModel();
					dailyReportModel.setRequester(queue.getReservationItem()
							.getReservation().getRequester().toString());
					// if(queue.getReservationItem().getReservation().getRequester().getEmployeeInfos().get(0).getDEPFINANCE().equals("ฝจ.")){
					// dailyReportModel.setDep(queue.getReservationItem().getReservation().getRequester().getEmployeeInfos().get(0).getDEPPERSON());
					// } else {
					// dailyReportModel.setDep(queue.getReservationItem().getReservation().getRequester().getEmployeeInfos().get(0).getDEPFINANCE());
					// }

					dailyReportModel.setCarName(queue.getCar().getName());
					dailyReportModel
							.setDriverName(queue.getDriver().toString());
					dailyReportModel.setDep(queue.getReservationItem()
							.getReservation().getRequester().getEmployeeInfos()
							.get(0).getDEPFINANCE());
					dailyReportModel.setEndTime(queue.getReservationItem()
							.getEndTime());
					dailyReportModel.setStartTime(queue.getReservationItem()
							.getStartTime());
					dailyReportModel.setParkLocation(queue.getReservationItem()
							.getReservation().getParkingLocation().getName());
					dailyReportModel.setRemark(queue.getReservationItem()
							.getReservation().getRemark());
					dailyReportModel.setPhone(queue.getReservationItem()
							.getReservation().getInternalPhone());

					dailyReportModels.add(dailyReportModel);
				}
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(report,
					parameters, new JRBeanCollectionDataSource(
							dailyReportModels));

			// JasperExportManager.exportReportToPdfFile(jasperPrint,"C:/summary.pdf");
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			ReportUtils.displayPdfReport(bytes);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
