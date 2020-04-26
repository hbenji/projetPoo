package be.iccbxl.poo.metier;

import java.util.ArrayList;
import java.util.List;

import be.iccbxl.poo.dao.DaoFile;
import be.iccbxl.poo.dao.IDao;
import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;


public class Metier implements IMetier {

	private IDao dao;
	
	private List<Person> people;
	
	public Metier() {
		people = new ArrayList<Person>();
		dao = new DaoFile();
	}

	public int computeRemainingDays(Book b) {	
		int nbRemainingDays = 0;
		
		//Book book = dao.findBook(b);
		
		//Quel est la date d'emprunt?
		//quel est la durée d'emprunt?
		//Calcul du nombre de jours restants
		
		return nbRemainingDays;
	}

	public List<Person> getMembres() {
		return dao.findAll();
	}

	public void register(Person p) {
		dao.save(p);
	}

	public void remove(Person p) {
		dao.delete(p);
	}

	public void update(Person p) {
		dao.update(p);
	}

	public void unregister(Person p) {
		dao.delete(p);
	}

}
