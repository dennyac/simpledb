package com.dennyac.simpledb.commands;

public class Rollback implements Command{

	public CommandType getCommandType() {
		return CommandType.ROLLBACK;
	}

}