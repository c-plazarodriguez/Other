package de.bund.bfr.busstopp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

public class XmlValidator {

	public static void main(String[] args) throws SOAPException, IOException {
		// System.err.println(new
		// XmlValidator().validate("/Users/arminweiser/Desktop/xml_test/Anleitung_pmmlab.txt"));
		// System.err.println(new
		// XmlValidator().validate("/Users/arminweiser/Desktop/xml_test/bbk/bbk1.xml"));
		// System.err.println(new
		// XmlValidator().validate("/Users/arminweiser/Desktop/xml_test/out.xml"));
		System.err.println(new XmlValidator().validateViaRequest("/Users/arminweiser/Downloads/null15.txt", new String[] {"kontrollpunktmeldung"}));
		// System.err.println(new
		// XmlValidator().validateViaRequest("C:/Users/weiser/Downloads/kontrollpunktmeldung.txt"));
	}

	public boolean validate(String filename) {
		return validate(new StreamSource(new File(filename)));
	}

	private boolean validate(Source ds) {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			URL xsd = getClass().getResource(
					//"/de/nrw/verbraucherschutz/idv/dienste/de.nrw.verbraucherschutz.idv.dienste.2016.2.warenrueckverfolgung.transport.schema.xsd");
					"/de/nrw/verbraucherschutz/idv/dienste/de.nrw.verbraucherschutz.idv.dienste.2016.2.warenrueckverfolgung.transport.schema.xsd");
			// URL xsd =
			// getClass().getResource("/de/bund/bfr/busstopp/util/xsd/dienste/de.nrw.verbraucherschutz.idv.dienste.2016.2.warenrueckverfolgung.transport.wsdl");
			Schema schema = factory.newSchema(xsd);
			Validator validator = schema.newValidator();
			validator.validate(ds);
		} catch (IOException | SAXException e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	   * Creates a request from an XML template
	   */
	  public boolean validateViaRequest(String filename, String[] tags) throws SOAPException, IOException {
		boolean result = true;
		boolean found = false;
	    InputStream template = new FileInputStream(new File(filename));
	    try {
	    	/*
			Unmarshaller reader;
				reader = JAXBContext.newInstance(Meldung.class.getPackage().getName()).createUnmarshaller();

				reader.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
						.newSchema(Meldung.class.getResource(
								"/de/nrw/verbraucherschutz/idv/dienste/de.nrw.verbraucherschutz.idv.dienste.2016.2.warenrueckverfolgung.transport.schema.xsd")));
								//"/de/bund/bfr/busstopp/util/xsd/dienste/de.nrw.verbraucherschutz.idv.dienste.2016.2.warenrueckverfolgung.transport.schema.xsd")));
	      */
	      MessageFactory mf = MessageFactory.newInstance(); // SOAPConstants.SOAP_1_1_PROTOCOL
	      SOAPMessage message = mf.createMessage(new MimeHeaders(), template);
	      SOAPPart sp = message.getSOAPPart();
	      SOAPEnvelope se = sp.getEnvelope();
	      SOAPBody body = se.getBody();
          NodeList nl = body.getChildNodes();
          for(int i=0;i<nl.getLength();i++) {
        	  if (result) {
                  Node nln = nl.item(i);
                  String nn = nln.getNodeName();
                  boolean tagAllowed = false;
                  for (String tag : tags) {
                	  if (nn.endsWith(":" + tag)) {
                		  tagAllowed = true;
                		  break;
                	  }
                  }
                  if (tagAllowed) { //  || nn.endsWith("analyseergebnis")
                      DOMSource ds = new DOMSource(nln);
                      //System.out.println(nln.getNodeName());
                      result = validate(ds); 
                      found = true;
                      if (result) {
                    	  //Kontrollpunktmeldung kpm = ((JAXBElement<Kontrollpunktmeldung>) reader.unmarshal(ds)).getValue();
                      }
                  }
        	  }
        	  else break;
          }

	      return result && found;
	    } catch (Exception e) {
		    return false;
	    } finally {
	      template.close();
	    }
	  }
	  
}
