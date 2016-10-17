package automation.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLUtils {
	
	public static void transformXmlToHtml(String xmlPath, String xslPath, String htmlPath){
		TransformerFactory tFactory = TransformerFactory.newInstance();
	
	    Source xslDoc = new StreamSource(new File(xslPath));
	    Source xmlDoc = new StreamSource(new File(xmlPath));
	
	    OutputStream htmlFile;
		try {
			htmlFile = new FileOutputStream(htmlPath);
		
			Transformer transform;
			transform = tFactory.newTransformer(xslDoc);
	
			transform.transform(xmlDoc, new StreamResult(htmlFile));
		} catch (FileNotFoundException e) {
			Logger.getLogger().error(e.getMessage(), e);
		} catch (TransformerConfigurationException e) {
			Logger.getLogger().error(e.getMessage(), e);
		} catch (TransformerException e) {
			Logger.getLogger().error(e.getMessage(), e);
		}
	}
	
	public static void buildXmlFromObject(String xmlPath, Object object){
		File file = new File(xmlPath);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(automation.reporter.Feature.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(object, file);
		} catch (JAXBException e) {
			Logger.getLogger().error(e.getMessage(), e);
		}
	}
}