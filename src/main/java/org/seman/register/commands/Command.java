package org.seman.register.commands;

import java.util.ArrayList;
import java.util.List;

public interface Command {

	List<List<String>> commandList =  new ArrayList<>();
	
	
	
	/**metoda zabezpecuje vykonanie prikazu reprezentovaneho 
	 *	triedou typu Command
	 * 
	 */
	public void execute();
	
	/** vrati retazec popisujuci pouztie triedy typu Command
	 * 
	 * @return  popis triedy typu Command formou retazca String
	 */
	public String getHelpDescription();
	
	/**nastavi hodnoty clenskej premennej pre triedy typu Command
	 * z ktorej sa zostavia hodnoty premennych pre spracovanie v databaze
	 *  
	 * @param commandString - podretazec prikazu (bez klucoveho slova oznacujuceho prikaz)
	 */
	public void setArguments(String commandString);
	
}
