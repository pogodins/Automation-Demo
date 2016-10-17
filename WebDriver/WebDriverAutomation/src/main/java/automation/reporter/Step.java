package automation.reporter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "step")
public class Step {
	String name, keyword, imageURL;
	Result result;
	
	public Step(){
		
	}
	
	public Step(String keyword, String name){
		setKeyword(keyword);
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	@XmlElement
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public Result getResult() {
		return result;
	}
	
	@XmlElement
	public void setResult(Result result) {
		this.result = result;
	}
}