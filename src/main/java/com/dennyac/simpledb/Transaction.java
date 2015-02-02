package com.dennyac.simpledb;

import java.util.HashMap;
import java.util.HashSet;

import com.dennyac.simpledb.commands.Set;
import com.dennyac.simpledb.commands.Unset;
import com.dennyac.simpledb.commands.WriteOperation;

/**
 * The transaction class is responsible for handling operations within a transaction.
 * Each transaction maintains state. The state consists of all changes related to keys updated
 * as part of the transaction. 
 *  
 * @author dennyac
 *
 */
public class Transaction {

	HashMap<String, String> keyOps;
	HashMap<String, Integer> valueCounts;
	HashSet<String> delKeys;
	
	public Transaction() {
		keyOps = new HashMap<String, String>();
		valueCounts = new HashMap<String, Integer>();
		delKeys = new HashSet<String>();
	}
	
	//State of parent transaction is passed to child transaction.
	public Transaction(Transaction parent) {
		keyOps = parent.getKeyOpsClone();
		valueCounts = parent.getValueCountsClone();
		delKeys = parent.getDelKeysClone();
	}

	//To check whether a call to the data store is required or whether data is available in transaction state
	public boolean containsKey(String key) {
		return keyOps.containsKey(key) || delKeys.contains(key);
	}

	//To check whether a call to the data store is required or whether data is available in transaction state
	public boolean containsValue(String value) {
		return valueCounts.containsKey(value);
	}

	public String getValue(String key) {
		if (keyOps.containsKey(key))
			return keyOps.get(key);
		return "NULL";
	}

	public int getNumEqualsTo(String value) {
		return valueCounts.get(value);
	}

	public HashSet<String> getDelKeys() {
		return delKeys;
	}

	public HashMap<String, String> getKeyOps() {
		return keyOps;
	}

	public HashMap<String, Integer> getValueCounts() {
		return valueCounts;
	}

	public HashMap<String, String> getKeyOpsClone() {
		return (HashMap<String, String>) keyOps.clone();
	}

	public HashMap<String, Integer> getValueCountsClone() {
		return (HashMap<String, Integer>) valueCounts.clone();
	}

	public HashSet<String> getDelKeysClone() {
		return (HashSet<String>) delKeys.clone();
	}

	//Update the transaction state with a given operation.
	//If the data is not present in transaction state, the initial state is fetched from db
	public void applyOperation(WriteOperation op, DataStore ds) {

		String key = op.getKey();
		String value;
		
		// If data not present in transaction state fetch from db
		if (!keyOps.containsKey(key) || !delKeys.contains(key)) {
			value = ds.get(key);
			if (value != null) {
				keyOps.put(key, value);
				valueCounts.put(value, ds.numEqualTo(value));
			}
		}

		if (keyOps.containsKey(key)) {
			value = keyOps.get(key);
			valueCounts.put(value, valueCounts.get(value) - 1);
		}
		if (op instanceof Set) {

			if (delKeys.contains(key))
				delKeys.remove(key);

			String opValue = ((Set) op).getValue();
			if (!valueCounts.containsKey(opValue)) {
				valueCounts.put(opValue, ds.numEqualTo(opValue));
			}
			valueCounts.put(opValue, valueCounts.get(opValue) + 1);
			keyOps.put(key, opValue);
		} else if (op instanceof Unset) {
			keyOps.remove(key);
			delKeys.add(key);
		}
	}

	//Apply the current transaction on the database
	public void commit(DataStore ds) {
		ds.commitTransaction(this);
	}

}
