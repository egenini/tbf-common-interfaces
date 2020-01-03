package ar.com.tbf.webapi.common.share;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestResponseAccessibility {

	private static ThreadLocal<RequestResponseContainer> requestResponseContainer = new ThreadLocal<RequestResponseContainer>() {
		
		protected RequestResponseContainer initialValue() {
			return new RequestResponseAccessibility().new RequestResponseContainer();
		}
	};
	
	public static void setRequestResponse( HttpServletRequest request, HttpServletResponse response ) {
		
		requestResponseContainer.get().setRequest(  request  );
		requestResponseContainer.get().setResponse( response );
	}
	
	public static HttpServletRequest getRequest() {
		
		return requestResponseContainer.get().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		
		return requestResponseContainer.get().getResponse();
	}

	public static String getQuery() {
		
		return requestResponseContainer.get().getQuery();
	}
	
	public static String getUrl() {
		
		return requestResponseContainer.get().getUrl();
	}
	
	public static String getRemoteIp() {
		
		return requestResponseContainer.get().remoteAddr;
	}

	public static String getRemoteHost() {
		
		return requestResponseContainer.get().remoteHost;
	}
	
	public static String getMyIp() throws UnknownHostException {
		
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	public static boolean mismoDominio( ) {
	
		boolean ok = false;
		
		try {
			
			String hostAddress = getMyIp();
			String remoteIp    = getRemoteIp();
			
			ok = hostAddress.equals(remoteIp);
								
			if( ! ok ) {
				
				ok = remoteIp.equals("0:0:0:0:0:0:0:1");
			}
			
		} catch (UnknownHostException e) {
		}
		
		return ok;
	}
	
	/**
	 * request.getScheme() +"://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath()
	 * 
	 * @return
	 */
	public static String getApplicationURL() {
		
		return requestResponseContainer.get().getApplicationURL();
	}
	
	/**
	 * Considerando el siguiente ej: hhtp://localhost:8080/application/resource/another-resuroce...
	 * 
	 * ApplicationURL es request.getScheme() +"://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath()
	 * 
	 * donde contextPath es el nombre de la aplicacion, todo lo le sigue a esta es lo que se devuelve en este método ( resource/another-resuroce... )
	 *  
	 * @return String (/resource/another-resuroce...)
	 */
	public static String getServletPath() {
		
		return requestResponseContainer.get().getServletPath();
	}

	public static  List<String> getServletPaths() {
		
		return requestResponseContainer.get().getServletPaths();
	}
	
	/**
	 * @return el nombre de la aplicación obtenida del request.
	 */
	public static String whoIAm() {
		
		return requestResponseContainer.get().whoIAm();
	}

	public class RequestResponseContainer {
		
		private String url = null;
		private String query = "";
		private HttpServletRequest  request  = null;
		private HttpServletResponse response = null;
		private String remoteAddr = "";
		private String remoteHost = "";
		private String servletPath = "";
		private List<String> servletPaths = new ArrayList<String>();
		
		public HttpServletRequest getRequest() {
			return request;
		}
		public void setRequest(HttpServletRequest request) {
			
			String contextPath = request.getRequestURI().split("/")[1];
			String url         = request.getRequestURL().toString().replace(request.getRequestURI(), "");

			this.setUrl(   url +"/"+ contextPath    );
			this.setQuery( request.getQueryString() == null ? "" : request.getQueryString() );
			
			this.request = request;

	        if (request != null) {
	        	
	        	servletPaths.add( request.getServletPath() );
	        	
	        	setServletPath(request.getServletPath());
	        	
	            remoteAddr = request.getHeader("X-FORWARDED-FOR");
	            
	            if (remoteAddr == null || remoteAddr.isEmpty() ) {
	            	
	                remoteAddr = request.getRemoteAddr();
	            }
	            
	            remoteHost = request.getRemoteHost();	            
	        }
		}
		public HttpServletResponse getResponse() { 
			return response;
		}
		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getQuery() {
			return query;
		}
		public void setQuery(String query) {
			this.query = query;
		}
		
		public String getApplicationURL(){
	    	
	        return request.getScheme() +"://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath();
	    }

		public String whoIAm(){
	    	
	        return this.getRequest().getContextPath();
	    }
		public String getServletPath() {
			return servletPath;
		}
		
		public void setServletPath(String servletPath) {
			this.servletPath = servletPath;
		}
		public List<String> getServletPaths() {
			return servletPaths;
		}
		public void setServletPaths(List<String> servletPaths) {
			this.servletPaths = servletPaths;
		}	
	}
}
