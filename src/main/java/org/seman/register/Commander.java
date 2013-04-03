package org.seman.register;

import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seman.register.commands.*;
import org.seman.register.dbo.DatabaseHandler;

public class Commander {
	static BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));
	
	
	
//	static DatabaseHandler DBHandler;
	

	/** 
	 * @param args
	 */
	public static void main(String[] args) {

		// Pattern pattern =
		// Pattern.compile("([A-Za-z]{4,6})|(([A-Za-z]{4,6})\\s([A-Za-z0-9\\s\\'=]*))");
		Command command = null;
		Pattern pattern = Pattern
				.compile("[ ]*([A-Za-z]{4,6})((\\s*[()A-Za-z0-9\\s\\'=><,;]+)*)");
				//.compile("([A-Za-z]{4,6})(\\w)*");
			
//		DatabaseHandler dbhandler = new DatabaseHandler();
//		dbhandler.pushObjectsToDB();
		
		DatabaseHandler dbhandler = DatabaseHandler.getDBHandler();
		dbhandler.pushObjectsToDB();

		while (true) {
			System.out.printf("%n>>>Insert command: %n");
			
			try {

				
				String consoleInput = input.readLine();
				Matcher matcher = pattern.matcher(consoleInput);
				if (matcher.matches()) {
					
				
					
					switch (matcher.group(1).toLowerCase()) {
					case "create":
						command =  new Create();
						System.out.println("creating...");
						break;
						
					case "read":
						command =  new Read();
						System.out.println("reading...");
						break;
						
					case "update":
						command = new Update();
						System.out.println("updating...");
						break;
						
					case "delete":
						command = new Delete();
						System.out.println("deleting...");
						break;
						
					case "help":
						command = new Help();	
						break;
						
					case "exit":
						System.out.println("Exiting...");
						System.exit(0);
						break;
						
					default:
						System.out.println("Wrong Input! Type \"help\" for list of commands.");
						continue;
					}
					
					
					
					command.setArguments(matcher.group(2));
					command.execute();
					
				} else
					System.out
							.println("!Wrong Input! Type \"help\" for list of commands.");
				

				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

	}
	
	
}
