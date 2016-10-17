package automation.reporter;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import automation.utils.Logger;
import automation.utils.XMLUtils;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

public class CustomReporter implements Formatter, Reporter{
	private automation.reporter.Feature feature;
	private automation.reporter.Scenario scenario;
	private automation.reporter.Step step;
	private List<automation.reporter.Step> steps;
	private int stepIndex;

	@Override
	public void background(Background arg0) {
	}

	@Override
	public void close() {
	}

	@Override
	public void done() {
		String reportsPath = "test-output/cucumberreports/";
		String xmlPath = reportsPath + "report.xml";
		String xslPath = reportsPath + "report.xsl";
		String htmlPath = reportsPath + "report.html";
		
		XMLUtils.buildXmlFromObject(xmlPath, feature);
		XMLUtils.transformXmlToHtml(xmlPath, xslPath, htmlPath);
	}

	@Override
	public void endOfScenarioLifeCycle(Scenario arg0) {
		Logger.info(arg0.getKeyword() + ": " + arg0.getName() + " ended");
		feature.addScenario(scenario);
	}

	@Override
	public void eof() {
	}

	@Override
	public void examples(Examples arg0) {
		System.out.println("examples");
	}

	@Override
	public void feature(Feature arg0) {
		Logger.info(arg0.getKeyword() + ": " + arg0.getName());
		feature = new automation.reporter.Feature(arg0.getName());
	}

	@Override
	public void scenario(Scenario arg0) {
	}

	@Override
	public void scenarioOutline(ScenarioOutline arg0) {
		Logger.info(arg0.getKeyword() + ": " + arg0.getName());
	}

	@Override
	public void startOfScenarioLifeCycle(Scenario arg0) {
		Logger.info(arg0.getKeyword() + ": " + arg0.getName() + " started");
		scenario = new automation.reporter.Scenario(arg0.getName());
		steps = new ArrayList<automation.reporter.Step>();
		stepIndex = 0;
	}

	@Override
	public void step(Step arg0) {
		step = new automation.reporter.Step(arg0.getKeyword().trim(), arg0.getName());
		steps.add(step);
	}

	@Override
	public void syntaxError(String arg0, String arg1, List<String> arg2,
			String arg3, Integer arg4) {
	}

	@Override
	public void uri(String arg0) {
	}

	@Override
	public void after(Match arg0, Result arg1) {
	}

	@Override
	public void before(Match arg0, Result arg1) {
	}

	@Override
	public void embedding(String arg0, byte[] arg1) {
	}

	@Override
	public void match(Match arg0) {
		//System.out.println(arg0.getArguments());
	}

	@Override
	public void result(Result arg0) {
		step = steps.get(stepIndex);
		
		String status = arg0.getStatus();
		Logger.info(step.getName() + " - " + status.toUpperCase());
		
		if(status.equalsIgnoreCase("failed")){
			Logger.error(arg0.getErrorMessage());
		}
		
		String pathToScreenshot = ("screenshots/" + step.getKeyword() + "_" + step.getName() + ".png")
				.replace(" ", "_").replace(":", "");
		
		try{
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File("test-output/cucumberreports/" + pathToScreenshot));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		automation.reporter.Result result = new automation.reporter.Result(status, pathToScreenshot);
		step.setResult(result);
		scenario.addStep(step);
		
		stepIndex++;
	}

	@Override
	public void write(String arg0) {
	}
}