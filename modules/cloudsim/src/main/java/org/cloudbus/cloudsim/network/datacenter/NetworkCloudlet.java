/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.network.datacenter;

import java.util.ArrayList;
import java.util.Map;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.core.CloudSim;

/**
 * NetworkCloudlet class extends Cloudlet to support simulation of complex applications. Each such
 * a network Cloudlet represents a task of the application. Each task consists of several stages.
 * 
 * <br/>Please refer to following publication for more details:<br/>
 * <ul>
 * <li><a href="http://dx.doi.org/10.1109/UCC.2011.24">Saurabh Kumar Garg and Rajkumar Buyya, NetworkCloudSim: Modelling Parallel Applications in Cloud
 * Simulations, Proceedings of the 4th IEEE/ACM International Conference on Utility and Cloud
 * Computing (UCC 2011, IEEE CS Press, USA), Melbourne, Australia, December 5-7, 2011.</a>
 * </ul>
 * 
 * @author Saurabh Kumar Garg
 * @since CloudSim Toolkit 1.0
 * @todo Attributes should be private
 * @todo The different cloudlet classes should have a class hierarchy, by means
 * of a super class and/or interface.
 */
public class NetworkCloudlet extends Cloudlet implements Comparable<Object> {
        /** Time when cloudlet will be submitted. */
	public double submittime; 

        /** Time when cloudlet finishes execution. */
	public double finishtime; 

        /** Execution time for cloudlet. */
	public double exetime; 

        /** Number of cloudlet's stages . */
	public double numStage;

        /** Current stage of cloudlet execution. */
	public int currStagenum; 

        /** Star time of the current stage. 
         */
	public double timetostartStage;

        /** Time spent in the current stage. 
         */
	public double timespentInStage; 

        /** 
         * @todo It doesn't appear to be used. 
        */
	public Map<Double, HostPacket> timeCommunicate;

        /** All stages which cloudlet execution. */
	public ArrayList<TaskStage> stages; 
        
        /**
         * Cloudlet's memory.
         * @todo Required, allocated, used memory?
         * It doesn't appear to be used.
         */
	long memory;

        /**
         * Cloudlet's start time.
         */
	public double starttime;
	
		/**
	     * Cloudlet is sending.
	     */
	public boolean isSending;
	
		/**
	     * Cloudlet is receiving.
	     */
	public boolean isReceiving;

	public NetworkCloudlet(
			int cloudletId,
			long cloudletLength,
			int pesNumber,
			long cloudletFileSize,
			long cloudletOutputSize,
			long memory,
			UtilizationModel utilizationModelCpu,
			UtilizationModel utilizationModelRam,
			UtilizationModel utilizationModelBw) {
		super(
				cloudletId,
				cloudletLength,
				pesNumber,
				cloudletFileSize,
				cloudletOutputSize,
				utilizationModelCpu,
				utilizationModelRam,
				utilizationModelBw);

		currStagenum = -1;
		this.memory = memory;
		stages = new ArrayList<TaskStage>();
		isSending = false;
		isReceiving = false;
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	public double getSubmittime() {
		return submittime;
	}
	
	public boolean getIsSending() {
		return isSending;
	}
	
	public boolean getIsReceiving() {
		return isReceiving;
	}
	
	public void setIsSending(boolean sending) {
		isSending = sending;
	}
	
	public void setIsReceiving(boolean receiving) {
		isReceiving = receiving;
	}
	
	/**
     * Gets the utilization percentage of cpu.
     *
     * @param time the time
     * @return the utilization of cpu
     */
	public double getUtilizationOfCpu(final double time) {
		if (currStagenum < 0) {
			return 0;
		}
		if (currStagenum < stages.size()) {
			switch(stages.get(currStagenum).type) {
			case NetworkConstants.EXECUTION:
				break;
			default:
				return 0;
				
			}
		}
        return getUtilizationModelCpu().getUtilization(time);
    }


    /**
     * Gets the utilization percentage of memory.
     *
     * @param time the time
     * @return the utilization of memory
     */
    public double getUtilizationOfRam(final double time) {
    	if (currStagenum < 0) {
			return 0;
		}
    	if (currStagenum < stages.size()) {
			switch(stages.get(currStagenum).type) {
			case NetworkConstants.EXECUTION:
				break;
			default:
				return 0;
				
			}
		}
        return getUtilizationModelRam().getUtilization(time);
    }

    /**
     * Gets the utilization percentage of bw.
     *
     * @param time the time
     * @return the utilization of bw
     */
    public double getUtilizationOfBw(final double time) {
    	if (currStagenum < 0) {
			return 0;
		}
    	System.out.println(getCloudletId());
		System.out.println(CloudSim.clock());
		System.out.println(isSending);
		if (isSending || isReceiving) {
			return getUtilizationModelBw().getUtilization(time);
		}
		else {
			return 0;
		}
//        return getUtilizationModelBw().getUtilization(time);
    }

}
