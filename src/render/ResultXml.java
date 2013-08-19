package render;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

public class ResultXml {
	private ResultSet resultSet;
	
	public ResultXml(ResultSet resultset) 
			{
		this.resultSet = resultset;
		
			}
	
	public String getResult() throws SQLException, TransformerException
	{
		return getResult("n/a");

	}
			
	
	
	public String getResult(String xsltFileName) throws SQLException, TransformerException{
		
		
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = null;
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Document doc = builder.newDocument();
		    Element results = doc.createElement("Results");
		    doc.appendChild(results);
		    
		    if(!xsltFileName.equalsIgnoreCase("n/a")){
		    Node pi = doc.createProcessingInstruction
		            ("xml-stylesheet", "type=\"text/xsl\" href=\"../../pages/"+xsltFileName+".xsl\"");
		      doc.insertBefore(pi, results);}

		   
		    
		    
		    ResultSetMetaData rsmd = resultSet.getMetaData();
	    	 int colCount = rsmd.getColumnCount();
	    	 for (int i=1;i<=colCount;i++)
	    	 {
	    		 System.out.print(rsmd.getColumnName(i));        		 
	    	 }
	    	 
	    	 System.out.println();
	    	 
	    	 while (resultSet.next()) {
	    	      Element row = doc.createElement("Row");
	    	      results.appendChild(row);
	    	      for (int i = 1; i <= colCount; i++) {
	    	        String columnName = rsmd.getColumnName(i);
	    	        Object value = resultSet.getObject(i);
	    	        Element node = doc.createElement(columnName);
	    	        if(value==null)
	    	        {
	    	        	node.appendChild(doc.createTextNode(""));	
	    	        }
	    	        else
	    	        {
	    	        	node.appendChild(doc.createTextNode(value.toString()));
	    	        }
	    	        
	    	        row.appendChild(node);
	    	      }
	    	    }
	    	 
	    	    Element newForm = doc.createElement("inputForm");
	    	       	    
			    results.appendChild(newForm);
			    
			    
			    for (int i = 1; i <= colCount; i++) {
			    	Element row = doc.createElement("column");
		    	    newForm.appendChild(row);
			    	String columnName = rsmd.getColumnName(i);
			    	row.appendChild(doc.createTextNode(columnName));
	    	        newForm.appendChild(row);

	    	      }
			    
			    Element row = doc.createElement("table");
	    	    newForm.appendChild(row);
		    	row.appendChild(doc.createTextNode(rsmd.getTableName(1)));
    	        newForm.appendChild(row);

			    
			    
	    	 
	    	    DOMSource domSource = new DOMSource(doc);
	    	    TransformerFactory tf = TransformerFactory.newInstance();
	    	    Transformer transformer = tf.newTransformer();
	    	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    	    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	    	    
	    	    StringWriter sw = new StringWriter();
	    	    StreamResult sr = new StreamResult(sw);
	    	    transformer.transform(domSource, sr);

	    	   return sw.toString();



		    

	}

}
