package com.dennyac.simpledb.commands;

import com.dennyac.simpledb.DataStore;

public class Get implements Command{
	
	String key;
	
	public String getKey(){
		return key;
	}
	
	public Get(String key){
		this.key = key;
	}
	
	public String getVal(DataStore ds){
		return ds.get(key);
	}

	public CommandType getCommandType() {
		return CommandType.GET;
	}

}
