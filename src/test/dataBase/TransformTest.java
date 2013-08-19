package test.dataBase;

import static org.junit.Assert.*;

import javax.xml.transform.Transformer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import render.Transform;
import server.Server;

public class TransformTest {
	
	final String ROOT ="C:\\Programming\\project\\xmlDb\\";
	final String output = ROOT+"output.xml";
	final String output1 = ROOT+"output1.xml";
	String xml =ROOT+"site.xml";
	
	String xsl;

	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception 
	{
			
		
	}


	@Test
	public void testRemoveColumn() throws Exception {
		Transform t = new Transform();
		
		
		xsl =ROOT+"removeCol.xsl";
		t.addParamater("columName", "id");
		t.generate(output, xsl, output1);		
		
	}
	
	@Test
	public void testAddColumn() throws Exception {
		Transform t = new Transform();
		
		String xsl =ROOT+"addCol.xsl";
		t.addParamater("columName", "new Column with space");
		t.addParamater("columType", "string");
		t.addParamater("defaultValue", "test");
		t.generate(xml, xsl, output);
		
	}
	// Update column and if a new default value has been assigned and the field of the item is empty
	// then it will be set to the default value otherwise just copy the current value
	@Test
	public void testUpdateColumn() throws Exception {
		Transform t = new Transform();
		String xsl =ROOT+"updateCol.xsl";
		t.addParamater("columnRef", "b");		
		t.addParamater("newColumName", "Column name updated");
		t.addParamater("newColumType", "string");
		t.addParamater("newDefaultValue", "none");		
		t.generate(xml, xsl, output);
		
	}
	
	

		// Update column and if a new default value has been assigned and the field of the item is empty
		// then it will be set to the default value otherwise just copy the current value
		@Test
		public void testquerySimpleCondition() throws Exception {
			Transform t = new Transform();
			String xsl =ROOT+"query.xsl";
			//t.addParamater("query", "a = '01'");
			t.addParamater("query", "b <> ''");
			t.generate(xml, xsl, output);
			
		}

}
