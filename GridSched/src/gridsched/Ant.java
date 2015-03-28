
package gridsched;


public class Ant
    {
        static int proc;
        static String cpu[][];
        static int N_jobs;
        static int jobs[][];
        static double base_pheromone;//=(1.0/(gridsched.Grid_AVSN.N_appl*3));
        static double pheromone[][];//=new double[gridsched.Grid_AVSN.proc][gridsched.Grid_AVSN.N_appl*3];
        static double t[][]={{1.0,1.5,2},{2.5,3,3.5},{4,4.5,5}};
        static double ET[][];//=new double[gridsched.Grid_AVSN.proc][gridsched.Grid_AVSN.N_appl*3];
        static int pcomp;
        static int no_ants;//=gridsched.Grid_AVSN.N_appl*3;
        double free[];//=new double[gridsched.Grid_AVSN.proc];
        static double rho=0.90;
        int tabu[];//= new int[gridsched.Grid_AVSN.N_appl*3];
        int tour[][];//= new int[gridsched.Grid_AVSN.N_appl*3][2];
        int tourindex;//=0;
        double tourLength;//=0;

     Ant()
    {
         free=new double[proc];
         tabu=new int[N_jobs];
         tour=new int[N_jobs][2];
         tourindex=0;
         tourLength=0;
    }
     public static void Init_stat_Ant(int nj,int p,int[][] j,String[][] pr)
     {
        N_jobs=nj;
        proc=p;
        jobs=j;
        cpu=pr;
        base_pheromone=(1.0/(N_jobs));
        pheromone=new double[proc][N_jobs];
        ET=new double[proc][N_jobs];
        no_ants=nj;
     }

     public static void initpheromone()
    {
       for(int i=0;i<proc;i++)
         for(int j=0;j<N_jobs;j++)
             pheromone[i][j]=base_pheromone;
    }

        static public void make_ET_Cost()
        {
        for(int i=0;i<proc;i++)
        {
            if(Integer.parseInt(cpu[i][0])<=30)
                pcomp=0;
            else if(Integer.parseInt(cpu[i][0])<=60)
                pcomp=1;
            else
                pcomp=2;
                   
            for(int j=0;j<(N_jobs);j++)
            {
                pheromone[i][j]=base_pheromone;
                if(pcomp==0 && jobs[j][2]==0)
                    ET[i][j]=t[0][0]*Integer.parseInt(cpu[i][4]);
                if(pcomp==0 && jobs[j][2]==1)
                    ET[i][j]=t[0][1]*Integer.parseInt(cpu[i][4]);
                if(pcomp==0 && jobs[j][2]==2)
                    ET[i][j]=t[0][2]*Integer.parseInt(cpu[i][4]);
                if(pcomp==1 && jobs[j][2]==0)
                    ET[i][j]=t[1][0]*Integer.parseInt(cpu[i][4]);
                if(pcomp==1 && jobs[j][2]==1)
                    ET[i][j]=t[1][1]*Integer.parseInt(cpu[i][4]);
                if(pcomp==1 && jobs[j][2]==2)
                    ET[i][j]=t[1][2]*Integer.parseInt(cpu[i][4]);
                if(pcomp==2 && jobs[j][2]==0)
                    ET[i][j]=t[2][0]*Integer.parseInt(cpu[i][4]);
                if(pcomp==2 && jobs[j][2]==1)
                    ET[i][j]=t[2][1]*Integer.parseInt(cpu[i][4]);
                if(pcomp==2 && jobs[j][2]==2)
                    ET[i][j]=t[2][2]*Integer.parseInt(cpu[i][4]);
                
            }
        }

        }

        static public void make_ET()
        {
        for(int i=0;i<proc;i++)
        {
            if(Integer.parseInt(cpu[i][0])<=30)
                pcomp=0;
            else if(Integer.parseInt(cpu[i][0])<=60)
                pcomp=1;
            else
                pcomp=2;
                   
            for(int j=0;j<(N_jobs);j++)
            {
                pheromone[i][j]=base_pheromone;
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


        


        public void next_move()
        {
            double d=0;
            double heur;
            double max_p=0,p;
            int index=0;




            for(int i=0;i<proc;i++)
            {
                if(free[i]==0)
                        heur=1;
                    else
                        heur=1/free[i];
                for(int j=0;j<N_jobs;j++)
                {
                    d=d+((pheromone[i][j]*heur)/ET[i][j]);
                }
            }
            int i;
            for(i=0;i<N_jobs;i++)
            {
                if(tabu[i]==0)
                {
                    tabu[i]=1;
                    break;
                 }
            }
            if(i!=N_jobs)
            {
                for(int j=0;j<proc;j++)
                {
                    if(free[j]==0)
                        heur=1;
                    else
                        heur=1/free[j];

                    p=(pheromone[j][i]*(heur/ET[j][i]))/d;
                    if(p>max_p)
                    {
                        max_p=p;
                        index=j;
                    }

                }
                this.tourindex++;
                this.tour[this.tourindex][0]=index;
                this.tour[this.tourindex][1]=i;
                int max_free=0;
                free[index]+=ET[index][i];
                for(int k=0;k<proc;k++)
                {
                    if(free[k]>max_free)
                        max_free=k;
                }
                this.tourLength=max_free;


            }
            else
                return;
        }
public void update_pheromone()
{
    for(int i=0;i<N_jobs;i++)
    {
        pheromone[this.tour[i][0]][this.tour[i][1]]+=rho*pheromone[this.tour[i][0]][this.tour[i][1]]+((1-rho)/this.tourLength);
    }
}




    }
