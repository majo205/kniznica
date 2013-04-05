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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seman.register.commands.*;
import org.seman.register.dbo.DatabaseHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class Commander {
	static BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));
	
	Map<String,Command> commands;
	
	
	
//	static DatabaseHandler DBHandler;
	

	/** 
	 * @param args
	 */
	public static void main(String[] args) {


			
//		DatabaseHandler dbhandler = new DatabaseHandler();
//		dbhandler.pushObjectsToDB();
		
//		DatabaseHandler dbhandler = DatabaseHandler.getDBHandler();
//		dbhandler.pushObjectsToDB();
		
		//ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
		BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("src/main/resources/Spring.xml"));
		
		DatabaseHandler.getDBHandler().pushObjectsToDB();
		
		Commander commander = (Commander) beanFactory.getBean("Commander");
		
		commander.getAndExecuteCommand();
		
		
		
		//System.out.println(" "+commandx);
	
		
//		BeanFactory springFactory = new XmlBeanFactory(new FileSystemResource("Spring.xml"));
//		Map<String, String> commandsMap = new HashMap<String, String>();
		
		
		
		
		

	}



public void getAndExecuteCommand() {
	// Pattern pattern =
	// Pattern.compile("([A-Za-z]{4,6})|(([A-Za-z]{4,6})\\s([A-Za-z0-9\\s\\'=]*))");
	Command command = null;
	Pattern pattern = Pattern
			.compile("[ ]*([A-Za-z]+)((\\s*[()A-Za-z0-9\\s\\'=><,;]+)*)");
			//.compile("([A-Za-z]{4,6})(\\w)*");
	
	while (true) {
		System.out.printf("%n>>>Insert command: %n");
		
		try {

			
			String consoleInput = input.readLine();
			Matcher matcher = pattern.matcher(consoleInput);
			if (matcher.matches()) {
				String comm = matcher.group(1).toLowerCase();
				
				if (!commands.containsKey(comm)){
					System.out.println("Wrong Input! Type \"help\" for list of commands.");
				}else{
				command = commands.get(comm);					
				command.setArguments(matcher.group(2));
				command.execute();}
				
			} else
				System.out
						.println("!Wrong Input! Type \"help\" for list of commands.");
			

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}



public Map<String, Command> getCommands() {
	return commands;
}



public void setCommands(Map<String, Command> commands) {
	this.commands = commands;
}
	
	
}
