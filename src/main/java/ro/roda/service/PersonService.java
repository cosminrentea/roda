package ro.roda.service;

import java.util.List;

import ro.roda.domain.Person;

public interface PersonService {

	public abstract long countAllPeople();

	public abstract void deletePerson(Person person);

	public abstract Person findPerson(Integer id);

	public abstract List<Person> findAllPeople();

	public abstract List<Person> findPersonEntries(int firstResult, int maxResults);

	public abstract void savePerson(Person person);

	public abstract Person updatePerson(Person person);

}
