package automation.reporter;

import javax.xml.bind.annotation.XmlElement;

public class Result {
	String status, URL;
	
	public Result(String status, String URL) {
		setStatus(status);
		setImageURL(URL);
	}
	
	public String getStatus() {
		return status;
	}
	
	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getImageURL() {
		return URL;
	}
	
	@XmlElement
	public void setImageURL(String URL) {
		this.URL = URL;
	}
}