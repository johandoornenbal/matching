package info.matchingservice.dom.Api;

import java.util.List;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;

@DomainService()
@DomainServiceLayout()
@Hidden
public class api extends AbstractFactoryAndRepository {
	
	public List<Person> AllPersons(){
		return persons.allPersons();
	}
	
	@Inject
	Persons persons;

}
