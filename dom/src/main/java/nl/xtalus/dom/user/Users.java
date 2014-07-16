package nl.xtalus.dom.user;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.services.clock.ClockService;

public class Users extends AbstractFactoryAndRepository {

    @ActionSemantics(Of.SAFE)
    public List<User> allUsers() {
        return allInstances(User.class);
    }

    public User newUser(
            @Named("Naam") String name,
            @Named("Geboortedatum") LocalDate dateOfBirth
            ) {
        // create transient object (not persistent)
        User user = newTransientInstance(User.class);
        // set obect values
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);
        user.setJoinedOn(clockService.nowAsLocalDateTime());
        // save object to database
        persist(user);
        return user;
    }

    @Inject
    private ClockService clockService;

}
