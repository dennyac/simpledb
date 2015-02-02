package com.dennyac.simpledb;

import java.util.ArrayList;
import java.util.List;

import com.dennyac.simpledb.commands.WriteOperation;


//Is transaction required or can I replace it with state
public class Transaction{

	TransactionState ts;

	public Transaction() {
		ts = new TransactionState();
	}
	
	public boolean containsKey(String key){
		return ts.containsKey(key);
	}
	
	public boolean containsValue(String value){
		return ts.containsValue(value);
	}
	
	public String getValue(String key){
		return ts.getValue(key);
	}
	
	public int getNumEqualsTo(String value){
		return ts.getNumEqualsTo(value);
	}


	public void addOp(WriteOperation op, DataStore ds) {
		ts.updateState(op, ds);
	}

	public void commit(DataStore ds) {
		ds.commitTransaction(ts);
	}

	public void rollback() {
		ts = null;		//Is this enough
	}
}
