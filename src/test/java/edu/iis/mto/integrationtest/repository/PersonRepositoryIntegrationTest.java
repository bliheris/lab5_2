package edu.iis.mto.integrationtest.repository;

import static edu.iis.mto.integrationtest.repository.PersonBuilder.person;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import edu.iis.mto.integrationtest.model.Person;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCanAccessDbAndGetTestData() {
        long count = personRepository.count();
        List<Person> foundTestPersons = personRepository.findAll();
        assertEquals(count, foundTestPersons.size());
    }

    @Test
    public void testSaveNewPersonAndCheckIsPersisted() {
        long count = personRepository.count();
        personRepository.save(a(person().withId(count + 1)
                .withFirstName("Roberto").withLastName("Mancini")));
        assertEquals(count + 1, personRepository.count());
        assertEquals("Mancini", personRepository.findOne(count + 1)
                .getLastName());
    }

    @Test
    public void findPersonByFirstNameTest() {
        long count = personRepository.count();
        long bob1Id = count + 1;
        long bob2Id = count + 2;
        personRepository.save(a(person().withId(bob1Id)
                .withFirstName("Bob").withLastName("Smith")));
        personRepository.save(a(person().withId(bob2Id)
                .withFirstName("Bob").withLastName("Potter")));

        List<Person> bobs = personRepository.findByFirstNameLike("Bob");
        assertEquals(2, bobs.size());
    }

    @Test
    public void deleteAllInBatchTest() {
        personRepository.deleteAllInBatch();
        assertEquals(0, personRepository.count());
    }

    private Person a(PersonBuilder builder) {
        return builder.build();
    }

}
