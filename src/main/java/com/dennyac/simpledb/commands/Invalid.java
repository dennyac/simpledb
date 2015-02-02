package com.dennyac.simpledb.commands;

public class Invalid implements Command{

	public CommandType getCommandType() {
		return CommandType.INVALID;
	}

}
