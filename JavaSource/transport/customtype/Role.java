package transport.customtype;

public enum Role {
	NORMAL("NORMAL","พนักงานปกติ"),
//	DIRECTOR("DIRECTOR","ผู้อำนวยการกอง"),
	SERVICE_DIRECTOR("SERVICE_DIRECTOR","ผู้อำนวยการกองบริการ"),
	SERVICE_STAFF("SERVICE_STAFF", "เจ้าหน้าที"),
	ADMIN("ADMIN", "ผู้ดูแลระบบ");
	
	private String id;
	private String value;
	
	Role(String aID, String aValue){
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
	
	public static Role find(String aID){
		for (Role role : Role.values()) {
			if (role.id.equals(aID)){
				return role;
			}
		}
		return null;
	}
}
