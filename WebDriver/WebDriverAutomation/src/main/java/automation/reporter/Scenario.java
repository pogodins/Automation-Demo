package automation.reporter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "scenario")
public class Scenario {
	String name;
	List<Step> steps;
	
	public Scenario(){
		
	}
	
	public Scenario(String name){
		setName(name);
		steps = new ArrayList<Step>();
	}

	public String getName(){
		return name;
	}

	@XmlElement
	public void setName(String name){
		this.name = name;
	}

	public List<Step> getSteps(){
		return steps;
	}

	@XmlElements({ 
	    @XmlElement(name="step", type=Step.class)
	})
	@XmlElementWrapper
	public void setSteps(List<Step> steps){
		this.steps = steps;
	}
	
	public void addStep(Step step){
		steps.add(step);
	}
}