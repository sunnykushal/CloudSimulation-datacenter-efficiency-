package org.cloudbus.cloudsim.examples.power.random;

import java.io.IOException;


public class IqrMc {

	
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = false;
		String inputFolder = "output";
		String outputFolder = "";
		String workload = "random"; 
		String vmAllocationPolicy = "iqr"; 
		String vmSelectionPolicy = "mc"; 
		String parameter = "1.5"; 

		new RandomRunner(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter);
	}

}
