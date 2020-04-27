package be.iccbxl.poo.metier;

import java.util.List;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public interface IMetier {
	//Data access needs
	
	//Person
	public List<Person> getMembres();
	public void register(Person p);
	public void update(Person p);
	public void unregister(Person p);
	public void serializableBooks();
	public List<Person> deserializablePeople();
	
	//Book
	public List<Book> getBooks();
	public void register(Book b);
	public void update(Book b);
	public void unregister(Book b);
	public void serializablePeople();
	public List<Book> deserializableBooks();
	
	//Business Logic
	public int computeRemainingDays(Book b);
	
}
