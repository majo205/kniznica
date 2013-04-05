package org.seman.register.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seman.register.Commander;

public class Help implements Command {

	private String commandString;
	private Map<String, Command> command;

	@Override
	public void execute() {

		System.out.println();
		if (!getCommand().containsKey(commandString)) {

			if (!commandString.isEmpty())
				System.out.println("\"" + commandString
						+ "\" is not supported command!");
			System.out.println("Supported commands :");

			Collection<?> listOfcommands = getCommand().values();

			for (Object object : listOfcommands) {
				System.out.println("  " + object.getClass().getSimpleName());
			}

			System.out
					.printf("%nType \"help <command>\" for more information%n");

		} else {
			System.out.println("Command "+getCommand().get(commandString).getClass().getSimpleName()+" - description :\n");
			System.out.println(getCommand().get(commandString)
					.getHelpDescription());
		}

		
	}

	@Override
	public String getHelpDescription() {

		return "Really?";
	}

	@Override
	public void setArguments(String command) {
		this.commandString = command.trim();

	}

	public Map<String, Command> getCommand() {
		return command;
	}

	public void setCommand(Map<String, Command> command) {
		this.command = command;
	}

}
