package com.dennyac.simpledb;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The InMemDB class implements the DataStore interface.
 * All data is stored in HashMaps.
 * 
 * @author dennyac
 *
 */
public class InMemDB implements DataStore {

	HashMap<String, String> store;
	HashMap<String, Integer> counter;

	public InMemDB() {
		store = new HashMap<String, String>();
		counter = new HashMap<String, Integer>();
	}

	public void set(String key, String value) {
		store.put(key, value);
		if (!counter.containsKey(value))
			counter.put(value, 0);
		counter.put(value, counter.get(value) + 1);
	}

	public String get(String key) {
		if (!store.containsKey(key))
			return null;
		return store.get(key);
	}

	public void unset(String key) {
		if (!store.containsKey(key))
			return;
		String value = store.remove(key);
		int count = counter.get(value);
		if (count == 0)
			counter.remove(value);
		counter.put(value, count - 1);
	}

	public int numEqualTo(String value) {
		if (!counter.containsKey(value))
			return 0;
		return counter.get(value);
	}

	//Saves state of the transaction onto the database
	public void commitTransaction(Transaction ts) {
		for (String key : ts.getDelKeys())
			store.remove(key);
		for (Entry<String, String> entry : ts.getKeyOps().entrySet())
			store.put(entry.getKey(), entry.getValue());
		for (Entry<String, Integer> entry : ts.getValueCounts().entrySet())
			counter.put(entry.getKey(), entry.getValue());
	}

}
