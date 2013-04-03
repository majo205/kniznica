package org.seman.register.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seman.register.dbo.DatabaseHandler;

public class Update implements Command{

	private List<List<String>> commandString;

	@Override
	public void execute() {
		DatabaseHandler.getDBHandler().updateTable(commandString);
		
	}

	@Override
	public String getHelpDescription() {
		
		return "update (columnname = newColumnValue) searchedColumnName operator searchedColumnValue, ...\n" +
				"criteriaColumnName : id, autor, nazov, rokvydania, vydavatel, zaner\n"+ 
				"operator : <, >, <=, >=, =\n";
	}

	@Override
	public void setArguments(String command) {
		this.commandString = parseCommand(command);
		
	} 

	
	/** pomocou regular expresions rozdeli podretazec prikazu na hodnoty spracovatelne prikazmi Update a Read 
	 * 
	 * @param command - podretazec prikazu (bez klucoveho slova oznacujuceho prikaz)
	 * @return zoznam dvojice a trojic retazcov nazvy stlpcov, novej vkladanej hodnoty, hladanej hodnoty a relacnych operatorov
	 */
	private List<List<String>> parseCommand(String command) {

		List<List<String>> listOfValues = new ArrayList<>();
		List<String> columnValues = new ArrayList<>();
		String finderCommand =null;
		
		System.out.println(" command string "+command);
		
		Pattern updaterPattern = Pattern
				.compile("([ ]*\\({1}[ ]*([A-Za-z-0-9_]+)[ ]*[=]{1}[ ]*([A-Za-z-0-9_]+)[ ]*\\){1})(.*)");
		
		Pattern finderPattern = Pattern.compile("([ ]*([A-Za-z0-9]+)[ ]*([=><]{1,2})[ ]*([A-Za-z0-9]+))([ ]*[,;])+");
		
		
		Matcher matcher; 
		matcher = updaterPattern.matcher(command);
		
		if (matcher.matches()){
		
			columnValues.add(matcher.group(2));
			columnValues.add(matcher.group(3));
			finderCommand = matcher.group(4);
		}else {System.out.println("No secified columns and/or values to update!");
				return new ArrayList<>();}
		
		listOfValues.add(columnValues);

		matcher = finderPattern.matcher(finderCommand);
		while (matcher.find()) {
			columnValues = new ArrayList<>();
			for (int i = 1; i <= matcher.groupCount(); i++) {

			

				if (i > 1 && i < 5) {
					columnValues.add(matcher.group(i));

				}

			}

			listOfValues.add(columnValues);

			System.out.println();
		}	
		

		return listOfValues;

	}
	
	
	
}
