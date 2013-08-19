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

public class ResultJSON {
	
	private ResultSet resultSet;
	public ResultJSON(ResultSet resultset) 
			{
		this.resultSet = resultset;
		
			}
	
	public String getResult() throws SQLException, TransformerException{
		
		
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
		    
		    ResultSetMetaData rsmd = resultSet.getMetaData();
	    	 int colCount = rsmd.getColumnCount();
	    	 for (int i=1;i<=colCount;i++)
	    	 {
	    		 System.out.print(rsmd.getColumnName(i));        		 
	    	 }
	    	 
	    	 
	    	 while (resultSet.next()) {
	    	      Element row = doc.createElement("Row");
	    	      results.appendChild(row);
	    	      for (int i = 1; i <= colCount; i++) {
	    	        String columnName = rsmd.getColumnName(i);
	    	        Object value = resultSet.getObject(i);
	    	        Element node = doc.createElement(columnName);
	    	        node.appendChild(doc.createTextNode(value.toString()));
	    	        row.appendChild(node);
	    	      }
	    	    }
	    	 
	    	    DOMSource domSource = new DOMSource(doc);
	    	    TransformerFactory tf = TransformerFactory.newInstance();
	    	    Transformer transformer = tf.newTransformer();
	    	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    	    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	    	    StringWriter sw = new StringWriter();
	    	    StreamResult sr = new StreamResult(sw);
	    	    transformer.transform(domSource, sr);

	    	   return sw.toString();



		    

	}

}

}
