package org.seman.register.dao;

public class BookDAOFactory {
	
	public static BookDAO create() {
	
		return ( BookDAOImpl.createBookDAO());
		
		}

}
