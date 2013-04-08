package org.seman.register.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.FirebirdDialect;
import org.seman.register.dbo.Book;
import org.seman.register.dbo.DatabaseHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class BookDAOImpl implements BookDAO {

	private SessionFactory sessionFactory = new Configuration().configure()
			.buildSessionFactory();
	private Session dbSession;

	private static BookDAOImpl bookDAO = null;

	private List<Object> objectsForDB = new ArrayList<Object>();

	private BookDAOImpl() {
		if (bookDAO != null) {
			throw new IllegalStateException("Already initialized!");
		}
	}

	protected static BookDAOImpl createBookDAO() {
		if (bookDAO == null) {

			BeanFactory beanFactory = new XmlBeanFactory(
					new FileSystemResource("src/main/resources/Spring.xml"));
			bookDAO = (BookDAOImpl) beanFactory.getBean("BookDAO");
			return bookDAO;
		} else
			return bookDAO;

	}

	@Override
	public List<Book> findBook(List<List<String>> commandList)
			throws HibernateException {
		dbSession = sessionFactory.openSession();
		dbSession.beginTransaction();

		Criteria criteria = dbSession.createCriteria(Book.class);

		for (int i = 0; i < commandList.size(); i++) {

			String columnName = commandList.get(i).get(0);
			String operator = commandList.get(i).get(1);
			String columnValue = commandList.get(i).get(2);

			System.out.println("[" + columnName + "]  [" + operator + "]  ["
					+ columnValue + "]");

			if ((columnName == "id")
			// || (columnName=="rokVydania")
			) {
				System.out.println("prezeram hodnoty pre ciselne stlpce");
				switch (operator) {

				case ">":
					criteria.add(Restrictions.gt(columnName,
							Integer.parseInt(columnValue)));
					break;
				case "<":
					criteria.add(Restrictions.lt(columnName,
							Integer.parseInt(columnValue)));
					break;
				case ">=":
				case "=>":
					criteria.add(Restrictions.ge(columnName,
							Integer.parseInt(columnValue)));
					break;
				case "<=":
				case "=<":
					criteria.add(Restrictions.le(columnName,
							Integer.parseInt(columnValue)));
					break;
				case "=":
					criteria.add(Restrictions.eq(columnName,
							Integer.parseInt(columnValue)));
					break;
				default:
					System.out.println("Wrong operator!");
					break;

				}
			} else {
				System.out.println("prezeram hodnoty pre neciselne stlpce");
				switch (operator) {

				case ">":
					criteria.add(Restrictions.gt(columnName, columnValue));
					break;
				case "<":
					criteria.add(Restrictions.lt(columnName, columnValue));
					break;
				case ">=":
				case "=>":
					criteria.add(Restrictions.ge(columnName, columnValue));
					break;
				case "<=":
				case "=<":
					criteria.add(Restrictions.le(columnName, columnValue));
					break;
				case "=":
					criteria.add(Restrictions.eq(columnName, columnValue));
					break;
				default:
					System.out.println("Wrong operator!");
					break;

				}
			}
		}

		List<Book> result = criteria.list();
		dbSession.getTransaction().commit();
		dbSession.close();
		return result;
	}

	@Override
	public int updateBook(List<List<String>> commandString)
			throws HibernateException {
		List<Book> itemsForUpdaeList;
		int zmenenychRiadkov = 0;
		String columnName, newValue;

		System.out.println("pocet poli " + commandString.size());
		System.out.println(" pocet vnutornych " + commandString.get(1).size());
		columnName = commandString.get(0).get(0);
		newValue = commandString.get(0).get(1);
		commandString.remove(0);

		itemsForUpdaeList = (List<Book>) this.findBook(commandString);

		Session updateSession = sessionFactory.openSession();
		updateSession.beginTransaction();

		for (Book object : itemsForUpdaeList) {

			if (columnName.toUpperCase().startsWith("AUTOR")) {
				object.setAutor(newValue);
				updateSession.update(object);

			} else if (columnName.toUpperCase().startsWith("NAZOV")) {
				object.setNazov(newValue);
				updateSession.update(object);

			} else if (columnName.toUpperCase().startsWith("ROKVYDANIA")) {
				object.setRokVydania(Integer.parseInt(newValue));
				updateSession.update(object);

			} else if (columnName.toUpperCase().startsWith("VYDAVATELSTVO")) {
				object.setVydavatelstvo(newValue);
				updateSession.update(object);

			} else if (columnName.toUpperCase().startsWith("ZANER")) {
				object.setZaner(newValue);
				updateSession.update(object);

			}

			zmenenychRiadkov++;

		}
		updateSession.getTransaction().commit();
		updateSession.close();
		return zmenenychRiadkov;
	}

	@Override
	public boolean createBook(List<List<String>> commandList)
			throws HibernateException {
		Session insertsSession = sessionFactory.openSession();
		insertsSession.beginTransaction();

		String autor = null;
		int rokVydania = 0;
		String nazov = null;
		String vydavatelstvo = null;
		String zaner = null;

		for (int i = 0; i < commandList.size(); i++) {

			String columnName = commandList.get(i).get(0);
			String columnValue = commandList.get(i).get(1);

			if (columnValue.equals("null"))
				columnValue = null;

			switch (columnName) {

			case "autor":
				autor = columnValue;
				break;
			case "rokvydania":
				if (columnValue == null)
					columnValue = "0";
				rokVydania = Integer.parseInt(columnValue);
				break;
			case "nazov":
				nazov = columnValue;
				break;
			case "vydavatelstvo":
				vydavatelstvo = columnValue;
				break;
			case "zaner":
				zaner = columnValue;
				break;

			}
		}

		Book insertBook = new Book(autor, rokVydania, nazov, vydavatelstvo,
				zaner, new Timestamp(new Date().getTime()));
		insertsSession.save(insertBook);

		insertsSession.getTransaction().commit();
		insertsSession.close();
		return true;
	}

	@Override
	public int deleteBook(List<Book> listKnih) throws HibernateException {
		int zmazanychRiadkov = 0;

		Session deleteSession = sessionFactory.openSession();

		deleteSession.beginTransaction();
		for (Book kniha : listKnih) {
			deleteSession.delete(kniha);
			zmazanychRiadkov++;
		}
		deleteSession.getTransaction().commit();
		deleteSession.close();

		return zmazanychRiadkov;

	}

	// public void setObjectsForDB(Object object) {
	// if (object instanceof List) {
	// objectsForDB.addAll((List) object);
	// } else
	// objectsForDB.add(object);
	// }

	public List<Object> getObjectsForDB() {
		return objectsForDB;

	}

	public void initializeObjectsForDB() {
		Date date = new Date();

		Object book1 = new Book("J.R.R. Tolkien", 1968, "LOTR", "ikar",
				"fantasy", new Timestamp(date.getTime()));
		Object book2 = new Book("JK Rowling", 1999, "HP", "slovart",
				"rozpravka", new Timestamp(date.getTime()));
		Object book3 = new Book();

		objectsForDB.add(book1);
		objectsForDB.add(book2);
		objectsForDB.add(book3);

	}

	public void insertCollectionToTable(List<?> objects) {

		try {
			dbSession = sessionFactory.openSession();
			dbSession.beginTransaction();

			for (Object object : objects) {

				dbSession.save(object);
			}

			dbSession.getTransaction().commit();

		} catch (HibernateException e) {
			dbSession.getTransaction().rollback();
			System.out.println("nastala chyba pocas transakcie");
			System.err.println(e);
			e.printStackTrace();
		} finally {
			dbSession.close();
		}

	}

	public void pushObjectsToDB() {
		initializeObjectsForDB();
		insertCollectionToTable(objectsForDB);

	}

	public void closeBookDAO() {
		this.dbSession.clear();
		this.dbSession.close();
		this.sessionFactory.close();
	}

}
