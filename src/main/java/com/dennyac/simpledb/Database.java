package com.dennyac.simpledb;

import java.util.Stack;

import com.dennyac.simpledb.commands.*;

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

		// Put each case into its own function.
		switch (cmd.getCommandType()) {
		case SET:
			if (ts == null)
				((Set) cmd).execute(ds);
			else
				ts.addOp((WriteOperation) cmd, ds);
			break;
		case GET:
			String value;
			if (ts == null)
				value = ((Get) cmd).getVal(ds);
			else {
				if (ts.containsKey(((Get) cmd).getKey()))
					value = ts.getValue(((Get) cmd).getKey());
				else
					value = ((Get) cmd).getVal(ds);
			}
			if(value==null) System.out.println("NULL");
			else
			System.out.println(value);	//Might have to change this
			break;
		case NUMEQUALSTO:
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
			break;
		case UNSET:
			if (ts == null)
				((Unset) cmd).execute(ds);
			else
				ts.addOp((WriteOperation) cmd, ds);
			break;
		case BEGIN:
			if (ts != null)
				tranStack.push(ts);
			ts = new Transaction();
			break;
		case COMMIT:
			if (ts == null)
				System.out.println("NO TRANSACTION");
			else {
				ds.commitTransaction(ts.ts); // Will have to change this. I
												// guess I'll have to print NO
												// TRANSACTION
				ts = null;
				tranStack.clear();

			}
			break;
		case ROLLBACK:
			if (ts != null) {
				ts = null;
				if (!tranStack.isEmpty())
					ts = tranStack.pop();
			}
			else
				System.out.println("NO TRANSACTION");
			break;
		case END:
			System.exit(0);
			break;
		case INVALID:
			System.out.println("Invalid Command");

		}

	}

}
