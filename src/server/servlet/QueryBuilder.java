package server.servlet;

import java.util.Map;
import java.util.Map.Entry;

public class QueryBuilder {
	
	private Map<String,String[]> paramMap;
	
	public QueryBuilder(Map<String,String[]> paramMap)
	{
		this.paramMap =paramMap;
		
	}
	
	public String getQuery()
	{        
        String select="*";
        
        if(paramMap.containsKey("select"))
        {
        	for(String column :  paramMap.get("select"))
        	{
        		select+=column+",";
        	}
        	
        	select = select.substring(0, select.length()-1);
        	
        }
        
        String table=" ";        
        if(paramMap.containsKey("table"))
        {
        	for(String column :  paramMap.get("table"))
        	{
        		table+=column+",";
        		System.out.println("table name = "+column);
        	}
        	
        	table = table.substring(0, table.length()-1);
        	
        }
        
        String where="";        
        if(paramMap.containsKey("where"))
        {
        	where=" WHERE ";
        	for(String column :  paramMap.get("where"))
        	{
        		where+=column+",";
        	}
        	where = where.substring(0, where.length()-1);
        	
        }
        
        String query = "SELECT "+select+" FROM "+table+where;
        System.out.println("query:"+query);
        return query;
	}
	
	public String getStyle()
	{
		String style="n/a";
        if(paramMap.containsKey("style"))
        {
        	style=paramMap.get("style")[0];
        }
        System.out.println("style:"+style);
        return style;
	}
	
	public String postQuery()
	{     
		String column="";
		String value="";
		String table="";
		for(Entry<String,String[]> entry : paramMap.entrySet())
		{
			System.out.println();
			if(considerParam(entry.getKey()))
			{
				column+=","+entry.getKey();
				value+=",'"+entry.getValue()[0]+"'";

			}
			if(entry.getKey().equalsIgnoreCase("ID"))
			{
				column+=","+entry.getKey();
				value+=","+entry.getValue()[0]+"";

			}
			else
			{
				table = paramMap.get("table")[0];
			}
			
			
			
		}
		column= column.substring(1);
		value = value.substring(1);
	 
        String query = "INSERT INTO "+table+" ("+column+") VALUES ("+value+")";
        System.out.println("query:"+query);
        return query;
	}
	
	public String doAlter()
	{     
		
		String table = paramMap.get("table")[0];
		String column= paramMap.get("columnName")[0];
		String action= paramMap.get("alterTable")[0];
		System.out.println("action: "+action);
		 String query="";
		if (action.equalsIgnoreCase("ADD"))		
		{
			  query = "ALTER TABLE "+table+" "+action+" "+column+" VARCHAR(25)";
		}
		else if(action.equalsIgnoreCase("DROP"))
		{
			 query = "ALTER TABLE "+table+" "+action+" "+column;
		}
       
        System.out.println("query:"+query);
        return query;
	}
	
	private boolean considerParam(String key)
	{
		String[] ignoreParam ={"table","style","redirectTo","alterTable","ID","columnName"};
	     for(String param : ignoreParam)
	     {
	    	 if(param.equalsIgnoreCase(key))
	    	 {
	    		 return false;
	    	 }
	     }
		return true;
	}
	
	
	
	

}
