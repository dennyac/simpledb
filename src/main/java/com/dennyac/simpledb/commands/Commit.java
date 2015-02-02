package com.dennyac.simpledb.commands;


public class Commit implements Command{

	public CommandType getCommandType() {
		return CommandType.COMMIT;
	}
	
}
