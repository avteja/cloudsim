package org.cloudbus.cloudsim.examples.network.datacenter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.network.datacenter.EdgeSwitch;
import org.cloudbus.cloudsim.network.datacenter.NetDatacenterBroker;
import org.cloudbus.cloudsim.network.datacenter.NetworkCloudlet;
import org.cloudbus.cloudsim.network.datacenter.NetworkCloudletSpaceSharedScheduler;
import org.cloudbus.cloudsim.network.datacenter.NetworkConstants;
import org.cloudbus.cloudsim.network.datacenter.NetworkDatacenter;
import org.cloudbus.cloudsim.network.datacenter.NetworkHost;
import org.cloudbus.cloudsim.network.datacenter.NetworkVm;
import org.cloudbus.cloudsim.network.datacenter.NetworkVmAllocationPolicy;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class NewNetworkExample {
	
	/** The cloudlet list. */
	private static List<NetworkCloudlet> cloudletList;

	/** The vmlist. */
	private static List<NetworkVm> vmlist;

	/**
	 * Creates main() to run this example.
	 * 
	 * @param args
	 *            the args
	 */
	public static void main(String[] args) {

		Log.printLine("Starting NewNetworkExample1...");

		try {
			int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			// Datacenters are the resource providers in CloudSim. We need at
			// list one of them to run a CloudSim simulation
			NetworkDatacenter datacenter0 = createDatacenter("Datacenter_0");

			// Third step: Create Broker
			NetDatacenterBroker broker = createBroker();
			broker.setLinkDC(datacenter0);
			// broker.setLinkDC(datacenter0);
			// Fifth step: Create one Cloudlet
			
			int brokerId = broker.getId();

			vmlist = new ArrayList<NetworkVm>();
			
			//VM description
			int vmid = 0;
			int mips = 1;
			long size = 10000; //image size (MB)
			int ram = 512; //vm memory (MB)
			long bw = 1000;
			int pesNumber = 2; //number of cpus
			String vmm = "Xen"; //VMM name

			//create two VMs
			NetworkVm vm1 = new NetworkVm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new NetworkCloudletSpaceSharedScheduler());

			//the second VM will have twice the priority of VM1 and so will receive twice CPU time
			vmid++;
			NetworkVm vm2 = new NetworkVm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new NetworkCloudletSpaceSharedScheduler());

			vmid++;
			NetworkVm vm3 = new NetworkVm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new NetworkCloudletSpaceSharedScheduler());

			//add the VMs to the vmList
			vmlist.add(vm1);
			vmlist.add(vm2);
			vmlist.add(vm3);
			
			Map<Vm, Double> vmCreateTimeMap = new HashMap<Vm, Double>();
			vmCreateTimeMap.put(vm1, 0.0);
			vmCreateTimeMap.put(vm2, 10.0);
			vmCreateTimeMap.put(vm3, 20.0);
			
			datacenter0.setVmCreateTimeMap(vmCreateTimeMap);

			// submit vm list to the broker

			broker.submitVmList(vmlist);
			
			//Fifth step: Create two Cloudlets
