package database;

/*

Derby - Class SimpleMobileApp

Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.TransformerException;

import org.apache.derby.jdbc.EmbeddedSimpleDataSource;  // from derby.jar
import org.apache.derby.tools.sysinfo;

import render.ResultXml;
import render.Transform;


/**
* <p>This sample program is a small JDBC application showing how to use and
* access Derby in a mobile (Java ME, formerly known as J2ME) environment.
* </p>
* <p>Instructions for how to run this program are given in 
* <A HREF=example.html>readme.html</A> located in the same directory as this
* source file by default.
*
* <p>Derby supports <em>embedded</em> access in a mobile Java environment 
* provided that the Java Virtual Machine supports all of the following:
* </p>
* <ul>
*   <li>Connected Device Configuration (CDC) 1.1 (JSR-218)</li>
*   <li>Foundation Profile (FP) 1.1 (JSR-219) or better</li>
*   <li>JDBC Optional package (JSR-169) for Java ME platforms</li>
* </ul>
* 
* <p>(Older versions of Derby may support older versions of the above mentioned
* specifications. Please refer to release notes or ask on the derby-user
* mailing list for details.)
* </p>
* <p>The main difference between accessing a database in a Java ME environment
* as opposed to a Java SE or EE environment is that the JSR-169 specification
* does not include the <code>java.sql.DriverManager</code> class. However, 
* Derby provides a DataSource class that can be used to obtain connections to 
* Derby databases: <code>org.apache.derby.jdbc.EmbeddedSimpleDataSource</code>.
* This is demonstrated in this simple demo application.
* </p>
* <p>To compile this application on your own, make sure you include derby.jar 
* in the compiler's classpath; see <a href="readme.html">readme.html</a> for 
* details.
* </p>
*/
public class DataBase {
	
	static boolean vmSupportValid=false;


private static void start() {
     
   
     if (vmSupportsJSR169()) {
        	 vmSupportValid=true;
		
     } else {
         System.err.println("No valid JDBC support detected in this JVM. If "
                 + "you are running a Java ME (CDC) JVM, make sure support "
                 + "for the JSR-169 optional package is available to the "
                 + "JVM. Otherwise, make sure your JVM supports JDBC 3.0 or "
                 + "newer.\n");
         System.exit(1);
     }
     System.out.println("SimpleMobileApp finished");
 }
 
 static DataBase demo = null;
 
 private DataBase()
 {
	 //Singleton
 }

 static public DataBase getInstance()
 {
	 if (demo==null)
	 {
		 demo =  new DataBase();
		 start();
		 return demo;
		 
      }
	 else
	 {
		 return demo ;
	 }
	 
 }
 
 
 public String find(String query) throws Exception {
     
     System.out.println("Derby Database started");
     String xml="";
     EmbeddedSimpleDataSource ds = new EmbeddedSimpleDataSource();
     String dbName = "simpleMobileDB"; // the name of the database
     ds.setDatabaseName(dbName);
     
     /* We will be using Statement and PreparedStatement objects for 
      * executing SQL. These objects are resources that should be released 
      * explicitly after use, hence the try-catch-finally pattern below.
      */
     Connection conn = null;
     Statement s = null;
     PreparedStatement ps = null;
     ResultSet rs = null;    // used for retreiving the results of a query
     try {
         conn = ds.getConnection();
         System.out.println("Connected to and created database " + dbName);
         s = conn.createStatement();
        // Select the rows and verify some of the results...
         rs = s.executeQuery(query);
         try {
			ResultXml resultXml  = new ResultXml(rs);
			
			xml = resultXml.getResult("result.xsl");
			
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

     
          try {
              ds.setShutdownDatabase("shutdown");
              ds.getConnection();
          } catch (SQLException se) {
              if (!( (se.getErrorCode() == 45000) 
                      && ("08006".equals(se.getSQLState()) ))) {
                 // if the error code or SQLState is different, we have an
                 // unexpected exception (shutdown failed)
                 printSQLException(se);
                 se.printStackTrace();
              } else {
                  System.out.println(dbName + " shut down successfully");
              }
          }

     } catch (SQLException e) {
         printSQLException(e);
     } finally {
         // release all open resources to avoid unnecessary memory usage
         
         // ResultSet
         try {
             if (rs != null) {
                 rs.close();
                 rs = null;
             }
         } catch (SQLException sqle) {
             printSQLException(sqle);
         }
         
         // Statement
         try {
             if (s != null) {
                 s.close();
                 s = null;
             }
         } catch (SQLException sqle) {
             printSQLException(sqle);
         }
         
         //PreparedStatement
         try {
             if (ps != null) {
                 ps.close();
                 ps = null;
             }
         } catch (SQLException sqle) {
             printSQLException(sqle);
         }

         //Connection
         try {
             if (conn != null) {
                 conn.close();
                 conn = null;
             }
         } catch (SQLException sqle) {
             printSQLException(sqle);
         }
         
         
     }
   return xml;
 }
 
 
 
 
 
 /**
  * Prints information about an SQLException to System.err.
  * Use this information to debug the problem.
  * 
  * @param e Some SQLException which info should be printed
  */
 public static void printSQLException(SQLException e) {

     do {
         System.err.println("\n----- SQLException caught: -----");
         System.err.println("  SQLState:   " + e.getSQLState());
         System.err.println("  Error Code: " + e.getErrorCode());
         System.err.println("  Message:    " + e.getMessage());
         //e.printStackTrace(System.err); // enable and recompile to get more info
         System.err.println();
         e = e.getNextException();
     } while (e != null);
 }
 
/**
 * Checks if this Java Virtual Machine includes support for the JDBC optional
 * package for CDC platforms (JSR-169), or better (JDBC 3.0 or newer), by
 * checking the availability of classes or interfaces introduced in or
 * removed from specific versions of JDBC-related specifications.
 * 
 * @return true if the required JDBC support level is detected, false 
 *         otherwise.
 */
 public static boolean vmSupportsJSR169() {
     if (haveClass("java.sql.Savepoint")) {
         /* New in JDBC 3.0, and is also included in JSR-169.
          * JSR-169 is a subset of JDBC 3 which does not include the Driver 
          * interface.
          * See http://wiki.apache.org/db-derby/VersionInfo for details.
          */
         return true;
     } else {
         return false;
     }
 }
 
/**
 * Checks if this JVM is able to load a specific class. May for instance
 * be used for determining the level of JDBC support available.
 * @param className Fully qualified name of class to attempt to load.
 * @return true if the class can be loaded, false otherwise.
 */
private static boolean haveClass(String className) {
    try {
        Class.forName(className);
        return true;
    } catch (Exception e) {
        return false;
    }
}

}
