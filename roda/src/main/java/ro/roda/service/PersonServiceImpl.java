package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Person;


@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	public long countAllPeople() {
        return Person.countPeople();
    }

	public void deletePerson(Person person) {
        person.remove();
    }

	public Person findPerson(Integer id) {
        return Person.findPerson(id);
    }

	public List<Person> findAllPeople() {
        return Person.findAllPeople();
    }

	public List<Person> findPersonEntries(int firstResult, int maxResults) {
        return Person.findPersonEntries(firstResult, maxResults);
    }

	public void savePerson(Person person) {
        person.persist();
    }

	public Person updatePerson(Person person) {
        return person.merge();
    }
}
