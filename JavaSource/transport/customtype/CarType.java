package transport.customtype;

public enum CarType {
	VAN("VAN","รถตู้บริษัทฯ"),
	RENT_VAN("RENT_VAN","รถตู้จัดจ้าง"),
	TAXI("TAXI","รถแท๊กซี่"),
	PICKUP("PICKUP","รถกระบะจัดจ้าง")
//	PRIVATE("PRIVATE","รถส่วนตัวของผู้ใช้")
	;
	
	private String id;
	private String value;
	
	CarType(String aID, String aValue){
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
	
	public static CarType find(String aID){
		for (CarType type : CarType.values()) {
			if (type.id.equals(aID)){
				return type;
			}
		}
		return null;
	}
}
