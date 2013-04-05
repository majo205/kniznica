package org.seman.register.commands;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seman.register.dbo.DatabaseHandler;
import org.seman.register.dbo.Book;

public class Read implements Command {

	private List<List<String>> commandList;
	private List<Book> resultList = new ArrayList<>();

	@Override
	public void execute() {

		try {
			resultList = (List<Book>) DatabaseHandler.getDBHandler()
					.selectFromTable(commandList);
			Field[] fields = Book.class.getDeclaredFields();
			System.out.println("resultov " + resultList.size()
					+ " pocet fieldov" + fields.length);
			for (int i = 0; i < fields.length; i++) {

				System.out.printf("%25s", fields[i].getName() + "|");

				for (Book selectedItem : resultList) {

					System.out.printf("%25s", fields[i].get(selectedItem));

					// System.out.printf("%25s%n", "" +
					// selectedItem.getIdKnihy());
					// System.out.printf("%25s%n", "" +
					// selectedItem.getAutor());
					// System.out.printf("%25s%n", "" +
					// selectedItem.getNazov());
					// System.out.printf("%25s%n",
					// "" + selectedItem.getRokVydania());
					// System.out.printf("%25s%n",
					// "" + selectedItem.getVydavatelstvo());
					// System.out.printf("%25s%n", "" +
					// selectedItem.getZaner());
					// System.out.printf("%25s%n",
					// "" + selectedItem.getTimestamp());

				}
				System.out.println();
			}
		} catch (IOException | IllegalArgumentException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String getHelpDescription() {

		return "read criteriaColumnName operator criteriaValue, ...\n"
				+ "criteriaColumnName : id, autor, nazov, rokvydania, vydavatel, zaner\n"
				+ "operator : <, >, <=, >=, =\n";
	}

	@Override
	public void setArguments(String command) {
		this.commandList = parseCommand(command);

	}

	/**
	 * pomocou regular expresions rozdeli podretazec prikazu na hodnoty
	 * spracovatelne prikazom Read
	 * 
	 * @param command
	 *            - podretazec prikazu (bez klucoveho slova oznacujuceho prikaz)
	 * @return zoznam trojic retazcov oznacujucich nazov stlpca s hladanou
	 *         hodnotou, relacneho operatora a hodnoty hladanje v stlpci
	 */
	private List<List<String>> parseCommand(String command) {

		System.out.println(" command v reade :" + command);

		List<List<String>> listOfValues = new ArrayList<>();

		Pattern readPattern = Pattern
				.compile("([ ]*([A-Za-z0-9]+)[ ]*([=><]{1,2})[ ]*([A-Za-z0-9]+))([ ]*[,;])+");
		Matcher matcher = readPattern.matcher(command);

		while (matcher.find()) {
			List<String> columnValues = new ArrayList<>();
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

	public List<Book> getResultList() {
		return resultList;
	}

	public List<Book> fillAndGetResultList(String commandString) {
		this.setArguments(commandString);
		this.execute();

		return this.resultList;
	}

	// public static void main(String[] args) {
	//
	// // Pattern pattern = Pattern.compile("([a-z0-9]*)\bword\b([a-z0-9])*");
	// Pattern pattern =
	// Pattern.compile("([\\( ]*[A-Za-z0-9= ]+[\\) ]*)(where[A-Za-z0-9=>< ,;]*)");
	//
	// Matcher matcher = pattern.matcher("nazov = 10  where lol = 5");
	// if (matcher.matches())
	// System.out.println(true);
	// else
	// System.out.println(false);
	//
	// // System.out.println("group 1 " + matcher.group(1));
	// // System.out.println("group 2 " + matcher.group(2));
	// // System.out.println("group 3 " + matcher.group(3));
	// // System.out.println("group 4 " + matcher.group(4));
	//
	// //
	// // List<String> list = readParser("(nazov) or (kniha)");
	// // System.out.println("parser presiel "+list.size());
	// // int i=1;
	// // for (List<String> list2 : list) {
	// // System.out.print(i+". list :");
	// // for (String string : list2) {
	// // System.out.print("["+string+"]");
	// //
	// // }
	// // i++;
	// // System.out.println();
	// // }
	//
	// // DatabaseHandler dbh = new DatabaseHandler();
	// // dbh.initializeObjectsForDB();
	// // dbh.pushObjectsToDB();
	// //
	// // List<Kniha> allBooks = dbh.selectAll();
	// //
	// //
	// //
	// // Command read = new Read();
	// // read.setArguments("nazov = LOTR; ");
	// //
	// //
	// // System.out.println(commandList.toString());
	// // for (List<String> list : commandList) {
	// //
	// // for (String string : list) {
	// // System.out.print("["+string+"]");
	// // }
	// // System.out.println();
	// // }
	// //
	// // read.execute();
	//
	// }
	//
	// public List<Kniha> getResultList() {
	// return resultList;
	// }
	//
	// public static List<String> readParser(String command) {
	// List<List<String>> parsedCommands= new ArrayList<>();
	// List<String> subCommands =new ArrayList<>();
	//
	// Pattern outerPattern = Pattern.compile("\\({1}(.+)\\){1}");
	// Pattern orPattern = Pattern
	// .compile("\\({1}(.+)\\){1}[ ]+(or)[ ]+\\({1}(.+)\\){1}");
	// Pattern innerPattern = Pattern
	// .compile("\\({0,1}[ ]*([A-Za-z0-9_]+)[ ]*([<>=]){1,2}[ ]*([A-Za-z0-9_]+)[ ]*[,]*\\){0,1}");
	//
	// //.compile("\\({1}[ ]*()(or)()\\){1}");
	//
	// Pattern pattern = outerPattern;
	// Matcher matcher;
	//
	// for (int i=0;i<3;i++){
	//
	// if(i==0){
	// pattern = outerPattern;
	// matcher= pattern.matcher(command);
	// if(matcher.matches()){
	// while (matcher.find()){
	// for(int j=1; j<=matcher.groupCount();j++){
	// System.out.println("outergroup ["+matcher.group(j)+"]");
	//
	// //parsedCommands.addAll(readParser(matcher.group(j)));
	// }
	// }
	// }
	// }
	// if(i==1){
	// pattern = orPattern;
	// matcher= pattern.matcher(command);
	// if(matcher.matches()){
	// while (matcher.find()){
	// for(int j=1; j<=matcher.groupCount();j++){
	// System.out.println("outergroup ["+matcher.group(j)+"]");
	// subCommands.add(matcher.group(2));
	// //parsedCommands.addAll(readParser(matcher.group(j)));
	// }
	// }
	// }
	//
	// }
	// if(i==2){
	// pattern = innerPattern;
	// matcher= pattern.matcher(command);
	// if(matcher.matches()){
	// while (matcher.find()){
	// for(int j=1; j<=matcher.groupCount();j++){
	// System.out.println("outergroup ["+matcher.group(j)+"]");
	// //parsedCommands.addAll(readParser(matcher.group(j)));
	// }
	// }
	// }
	//
	// }
	//
	//
	// }

	// for (int patt=0; patt<3;patt++){
	// System.out.println("som v cykle "+patt);
	// Matcher matcher = pattern.matcher(command);
	//
	// if (matcher.matches()){
	//
	// while (matcher.find()){
	// for(int i=1; i<=matcher.groupCount();i++){
	// System.out.println("outergroup ["+matcher.group(i)+"]");
	// parsedCommands.addAll(readParser(matcher.group(i)));}
	// }
	// }else if(patt==2){
	// System.out.println("menin ma pattern 2");
	// pattern=orPattern;
	// }
	// else if(patt==3){
	// System.out.println("menin ma pattern 3");
	// pattern=innerPattern;
	// }
	//
	// }
	//
	//
	// return subCommands;
	// }
	//
}