//			cloudletList = new ArrayList<NetworkCloudlet>();
//
//			//Cloudlet properties
//			int id = 0;
//			long length = 40000;
//			long fileSize = 300;
//			long outputSize = 300;
//			int memory = 100;
//			UtilizationModel utilizationModel = new UtilizationModelFull();
//
//			NetworkCloudlet cloudlet1 = new NetworkCloudlet(id, length, 1, fileSize, outputSize, memory, utilizationModel, utilizationModel, utilizationModel);
//			cloudlet1.setUserId(brokerId);
//
//			id++;
//			NetworkCloudlet cloudlet2 = new NetworkCloudlet(id, length*2, 2, fileSize, outputSize, memory, utilizationModel, utilizationModel, utilizationModel);
//			cloudlet2.setUserId(brokerId);
//			
//			id++;
//			NetworkCloudlet cloudlet3 = new NetworkCloudlet(id, length*2, 2, fileSize, outputSize, memory, utilizationModel, utilizationModel, utilizationModel);
//			cloudlet3.setUserId(brokerId);
//			
//			Map<NetworkCloudlet, Double> cloudletSubmitTimeMap = new HashMap<NetworkCloudlet, Double>();
//			cloudletSubmitTimeMap.put(cloudlet1, 0.0);
//			cloudletSubmitTimeMap.put(cloudlet2, 100.0);
//			cloudletSubmitTimeMap.put(cloudlet3, 400.0);
//
//			//add the cloudlets to the list
//			cloudletList.add(cloudlet1);
//			cloudletList.add(cloudlet2);
//			cloudletList.add(cloudlet3);
//
//			//submit cloudlet list to the broker
//			broker.submitCloudletList(cloudletList);
//			broker.setCloudletSubmitTimeMap(cloudletSubmitTimeMap);
//
//
//			//bind the cloudlets to the vms. This way, the broker
//			// will submit the bound cloudlets only to the specific VM
//			broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());
//			broker.bindCloudletToVm(cloudlet2.getCloudletId(),vm2.getId());
//			broker.bindCloudletToVm(cloudlet3.getCloudletId(),vm2.getId());


			// Sixth step: Starts the simulation
			CloudSim.startSimulation();

			CloudSim.stopSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			printCloudletList(newList);
			System.out.println("numberofcloudlet " + newList.size() + " Cached "
					+ NetDatacenterBroker.cachedcloudlet + " Data transfered "
					+ NetworkConstants.totaldatatransfer);

			Log.printLine("CloudSimExample1 finished!");
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Unwanted errors happen");
		}
	}

	/**
	 * Creates the datacenter.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the datacenter
	 */
	private static NetworkDatacenter createDatacenter(String name) {

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		// our machine

		List<NetworkHost> hostList = new ArrayList<NetworkHost>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		// List<Pe> peList = new ArrayList<Pe>();

		int mips = 10;

		// 3. Create PEs and add these into a list.
		// peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to
		// store Pe id and MIPS Rating

		// 4. Create Host with its id and list of PEs and add them to the list
		// of machines
		int ram = 2048; // host memory (MB)
		long storage = 1000000; // host storage
		int bw = 10000;
		for (int i = 0; i < NetworkConstants.EdgeSwitchPort * NetworkConstants.AggSwitchPort
				* NetworkConstants.RootSwitchPort; i++) {
			// 2. A Machine contains one or more PEs or CPUs/Cores.
			// In this example, it will have only one core.
			// 3. Create PEs and add these into an object of PowerPeList.
			List<Pe> peList = new ArrayList<Pe>();
			peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(1, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(2, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(3, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(4, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(5, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(6, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating
			peList.add(new Pe(7, new PeProvisionerSimple(mips))); // need to
			// store
			// PowerPe
			// id and
			// MIPS
			// Rating

			// 4. Create PowerHost with its id and list of PEs and add them to
			// the list of machines
			hostList.add(new NetworkHost(
					i,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList,
					new VmSchedulerTimeShared(peList))); // This is our machine
		}

		// 5. Create a DatacenterCharacteristics object that stores the
		// properties of a data center: architecture, OS, list of
		// Machines, allocation policy: time- or space-shared, time zone
		// and its price (G$/Pe time unit).
		String arch = "x86"; // system architecture
		String os = "Linux"; // operating system
		String vmm = "Xen";
		double time_zone = 10.0; // time zone this resource located
		double cost = 3.0; // the cost of using processing in this resource
		double costPerMem = 0.05; // the cost of using memory in this resource
		double costPerStorage = 0.001; // the cost of using storage in this
		// resource
		double costPerBw = 0.0; // the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are
		// not
		// adding
		// SAN
		// devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch,
				os,
				vmm,
				hostList,
				time_zone,
				cost,
				costPerMem,
				costPerStorage,
				costPerBw);

		// 6. Finally, we need to create a NetworkDatacenter object.
		NetworkDatacenter datacenter = null;
		try {
			datacenter = new NetworkDatacenter(
					name,
					characteristics,
					new NetworkVmAllocationPolicy(hostList),
					storageList,
					0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Create Internal Datacenter network
		CreateNetwork(2, datacenter);
		return datacenter;
	}

	// We strongly encourage users to develop their own broker policies, to
	// submit vms and cloudlets according
	// to the specific rules of the simulated scenario
	/**
	 * Creates the broker.
	 * 
	 * @return the datacenter broker
	 */
	private static NetDatacenterBroker createBroker() {
		NetDatacenterBroker broker = null;
		try {
			broker = new NetDatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	/**
	 * Prints the Cloudlet objects.
	 * 
	 * @param list
	 *            list of Cloudlets
	 * @throws IOException
	 */
	private static void printCloudletList(List<Cloudlet> list) throws IOException {
		int size = list.size();
		Cloudlet cloudlet;
		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID"
				+ indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");
				Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent
						+ cloudlet.getVmId() + indent + indent + dft.format(cloudlet.getActualCPUTime())
						+ indent + indent + dft.format(cloudlet.getExecStartTime()) + indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}

	}

	static void CreateNetwork(int numhost, NetworkDatacenter dc) {

		// Edge Switch
		EdgeSwitch edgeswitch[] = new EdgeSwitch[1];

		for (int i = 0; i < 1; i++) {
			edgeswitch[i] = new EdgeSwitch("Edge" + i, NetworkConstants.EDGE_LEVEL, dc);
			// edgeswitch[i].uplinkswitches.add(null);
			dc.Switchlist.put(edgeswitch[i].getId(), edgeswitch[i]);
			// aggswitch[(int)
			// (i/Constants.AggSwitchPort)].downlinkswitches.add(edgeswitch[i]);
		}

		for (Host hs : dc.getHostList()) {
			NetworkHost hs1 = (NetworkHost) hs;
			hs1.bandwidth = NetworkConstants.BandWidthEdgeHost;
			int switchnum = (int) (hs.getId() / NetworkConstants.EdgeSwitchPort);
			edgeswitch[switchnum].hostlist.put(hs.getId(), hs1);
			dc.HostToSwitchid.put(hs.getId(), edgeswitch[switchnum].getId());
			hs1.sw = edgeswitch[switchnum];
			List<NetworkHost> hslist = hs1.sw.fintimelistHost.get(0D);
			if (hslist == null) {
				hslist = new ArrayList<NetworkHost>();
				hs1.sw.fintimelistHost.put(0D, hslist);
			}
			hslist.add(hs1);

		}

	}
}
