package gridsched;
import java.io.*;
import java.sql.*;
import java.util.*;
//import javax.swing.JFileChooser;
import java.net.*;
import java.awt.*;
import javax.swing.*;



public class Grid_AVSN extends javax.swing.JFrame
{

    static String cpu[][];
    static String algos[][];
    static int jobs[][]=new int[99][6];
    static int proc=0;
    static int N_appl;
    static String Appl_Name[][]=new String[100][3];
    static int fileno=0;
    static int map[][]=new int[99][4];
    static String f_cpu[][]; //final cpu list in order
    static long s_sch_time;
    static long sch_time;
    static long s_time;
    static long e_time;
    static long p_t[];
    static int failed_jobs[][];//=new int[99][5];
    static int failed_c=0;
    static int failed_flag=0;
    static double total_cost;
    int a;
    public Grid_AVSN()
    {
        
        initComponents();
        //Schedule.enable(false);

        Ant.setEnabled(false);
        Genetic.setEnabled(false);
        Fuzzy.setEnabled(false);
        Ant_Genetic.setEnabled(false);
        Schedule.setEnabled(false);
        Add_Appl.setEnabled(false);
        Execute.setEnabled(false);
        Refresh.setEnabled(false);
        Ant_option.setEnabled(false);
        algos=new String[4][3];
        algos[0][1]="Ant";algos[0][0]="0";
        algos[1][1]="Fuzzy";algos[1][0]="0";
        algos[2][1]="Genetic";algos[2][0]="0";
        algos[3][1]="Ant+Genetic";algos[3][0]="0";

    }

