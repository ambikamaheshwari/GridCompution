package gridsched;
import java.util.*;
public class Genetic
{

    public static String cpu[][];
    public static int jobs[][];
    public static int proc;
    public static int N_jobs;
    public static int s1[];// = new int[gridsched.Grid_AVSN.N_appl*3];
    public static int s2[];// = new int[gridsched.Grid_AVSN.N_appl*3];
    static double t[][]={{1.0,1.5,2},{2.5,3,3.5},{4,4.5,5}};
    static double ET[][];//=new double[gridsched.Grid_AVSN.proc][gridsched.Grid_AVSN.N_appl*3];
    public static double free1[];//=new double[gridsched.Grid_AVSN.proc];
    public static double free2[];//=new double[gridsched.Grid_AVSN.proc];
    public static double fs2,fs1,fit;
    public static double pload[];//= new double[gridsched.Grid_AVSN.N_appl*3];
    public static double c_fit[];// = new double[gridsched.Grid_AVSN.proc];
    public static double fitness;// = 0;
    
    

    public Genetic(String[][] cpug,int[][] jobsg,int N_applg,int procg)
{

        cpu=cpug;
        jobs=jobsg;
        N_jobs=N_applg;
        proc=procg;
        s1=new int[N_jobs];
        s2=new int[N_jobs];
        ET=new double[proc][N_jobs];
        free1=new double[proc];
        free2=new double[proc];
        fs1=0;
        fs2=0;
        fit=0;
        pload= new double[N_jobs];
        c_fit = new double[proc];
        fitness=0;
        make_ET();
        Init_pl();

        for(int i=0;i<N_jobs;i++)
            {
                s1[i]=i%proc;
                free1[i%proc]+=ET[i%proc][i];
                s2[i]=(proc-1)-(i%proc);
                free2[(proc-1)-(i%proc)]+=ET[(proc-1)-(i%proc)][i];
                System.out.println(free1[i%proc]+"\t"+free2[(proc-1)-(i%proc)]);
        }


}

    public Genetic(String[][] cpug,int[][] jobsg,int N_applg,int procg,int[][] tour)
{

        cpu=cpug;
        jobs=jobsg;
        proc=procg;
        N_jobs=N_applg;
        s1=new int[N_jobs];
        s2=new int[N_jobs];
        ET=new double[proc][N_jobs];
        free1=new double[proc];
        free2=new double[proc];
        fs1=0;
        fs2=0;
        fit=0;
        pload= new double[N_jobs];
        c_fit = new double[proc];
        fitness=0;
        make_ET();
        Init_pl();

       for(int i=0;i<N_jobs;i++)
        {
            s1[tour[i][1]]=tour[i][0];
            free1[tour[i][0]]+=ET[tour[i][0]][i];
            s2[i]=(proc-1)-(i%proc);
            free2[(proc-1)-(i%proc)]+=ET[(proc-1)-(i%proc)][i];

        }

}
    static public void Init_pl()
    {
        System.out.println("Init_pl"+N_jobs);
        
        for (int i = 0; i < N_jobs; i++)
        {
            if (jobs[i][2]==0)
            {
                pload[i] = 0.3;
            }
            else if (jobs[i][2]==1)
            {
                pload[i] = 0.6;
            }
            else
            {
                pload[i] = 0.9;
            }

        }
    }

