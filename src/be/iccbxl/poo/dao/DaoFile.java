package be.iccbxl.poo.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import be.iccbxl.poo.entities.Person;

public class DaoFile implements IDao {

	private List<Person> people;
	private String filename;
	
	public DaoFile() {
		people = new ArrayList<Person>();
	}

	public DaoFile(String filename) {
		this.people = new ArrayList<Person>();
		this.filename = filename;
	}

	public List<Person> findAll() {
		//TODO écrire le code de lecture du fichier
		Person bob = new Person(UUID.fromString("a7aa0ae7-9ce3-44bc-a72a-894edb9a4653"), "Bob Smith");
		Person julie = new Person(UUID.fromString("02417998-c4fb-41d8-96b3-6b7d0db7aa97"), "Julie Dern");
		Person mike = new Person(UUID.fromString("3a50a4dc-f518-427b-b24e-078e252a6e4a"), "Mike Dougs");
		
		this.people.add(bob);
		this.people.add(julie);
		this.people.add(mike);
		
		return people;
	}

	public Person findById(UUID id) {
		findAll();
		
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

	public List<Person> findBy(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(UUID id) {
		findAll();
		
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
		// TODO Auto-generated method stub

	}

	public void update(Person p) {
		findAll();
		
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
		findAll();
		
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

}
