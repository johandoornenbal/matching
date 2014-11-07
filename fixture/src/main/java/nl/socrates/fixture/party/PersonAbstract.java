package nl.socrates.fixture.party;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import nl.socrates.dom.communicationchannel.CommunicationChannelContributions;
import nl.socrates.dom.communicationchannel.CommunicationChannelType;
import nl.socrates.dom.geography.Countries;
import nl.socrates.dom.geography.Country;
import nl.socrates.dom.geography.State;
import nl.socrates.dom.geography.States;
import nl.socrates.dom.party.SocParty;
import nl.socrates.dom.party.PersonGenderType;
import nl.socrates.dom.party.SocPersons;
import nl.socrates.fixture.SocratesFixtureScript;

public abstract class PersonAbstract extends SocratesFixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);

    protected SocParty createPerson(
            String reference, 
            String initials, 
            String firstName, 
            String middleName, 
            String lastName, 
            String baptismalName, 
            PersonGenderType Gender, 
            LocalDate dateOfBirth, 
            String placeOfBirth, 
            String Nationality,
            String address1,
            String address2,
            String postalCode,
            String city,
            String stateReference,
            String countryReference,
            String phone,
            String fax,
            String emailAddress,            
            ExecutionContext executionContext) {

        SocParty party = persons.newPerson(reference, initials, firstName, middleName,lastName, baptismalName, Gender, dateOfBirth, placeOfBirth, Nationality);
        
        createCommunicationChannels(party, address1, address2, postalCode, city, stateReference, countryReference, phone, fax, emailAddress, executionContext);
        
        return executionContext.add(this, party.getReference(), party);
        
        
    }
    
    protected SocParty createCommunicationChannels(
            SocParty party,
            String address1,
            String address2,
            String postalCode,
            String city,
            String stateReference,
            String countryReference,
            String phone,
            String fax,
            String emailAddress,
            ExecutionContext executionContext) {

        if (address1 != null) {
            final Country country = countries.findCountry(countryReference);
            final State state = states.findState(stateReference);
            communicationChannelContributedActions.newPostal(
                    party,
                    CommunicationChannelType.POST_ADRES,
                    country,
                    state,
                    address1,
                    address2,
                    null,
                    postalCode,
                    city);
            getContainer().flush();
        }
        if (phone != null) {
            communicationChannelContributedActions.newPhoneOrFax(
                    party,
                    CommunicationChannelType.TELEFOON_NUMMER,
                    phone);
            getContainer().flush();
        }
        if (fax != null) {
            communicationChannelContributedActions.newPhoneOrFax(
                    party,
                    CommunicationChannelType.MOBIEL_NUMMER,
                    fax);
            getContainer().flush();
        }
        if (emailAddress != null) {
            communicationChannelContributedActions.newEmail(
                    party,
                    CommunicationChannelType.EMAIL_ADRES,
                    emailAddress);
            getContainer().flush();
        }

        return executionContext.add(this, party.getReference(), party);
    }

    // //////////////////////////////////////

 

    @Inject
    protected SocPersons persons;
    
    @Inject
    protected Countries countries;

    @Inject
    protected States states;
    
    @Inject
    protected CommunicationChannelContributions communicationChannelContributedActions;

}
