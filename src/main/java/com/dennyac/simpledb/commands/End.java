package com.dennyac.simpledb.commands;

public class End implements Command {

	public CommandType getCommandType() {
		return CommandType.END;
	}

}
