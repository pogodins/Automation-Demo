package automation.reporter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Feature {
	String name;
	List<Scenario> scenarios;

	public Feature(){
		
	}
	
	public Feature(String name){
		setName(name);
		scenarios = new ArrayList<Scenario>();
	}

	public String getName(){
		return name;
	}
	
	@XmlElement
	public void setName(String name){
		this.name = name;
	}

	public List<Scenario> getScenarios(){
		return scenarios;
	}
	
	@XmlElements({ 
	    @XmlElement(name="scenario", type=Scenario.class)
	})
	@XmlElementWrapper
	public void setScenarios(List<Scenario> scenarios){
		this.scenarios = scenarios;
	}

	public void addScenario(Scenario scenario){
		scenarios.add(scenario);
	}
}