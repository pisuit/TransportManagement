package transport.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import transport.application.TravellersAutocomplete;
import transport.model.PersonalInfo;

@FacesConverter(value="employeeConverter")
public class EmployeeConverter implements Converter {
	private List<PersonalInfo> employeeList = new ArrayList<PersonalInfo>();
	
	public EmployeeConverter(){	
		if(employeeList != null) employeeList.clear();
		FacesContext context = FacesContext.getCurrentInstance();
		TravellersAutocomplete travellers  =  (TravellersAutocomplete) context.getApplication().evaluateExpressionGet(context, "#{travellersAutocomplete}", TravellersAutocomplete.class); 
		employeeList.addAll(travellers.getEmployeeList());
	}
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submitedValue) {
		// TODO Auto-generated method stub
		if(submitedValue.trim().equals("")){
			return null;
		} else {
			for(PersonalInfo personalInfo : employeeList){
				if((personalInfo.toString()).contains(submitedValue)){
					return personalInfo;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		if(value == null || value.equals("")){
			return "";
		} else {
			return String.valueOf(((PersonalInfo)value).toString());
		}
	}

}