    public static void Grid_Status()
    {

        cpu=new String[50][7];
        int pno,cid,ccost;
        String cname,av,fire,os;
        int total=0,af,o;
        Thread t=null;
        Thread l=null;

        try //reading client .txt
     {

            Scanner sc = new Scanner(new FileInputStream("Client.txt"));
            while(sc.hasNext())
            {
                af=0;
                o=0;
                cid = sc.nextInt();
                pno = sc.nextInt();
                cname = sc.next();
                ccost = sc.nextInt();
                av=sc.next();
                fire=sc.next();
                os=sc.next();
                if(av.equals("N"))
                    af+=4;
                if(av.equals("E"))
                    af+=3;
                if(av.equals("A"))
                    af+=1;
                //Based on Firewall
                if(fire.equals("H"))
                    af=4;
                if(fire.equals("M"))
                    af+=2;
                if(fire.equals("L"))
                    af+=1;
                if(fire.equals("NA"))
                    af+=0;
                //Operating System
                if(os.equals("W"))
                    o=1;
                if(os.equals("L"))
                    o=0;

                total++;
                t=new ConnectAndGet(cid,pno,cname,l,ccost,af,o);
                l=t;
                t.start();
            }
            proc=total;
            if(t != null) t.join() ;

          
     }

        catch(Exception e)
        {
            System.out.println("Grid_Status() reading file : " + e) ;
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
  int CompCost,anti_fire,os ;

  Thread towait;
  public ConnectAndGet(int cid,int pno,String cname,Thread l,int ccost,int af,int o)
  {
      CompName = cname ;
      PortNumber =pno  ;
      compnumber = cid ;
      CompCost=ccost;
      anti_fire=af;
      os=o;
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
        cpu[compnumber][1]=CompName;
        cpu[compnumber][2]=""+PortNumber;
        cpu[compnumber][3]="1";
        cpu[compnumber][4]=""+CompCost;
        cpu[compnumber][5]=""+anti_fire;
        cpu[compnumber][6]=""+os;
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

    public static void f_cpu_c()
    {
        try{
        f_cpu=new String[50][7];
        int count=0;
        
        for(int i=0;i<50;i++)
        {
            if(cpu[i][3]!=null)
            {
                f_cpu[count][0]=cpu[i][0];
                f_cpu[count][1]=cpu[i][1];
                f_cpu[count][2]=cpu[i][2];
                f_cpu[count][3]=cpu[i][3];
                f_cpu[count][4]=cpu[i][4];
                f_cpu[count][5]=cpu[i][5];
                f_cpu[count][6]=cpu[i][6];
                count++;
                               
            }
            if(count==proc)
                break;
        }
        f_cpu[1][0]="0";

        }

        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        NA = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        EnterNA = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        App_List = new javax.swing.JTextArea();
        Add_Appl = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Split_Appl = new javax.swing.JTextArea();
        Ant = new javax.swing.JRadioButton();
        Fuzzy = new javax.swing.JRadioButton();
        Genetic = new javax.swing.JRadioButton();
        Ant_Genetic = new javax.swing.JRadioButton();
        Schedule = new javax.swing.JButton();
        Close = new javax.swing.JButton();
        Show_Graph = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        Execute = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Output = new javax.swing.JTextArea();
        Ant_option = new javax.swing.JComboBox();
        C_N = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        NA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NAActionPerformed(evt);
            }
        });

        jLabel1.setText("Number of Applications");

        EnterNA.setText("Enter");
        EnterNA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterNAActionPerformed(evt);
            }
        });

        App_List.setColumns(20);
        App_List.setRows(5);
        jScrollPane1.setViewportView(App_List);

        Add_Appl.setText("Add Application");
        Add_Appl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_ApplActionPerformed(evt);
            }
        });

        Split_Appl.setColumns(20);
        Split_Appl.setRows(5);
        jScrollPane2.setViewportView(Split_Appl);

        buttonGroup1.add(Ant);
        Ant.setText("Ant");
        Ant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AntActionPerformed(evt);
            }
        });

        buttonGroup1.add(Fuzzy);
        Fuzzy.setText("Fuzzy");

        buttonGroup1.add(Genetic);
        Genetic.setText("Genetic");
        Genetic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeneticActionPerformed(evt);
            }
        });

        buttonGroup1.add(Ant_Genetic);
        Ant_Genetic.setText("Ant + Genetic");
        Ant_Genetic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ant_GeneticActionPerformed(evt);
            }
        });

        Schedule.setText("Display Schedule");
        Schedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScheduleActionPerformed(evt);
            }
        });

        Close.setText("Terminate Client");
        Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });

        Show_Graph.setText("Show Graph");
        Show_Graph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Show_GraphActionPerformed(evt);
            }
        });

        Refresh.setText("Refresh");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        Execute.setText("Execute");
        Execute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecuteActionPerformed(evt);
            }
        });

        Output.setColumns(20);
        Output.setRows(5);
        jScrollPane3.setViewportView(Output);

        Ant_option.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Minimise Makespan", "Minimise Cost" }));
        Ant_option.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ant_optionActionPerformed(evt);
            }
        });

        C_N.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C_NActionPerformed(evt);
            }
        });

        jLabel2.setText("Enter Client Port");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Ant_Genetic)
                            .addComponent(Genetic)
                            .addComponent(Fuzzy)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Ant)
                                .addGap(18, 18, 18)
                                .addComponent(Ant_option, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Add_Appl, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(NA, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EnterNA))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Show_Graph, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Schedule, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Execute, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(C_N, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(Close)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EnterNA))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Add_Appl)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Ant)
                            .addComponent(Ant_option, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Fuzzy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Genetic)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Ant_Genetic)
                        .addGap(23, 23, 23))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Schedule)
                    .addComponent(Execute))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Show_Graph)
                    .addComponent(Refresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C_N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Close))
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnterNAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterNAActionPerformed
        try
        {
            Add_Appl.setEnabled(true);
            String t=NA.getText();
            System.out.println(t);
            N_appl = Integer.parseInt(NA.getText());
            a=N_appl;
            EnterNA.setEnabled(false);
        }
        catch(Exception e)
        {
          javax.swing.JOptionPane.showMessageDialog(this,"Please Enter Number of Applications");
        }
}//GEN-LAST:event_EnterNAActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed

        Output.setText("");
        NA.setText("");
        App_List.setText("");
        Split_Appl.setText("");
        EnterNA.setEnabled(true);
        Ant.setEnabled(false);
        Genetic.setEnabled(false);
        Fuzzy.setEnabled(false);
        Ant_Genetic.setEnabled(false);
        Schedule.setEnabled(false);
        Add_Appl.setEnabled(false);
        Execute.setEnabled(false);
        Refresh.setEnabled(false);
        Ant_option.setEnabled(false);
        algos=new String[4][3];
        algos[0][1]="Ant";algos[0][0]="0";
        algos[1][1]="Fuzzy";algos[1][0]="0";
        algos[2][1]="Genetic";algos[2][0]="0";
        algos[3][1]="Ant+Genetic";algos[3][0]="0";
        buttonGroup1.clearSelection();
        N_appl=0;/*gives total appl*/
        jobs = new int[99][5];
        map = new int[99][3];
        Appl_Name=new String[100][3];
        fileno=0;
        proc=0;
        Grid_Status();
        

        

}//GEN-LAST:event_RefreshActionPerformed

    private void AntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AntActionPerformed
    Ant_option.setEnabled(true);
}//GEN-LAST:event_AntActionPerformed

    private void Ant_GeneticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ant_GeneticActionPerformed
        
}//GEN-LAST:event_Ant_GeneticActionPerformed

    private void NAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NAActionPerformed

    }//GEN-LAST:event_NAActionPerformed

    private void Add_ApplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_ApplActionPerformed

        if(proc == 0)
       {
           Output.append("None of the Clients are available");
           Add_Appl.setEnabled(false);
       }


       else
       {
           if(a!=0)
       {
               
         //FileChooser.setVisible(true);
            File_C_S ff = new File_C_S();
            ff.setVisible(true);
            
            if(a==1)
            {
              Ant.setEnabled(true);
              Genetic.setEnabled(true);
              Fuzzy.setEnabled(true);
              Ant_Genetic.setEnabled(true);
              Schedule.setEnabled(true);
              Add_Appl.setEnabled(true);
              Execute.setEnabled(true);
              Refresh.setEnabled(true);
            }
            a--;
           

       }
       else if(N_appl == 0)
       {
           javax.swing.JOptionPane.showMessageDialog(this,"You have not added any the Applications","Please add Application ",javax.swing.JOptionPane.ERROR_MESSAGE);

       }
       else
       {
            Add_Appl.setEnabled(false);
            javax.swing.JOptionPane.showMessageDialog(this,"You have already added all the Applications","Cant add more Application ",javax.swing.JOptionPane.ERROR_MESSAGE);
       }

    }
    }//GEN-LAST:event_Add_ApplActionPerformed


    private void ScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScheduleActionPerformed
        s_sch_time = System.currentTimeMillis();
        total_cost=0;
        if(Fuzzy.isSelected())
        {
            Output.setText("");
            Fuzzy fuz=new Fuzzy(proc,N_appl*3,f_cpu,jobs,Appl_Name);
            fuz.make_ET();
            map=fuz.Fuzzy_Main();
             for(int i=0;i<N_appl*3;i++)
            {
                 Output.append("Job"+map[i][0]+" on Processor"+map[i][1]+"\n");
            }
            sch_time=(System.currentTimeMillis()-s_sch_time)/1000;
            algos[1][2]=""+sch_time;

        }
        if(Ant.isSelected())
        {
            
            Output.setText("");
            gridsched.Ant.Init_stat_Ant(N_appl*3, proc,jobs,f_cpu);
            gridsched.Ant.initpheromone();
            if(Ant_option.getSelectedIndex()==0)
            gridsched.Ant.make_ET();
            else
            gridsched.Ant.make_ET_Cost();
            Ant[] a=new Ant[N_appl*3];
            int iteration=0;
            int c=0;
            int N_Iter=4*gridsched.Ant.no_ants;
            int best=999,bestIndex;
            for(int i=0;i<N_appl*3;i++)
            {
                a[i]=new Ant();
            }

            int c_p=0;

            for(int i=0;i<gridsched.Ant.no_ants;i++)
                for(int j=0;j<N_appl*3;j++)
                            a[i].tabu[j]=0;


            for(int i=0;i<N_appl*3;i++)
            {
                if(c_p%proc==0)
                    c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;
              
            }

            while(iteration<N_Iter)
            {

                for(int i=0;i<gridsched.Ant.no_ants;i++)
                {
                    a[i].next_move();
                }
                c++;
                if(c%((N_appl*3)-1)==0)
                {
                    for(int j=0;j<gridsched.Ant.no_ants;j++)
                        a[j].update_pheromone();
                    c_p=0;
                    for(int i=0;i<gridsched.Ant.no_ants;i++)
                    {
                    for(int j=0;j<N_appl*3;j++)
                            a[i].tabu[j]=0;
                    for(int j=0;j<proc;j++)
                            a[i].free[j]=0;
                       /* a[i].tourindex=0;
                        a[i].tabu[a[i].tour[(N_appl*3)-1][1]]=1;
                        a[i].tour[a[i].tourindex][0]=a[i].tour[(N_appl*3)-1][0];
                        a[i].tour[a[i].tourindex][1]=a[i].tour[(N_appl*3)-1][1];
                        a[i].tourLength=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];
                        a[i].free[a[i].tour[(N_appl*3)-1][0]]=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];*/
                    if(c_p%proc==0)
                        c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;
                    }
                    c=0;
                 }

                 iteration++;
            }

            int best_index=0;
            double best_Length=999;
            for(int i=0;i<gridsched.Ant.no_ants;i++)
            {
                if(a[i].tourLength<best_Length)
                {
                    best_Length=a[i].tourLength;
                    best_index=i;
                }


            }
            System.out.println("before Schedule:");
            Output.append("Schedule:\n");
            System.out.println("after Schedule:");

            for(int i=0;i<N_appl*3;i++)
            {
                map[i][0]=a[best_index].tour[i][1];
                map[i][1]=a[best_index].tour[i][0];


                //System.out.println(map[i][0]+":"+map[i][1]);
                Output.append("Job"+jobs[map[i][0]][0]+jobs[map[i][0]][1]+" on Processor"+map[i][1]+" on port "+f_cpu[map[i][1]][2]+"\n");
            }


            sch_time=(System.currentTimeMillis()-s_sch_time)/1000;
            algos[0][2]=""+sch_time;


        }
        else if(Genetic.isSelected())
        {
            
            Output.setText("");
            Genetic gen=new Genetic(f_cpu,jobs,N_appl*3,proc);
            gen.geneticalgo();
             for(int i=0;i<N_appl*3;i++)
                Output.append("Job"+jobs[map[i][0]][0]+jobs[map[i][0]][1]+" on Processor"+map[i][1]+" on port "+f_cpu[map[i][1]][2]+"\n");
            
            sch_time=(System.currentTimeMillis()-s_sch_time)/1000;
            algos[2][2]=""+sch_time;

        }
        else if(Ant_Genetic.isSelected())
        {
            
            Output.setText("");
            gridsched.Ant.Init_stat_Ant(N_appl*3,proc ,jobs,f_cpu);
            gridsched.Ant.initpheromone();
             gridsched.Ant.make_ET();
            Ant[] a=new Ant[N_appl*3];
            int iteration=0;
            int c=0;
            int N_Iter=4*gridsched.Ant.no_ants;
            int best=999,bestIndex;
            for(int i=0;i<N_appl*3;i++)
            {
                a[i]=new Ant();
            }

            int c_p=0;

            for(int i=0;i<gridsched.Ant.no_ants;i++)
                for(int j=0;j<N_appl*3;j++)
                            a[i].tabu[j]=0;


            for(int i=0;i<N_appl*3;i++)
            {
                if(c_p%proc==0)
                    c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;

            }

            while(iteration<N_Iter)
            {

                for(int i=0;i<gridsched.Ant.no_ants;i++)
                {
                    a[i].next_move();
                }
                c++;
                if(c%((N_appl*3)-1)==0)
                {
                    for(int j=0;j<gridsched.Ant.no_ants;j++)
                        a[j].update_pheromone();
                    c_p=0;
                    for(int i=0;i<gridsched.Ant.no_ants;i++)
                    {
                    for(int j=0;j<N_appl*3;j++)
                            a[i].tabu[j]=0;
                    for(int j=0;j<proc;j++)
                            a[i].free[j]=0;
                       /* a[i].tourindex=0;
                        a[i].tabu[a[i].tour[(N_appl*3)-1][1]]=1;
                        a[i].tour[a[i].tourindex][0]=a[i].tour[(N_appl*3)-1][0];
                        a[i].tour[a[i].tourindex][1]=a[i].tour[(N_appl*3)-1][1];
                        a[i].tourLength=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];
                        a[i].free[a[i].tour[(N_appl*3)-1][0]]=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];*/
                    if(c_p%proc==0)
                        c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;
                    }
                    c=0;
                 }

                 iteration++;
            }

            int best_index=0;
            double best_Length=999;
            for(int i=0;i<gridsched.Ant.no_ants;i++)
            {
                if(a[i].tourLength<best_Length)
                {
                    best_Length=a[i].tourLength;
                    best_index=i;
                }
            }

            Genetic gen=new Genetic(f_cpu,jobs,N_appl*3,proc,a[best_index].tour);
            gen.geneticalgo();
            for(int i=0;i<N_appl*3;i++)
                Output.append("Job"+jobs[map[i][0]][0]+jobs[map[i][0]][1]+" on Processor"+map[i][1]+" on port "+f_cpu[map[i][1]][2]+"\n");

            sch_time=(System.currentTimeMillis()-s_sch_time)/1000;
            algos[3][2]=""+sch_time;

        }
       


    }//GEN-LAST:event_ScheduleActionPerformed

    private void GeneticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GeneticActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GeneticActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void ExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecuteActionPerformed
        s_time=System.currentTimeMillis();
        runSchedule(N_appl*3);
       System.out.println("failed_c="+failed_c);
       while(failed_flag==1)
       { int f;
         Grid_Status();
         f_cpu_c();
         Output.append("\n\nClients now available on grid:\n\n");
         Output.append("Processor No.\tName\tPort No.\n");
         for(int i=0;i<proc;i++)
                Output.append(i+"\t"+f_cpu[i][1]+"\t"+f_cpu[i][2]+"\n");
         System.out.println("reshed has been called");
         
         int failed_count=0;
         failed_jobs=new int[99][6];
         for(int i=0;i<N_appl*3;i++)
         {
             if(jobs[i][4]==0)
             {
                Grid_AVSN.failed_jobs[failed_count][0]=Grid_AVSN.jobs[i][0];
                Grid_AVSN.failed_jobs[failed_count][1]=Grid_AVSN.jobs[i][1];
                Grid_AVSN.failed_jobs[failed_count][2]=Grid_AVSN.jobs[i][2];
                Grid_AVSN.failed_jobs[failed_count][3]=Grid_AVSN.jobs[i][3];
                Grid_AVSN.failed_jobs[failed_count][4]=Grid_AVSN.jobs[i][4];
                Grid_AVSN.failed_jobs[failed_count][5]=i;
                failed_count++;
             }
         }

         if(Ant.isSelected())
        {
            

            gridsched.Ant.Init_stat_Ant(failed_c, proc,failed_jobs,f_cpu);
            gridsched.Ant.initpheromone();
             if(Ant_option.getSelectedIndex()==0)
            gridsched.Ant.make_ET();
            else
            gridsched.Ant.make_ET_Cost();
            Ant[] a=new Ant[failed_c];
            int iteration=0;
            int c=0;
            int N_Iter=4*gridsched.Ant.no_ants;
            int best=999,bestIndex;
            for(int i=0;i<failed_c;i++)
            {
                a[i]=new Ant();
            }

            int c_p=0;

            for(int i=0;i<gridsched.Ant.no_ants;i++)
                for(int j=0;j<failed_c;j++)
                            a[i].tabu[j]=0;


            for(int i=0;i<failed_c;i++)
            {
                if(c_p%proc==0)
                    c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;

            }

            while(iteration<N_Iter)
            {

                for(int i=0;i<gridsched.Ant.no_ants;i++)
                {
                    a[i].next_move();
                }
                c++;
                if(failed_c==1 || c%((failed_c)-1)==0)
                {
                    for(int j=0;j<gridsched.Ant.no_ants;j++)
                        a[j].update_pheromone();
                    c_p=0;
                    for(int i=0;i<gridsched.Ant.no_ants;i++)
                    {
                    for(int j=0;j<failed_c;j++)
                            a[i].tabu[j]=0;
                    for(int j=0;j<proc;j++)
                            a[i].free[j]=0;
                       /* a[i].tourindex=0;
                        a[i].tabu[a[i].tour[(N_appl*3)-1][1]]=1;
                        a[i].tour[a[i].tourindex][0]=a[i].tour[(N_appl*3)-1][0];
                        a[i].tour[a[i].tourindex][1]=a[i].tour[(N_appl*3)-1][1];
                        a[i].tourLength=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];
                        a[i].free[a[i].tour[(N_appl*3)-1][0]]=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];*/
                    if(c_p%proc==0)
                        c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;
                    }
                    c=0;
                 }

                 iteration++;
            }

            int best_index=0;
            double best_Length=999;
            for(int i=0;i<gridsched.Ant.no_ants;i++)
            {
                if(a[i].tourLength<best_Length)
                {
                    best_Length=a[i].tourLength;
                    best_index=i;
                }


            }
            System.out.println("beffore Schedule:");
            Output.append("\nRescheduling of failed jobs:\n");
            System.out.println("after Schedule:");

            for(int i=0;i<failed_c;i++)
            {
                map[i][0]=a[best_index].tour[i][1];
                map[i][1]=a[best_index].tour[i][0];
                //System.out.println(map[i][0]+":"+map[i][1]);
                Output.append("Job"+failed_jobs[map[i][0]][0]+failed_jobs[map[i][0]][1]+" on Processor"+map[i][1]+" on port "+f_cpu[map[i][1]][2]+"\n");
            }

        }
        else if(Genetic.isSelected())
        {
            
            Genetic gen=new Genetic(f_cpu,failed_jobs,failed_c,proc);
            gen.geneticalgo();
             for(int i=0;i<failed_c;i++)
                Output.append("Job"+failed_jobs[map[i][0]][0]+failed_jobs[map[i][0]][1]+" on Processor"+map[i][1]+" on port "+f_cpu[map[i][1]][2]+"\n");
        }
        else if(Ant_Genetic.isSelected())
        {
            
            gridsched.Ant.Init_stat_Ant(failed_c, proc,failed_jobs,f_cpu);
            gridsched.Ant.initpheromone();
             gridsched.Ant.make_ET();
            Ant[] a=new Ant[failed_c];
            int iteration=0;
            int c=0;
            int N_Iter=4*gridsched.Ant.no_ants;
            int best=999,bestIndex;
            for(int i=0;i<failed_c;i++)
            {
                a[i]=new Ant();
            }

            int c_p=0;

            for(int i=0;i<gridsched.Ant.no_ants;i++)
                for(int j=0;j<failed_c;j++)
                            a[i].tabu[j]=0;


            for(int i=0;i<failed_c;i++)
            {
                if(c_p%proc==0)
                    c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;

            }

            while(iteration<N_Iter)
            {

                for(int i=0;i<gridsched.Ant.no_ants;i++)
                {
                    a[i].next_move();
                }
                c++;
                if(failed_c==1 || c%((failed_c)-1)==0)
                {
                    for(int j=0;j<gridsched.Ant.no_ants;j++)
                        a[j].update_pheromone();
                    c_p=0;
                    for(int i=0;i<gridsched.Ant.no_ants;i++)
                    {
                    for(int j=0;j<failed_c;j++)
                            a[i].tabu[j]=0;
                    for(int j=0;j<proc;j++)
                            a[i].free[j]=0;
                       /* a[i].tourindex=0;
                        a[i].tabu[a[i].tour[(N_appl*3)-1][1]]=1;
                        a[i].tour[a[i].tourindex][0]=a[i].tour[(N_appl*3)-1][0];
                        a[i].tour[a[i].tourindex][1]=a[i].tour[(N_appl*3)-1][1];
                        a[i].tourLength=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];
                        a[i].free[a[i].tour[(N_appl*3)-1][0]]=gridsched.Ant.ET[a[i].tour[(N_appl*3)-1][0]][i];*/
                    if(c_p%proc==0)
                        c_p=0;
                //Random r=new Random();
               //int ran=r.nextInt(proc);
                a[i].tabu[i]=1;
                a[i].tourindex=0;
                a[i].tour[0][0]=c_p;
                a[i].tour[0][1]=i;
                a[i].tourLength=gridsched.Ant.ET[c_p][i];
                a[i].free[c_p]=gridsched.Ant.ET[c_p][i];
                c_p++;
                    }
                    c=0;
                 }

                 iteration++;
            }

            int best_index=0;
            double best_Length=999;
            for(int i=0;i<gridsched.Ant.no_ants;i++)
            {
                if(a[i].tourLength<best_Length)
                {
                    best_Length=a[i].tourLength;
                    best_index=i;
                }
            }

            Genetic gen=new Genetic(f_cpu,failed_jobs,failed_c,proc,a[best_index].tour);
            gen.geneticalgo();
            for(int i=0;i<failed_c;i++)
                Output.append("Job"+failed_jobs[map[i][0]][0]+failed_jobs[map[i][0]][1]+" on Processor"+map[i][1]+" on port "+f_cpu[map[i][1]][2]+"\n");
        }
        f=failed_c;
        failed_flag=0;
        failed_c=0;
        for(int i=0;i<f;i++)
            map[i][0]=failed_jobs[map[i][0]][5];

        runSchedule(f);
       }
