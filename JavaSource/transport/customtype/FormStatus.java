package transport.customtype;

public enum FormStatus {
//	REQUESTING("REQUESTING","�͡��͹��ѵԨҡ���ѧ�Ѵ"),
	REQUESTING("REQUESTING","�͡��͹��ѵԨҡ���.��."),
//	LOCAL_APPROVED("LOCAL_APPROVED","�͡��͹��ѵԨҡ���.��."),
	TRAN_APPROVED("TRAN_APPROVED","�͡�èѴö"),
	CAR_ASSIGNED("CAR_ASSIGNED","�Ѵö���º��������"),
	NOT_AVAILABLE("NOT_AVAILABLE","�������ö�Ѵö�����"),
//	NO_LOCAL_APPROVE("NO_LOCAL_APPROVE","���ѧ�Ѵ���͹��ѵ�"),
	NO_TRAN_APPROVE("NO_TRAN_APPROVE","���.��.���͹��ѵ�"),
	CANCELED("CANCELED","¡��ԡ㺨ͧö"),
	FINISHED("FINISHED","�Դ�ҹ")
//	PENDING_LOCAL_APPROVE("PENDING_LOCAL_APPROVE","�͡��͹��ѵ���͹��ѧ�ҡ���ѧ�Ѵ"),
//	PENDING_TRAN_APPROVE("PENDING_TRAN_APPROVE","�͡��͹��ѵ���͹��ѧ�ҡ���.��.")
//	EMERGENCY("EMERGENCY","��öẺ��觴�ǹ"),
//	CAR_ASSIGNED_EMERGENCY("CAR_ASSIGN_EMERGENCY","�Ѵö��觴�ǹ���º��������")
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
