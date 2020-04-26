package be.iccbxl.poo.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public class DaoFile implements IDao {

	private List<Person> people;
	private List<Book> books;
	private String filename;
	
	public DaoFile() {
		people = new ArrayList<Person>();
		books = new ArrayList<Book>();
	}

	public DaoFile(String filename) {
		this.people = new ArrayList<Person>();
		this.books = new ArrayList<Book>();
		this.filename = filename;
	}
	
	public Person find(Person p) {
		return findByIdPerson(p.getId());		
	}

	public List<Person> findAllPeople() {
		//TODO écrire le code de lecture du fichier
		Person bob = new Person(UUID.fromString("a7aa0ae7-9ce3-44bc-a72a-894edb9a4653"), "Bob Smith");
		Person julie = new Person(UUID.fromString("02417998-c4fb-41d8-96b3-6b7d0db7aa97"), "Julie Dern");
		Person mike = new Person(UUID.fromString("3a50a4dc-f518-427b-b24e-078e252a6e4a"), "Mike Dougs");
		
		this.people.add(bob);
		this.people.add(julie);
		this.people.add(mike);
		
		return people;
	}

	public Person findByIdPerson(UUID id) {
		findAllPeople();
		
		Person p = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			p = it.next();
			
			if(p.getId().equals(id)) {
				return p;
			}
		}
		
		return null;
	}

	public List<Person> findByPerson(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletePerson(UUID id) {
		findAllPeople();
		
		Person p = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			p = it.next();
			
			if(p.getId().equals(id)) {
				it.remove();
			}
		}
		
		//TODO écrire le code de l'écriture du fichier
	}

	public void delete(Person p) {
		deletePerson(p.getId());
	}

	public void update(Person p) {
		findAllPeople();
		
		boolean trouve = false;
		Person currentPerson = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			currentPerson = it.next();
			
			if(currentPerson.getId().equals(p.getId())) {
				it.remove();
				trouve = true;
			}
		}
		
		if(trouve) {
			people.add(p);
		}
		
		//TODO écrire le code de l'écriture du fichier
	}

	public void save(Person p) {
		findAllPeople();
		
		boolean trouve = false;
		Person currentPerson = null;
		Iterator<Person> it = people.iterator();
		
		while(it.hasNext()) {
			currentPerson = it.next();
			
			if(currentPerson.getId().equals(p.getId())) {
				update(p);
				return;
			}
		}
		
		if(!trouve) {
			people.add(p);
		}
		
		//TODO écrire le code de l'écriture du fichier
	}
	
	public Book find(Book b) {
		return findByIdBook(b.getId());
	}

	public List<Book> findAllBooks() {
		//TODO écrire le code de lecture du fichier
		Book bob = new Book(UUID.fromString("a7aa0ae7-9ce3-44bc-a72a-894edb9a4653"), "Bob Smith", "zrz", (short)5, "apou");
		Book julie = new Book(UUID.fromString("02417998-c4fb-41d8-96b3-6b7d0db7aa97"), "Julie Dern", "zrz", (short)5, "apou");
		Book mike = new Book(UUID.fromString("3a50a4dc-f518-427b-b24e-078e252a6e4a"), "Mike Dougs", "zrz", (short)5, "apou");
			
		this.books.add(bob);
		this.books.add(julie);
		this.books.add(mike);
				
		return this.books;
	}

	public Book findByIdBook(UUID id) {
		findAllBooks();
		
		Book b = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			b = it.next();
			
			if(b.getId().equals(id)) {
				return b;
			}
		}
		
		return null;
	}

	public List<Book> findByBook(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteBook(UUID id) {
		findAllBooks();
		
		Book b = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			b = it.next();
			
			if(b.getId().equals(id)) {
				it.remove();
			}
		}
		
		//TODO écrire le code de l'écriture du fichier
		
	}

	@Override
	public void delete(Book b) {
		deleteBook(b.getId());
	}

	@Override
	public void update(Book b) {
		findAllBooks();
		
		boolean trouve = false;
		Book currentPerson = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			currentPerson = it.next();
			
			if(currentPerson.getId().equals(b.getId())) {
				it.remove();
				trouve = true;
			}
		}
		
		if(trouve) {
			books.add(b);
		}
		
		//TODO écrire le code de l'écriture du fichier
		
	}

	@Override
	public void save(Book b) {
		findAllBooks();
		
		boolean trouve = false;
		Book currentPerson = null;
		Iterator<Book> it = books.iterator();
		
		while(it.hasNext()) {
			currentPerson = it.next();
			
			if(currentPerson.getId().equals(b.getId())) {
				update(b);
				return;
			}
		}
		
		if(!trouve) {
			books.add(b);
		}
		
		//TODO écrire le code de l'écriture du fichier
		
	}

}
