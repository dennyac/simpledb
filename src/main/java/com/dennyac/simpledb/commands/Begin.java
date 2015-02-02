package com.dennyac.simpledb.commands;

public class Begin implements Command{

	public CommandType getCommandType() {
		return CommandType.BEGIN;
	}

}