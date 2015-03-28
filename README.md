# GridCompution

The emerging paradigm of grid computing and the construction of computational grids are making the development of large scale applications possible. The development or adaptation of applications for grid environments is being challenged by the need of scheduling a large number of jobs to resources efficiently.

Moreover, the computing resources may vary in regard to their resource management policies such as access and cost. Job scheduling on computational grids is gaining importance due to the large scale applications of the grid, e.g. optimization, collaborative/eScience computing, data-intensive computing that need efficient schedulers.

This scheduling task is much more complex than its version in traditional computing environments. Indeed, the grid environment is dynamic and, also the number of resources to manage and the number of jobs to be scheduled are usually very large making thus the problem a complex large scale optimization problem. This problem is multi-objective in its general definition, as several optimization criteria such as makespan, flowtime and resource utilization are to be matched.

The scheduling algorithms being used in the grids are traditionally static. Static algorithms work in the following way. There is a predefined decision making strategy which is used. Some previously obtained performance parameters are judged and scheduler takes decisions based on them. Even if there are changes observed dynamically, the scheduler will stick to a rigid decision making process and thus the grid performance goes down.

This drawback had been dealt with by devising a mechanism to take dynamic decisions for scheduling. We have further tried to optimize the results of the dynamic algorithms implemented by the existing system, by combining algorithms. I have used algorithms such as Ant Colony Optimization Algorithm, Genetic Algorithm, Fuzzy Algorithm. 

For every application that is being executed, the performance parameters are updated. Whenever a new scheduling decision needs to be taken, decision is taken dynamically based on these parameters and by evaluating the entire search space for an optimal solution; performance is thus improved.

The dynamic decision taking ability has been enhanced by querying the work-stations connected in the grid for their current processor utilization and thereby dividing the total work-load according to the availability of the resources. The system can also detect if a particular client had shutdown/failed after jobs have been assigned to it. It detects failure of resource n reschedules the failed jobs till all jobs are successfully executed. 

The makespan provided by each of the algorithms or their combination is then plotted on a graph, so that the user gets a comprehensive analysis of the scheduling algorithms and their performance in that environment.
