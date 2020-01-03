package ar.com.tbf.webapi.common.interfaces;

import java.util.List;
import java.util.Map;

public interface MessageManager {

	public boolean hasErrors();

	public boolean hasMessages();
	public void addMessage( String message );
	public List<String> getMessages();
	
	public boolean hasFieldErrors();
	public void addFieldError( String field, String message );
	public Map<String, List<String>> getFieldErrors();
	public List<String> getFieldErrors( String field );
	public void setFieldErrors(Map<String, List<String>> fieldErrors);
	public boolean hasFieldError( String field );

	public boolean hasGenericErrors();
	public void addGenericError( String message );
	public List<String> getGenericErrors();
	public void setGenericErrors(List<String> genericErrors);

}
