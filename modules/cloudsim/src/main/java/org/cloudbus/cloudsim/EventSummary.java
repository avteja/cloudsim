package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventSummary {
	
	private List<Vm> vmList;
	
	private List<Host> hostList;
	
	private Map<Vm, List<VmStateHistoryEntry> > vmStateMap;
	
	private Map<Host, List<HostStateHistoryEntry> > hostStateMap;
	
	public EventSummary(List<Vm> vmList, List<Host> hostList) {
		this.vmList = vmList;
		this.hostList = hostList;
		vmStateMap = new HashMap<Vm, List<VmStateHistoryEntry> >();
		hostStateMap = new HashMap<Host, List<HostStateHistoryEntry> >();
		for (Vm vm: vmList) {
			vmStateMap.put(vm, new ArrayList<VmStateHistoryEntry>());
		}
		for (Host host: hostList) {
			hostStateMap.put(host, new ArrayList<HostStateHistoryEntry>());
		}
	}
	
	public void storePresentState(double time) {
		
	}
	
	public void setVmList(List<Vm> vmList) {
		this.vmList = vmList;
	}
	
	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}
	
}
