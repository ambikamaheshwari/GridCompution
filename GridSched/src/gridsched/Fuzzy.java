package gridsched;
import java.sql.*;
public class Fuzzy
{

     static int proc,N_jobs;
     static String cpu[][];
     static int jobs[][];
     static String App[][];
     static int map_fuz[][];
     static double t[][]={{1.0,1.5,2},{2.5,3,3.5},{4,4.5,5}};
     static double ET[][];
     static double free[];
     static int map[][];

     public Fuzzy(int procf,int Nj,String[][] cpuf,int[][]jobsf,String[][] A)
    {
        proc=procf;
        N_jobs=Nj;
        cpu=cpuf;
        jobs=jobsf;
        App=A;
        map_fuz=new int[proc][N_jobs];
        ET=new double[proc][N_jobs];
        free=new double[proc];
        map=new int[N_jobs][3];
    }
     static public void make_ET()
     {

         int pcomp;
         for(int i=0;i<proc;i++)
        {
            if(Integer.parseInt(cpu[i][0])<=30)
               pcomp=0;
            else if(Integer.parseInt(cpu[i][0])<=60)
               pcomp=1;
            else
               pcomp=2;

         for(int j=0;j<(gridsched.Grid_AVSN.N_appl*3);j++)
            {

                if(pcomp==0 && jobs[j][2]==0)
                    ET[i][j]=t[0][0];
                if(pcomp==0 && jobs[j][2]==1)
                    ET[i][j]=t[0][1];
                if(pcomp==0 && jobs[j][2]==2)
                    ET[i][j]=t[0][2];
                if(pcomp==1 && jobs[j][2]==0)
                    ET[i][j]=t[1][0];
                if(pcomp==1 && jobs[j][2]==1)
                    ET[i][j]=t[1][1];
                if(pcomp==1 && jobs[j][2]==2)
                    ET[i][j]=t[1][2];
                if(pcomp==2 && jobs[j][2]==0)
                    ET[i][j]=t[2][0];
                if(pcomp==2 && jobs[j][2]==1)
                    ET[i][j]=t[2][1];
                if(pcomp==2 && jobs[j][2]==2)
                    ET[i][j]=t[2][2];

            }
        }

   }

     
     public int[][] Fuzzy_Main()
     {
        try
        {
        for(int i=0;i<proc;i++)
        {
            System.out.println(cpu[i][5]+"  "+cpu[i][6]);
        }

        for(int i=0;i<proc;i++)
        {
            for(int j=0;j<N_jobs;j++)
            {
                if(Integer.parseInt(cpu[i][5])>=Integer.parseInt(App[jobs[j][0]][1])-1&&Integer.parseInt(cpu[i][6])==Integer.parseInt(App[jobs[j][0]][2]))
                {
                    map_fuz[i][j]=1;
                }
                else
                    map_fuz[i][j]=0;
            }
        }
        double min=999;
        int index=0;
        for(int i=0;i<N_jobs;i++)
        {
            min=999;
            for(int j=0;j<proc;j++)
            {
                if(map_fuz[j][i]==1)
                {
                    if(free[j]<min)
                    {
                        min=free[j];
                        index=j;
                    }
                }
            }
            map[i][0]=i;
            map[i][1]=index;
            free[index]+=ET[index][i];

        }


    }


        catch (Exception err)
        {
        System.out.println("ERROR: " + err);
        }
           return map;

     }
     
    

}
