package server;

import database.DerbyDb;
import render.Transform;

public class Application {

	DerbyDb db =null;
	public Application()
	{
		db = DerbyDb.getInstance();		
	}

	public String getResult(String query)
	{
		String xml =null;
		try {
			xml = db.getItems(query);//"SELECT num, addr FROM streetaddr ORDER BY num");			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return xml;


	}
	
	public String getResult(String query,String style)
	{
		String xml =null;
		try {
			xml = db.getItems(query,style);//"SELECT num, addr FROM streetaddr ORDER BY num");			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return xml;

	}
	
	public String insert(String query)
	{
		String info =null;
		try {
			info = db.insert(query);//"INSERT INTO 		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return info;
	}
	
	
	public void alter(String query)
	{
		try {
			db.alterTable(query);//"ALTER INTO 		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}

}
