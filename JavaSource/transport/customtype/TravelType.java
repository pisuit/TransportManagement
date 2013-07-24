package transport.customtype;

public enum TravelType {
	TWOWAY("TWOWAY","ไปและกลับ"),
	ONEWAYGO("ONEWAYGO","ไปอย่างเดียว"),
	ONEWAYBACK("ONEWAYBACK","กลับอย่างเดียว");
	
	private String id;
	private String value;
	
	TravelType(String aID, String aValue){
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
	
	public static TravelType find(String aID){
		for (TravelType type : TravelType.values()) {
			if (type.id.equals(aID)){
				return type;
			}
		}
		return null;
	}
}
