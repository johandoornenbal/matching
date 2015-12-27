package info.matchingservice.webapp.custom_rest.viewmodels.interfaces;

import info.matchingservice.webapp.custom_rest.viewmodels.Company;
import info.matchingservice.webapp.custom_rest.viewmodels.Interest;
import info.matchingservice.webapp.custom_rest.viewmodels.Quality;

import java.util.List;

/**
 * Created by jonathan on 27-12-15.
 */
public interface API {



    List<Quality> getQualitiesByPerson();

    Company getCompanyByPerson();

    List<Interest> getInterestsByPerson();






}
