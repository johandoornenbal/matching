package nl.yodo.fixture.party;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.yodo.dom.TrustLevel;
import nl.yodo.dom.Party.YodoParty;
import nl.yodo.dom.Party.YodoPerson;
import nl.yodo.dom.Party.YodoPersonalContact;
import nl.yodo.dom.Party.YodoPersonalContacts;
import nl.yodo.dom.Party.YodoPersons;

public abstract class YodoPersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected YodoParty createYodoPerson (
            String uniquePartyId,
            String firstName,
            String middleName,
            String lastName,
            String user,
            String connectedTo1,
            TrustLevel trustLevel1,
            String connectedTo2,
            TrustLevel trustLevel2,            
            ExecutionContext executionContext
            ) {
        YodoParty newPerson = yodoPersons.newPerson(uniquePartyId, firstName, middleName, lastName, user);
        createPersonalContact(connectedTo1, trustLevel1, newPerson, executionContext);
        // TODO: onderstaande test werkt niet
        if (connectedTo2!=null || connectedTo2!="") {
            createPersonalContact(connectedTo2, trustLevel2, newPerson, executionContext);
        }
        return executionContext.add(this, newPerson);
    }
    
    protected YodoPersonalContact createPersonalContact(
            final String ownedBy,
            final TrustLevel trustLevel,
            final YodoParty newPerson,
            final ExecutionContext executionContext
            ) {
        YodoPersonalContact newContact = yodoPersonalContacts.newPersonalContact((YodoPerson) newPerson, ownedBy, trustLevel);
        return executionContext.add(this, newContact);
    }
    
    //region > injected services
    @javax.inject.Inject
    private YodoPersons yodoPersons;
    
    @javax.inject.Inject
    private YodoPersonalContacts yodoPersonalContacts;

}
