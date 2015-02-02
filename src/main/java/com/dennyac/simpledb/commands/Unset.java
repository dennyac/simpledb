package com.dennyac.simpledb.commands;

import com.dennyac.simpledb.DataStore;

public class Unset implements WriteOperation,Command {
	String key;
	public Unset(String key){
		this.key = key;
	}
	
	public String getKey(){
		return key;
	}
	
	public void execute(DataStore ds){
		ds.unset(key);
	}

	public CommandType getCommandType() {
		return CommandType.UNSET;
	}
}
