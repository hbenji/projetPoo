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
	
	//Book
	public List<Book> getBooks();
	public void register(Book b);
	public void update(Book b);
	public void unregister(Book b);
	
	//Business Logic
	public int computeRemainingDays(Book b);
		
	//...
}
