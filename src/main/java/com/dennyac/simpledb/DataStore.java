package com.dennyac.simpledb;

public interface DataStore {

	public void set(String key, String value);

	public String get(String key);

	public void unset(String key);

	public int numEqualTo(String value);
	
	public void commitTransaction(TransactionState ts);

}
