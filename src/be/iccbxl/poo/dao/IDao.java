package be.iccbxl.poo.dao;

import java.util.List;
import java.util.UUID;

import be.iccbxl.poo.entities.Person;

public interface IDao {
	//Read operations
	public List<Person> findAll();
	public Person findById(UUID id);
	public List<Person> findBy(String property, String value);
	
	//Delete operation
	public void delete(UUID id);
	public void delete(Person p);
	
	//Update operation
	public void update(Person p);
	
	//Create operation
	public void save(Person p);
}
