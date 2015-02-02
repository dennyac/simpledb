package com.dennyac.simpledb;

/**
 * The DataStore interface contains all operations that a data store should perform. 
 * 
 * @author dennyac
 *
 */
public interface DataStore {

	public void set(String key, String value);

	public String get(String key);

	public void unset(String key);

	public int numEqualTo(String value);

	public void commitTransaction(Transaction ts);

}
