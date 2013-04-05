package org.seman.register.commands;

public class Exit implements Command{

	@Override
	public void execute() {
		System.out.println("Exiting application...");
		System.exit(0);
		
	}

	@Override
	public String getHelpDescription() {		
		return "Exits application.";
	}

	@Override
	public void setArguments(String commandString) {
				
	}

}
