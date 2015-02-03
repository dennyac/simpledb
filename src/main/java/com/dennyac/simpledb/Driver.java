package com.dennyac.simpledb;

/**
 * 
 * The Driver class is responsible for initializing the database and parser. 
 * It reads commands for Standard input and passes the commands to the database for execution
 * @author dennyac
 * 
 * Sample Usage - java -cp simpledb-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.dennyac.simpledb.Driver
 *
 */
public class Driver {

	public static void main(String[] args) {
		Database db = new Database(new InMemDB());
		Parser parse = new Parser();
		while (parse.hasCommand()) {
			db.execute(parse.nextCommand());
		}
	}
}
