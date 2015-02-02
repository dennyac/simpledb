package com.dennyac.simpledb;

public class Driver {
	
	public static void main(String[] args){
		Database db = new Database(new InMemDB());	//Probably pass Output Stream
		Parser parse = new Parser();
		while(parse.hasCommand()){
			db.execute(parse.nextCommand());
		}
	}
}
