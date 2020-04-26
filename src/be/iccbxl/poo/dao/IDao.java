package be.iccbxl.poo.dao;

import java.util.List;
import java.util.UUID;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public interface IDao {
	
	/*Person*/
	
	//Read operations
	public Person find(Person p);
	public List<Person> findAllPeople();
	public Person findByIdPerson(UUID id);
	public List<Person> findByPerson(String property, String value);	
	//Delete operation
	public void deletePerson(UUID id);
	public void delete(Person p);	
	//Update operation
	public void update(Person p);	
	//Create operation
	public void save(Person p);
	
	/*Book*/
	
	//Read operations
	public Book find(Book b);
	public List<Book> findAllBooks();
	public Book findByIdBook(UUID id);
	public List<Book> findByBook(String property, String value);		
	//Delete operation
	public void deleteBook(UUID id);
	public void delete(Book b);		
	//Update operation
	public void update(Book b);		
	//Create operation
	public void save(Book b);
	
	
}
