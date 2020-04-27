package be.iccbxl.poo.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public class DaoFile implements IDao {

	private List<Person> people;
	private List<Book> books;
	private String filenamePeopleCsv;
	private String filenamePeopleXml;
	private String filenameBooksCsv;
	private String filenameBooksXml;
	
	public DaoFile() {
		people = new ArrayList<Person>();
		books = new ArrayList<Book>();
	}

	public DaoFile(String filenamePeopleCsv, String filenamePeopleXml, String filenameBooksCsv, String filenameBooksXml) {
		this.people = new ArrayList<Person>();
		this.books = new ArrayList<Book>();
		this.filenamePeopleCsv = filenamePeopleCsv;
		this.filenamePeopleXml = filenamePeopleXml;
		this.filenameBooksCsv = filenameBooksCsv;
		this.filenameBooksXml = filenameBooksXml;
	}
	
	public Person find(Person p) {
		return findByIdPerson(p.getId());		
	}
	
	public List<Person> findAllPeopleCsv(){
		//int cpt = 0;
		
		File f = new File(filenamePeopleCsv);
		
		if(f.exists()) {
			FileReader fr = null;
			
			try {
				try {
					fr = new FileReader(f);
					Person p;
					String id;
					String name;
					String nbMaxBooks;
					String registrationDate;
					
					Iterable<CSVRecord> records = CSVFormat.EXCEL
							.withSkipHeaderRecord(true)
							.withHeader("id","name","maxBooks","registrationDate")
							.withDelimiter(';').parse(fr);
					
					for(CSVRecord record : records) {
						id = record.get("id");
						name = record.get("name");
						nbMaxBooks = record.get("maxBooks");
						registrationDate = record.get("registrationDate");
						
						p = new Person(UUID.fromString(id), name);
						p.setMaxBooks(Byte.parseByte(nbMaxBooks));
						p.setRegistrationDate(LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd-MM-yy")));
						
						//ajouter cet Person dans people
						this.people.add(p);
						//cpt++;
					}
				} finally {
					fr.close();
				}
			} catch (IOException e) {
				
			}
			
			System.out.println(people);
		} 
		
		return this.people;
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> findAllPeopleXml(){
				
		File f = new File(this.filenamePeopleXml);
		
		if(f.exists()) {
			FileReader fr = null;
			
			try {
				try {
					fr = new FileReader(f);
										
					XStream xs = new XStream(new DomDriver());
					
					//Configuration du parser XML
					xs.alias("person", Person.class);
					
					//Lecture
					this.people = (ArrayList<Person>) xs.fromXML(fr);					
				} finally {
					fr.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println(people);
		} 
		
		return this.people;
	}

	public List<Person> findAllPeople(){
		return this.findAllPeopleXml();		
	}
	
	/*public List<Person> findAllPeople() {
		//TODO écrire le code de lecture du fichier
		Person bob = new Person(UUID.fromString("a7aa0ae7-9ce3-44bc-a72a-894edb9a4653"), "Bob Smith");
		Person julie = new Person(UUID.fromString("02417998-c4fb-41d8-96b3-6b7d0db7aa97"), "Julie Dern");
		Person mike = new Person(UUID.fromString("3a50a4dc-f518-427b-b24e-078e252a6e4a"), "Mike Dougs");
		
		this.people.add(bob);
		this.people.add(julie);
		this.people.add(mike);
		
		return people;
	}*/

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
		
		//Sauvegarde dans les fichiers
		this.savePeopleXml();
		this.savePeopleCsv();
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
		
		//Sauvegarde dans les fichiers
		this.savePeopleXml();
		this.savePeopleCsv();
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
		
		//Sauvegarde dans les fichiers
		this.savePeopleXml();
		this.savePeopleCsv();
	}
	
	public boolean savePeopleCsv() {
		File f = new File(this.filenamePeopleCsv);
		FileWriter fw = null;
		
		try {
			try {
				if(!f.exists()) {
					f.createNewFile();
				}
				
				fw = new FileWriter(f);
				//Charset charset = Charset.forName("Cp1252");
				
				final CSVPrinter printer = CSVFormat.EXCEL.withDelimiter(';').print(fw);
				
				printer.printRecord("id","name","maxBooks","registrationDate");
				
				for(Person p : people) {
					printer.print(p.getId());
					printer.print(p.getName());
					printer.print(p.getMaxBooks());
					
					String registrationDate = p.getRegistrationDate()
							.format(DateTimeFormatter.ofPattern("dd-MM-yy")).toString();
					
					printer.print(registrationDate);
					printer.println();
				}
				
				printer.close();
				
			} finally {
				fw.close();
			}
		} catch(IOException e) {
			
		}
		
		return false;
	}
	
	public boolean savePeopleXml() {
		File f = new File(this.filenamePeopleXml);
		FileWriter fw = null;
		
		try {
			try {
				if(!f.exists()) {
					f.createNewFile();
				}
				
				fw = new FileWriter(f);
				
				XStream xs = new XStream(new DomDriver());
				
				//Configuration du parser XML
				xs.alias("person", Person.class);
				
				//Conversion en XML
				String xml = xs.toXML(people);
				
				//Sauvegarder dans un fichier texte
				fw.write(xml);
			} finally {
				fw.close();
			}
		} catch(IOException e) {
			
		}
		
		return false;
	}
	
	/*BOOKS*/
	
	public List<Book> findAllBooksCsv(){
				
		File f = new File(this.filenameBooksCsv);
		
		if(f.exists()) {
			FileReader fr = null;
			
			try {
				try {
					fr = new FileReader(f);
					Book b;
					String id;
					String title;
					String author;
					String totalPages;
					String langues;
					String rentalPrice;
					
					Iterable<CSVRecord> records = CSVFormat.EXCEL
							.withSkipHeaderRecord(true)
							.withHeader("id","title","author","totalPages", "langues", "rentalPrice")
							.withDelimiter(';').parse(fr);
					
					for(CSVRecord record : records) {
						id = record.get("id");
						title = record.get("title");
						author = record.get("author");
						totalPages = record.get("totalPages");
						langues = record.get("langues");						
						rentalPrice = record.get("rentalPrice");
						
						b = new Book(UUID.fromString(id), title, author, Short.parseShort(totalPages), langues);
						b.setRentalPrice(Double.parseDouble(rentalPrice));
												
						this.books.add(b);
						
					}
				} finally {
					fr.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println(books);
		} 
		
		return this.books;
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> findAllBooksXml(){

		File f = new File(this.filenameBooksXml);
		
		if(f.exists()) {
			FileReader fr = null;
			
			try {
				try {
					fr = new FileReader(f);
										
					XStream xs = new XStream(new DomDriver());
					
					//Configuration du parser XML
					xs.alias("book", Book.class);
					
					//Lecture
					this.books = (ArrayList<Book>) xs.fromXML(fr);					
				} finally {
					fr.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println(books);
		} 
		
		return this.books;
	}
	
	public Book find(Book b) {
		return findByIdBook(b.getId());
	}

	public List<Book> findAllBooks(){
		return this.findAllBooksXml();
	}
/*	public List<Book> findAllBooks() {
		//TODO écrire le code de lecture du fichier
		Book bob = new Book(UUID.fromString("a7aa0ae7-9ce3-44bc-a72a-894edb9a4653"), "Bob Smith", "zrz", (short)5, "apou");
		Book julie = new Book(UUID.fromString("02417998-c4fb-41d8-96b3-6b7d0db7aa97"), "Julie Dern", "zrz", (short)5, "apou");
		Book mike = new Book(UUID.fromString("3a50a4dc-f518-427b-b24e-078e252a6e4a"), "Mike Dougs", "zrz", (short)5, "apou");
			
		this.books.add(bob);
		this.books.add(julie);
		this.books.add(mike);
				
		return this.books;
	}*/

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
		
		//Sauvegarde dans les fichiers
		this.saveBooksXml();
		this.saveBooksCsv();
		
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
		
		//Sauvegarde dans les fichiers
		this.saveBooksXml();
		this.saveBooksCsv();
		
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
		
		//Sauvegarde dans les fichiers
		this.saveBooksXml();
		this.saveBooksCsv();	
	}
	
	public boolean saveBooksCsv() {
		File f = new File(this.filenameBooksCsv);
		FileWriter fw = null;
		
		try {
			try {
				if(!f.exists()) {
					f.createNewFile();
				}
				
				fw = new FileWriter(f);
				//Charset charset = Charset.forName("Cp1252");
				
				final CSVPrinter printer = CSVFormat.EXCEL.withDelimiter(';').print(fw);
				
				printer.printRecord("id","title","author","totalPages", "langues", "rentalPrice");
				
				for(Book b : books) {
					printer.print(b.getId());
					printer.print(b.getTitle());
					printer.print(b.getAuthor());					
					printer.print(b.getTotalPages());
					printer.print(b.getLanguage());
					printer.print(b.getRentalPrice());

					printer.println();
				}
				
				printer.close();
				
			} finally {
				fw.close();
			}
		} catch(IOException e) {
			
		}
		
		return false;		
	}
	
	public boolean saveBooksXml() {
		File f = new File(this.filenameBooksXml);
		FileWriter fw = null;
		
		try {
			try {
				if(!f.exists()) {
					f.createNewFile();
				}
				
				fw = new FileWriter(f);
				
				XStream xs = new XStream(new DomDriver());
				
				//Configuration du parser XML
				xs.alias("book", Book.class);
				
				//Conversion en XML
				String xml = xs.toXML(books);
				
				//Sauvegarder dans un fichier texte
				fw.write(xml);

			} finally {
				fw.close();
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}


}
