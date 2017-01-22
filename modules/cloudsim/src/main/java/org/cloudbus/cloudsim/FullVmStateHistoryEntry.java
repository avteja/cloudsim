package org.cloudbus.cloudsim;

public class FullVmStateHistoryEntry extends VmStateHistoryEntry {
	
	public FullVmStateHistoryEntry(double time, double allocatedMips, double requestedMips, boolean isInMigration) {
		super(time, allocatedMips, requestedMips, isInMigration);
	}

}
