package com.dance.config;

import java.util.Arrays;

public enum Dance {

	WALL_HUGGER("No Dance", "no_dance"),
	TANGO("Tango", "tango");

	final String danceName;
	final String workflowProcessName;

	Dance(final String danceName, final String workflowProcessName){
		this.danceName= danceName;
		this.workflowProcessName = workflowProcessName;
	}

	public static Dance getDanceByName(final String danceName) {
		return Arrays.stream(Dance.values()).filter(d -> d.danceName.equalsIgnoreCase(danceName)).findFirst()
				.orElse(WALL_HUGGER);
	}

	public String getProcessName(){
		return workflowProcessName;
	}
}