System.out.println("failed flag="+failed_flag+"failed_c="+failed_c);
       getoutput();
}//GEN-LAST:event_ExecuteActionPerformed

    private void Ant_optionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ant_optionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ant_optionActionPerformed

    private void Show_GraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Show_GraphActionPerformed
        Graph g = new Graph();
        g.main();
}//GEN-LAST:event_Show_GraphActionPerformed

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        String port,name="";
        port = C_N.getText();
        if(proc==0)
            Output.append("\nThere are no Client's available");
        else
        {
        for(int i=0;i<proc;i++)
        {
            if(port.equals(f_cpu[i][2]))
            {
                name=f_cpu[i][1];
            }
        }
        if(name.equals(""))
            Output.append("\nNo such Client");
        else
        {
            terClient tc=new terClient(name,Integer.parseInt(port));
            tc.t_run();
        }
        Grid_Status();
         f_cpu_c();
        }
    }//GEN-LAST:event_CloseActionPerformed

    private void C_NActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C_NActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_C_NActionPerformed
        private class terClient
    {
        String comp;
        int port1;
        public terClient(String computers,int port )
        {
            comp = computers;
            port1= port;

        }
        public void t_run()
        {

            try
        {

                Socket ss = new Socket(comp,port1);
                PrintWriter pw = new PrintWriter(ss.getOutputStream());
                pw.println("terminate");
                pw.flush();
                pw.close();
                ss.close();
        }
        catch(Exception e)
        {
            System.out.println("There is problem in terminating : "+e);

        }

        }
    }

    public void runSchedule(int nj)
    {
        try
        {
            p_t=new long[proc];
            Thread t=null;
            Thread l=null;
            int N_J=nj;
            for(int i=0;i<proc;i++)
            {
                System.out.println("creating processor thread" + proc);
                t = new Thread(new Proc_Thread(i,this,l,N_J));
                l=t;
                System.out.println("Before the start");
                t.start();

            }
            if(t != null) t.join() ;

        }
        catch(Exception e)
        {
            System.out.println(e+"") ;
        }
    }


    public void getoutput()
    {
        FileInputStream fis;
        Scanner sf;
        PrintWriter pwo;
        String str;

        try
        {
        for(int i=0;i<N_appl;i++)
        {
            pwo = new PrintWriter(new FileOutputStream("Out"+i+".txt"));
            for(int j=0;j<3;j++)
            {

                  fis = new FileInputStream("Output"+i+j+".txt");
                  sf = new Scanner(fis);
                  str = sf.nextLine() ;
                  pwo.print(str);
                  sf.close();
                  fis.close();
            }
            pwo.close();
        }
        e_time=(System.currentTimeMillis()-s_time)/1000;
        long var=0;
        if(Ant.isSelected())
        {
            algos[0][0]="1";
            var=Long.parseLong(algos[0][2])+e_time;
            algos[0][2]=""+var;
        }
        else if(Fuzzy.isSelected())
        {
            algos[1][0]="1";
            var=Long.parseLong(algos[1][2])+e_time;
            algos[1][2]=""+var;
        }
        else if(Genetic.isSelected())
        {
            algos[2][0]="1";
            var=Long.parseLong(algos[2][2])+e_time;
            algos[2][2]=""+var;
        }
        else
        {
            algos[3][0]="1";
            var=Long.parseLong(algos[3][2])+e_time;
            algos[3][2]=""+var;
        }


        Output.append("Total time = "+var+"sec");
        Output.append("\nCost = "+total_cost+"Rs.");

        }
        catch(Exception e)
        {System.out.println("Output::"+e);}
    }

    public static void main(String args[])
    {
        Grid_Status();
        f_cpu_c();

        new Grid_AVSN().setVisible(true);
        Output.append("Clients available on grid:\n\n");
        Output.append("Processor No.\tName\tPort No.\n");
        for(int i=0;i<proc;i++)
               Output.append(i+"\t"+f_cpu[i][1]+"\t"+f_cpu[i][2]+"\n");
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                

            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_Appl;
    private javax.swing.JRadioButton Ant;
    private javax.swing.JRadioButton Ant_Genetic;
    private javax.swing.JComboBox Ant_option;
    public static javax.swing.JTextArea App_List;
    private javax.swing.JTextField C_N;
    private javax.swing.JButton Close;
    private javax.swing.JButton EnterNA;
    private javax.swing.JButton Execute;
    private javax.swing.JRadioButton Fuzzy;
    private javax.swing.JRadioButton Genetic;
    private javax.swing.JTextField NA;
    private static javax.swing.JTextArea Output;
    private javax.swing.JButton Refresh;
    private javax.swing.JButton Schedule;
    private javax.swing.JButton Show_Graph;
    public static javax.swing.JTextArea Split_Appl;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

}

 class Proc_Thread implements Runnable
 {
        int processor;
        Grid_AVSN Grid;
        Thread towait;
        int N_job;
        public Proc_Thread(int proc_no,Grid_AVSN t,Thread l,int nj)
        {
            processor=proc_no;
            Grid =t;
            towait=l;
            N_job=nj;
        }

        public void run()
        {
            try
            {
            gridsched.Grid_AVSN.p_t[processor]=System.currentTimeMillis();
            System.out.println("we are in proc thread ok"+processor);
            for(int i=0;i<N_job;i++)
            {
                if(Grid.map[i][1]==processor)
                {
                    Thread tj=new Thread(new Sub_Job(processor,Grid.map[i][0]));
                    tj.start();
                    System.out.println("end of start");
                    tj.join();
                }
            }
            gridsched.Grid_AVSN.p_t[processor]=System.currentTimeMillis()-gridsched.Grid_AVSN.p_t[processor];
            gridsched.Grid_AVSN.total_cost+=((gridsched.Grid_AVSN.p_t[processor])/1000)*Integer.parseInt(gridsched.Grid_AVSN.f_cpu[processor][4]);
            }



        catch(Exception e)
        {
            System.out.println(e+"") ;
        }

        try
        {
            if(towait != null) towait.join() ;
        }
        catch(Exception e)
        {
               System.out.println(e+"") ;
        }

      }
    private class Sub_Job implements Runnable
    {
        int Job_ID;
        int pr;
        Sub_Job(int p,int ji)
        {
            pr=p;
            Job_ID=ji;
        }
        synchronized public void run()
        {
            try
            {

                  System.out.println("I Am in Job Thread of:"+Grid.jobs[Job_ID][0]+Grid.jobs[Job_ID][1] );
                  System.out.println(Grid.f_cpu[pr][1]+" "+Integer.parseInt(Grid.f_cpu[pr][2]));
                  Socket s = new Socket(Grid.f_cpu[pr][1],Integer.parseInt(Grid.f_cpu[pr][2]));
                  PrintWriter pw = new PrintWriter(s.getOutputStream());
                  Scanner soc = new Scanner(s.getInputStream());
                  pw.println("code");
                  FileInputStream fis = new FileInputStream("Jobs"+Grid.jobs[Job_ID][0]+Grid.jobs[Job_ID][1]+".txt");
                  Scanner sf = new Scanner(fis);
                  String str;
                  while (sf.hasNextLine())
                  {
                      str = sf.nextLine() ;
                      System.out.println(str);
                      pw.println(str);
                  }
                  sf.close();
                  fis.close();
                  PrintWriter pwo = new PrintWriter(new FileOutputStream("Output"+Grid.jobs[Job_ID][0]+Grid.jobs[Job_ID][1]+".txt"));
                  pw.flush();
                  str= soc.nextLine();
                  pwo.print(str);
                  pwo.flush();
                  pwo.close();
                  pw.close();
                  
                  soc.close();
                  Grid.jobs[Job_ID][4]=1;

            }
            catch(Exception e)
            {
                System.out.println("Grid"+e);
                Grid_AVSN.failed_c++;
                Grid_AVSN.failed_flag=1;
            }

        }
    }
 }

