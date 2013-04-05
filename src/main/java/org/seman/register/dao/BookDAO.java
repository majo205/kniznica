package org.seman.register.dao;

import org.hibernate.HibernateException;
import org.seman.register.dbo.Book;

public interface BookDAO {

	public Book[] findBook() throws HibernateException;
	public int updateBook() throws HibernateException;
	public int deleteBook() throws HibernateException;
	public boolean createBook() throws HibernateException;
	
	
	
}
