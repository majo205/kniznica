package org.seman.register.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Help implements Command {

	private String commandString;

	@Override
	public void execute() {

		
		switch (commandString) {
		case " create":
			System.out.println(new Create().getHelpDescription());
			break;
		case " read":
			System.out.println(new Read().getHelpDescription());
			break;
		case " update":
			System.out.println(new Update().getHelpDescription());
			break;
		case " delete":
			System.out.println(new Delete().getHelpDescription());
			break;
		case " help":
			System.out.println(new Help().getHelpDescription());
			break;
		default:
			//System.out.println("Unsupported command!");
			System.out.println("Supported commands: ");
			System.out.println("create");
			System.out.println("read");
			System.out.println("update");
			System.out.println("delete");
			System.out.printf("%nType \"help <command>\" for more information%n");
			
			break;
		}

	}

	@Override
	public String getHelpDescription() {

		return "Really?";
	}

	@Override
	public void setArguments(String command) {
		this.commandString = command;

	}

	

}
