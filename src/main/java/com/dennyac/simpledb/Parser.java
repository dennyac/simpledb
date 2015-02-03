package com.dennyac.simpledb;

import com.dennyac.simpledb.commands.*;

import java.util.Scanner;

/**
 * The Parser class parses commands from Standard input
 * 
 * @author dennyac
 *
 */
public class Parser {

	Scanner in;

	public Parser() {
		in = new Scanner(System.in);
	}

	public boolean hasCommand() {
		return in.hasNext();
	}

	public Command nextCommand() {
		String[] tokens = in.nextLine().split("\\s+");
		try {

			if (tokens[0].equals("BEGIN"))
				return new Begin();
			else if (tokens[0].equals("END"))
				return new End();
			else if (tokens[0].equals("GET"))
				return new Get(tokens[1]);
			else if (tokens[0].equals("SET"))
				return new Set(tokens[1], tokens[2]);
			else if (tokens[0].equals("UNSET"))
				return new Unset(tokens[1]);
			else if (tokens[0].equals("NUMEQUALTO"))
				return new NumEqualTo(tokens[1]);
			else if (tokens[0].equals("COMMIT"))
				return new Commit();
			else if (tokens[0].equals("ROLLBACK"))
				return new Rollback();
			else
				return new Invalid();
		} catch (Exception e) {
			return new Invalid();
		}

	}

}
