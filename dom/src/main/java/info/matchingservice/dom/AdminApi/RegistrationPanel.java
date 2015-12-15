package info.matchingservice.dom.AdminApi;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.Collection;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 12-12-15.
 */
@ViewModel
public class RegistrationPanel {

    @Inject
    Persons persons;

    @Inject
    DomainObjectContainer container;


    @Collection(notPersisted = true)
    @CollectionLayout(named = "Niet geactiveerde gebruikers", render = RenderType.EAGERLY)
    public List<PersonRegistrationViewModel> getNonActiveUsers(){
        return persons.allPersons().stream().filter(nonActivePerson -> !nonActivePerson.getActivated()).map(PersonRegistrationViewModel::new).collect(Collectors.toList());
    }




    @Collection(notPersisted = true)
    @CollectionLayout(named = "Geactiveerde gebruikers", render = RenderType.EAGERLY)
    public List<PersonRegistrationViewModel> getActivatedUsers(){
        return persons.allPersons().stream().filter(Actor::getActivated).map(PersonRegistrationViewModel::new).collect(Collectors.toList());
    }


    @ActionLayout(named = "Accepteer gebruiker")
    public RegistrationPanel acceptUser(Person p){

        final String mailEndpoint = "/api/mail/confirm/activation-accepted";
        if(!sendMail(p.getFirstName(), p.getMiddleName(), p.getLastName(), p.getOwnedBy(),"Uw Xtalus account is geactiveerd", mailEndpoint)){
            container.informUser("Er is geen mail verstuurd naar de gebruiker, probeer opnieuw");
            //GEBRUIKER IS NIET GEINFORMEERD
        }else{
            persons.activatePerson(p.getOwnedBy());
            container.informUser("Gebruiker geactiveerd");
            container.informUser("Er is een mail verstuurd naar de gebruiker.");
        }
        return this;
    }



    @ActionLayout(named = "Weiger gebruiker")
    public RegistrationPanel declineUser(Person p){

        final String mailEndpoint = "/api/mail/confirm/activation-declined";
        if(!sendMail(p.getFirstName(), p.getMiddleName(), p.getLastName(), p.getOwnedBy(),"Uw Xtalus account is geweigerd", mailEndpoint)){
            container.informUser("Er is geen mail verstuurd naar de gebruiker, probeer opnieuw");
            //GEBRUIKER IS NIET GEINFORMEERD
        }else{
            persons.deletePerson(p.getOwnedBy());
            container.informUser("Gebruiker verwijderd");
            container.informUser("Er is een mail verstuurd naar de gebruiker.");
        }
        return this;
    }



    //AUTO COMPLETE
    public List<Person> choices0DeclineUser() {
        return persons.allPersons().stream().filter(nonActivePerson -> !nonActivePerson.getActivated()).collect(Collectors.toList());
    }

    public List<Person> choices0AcceptUser() {
        return persons.allPersons().stream().filter(nonActivePerson -> !nonActivePerson.getActivated()).collect(Collectors.toList());
    }


    @ActionLayout(named = "Verwijder/Activeer snel")
    public RegistrationPanel processUser(@ParameterLayout(named = "achternaam") final Person person, @ParameterLayout(named = "weigeren?")@Parameter(optionality = Optionality.MANDATORY)final boolean reject){
        if(reject){
            declineUser(person);
            return this;
        }

        acceptUser(person);
        return this;
    }

    public List<Person> autoComplete0ProcessUser(final String search) {
        return persons.autoComplete(search);
    }








    /**sends a post to the email server so the user gets notified if he is accepted or rejected
     *
     * @param
     * @return
     */
    private boolean sendMail(final String firstName, final String middleName, final String lastName,
                             final String email, final String subject, final String mailEndpoint){

        final String mailHost = "dev.xtalus.nl";
        //final String mailEndpoint = "localhost";
        // create data

        JsonObject data = new JsonObject();
        data.addProperty("firstname", firstName);
        data.addProperty("middlename", middleName);
        data.addProperty("lastname", lastName);
        data.addProperty("email", email);
        data.addProperty("subject", subject);

        JsonObject jsonBody = new JsonObject();
        jsonBody.add("data", data);

        System.out.println(" DATA : " + jsonBody.toString());

        // setup client
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost host = new HttpHost(mailHost);
        HttpPost request = new HttpPost(mailEndpoint);


        try {
            StringEntity body = new StringEntity(jsonBody.toString(), ContentType.APPLICATION_JSON);

            System.out.println(" BODY : " + body.toString());
            request.setEntity(body);
            httpClient.execute(host, request);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;


        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



        return true;

    }





}
