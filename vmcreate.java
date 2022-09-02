
package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;



public class vmcreate {

	
	private static List<Cloudlet> cloudletList;

	
	private static List<Vm> vmlist;

	
	public static void main(String[] args) {

		Log.printLine("Starting VM cloudlets establishment...");

		try {
			
			int num_user = 1;   
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  

			
			CloudSim.init(num_user, calendar, trace_flag);

			
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");

		
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();

			
			vmlist = new ArrayList<Vm>();

			
			int vmid = 0;
			int mips = 250;
			long size = 10000; 
			int ram = 2048; 
			long bw = 1000;
			int pesNumber = 1; 
			String vmm = "Xen"; 

			
			Vm vm1 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			vmid++;
			Vm vm2 = new Vm(vmid, brokerId, mips * 2, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			vmid++;
			Vm vm3 = new Vm(vmid, brokerId, mips * 3, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			vmid++;
			Vm vm4 = new Vm(vmid, brokerId, mips * 0.5, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

			
			vmlist.add(vm1);
			vmlist.add(vm2);
			vmlist.add(vm3);
			vmlist.add(vm4);

			
			broker.submitVmList(vmlist);


			
			cloudletList = new ArrayList<Cloudlet>();

			
			int id = 0;
			long length = 40000;
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setUserId(brokerId);

			id++;
			Cloudlet cloudlet2 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet2.setUserId(brokerId);
			
			id++;
			Cloudlet cloudlet3 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet3.setUserId(brokerId);
			
			id++;
			Cloudlet cloudlet4 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet4.setUserId(brokerId);

			
			cloudletList.add(cloudlet1);
			cloudletList.add(cloudlet2);
			cloudletList.add(cloudlet3);
			cloudletList.add(cloudlet4);

			
			broker.submitCloudletList(cloudletList);


			
			broker.bindCloudletToVm(cloudlet2.getCloudletId(),vm1.getId());
			broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm2.getId());
			broker.bindCloudletToVm(cloudlet3.getCloudletId(),vm2.getId());
			broker.bindCloudletToVm(cloudlet4.getCloudletId(),vm1.getId());

			
			CloudSim.startSimulation();


			
			List<Cloudlet> newList = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

        	printCloudletList(newList);

			Log.printLine("VM instaces establishment finished!");
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static Datacenter createDatacenter(String name){

		
		List<Host> hostList = new ArrayList<Host>();

		
		List<Pe> peList = new ArrayList<Pe>();

		int mips = 1000;

		
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); 
		int hostId=0;
		int ram = 2048; 
		long storage = 1000000;
		int bw = 10000;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList)
    			)
    		); 
		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));

		hostId++;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); 
		String arch = "x86";      
		String os = "Linux";          
		String vmm = "Xen";
		double time_zone = 10.0;         
		double cost = 3.0;              
		double costPerMem = 0.05;		
		double costPerStorage = 0.001;	
		double costPerBw = 0.0;			
		LinkedList<Storage> storageList = new LinkedList<Storage>();	

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

		
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("==THE CREATED VM INNSTACES INFO ARE==");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("CREATED");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
						indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}
}
