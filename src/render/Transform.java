package render;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Transform {
	
	Map<String,String> params;
	
	
	public Transform()
	{
		params = new TreeMap<>();
	}
	
	public void withParamaters(Map<String,String> parameters)
	{
		this.params = parameters;
	}
	
	public void addParamater(String paramName,String paramValue)
	{
		params.put(paramName,paramValue);
	}
	
	
	public void generate(String xml, String xsl, String output) throws Exception{
		// Création de la source DOM
		DocumentBuilderFactory fabriqueD = DocumentBuilderFactory.newInstance();
		DocumentBuilder constructeur = fabriqueD.newDocumentBuilder();
		File fileXml = new File(xml);
		Document document = constructeur.parse(fileXml);
		Source source = new DOMSource(document);
		
		// Création du fichier de sortie
		File fileHtml = new File(output);
		Result resultat = new StreamResult(fileHtml);
		
		// Configuration du transformer
		TransformerFactory fabriqueT = TransformerFactory.newInstance();
		StreamSource stylesource = new StreamSource(xsl);
		Transformer transformer = fabriqueT.newTransformer(stylesource);
		for(Entry<String,String> param : params.entrySet())
		{
			transformer.setParameter(param.getKey(),param.getValue());
		}
		//transformer.setOutputProperty(OutputKeys.METHOD, "html");
		
		// Transformation
		transformer.transform(source, resultat);
	}
	
	public String generate(String documentXml, String xsl) throws Exception{
		// Création de la source DOM
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( documentXml ) ) );  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
		Source source = new DOMSource(document);
		
		// Création du fichier de sortie
		StreamResult xmlOutput = new StreamResult(new StringWriter());

		
		// Configuration du transformer
		TransformerFactory fabriqueT = TransformerFactory.newInstance();
		StreamSource stylesource = new StreamSource(xsl);
		Transformer transformer = fabriqueT.newTransformer(stylesource);
		for(Entry<String,String> param : params.entrySet())
		{
			transformer.setParameter(param.getKey(),param.getValue());
		}
		//transformer.setOutputProperty(OutputKeys.METHOD, "html");
		
		// Transformation
		transformer.transform(source, xmlOutput);
		return xmlOutput.getWriter().toString();
		
	}

}
