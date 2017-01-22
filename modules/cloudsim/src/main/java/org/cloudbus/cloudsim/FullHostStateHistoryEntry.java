package org.cloudbus.cloudsim;

public class FullHostStateHistoryEntry extends HostStateHistoryEntry {
	
	public FullHostStateHistoryEntry(double time, double allocatedMips, double requestedMips, boolean isActive) {
		super(time, allocatedMips, requestedMips, isActive);
	}

}
