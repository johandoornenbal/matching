package info.matchingservice.dom.Api;

import java.util.List;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.Tags;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.ParameterLayout;

@DomainService()
@DomainServiceLayout()
public class api extends AbstractFactoryAndRepository {
	
	public List<Person> thisIsYou(){
		return persons.thisIsYou();
	}
	
	public List<Person> findPersons(
			@ParameterLayout(named="lastName")
			final String lastName){
		return persons.findPersons(lastName);
	}
	
	public List<Person> findPersonContains(
			@ParameterLayout(named="lastName")
			final String lastName){
		return persons.findPersonsContains(lastName);
	}
	
	public List<Tag> findTagsUsedMoreThanThreshold(
    		@ParameterLayout(named = "tagDescription")
    		final String tagDescription,
    		@ParameterLayout(named = "tagCategoryDescription")
    		final String tagCategoryDescription,
    		@ParameterLayout(named = "threshold")
    		final Integer threshold){
		return tags.findTagsUsedMoreThanThreshold(tagDescription, tagCategoryDescription, threshold);
	}
	
	
	
	@Inject
	Persons persons;
	
	@Inject
	Tags tags;

}
