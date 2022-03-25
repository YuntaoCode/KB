package com.yuntao;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AddParentNode {
	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse("input.xml");
			
//			String value = doc.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
//			System.out.println(value);
			
			Element language = doc.createElement("Language");			
			language.setAttribute("value", "English");
			
			Element eltRoot = doc.getDocumentElement();
			NodeList nList = eltRoot.getChildNodes();
	        for (int i = 0; i < nList.getLength(); i++) {
	            Node nNode = nList.item(i);	            
	            eltRoot.insertBefore(language, nNode);
	            language.appendChild(nNode);
	        }
			
	        
			// Output Document
	        TransformerFactory tf = TransformerFactory.newInstance();
	        
	        Transformer t = tf.newTransformer();
	        t.setOutputProperty(OutputKeys.INDENT, "yes");
	        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        DOMSource source = new DOMSource(doc);
	        
	        StreamResult result = new StreamResult(System.out);
	        t.transform(source, result);
	        
	        StreamResult file = new StreamResult(new File("output.xml"));
	        t.transform(source, file);

		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
		}
	}

}
