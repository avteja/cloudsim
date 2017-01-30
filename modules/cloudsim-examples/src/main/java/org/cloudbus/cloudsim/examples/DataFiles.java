package org.cloudbus.cloudsim.examples;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.cloudbus.cloudsim.FullHostStateHistoryEntry;
import org.cloudbus.cloudsim.FullVmStateHistoryEntry;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;

public class DataFiles {
	public static void addToDataFile(String header, double time, double value) {
		String fileName = "/home/ravi/Documents/Ravi Teja A.V/RnD/data_files/"+header;
		try {
			File file = new File(fileName);
			int nocreate = 0;
            if (!file.exists()) {
                file.createNewFile();
                nocreate = 1;
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            if (nocreate == 1) {
            	fw.write("Time, " + header + "\n");
            }
            fw.write(Double.toString(time) + ", " + Double.toString(value) + "\n");
            fw.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
