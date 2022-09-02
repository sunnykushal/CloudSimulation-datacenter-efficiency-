
# Energy efficiency in cloud environment using Brownout/Lowest Utilization First Component Selection Policy(LUFCSP) using CloudSim(Language-JAVA).


1.Our project uses CloudSim environment run in Eclipse(preferred) or any JVM supported platform
. The aim of the project is to use the brownout algorithm and calculate the individual Virtual Machine Instances(VM's)
/Hosts Energy Utilization. 

2.Upon the data on the Utilizations of all the VM's/Host, the algorithm now determines the overloaded 
and the underloaded VM's/Host and accordingly does the live migration for balacing the overall
overhead that otherwise results in energy loss/problems.

-------------------------------------------------------------------------------------------

#step 1(optional)://
  Create the amount of VM's as per requirement by creating vm's using the vmlist class in java,
  allocate each of the vm's to the corresponding cloudlets and then by using the broker object in java 
  that is responsible for task assignments to the vm's, bind the vm to the cloudlet.

#step 2:
  Using the runnerAbstract(java file), containing Adaptive
  Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
  Cloud Data Centers, which uses our algorithm of LUFCSP,detemines the lowest and
  the overloaded Vm's.The individual energy Utilizations are determined.

#step 3:
  Using the iqrmc(java file), containing the main vmallocation and selection policy responsible for livemigration for energy conservation
  , we perform the balacing.

#step 4:
 Lastly, we customize our total simulation time(here 1500) in constant(java file) and for taking user input of the number of vm's and hosts we modify the randomrunner(java file).

// Run the iqrMc file for the results detemination//
  
        

