package com.dennyac.simpledb;

import java.util.HashMap;
import java.util.HashSet;

import com.dennyac.simpledb.commands.Set;
import com.dennyac.simpledb.commands.Unset;
import com.dennyac.simpledb.commands.WriteOperation;

public class TransactionState {

	HashMap<String, String> keyOps;
	HashMap<String, Integer> valueCounts;
	HashSet<String> delKeys;
	
	public boolean containsKey(String key){
		return keyOps.containsKey(key) || delKeys.contains(key);
	}
	
	public boolean containsValue(String value){
		return valueCounts.containsKey(value);
	}
	
	public String getValue(String key){
		if(keyOps.containsKey(key)) return keyOps.get(key);
		return "NULL";
	}
	
	public int getNumEqualsTo(String value){
		return valueCounts.get(value);
	}

	public TransactionState() {
		keyOps = new HashMap<String, String>();
		valueCounts = new HashMap<String, Integer>();
		delKeys = new HashSet<String>();
	}
	
	public HashSet<String> getDelKeys(){
		return delKeys;
	}
	
	public HashMap<String, String> getKeyOps() {
		return keyOps;
	}

	public HashMap<String, Integer> getValueCounts() {
		return valueCounts;
	}

	

	public TransactionState(TransactionState ts) {
		keyOps = ts.getKeyOpsClone();
		valueCounts = ts.getValueCountsClone();
		delKeys = ts.getDelKeysClone();
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

	public void updateState(WriteOperation op, DataStore ds) {
		// If not in state fetch from db
		String key = op.getKey();
		String value;
		if (!keyOps.containsKey(key) || !delKeys.contains(key)) {
			value = ds.get(key);
			if (value != null) {
				keyOps.put(key, value);
				valueCounts.put(value, ds.numEqualTo(value));
			}
		}

		if (keyOps.containsKey(key)) {
			value = keyOps.get(key);
			System.out.println("Came here and value is " + value);
			System.out.println("Value Count before is " + valueCounts.get(value));
			valueCounts.put(value, valueCounts.get(value) - 1);
			System.out.println("Value Count after is " + valueCounts.get(value));

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

}
