package com.dennyac.simpledb.commands;

import com.dennyac.simpledb.DataStore;

public class NumEqualTo implements Command{
	
	String value;
	
	public String getValue(){
		return value;
	}
	
	public NumEqualTo(String value){
		this.value = value;
	}
	
	public int getCount(DataStore ds){
		return ds.numEqualTo(value);
	}

	public CommandType getCommandType() {
		return CommandType.NUMEQUALSTO;
	}

}
