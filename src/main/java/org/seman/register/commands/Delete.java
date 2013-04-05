package org.seman.register.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.seman.register.dbo.DatabaseHandler;
import org.seman.register.dbo.Book;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class Delete implements Command {

	private String commandString;

	@Override
	public void execute() {

		DatabaseHandler dbHandler = DatabaseHandler.getDBHandler();
		try {
			if ((commandString == null) || (commandString.isEmpty())) {
				System.out
						.println("This will edlete ALL tables! Continue? (y/n)");

				String choice = new BufferedReader(new InputStreamReader(
						System.in)).readLine().toLowerCase();
				if (choice.equals("y")) {

					List<Book> listKnih = new Read()
							.fillAndGetResultList(commandString);

					System.out.println("Deleted rows :"
							+ dbHandler.deleteFromTable(listKnih));

				} else
					System.out.println("Deleting canceled!");

			} else {
				List<Book> listKnih = new Read()
						.fillAndGetResultList(commandString);

				System.out.println("Deleted rows :"
						+ dbHandler.deleteFromTable(listKnih));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String getHelpDescription() {

		return "delete searchedColumnName operator searchedColumnValue, ...\n"
				+ "criteriaColumnName : id, autor, nazov, rokvydania, vydavatel, zaner\n"
				+ "operator : <, >, <=, >=, =\n";
	}

	@Override
	public void setArguments(String command) {
		this.commandString = command;

	}

}