    static public void make_ET()
        {
        int pcomp;
         for(int i=0;i<proc;i++)
        {
            if(Integer.parseInt(cpu[i][0])<=30)
            {   pcomp=0;
                c_fit[i]=1;
            }
            else if(Integer.parseInt(cpu[i][0])<=60)
            {
                pcomp=1;
                c_fit[i]=0.6;
            }
            else
            {
                pcomp=2;
                c_fit[i]=0.2;
            }
            for(int j=0;j<(N_jobs);j++)
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
    public int fitness()
    {
        int f=0;
        for (int i = 0; i < N_jobs; i++)
        {
            fs1 = fs1 + pload[i] * c_fit[s1[i]];
        }
        fs1 = fs1 /N_jobs;

        for (int j = 0; j < N_jobs; j++)
        {
            fs2 = fs2 + pload[j] * c_fit[s2[j]];
        }
        fs2 = fs2 /N_jobs;
        if (fs1 >= fs2)
        {
            fit = fs1;
            
        } else
        {
            fit = fs2;
            f = 1;
        }
        return f;
    }

    public void geneticalgo()
    {
         int count=0;
         
         int best[] = new int[N_jobs];
         double best_fit=0;
           Random r=new Random();
           int temp,ran1,ran2;
           double max,min;
           int index1=0,index2=0;

        while(count!=50){
        //crossover
        for(int i=0;i<proc;i++)
        {
            System.out.println(free1[i]+"   "+free2[i]);
        }
        max=0;
        min=99999;
        for(int i=0;i<proc;i++)
        {
            if(free1[i]>max)
            {
                max=free1[i];
                index1=i;
            }
            if(free2[i]<min && free2[i]>0)
            {
                min=free2[i];
                index2=i;
            }

        }

        int c1=0,c2=0;
        int cr1[]=new int[N_jobs];
        int cr2[]=new int[N_jobs];
        for(int i=0;i<N_jobs;i++)
        {
            if(s1[i]==index1)
                cr1[c1++]=i;
            if(s2[i]==index2)
                 cr2[c2++]=i;
        }
        //for(int i=0;i<N_jobs;i++)
            //System.out.println(s1[i]+"\t"+s2[i]);
        System.out.println("c1 "+c1+" c2 "+c2);
        ran1=r.nextInt(c1);
        ran2=r.nextInt(c2);
        System.out.println(ran1+"  "+ran2);
        free1[s1[cr1[ran1]]]-=ET[s1[cr1[ran1]]][cr1[ran1]];
        free1[s2[cr2[ran2]]]+=ET[s2[cr2[ran2]]][cr1[ran1]];
        free2[s2[cr2[ran2]]]-=ET[s2[cr2[ran2]]][cr2[ran2]];
        free2[s1[cr1[ran1]]]+=ET[s1[cr1[ran1]]][cr2[ran2]];

        temp=s1[cr1[ran1]];
        s1[cr1[ran1]]=s2[cr2[ran2]];
        s2[cr2[ran2]]=temp;



        //End crossover
        int flag=fitness();
        if(flag==0)
        {
            ran1=r.nextInt(N_jobs);
            free2[s2[ran1]]=free2[s2[ran1]]-ET[s2[ran1]][ran1]+ET[s2[ran1]][(ran1+1)%N_jobs];
            free2[s2[(ran1+1)%N_jobs]]=free2[s2[(ran1+1)%N_jobs]]-ET[s2[(ran1+1)%N_jobs]][(ran1+1)%N_jobs]+ET[s2[(ran1+1)%N_jobs]][ran1];
            temp=s2[ran1];
            s2[ran1]=s2[(ran1+1)%N_jobs];
            s2[(ran1+1)%N_jobs]=temp;
        }
        else
        {
            ran1=r.nextInt(N_jobs);
            free1[s1[ran1]]=free1[s1[ran1]]-ET[s1[ran1]][ran1]+ET[s1[ran1]][(ran1+1)%N_jobs];
            free1[s1[(ran1+1)%N_jobs]]=free1[s1[(ran1+1)%N_jobs]]-ET[s1[(ran1+1)%N_jobs]][(ran1+1)%N_jobs]+ET[s1[(ran1+1)%N_jobs]][ran1];

            temp=s1[ran1];
            s1[ran1]=s1[(ran1+1)%N_jobs];
            s1[(ran1+1)%N_jobs]=temp;
        }
       flag=fitness();
       System.out.println("Fitness"+fit);
       if(fit>best_fit)
       {

            best_fit=fit;
           if(flag==0)
           {
               for(int i=0;i<N_jobs;i++)
               {
                   best[i]=s1[i];
               }
           }
           else
           {
               for(int i=0;i<N_jobs;i++)
               {
                   best[i]=s2[i];
               }

           }
       }
       count++;
        }
           System.out.println(best_fit);
           for(int i=0;i<N_jobs;i++)
               {
                   gridsched.Grid_AVSN.map[i][0]=i;
                   gridsched.Grid_AVSN.map[i][1]=best[i];
               }

         
 }
}
