package gridsched;

import java.util.*;
import java.io.*;
import javax.swing.*;

public class File_C_S extends javax.swing.JFrame {

    public File_C_S() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        if (1 == JFileChooser.CANCEL_OPTION)
        {
            this.dispose();//fc.fir.enable(false);
        }
        if (0 == JFileChooser.APPROVE_OPTION)
        {

            gridsched.Grid_AVSN.Appl_Name[gridsched.Grid_AVSN.fileno][0] = jFileChooser1.getSelectedFile().getName();

            gridsched.Grid_AVSN.App_List.append(gridsched.Grid_AVSN.Appl_Name[gridsched.Grid_AVSN.fileno][0] + "\n");


            try {
                int r1,r2,c1,c2;
                BufferedReader br = new BufferedReader(new FileReader(gridsched.Grid_AVSN.Appl_Name[gridsched.Grid_AVSN.fileno][0]));
                String ch1,ch2,security;
                ch1 = br.readLine();
                String ra[]=ch1.split(":");
                r1=ra.length;
                String c[]=ra[0].split(" ");
                c1=c.length;
                ch2 = br.readLine();
                String rb[]=ch2.split(":");
                r2=rb.length;
                c=rb[0].split(" ");
                c2=c.length;
                security=br.readLine();
                Random ran = new Random();
                int m = ran.nextInt(r1);
                int n;
                if(m==0||m==1)
                {
                    m=m+2;
                }
                n= ran.nextInt(m);
                if(n==0)
                {
                    n=n+1;
                }
               String afo[]=security.split(" ");
               int af=0; 
               //Based on Anti-Virus
                if(afo[0].equals("N"))
                    af+=4;
                if(afo[0].equals("E"))
                    af+=3;
                if(afo[0].equals("A"))
                    af+=1;
                //Based on Firewall
                if(afo[1].equals("H"))
                    af+=4;
                if(afo[1].equals("M"))
                    af+=2;
                if(afo[1].equals("L"))
                    af+=1;
                if(afo[1].equals("NA"))
                    af+=0;
               gridsched.Grid_AVSN.Appl_Name[gridsched.Grid_AVSN.fileno][1]=""+af;
               //Operating System
                if(afo[2].equals("W"))
                    gridsched.Grid_AVSN.Appl_Name[gridsched.Grid_AVSN.fileno][2]=""+1;
                if(afo[2].equals("L"))
                    gridsched.Grid_AVSN.Appl_Name[gridsched.Grid_AVSN.fileno][2]=""+0;

                for (int x = 0; x < 3; x++)
                {

                    gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][4] = 0;
                    if (x == 0)
                    {
                        gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][1] = 0;
                        gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][3] = n;
                        if (n < 26) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 0;
                        } else if (n >25  && n < 61) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 1;
                        } else if (n > 60) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 2;
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter("Jobs" + gridsched.Grid_AVSN.fileno + x + ".txt"));
                        bw.write("" + n);
                        bw.newLine();
                        bw.write(""+c1);
                        bw.newLine();
                        bw.write(""+c2);
                        bw.newLine();
                        ch1="";
                        for(int i=0;i<n;i++)
                        {
                            if(i==n-1)
                                ch1=ch1+ra[i];
                            else
                                ch1=ch1+ra[i]+":";

                        }
                        bw.write(ch1);
                        bw.newLine();
                        bw.write(ch2);
                        bw.newLine();
                        bw.close();
                    }

                    if (x == 1)
                    {
                        gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][1] = 1;
                        gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][3] = m - n;
                        if ((m - n) < 26) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 0;
                        } else if ((m - n) > 25 && (m - n) < 61) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 1;
                        } else if ((m - n) > 60) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 2;
                        }

                        BufferedWriter bw = new BufferedWriter(new FileWriter("Jobs" + gridsched.Grid_AVSN.fileno + x + ".txt"));
                        bw.write("" + (m-n));
                        bw.newLine();
                        bw.write(""+c1);
                        bw.newLine();
                        bw.write(""+c2);
                        bw.newLine();
                        ch1="";
                        for(int i=n;i<m;i++)
                        {
                            if(i==m-1)
                                ch1=ch1+ra[i];
                            else
                                ch1=ch1+ra[i]+":";

                        }
                        bw.write(ch1);
                        bw.newLine();
                        bw.write(ch2);
                        bw.newLine();
                        bw.close();

                    }
                    if (x == 2)
                    {
                        gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][1] = 2;
                        gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][3] = r1- m;
                        if ((r1- m) < 26) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 0;
                        } else if ((r1- m) > 25 && (r1- m) < 61) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 1;
                        } else if ((r1- m) > 60) {
                            gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] = 2;
                        }

                        BufferedWriter bw = new BufferedWriter(new FileWriter("Jobs" + gridsched.Grid_AVSN.fileno + x + ".txt"));
                        bw.write("" + (r1- m));
                        bw.newLine();
                        bw.write(""+c1);
                        bw.newLine();
                        bw.write(""+c2);
                        bw.newLine();
                        ch1="";
                        for(int i=m;i<r1;i++)
                        {
                            if(i==r1-1)
                                ch1=ch1+ra[i];
                            else
                                ch1=ch1+ra[i]+":";

                        }
                        bw.write(ch1);
                        bw.newLine();
                        bw.write(ch2);
                        bw.newLine();
                        bw.close();

                    }
                }


                gridsched.Grid_AVSN.Split_Appl.append("Job Name"+"\t"+"Rows" + "\t" + "Job weight" + "\n");
                for (int x = 0; x < 3; x++)
                {
                    gridsched.Grid_AVSN.Split_Appl.append("Jobs" + gridsched.Grid_AVSN.fileno + x + "\t");
                    gridsched.Grid_AVSN.Split_Appl.append(gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][3] + "\t");
                    gridsched.Grid_AVSN.Split_Appl.append(gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] + "\t");
                    gridsched.Grid_AVSN.Split_Appl.append("\n");
                    //System.out.println(gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][0] + "\t");
                    //System.out.println(gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][1] + "\t");
                    //System.out.println(gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][2] + "\t");
                    //System.out.println(gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][3] + "\t");
                    gridsched.Grid_AVSN.jobs[x + 3 * gridsched.Grid_AVSN.fileno][0] = gridsched.Grid_AVSN.fileno;

                }

       
            } catch (Exception e)
            {
                System.out.println(e);
            }


            gridsched.Grid_AVSN.fileno++;

        }



    }//GEN-LAST:event_jFileChooser1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new File_C_S().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}
