package org.seman.register.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.seman.register.dbo.Book;

public interface BookDAO {

	public List<Book> findBook(List<List<String>> commandList)
			throws HibernateException;

	public int updateBook(List<List<String>> commandString)
			throws HibernateException;

	public boolean createBook(List<List<String>> commandList)
			throws HibernateException;

	public int deleteBook(List<Book> listKnih) throws HibernateException;

	public void pushObjectsToDB();
}
