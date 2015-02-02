package com.dennyac.simpledb;

import java.util.Stack;

import com.dennyac.simpledb.commands.*;

/**
 * The Database class contains the data store (In-memory), state of current and nested transactions. 
 * It accepts commands and executes them.
 * 
 * @author dennyac
 *
 */
public class Database {
	DataStore ds;
	Transaction ts;
	Stack<Transaction> tranStack;

	public Database(DataStore ds) {
		this.ds = ds;
		ts = null;
		tranStack = new Stack<Transaction>();
	}

	public void execute(Command cmd) {
		switch (cmd.getCommandType()) {
		case SET:
			set(cmd);
			break;
		case GET:
			get(cmd);
			break;
		case NUMEQUALSTO:
			numEqualsTo(cmd);
			break;
		case UNSET:
			unset(cmd);
			break;
		case BEGIN:
			begin();
			break;
		case COMMIT:
			commit();
			break;
		case ROLLBACK:
			rollback();
			break;
		case END:
			end();
			break;
		case INVALID:
			System.out.println("Invalid Command");
		}

	}
	
	public void set(Command cmd) {
		if (ts == null)
			((Set) cmd).execute(ds);
		else
			ts.applyOperation((WriteOperation) cmd, ds);
	}

	public void get(Command cmd) {
		String value;
		if (ts == null)
			value = ((Get) cmd).getVal(ds);
		else {
			if (ts.containsKey(((Get) cmd).getKey()))
				value = ts.getValue(((Get) cmd).getKey());
			else
				value = ((Get) cmd).getVal(ds);
		}
		if (value == null)
			System.out.println("NULL");
		else
			System.out.println(value);
	}

	public void numEqualsTo(Command cmd) {
		int count;
		if (ts == null)
			count = ((NumEqualTo) cmd).getCount(ds);
		else {
			if (ts.containsValue(((NumEqualTo) cmd).getValue()))
				count = ts.getNumEqualsTo(((NumEqualTo) cmd).getValue());
			else
				count = ((NumEqualTo) cmd).getCount(ds);
		}
		System.out.println(count);
	}

	public void unset(Command cmd) {
		if (ts == null)
			((Unset) cmd).execute(ds);
		else
			ts.applyOperation((WriteOperation) cmd, ds);
	}

	public void begin() {
		if (ts != null) {
			tranStack.push(ts);
			ts = new Transaction(tranStack.peek());
		} else
			ts = new Transaction();
	}

	public void commit() {
		if (ts == null)
			System.out.println("NO TRANSACTION");
		else {
			ds.commitTransaction(ts);
			ts = null;
			tranStack.clear();

		}
	}

	public void rollback() {
		if (ts != null) {
			ts = null;
			if (!tranStack.isEmpty())
				ts = tranStack.pop();
		} else
			System.out.println("NO TRANSACTION");
	}

	public void end() {
		System.exit(0);
	}


}
