/**
 * ReverseEchoServer.java
 * Copyright (c) 2002 by Dr. Herong Yang
 */
package server;
import java.io.*;
import java.net.*;
public class Server implements Runnable {
   private Socket con = null;
   public Server() {
      try {
         ServerSocket s = new ServerSocket(8888);
         printServerSocketInfo(s);
         while (true) {
            Socket c = s.accept();
            printSocketInfo(c);
            Server v = new Server(c);
            Thread t = new Thread(v);
            t.start();
         }
      } catch (IOException e) {
         System.err.println(e.toString());
      }
   }
   
   public Server(Socket c){
      con = c;
   }
   public void run() { 
      try {
         BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
            con.getOutputStream()));
         BufferedReader r = new BufferedReader(new InputStreamReader(
            con.getInputStream()));
         String m = "Welcome to Reverse Echo Server."+
            " Please type in some words.";
         w.write(m,0,m.length());
         w.newLine();
         w.flush();
         while ((m=r.readLine())!= null) {
            if (m.equals(".")) break;
            char[] a = m.toCharArray();
            int n = a.length;
            for (int i=0; i<n/2; i++) {
               char t = a[i];
               a[i] = a[n-1-i];
               a[n-i-1] = t;
            }
            w.write(a,0,n);
            w.newLine();
            w.flush();
         }
         w.close();
         r.close();
         con.close();
      } catch (IOException e) {
         System.err.println(e.toString());
      }
   }
   private static void printSocketInfo(Socket s) {
      System.out.println("Remote address = "
         +s.getInetAddress().toString());
      System.out.println("Remote port = "
         +s.getPort());
      System.out.println("Local socket address = "
         +s.getLocalSocketAddress().toString());
      System.out.println("Local address = "
         +s.getLocalAddress().toString());
      System.out.println("Local port = "
         +s.getLocalPort());
   }
   private static void printServerSocketInfo(ServerSocket s) {
      System.out.println("Server socker address = "
         +s.getInetAddress().toString());
      System.out.println("Server socker port = "
         +s.getLocalPort());
   } 
}