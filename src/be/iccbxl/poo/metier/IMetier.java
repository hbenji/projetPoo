package be.iccbxl.poo.metier;

import java.util.List;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;

public interface IMetier {
	//Data access needs
		public List<Person> getMembres();
		public void register(Person p);
		public void update(Person p);
		public void unregister(Person p);
		
		//Business Logic
		public int computeRemainingDays(Book b);
		
		//...
}
