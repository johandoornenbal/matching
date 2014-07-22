package nl.xtalus.dom.user;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.value.Blob;

public class Users extends AbstractFactoryAndRepository {

    @ActionSemantics(Of.SAFE)
    public List<User> allUsers() {
        return allInstances(User.class);
    }

    public User newUser(
            @Named("Voornaam") String firstName,
            @Optional @Named("tussen") String middleName,
            @Named("Achternaam") String lastName,
            @Named("Geboortedatum") LocalDate dateOfBirth,
            @Named("Geslacht") Sex sex,
            @Optional @Named("Foto") Blob picture
            ) {
        // create transient object (not persistent)
        User user = newTransientInstance(User.class);
        // set obect values
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setSex(sex);
        user.setPicture(picture);
        user.setJoinedOn(clockService.nowAsLocalDateTime());
        // save object to database
        persist(user);
        return user;
    }

    @Inject
    private ClockService clockService;

}
