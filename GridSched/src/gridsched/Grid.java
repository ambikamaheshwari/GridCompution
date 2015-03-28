
package gridsched;
import java.io.*;
import java.sql.*;
import java.util.*;
//import javax.swing.JFileChooser;
import java.net.*;
import java.awt.*;
/**
 *
 * @author Ali Zaveri
 */
public class Grid {

    static String cpu[][]=new String[50][2];
    int jobs[][]=new int[99][3];
    static int proc=0;
    public static void Grid_Status()
    {

        int pno,cid;
        String cname;
        int total=0;
        Thread t=null;
        Thread l=null;

        try //reading client .txt
     {
    
            Scanner sc = new Scanner(new FileInputStream("C:\\Documents and Settings\\Ali Zaveri\\Desktop\\GridShed\\Client.txt"));
            while(sc.hasNext())
            {
                cid = sc.nextInt();
                pno = sc.nextInt();
                cname = sc.next();
                
                total++;
                t=new ConnectAndGet(cid,pno,cname,l);
                l=t;
                t.start();
            }
            proc=total;
            if(t != null) t.join() ;
            
     }
        
        catch(Exception e)
        {
            System.out.println("getGridStatus() reading file : " + e) ;
        }

    }
     synchronized static void decrProcessors()
    {
        --proc ;
    }

    synchronized static int getProcessors()
    {
        return proc ;
    }

     

    private static class ConnectAndGet extends Thread
 {


  String CompName ;
  int PortNumber = 4444 ;
  int compnumber = -1 ;
  Thread towait;
  public ConnectAndGet(int cid,int pno,String cname,Thread l)
  {
      CompName = cname ;
      PortNumber =pno  ;
      compnumber = cid ;
      towait=l;
  }
  public void run()
  {

    try
       {
        System.out.println("i m connecting to : "+PortNumber);
        Socket s = new Socket(CompName,PortNumber) ;
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
        Scanner sc = new Scanner(new BufferedInputStream(s.getInputStream()));
        pw.println("Identify");
        pw.flush();
        System.out.println("i will get output");
        cpu[compnumber][0] = sc.next() ;
        System.out.println("Checking CPU whether string proper: "+cpu[compnumber][0]);
        cpu[compnumber][1]="1";
        System.out.println("i got the output");
	    System.out.println(compnumber + " " +cpu[compnumber][0]);
        pw.close();
        sc.close();
        System.out.println("i m exiting to : "+PortNumber);
      }


   catch(Exception e)
   {
    System.out.println(e+"") ;
     decrProcessors();
    //System.out.println("No. of Processors available :"+ proc);
   }
   try
    {
        System.out.println("i m entering to : "+compnumber);
       if(towait != null) towait.join() ;
        System.out.println("i m exiting to : "+compnumber);
    }
    catch(Exception e)
    {
        System.out.println("Waiting for previous thread in connectandget : " + e) ;
   }
  }
 }

    public static void Show()
    {
        System.out.println("No. of Processors available :"+ getProcessors());
        System.out.println(cpu[0][0]+ " " +cpu[0][1]);
        
    }
    public static void main(String[] args)
    {
        Grid_Status();
        Show();

    }

}
