package nl.yodo.fixture.party;

import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScript.ExecutionContext;

import nl.yodo.dom.Party.YodoPerson;
import nl.yodo.dom.Party.YodoPersonalContact;
import nl.yodo.dom.Party.YodoPersonalContacts;
import nl.yodo.dom.Party.YodoPersons;
import nl.yodo.dom.pruts.Secobject;
import nl.yodo.dom.pruts.Secobjects;
import nl.yodo.fixture.Util;

public class YodoPersonsCreate extends FixtureScript {
    //region > constructor
    private final String user;

    /**
     * @param user - if null then executes for the current user or will use any {@link #run(String) parameters} provided when run.
     */
    public YodoPersonsCreate(final String user) {
        super(null, Util.localNameFor("create", user));
        this.user = user;
    }
    //endregion
    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = Util.coalesce(user, executionContext.getParameters(), getContainer().getUser().getName());

        // this fixture
        createYodoPerson("111", "Johan","", "Doornenbal", ownedBy, executionContext);
        createYodoPerson("123", "T.", "van der", "Test", "test1", executionContext);
        createYodoPerson("124", "Tessa", "de", "Tester2", "test2", executionContext);
        createYodoPerson("125", "Teo","", "Test3", "test3", executionContext);
        
    }
    
    private YodoPerson createYodoPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional @Named("Tussen") String middleName,
            final @Named("Achternaam") String lastName,
            final String user,
            final ExecutionContext executionContext) {
        YodoPerson newPerson = yodoPersons.newPerson(uniquePartyId, firstName, middleName, lastName, user);
        //user 'test4' wordt gebruikt om te contacten naar de hier aangemaakte personen
        createPersonalContact("test4", newPerson, executionContext);
        createSecObject(newPerson, executionContext);
        return executionContext.add(this, newPerson);
    }
    
    protected YodoPersonalContact createPersonalContact(
            final String ownedBy,
            final YodoPerson newPerson,
            final ExecutionContext executionContext
            ) {
        YodoPersonalContact newContact = yodoPersonalContacts.newPersonalContact(newPerson, ownedBy);
        return executionContext.add(this, newContact);
    }
    
    protected Secobject createSecObject(
            final YodoPerson newPerson,
            final ExecutionContext executionContext) {
        Secobject secobj = secObjects.newSecobject("Van " + newPerson.getFirstName(), newPerson.getOwnedBy());
        return executionContext.add(this, secobj);
    }

    //region > injected services
    @javax.inject.Inject
    private YodoPersons yodoPersons;
    
    @javax.inject.Inject
    private YodoPersonalContacts yodoPersonalContacts;
    
    @javax.inject.Inject
    private Secobjects secObjects;
}
