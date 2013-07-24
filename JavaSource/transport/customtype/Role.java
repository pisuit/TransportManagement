package transport.customtype;

public enum Role {
	NORMAL("NORMAL","��ѡ�ҹ����"),
//	DIRECTOR("DIRECTOR","����ӹ�¡�áͧ"),
	SERVICE_DIRECTOR("SERVICE_DIRECTOR","����ӹ�¡�áͧ��ԡ��"),
	SERVICE_STAFF("SERVICE_STAFF", "���˹�ҷ�"),
	ADMIN("ADMIN", "�������к�");
	
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
