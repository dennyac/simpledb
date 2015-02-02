package com.dennyac.simpledb.commands;

import com.dennyac.simpledb.DataStore;

public class Set implements WriteOperation, Command{
	
	String key;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	String value;
	
	public Set(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	public void execute(DataStore ds){
		ds.set(key, value);
	}

	public CommandType getCommandType() {
		return CommandType.SET;
	}
}
