package transport.customtype;

public enum FormStatus {
//	REQUESTING("REQUESTING","รอการอนุมัติจากต้นสังกัด"),
	REQUESTING("REQUESTING","รอการอนุมัติจากผบก.ทบ."),
//	LOCAL_APPROVED("LOCAL_APPROVED","รอการอนุมัติจากผบก.ทบ."),
	TRAN_APPROVED("TRAN_APPROVED","รอการจัดรถ"),
	CAR_ASSIGNED("CAR_ASSIGNED","จัดรถเรียบร้อยแล้ว"),
	NOT_AVAILABLE("NOT_AVAILABLE","ไม่สามารถจัดรถให้ได้"),
//	NO_LOCAL_APPROVE("NO_LOCAL_APPROVE","ต้นสังกัดไม่อนุมัติ"),
	NO_TRAN_APPROVE("NO_TRAN_APPROVE","ผบก.ทบ.ไม่อนุมัติ"),
	CANCELED("CANCELED","ยกเลิกใบจองรถ"),
	FINISHED("FINISHED","ปิดงาน")
//	PENDING_LOCAL_APPROVE("PENDING_LOCAL_APPROVE","รอการอนุมัติย้อนหลังจากต้นสังกัด"),
//	PENDING_TRAN_APPROVE("PENDING_TRAN_APPROVE","รอการอนุมัติย้อนหลังจากผบก.บท.")
//	EMERGENCY("EMERGENCY","ขอรถแบบเร่งด่วน"),
//	CAR_ASSIGNED_EMERGENCY("CAR_ASSIGN_EMERGENCY","จัดรถเร่งด่วนเรียบร้อยแล้ว")
	;
	
	private String id;
	private String value;
	
	FormStatus(String aID, String aValue){
		id = aID;
		value = aValue;
	}
	
	public String getID(){
		return id;
	}
	
	public String getValue(){
		return value;
	}

	public String toString() {
		return value;
	}
	
	public static FormStatus find(String aID){
		for (FormStatus type : FormStatus.values()) {
			if (type.id.equals(aID)){
				return type;
			}
		}
		return null;
	}
}
