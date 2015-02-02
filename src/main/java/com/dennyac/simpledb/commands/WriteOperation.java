package com.dennyac.simpledb.commands;

import com.dennyac.simpledb.DataStore;

public interface WriteOperation {
	
	public void execute(DataStore ds);
	
	public String getKey();

}
