package transport.customtype;

public enum CarType {
	VAN("VAN","ö������ѷ�"),
	RENT_VAN("RENT_VAN","ö���Ѵ��ҧ"),
	TAXI("TAXI","ö�ꡫ��"),
	PICKUP("PICKUP","ö��кШѴ��ҧ")
//	PRIVATE("PRIVATE","ö��ǹ��Ǣͧ�����")
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
