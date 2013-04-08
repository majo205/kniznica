package org.seman.register.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seman.register.dao.BookDAOFactory;
import org.seman.register.dbo.DatabaseHandler;

public class Create implements Command{
	
	List<List<String>> commandList = null;
	
	
	@Override
	public void execute() {
		

		//new DatabaseHandler().insertToTable(commandList);
		//DatabaseHandler.getDBHandler().insertToTable(commandList);
		BookDAOFactory.create().createBook(commandList);
		
		
	}

	
	@Override
	public String getHelpDescription() {
		
		return "create nazov= , autor= , rokVydania= , vydavatelstvo= , zaner= ;";
	}

	@Override
	public void setArguments(String command) {
		this.commandList =parseCommand(command); 
		
	}
	
	/** pomocou regular expresions rozdeli podretazec prikazu na hodnoty spracovatelne prikazom Create 
	 * 
	 * @param command - podretazec prikazu (bez klucoveho slova oznacujuceho prikaz)
	 * @return zoznam dvojic retazcov oznacujucich nazov stlpca hodnoty a odpovedajucej hodnoty 
	 */
	private List<List<String>> parseCommand(String command) {

		List<List<String>> listOfValues = new ArrayList<>();
	

		Pattern createPattern = Pattern
				.compile("([ ]*([A-Za-z0-9]+)[ ]*=[ ]*([A-Za-z0-9]+))([ ]*[,;])+");
		Matcher matcher = createPattern.matcher(command);

		while (matcher.find()) {
			List<String> columnValues = new ArrayList<>();
			for (int i = 1; i <= matcher.groupCount(); i++) {			

				if (i == 2 || i == 3) {
					columnValues.add(matcher.group(i));

				}

			}

			listOfValues.add(columnValues);


			System.out.println();
		}

		return listOfValues;

	}

}
