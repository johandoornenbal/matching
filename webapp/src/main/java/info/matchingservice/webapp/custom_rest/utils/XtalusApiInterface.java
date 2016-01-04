package info.matchingservice.webapp.custom_rest.utils;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.webapp.custom_rest.viewmodels.Interest;
import info.matchingservice.webapp.custom_rest.viewmodels.Quality;

import java.util.List;

/**
 * Created by jonathan on 4-1-16.
 */
public interface XtalusApiInterface {

    public List<Quality> getQualities(Person student);
    public List<Interest> getInterests(Person student);




}
