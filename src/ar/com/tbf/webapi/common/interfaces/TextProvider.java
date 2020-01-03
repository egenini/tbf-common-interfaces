package ar.com.tbf.webapi.common.interfaces;

import java.util.Locale;

public interface TextProvider {

	public String toText( String key );
	public String toText( String key, Locale locale );
	
	public String toText( String key, Object... values );
	public String toText( String key, Locale locale, Object... values );
	
	
	/**
	 * 
	 * @param key: el texto a buscar.
	 * @param values: valores que pueden completar el texto cuando este tenga marcas como por ej: {0}. Nullable.
	 * @param locale: Nullable.
	 * @return El texto encontrado o la clave si es que no se encuentra.
	 */
	public String toText(String key, Object[] values, Locale locale );
}
