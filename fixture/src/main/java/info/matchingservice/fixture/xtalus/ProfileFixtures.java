package info.matchingservice.fixture.xtalus;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.Tags.TagHolder;
import info.matchingservice.dom.Tags.TagHolders;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.fixture.demand.*;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by jonathan on 4-1-16.
 */
public class ProfileFixtures extends FixtureScript {




    @Inject
    Profiles profiles;



    protected void createCompany(final String name, final String description, final Person person, final String postal, final String city, final String branche, ExecutionContext executionContext, Supply s){


        Profile p = profiles.createSupplyProfile(name, 10, null, null, ProfileType.COMPANY_PROFILE, null, s, s.getOwnedBy());
        p.createStoryElement(description);
        p.createLocationElement(postal, 10);
        p.createCityElement(city, 10);
        p.createBrancheElement(branche, 10);

        executionContext.add(this, p);



    }


    protected void createEducation(final String institute, final String honoursProgram, final String postal, final String city, final String education, ExecutionContext executionContext, Supply s){


        Profile p = profiles.createSupplyProfile(institute, 10, null, null, ProfileType.EDUCATION_PROFILE, null, s, s.getOwnedBy());
        p.createLocationElement(postal, 10);
        p.createCityElement(city, 10);
        p.createBrancheElement(education, 10);

        if(honoursProgram != null){
            p.createHonoursProgramElement(honoursProgram, 10);
        }

        executionContext.add(this, p);



    }

    @Inject
    private Api api;

    @Inject
    private Persons persons;





    @Inject
    Supplies supplies;


    @Override
    protected void execute(ExecutionContext executionContext) {

        executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);


        final String story = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris. Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor. Praesent et diam eget libero egestas mattis sit amet vitae augue. Nam tincidunt congue enim, ut porta lorem lacinia consectetur. Donec ut libero sed arcu vehicula ultricies a non tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ut gravida lorem. Ut turpis felis, pulvinar a semper sed, adipiscing id dolor. Pellentesque auctor nisi id magna consequat sagittis. Curabitur dapibus enim sit amet elit pharetra tincidunt feugiat nisl imperdiet. Ut convallis libero in urna ultrices accumsan. Donec sed odio eros. Donec viverra mi quis quam pulvinar at malesuada arcu rhoncus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In rutrum accumsan ultricies. Mauris vitae nisi at sem facilisis semper ac in est.";

        String imgUrl = "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fehsjournal.org%2Fwp-content%2Fuploads%2F2010%2F03%2FJana-Kollarova-Red-Background1.jpg&f=1";


        String profileUrl = "http://lh6.ggpht.com/s_VyyVsph5meqhCeEGjTCM1cbzTfWr6rUpQmINYrktB18aHES2QQ7LxD6QrvPA-7i_glG54dQRCvUBFYT38SVDAO=s800";
//        persons.allPersons().forEach(person -> service.createProfile(person, imgUrl, story, profileUrl));
//


        for(Person p: persons.allPersons()){



            p.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/KevinSpaceyApr09.jpg/220px-KevinSpaceyApr09.jpg");





        }






        for(Supply s : supplies.allSupplies()){

            // profiel
            if(s.getSupplyType() == DemandSupplyType.PERSON_DEMANDSUPPLY){

                if(s.getOwner() instanceof Person){


                    Person p = (Person) s.getOwner();
                    if(!p.getIsStudent()){
                        createCompany("Code Rehab", "It's time for an intervention", p, "7521BE" , "Enschede", "Webdevelopment", executionContext, s);
                    }else{

                        // create education
                        createEducation("Saxion", "Natural leadership", "7513AB", "Enschede", "Informatica", executionContext, s);

                    }
                }


                for(Profile p: s.getProfiles()){

                    if(p.getType() == ProfileType.PERSON_PROFILE){

                        System.out.println("PERSON PROFILE OF"+ s.getOwner());
                        p.createStoryElement(story);
                        p.createBackgroundImgElement(imgUrl);



                        break;

                    }
                }

            }

        }


        // bijbaantjes
        persons.allPersons().stream().filter(Person::getIsStudent).forEach(person2 -> api.createInterestProfile(person2, "Leuk bijbaantje", LocalDate.now(), LocalDate.now().plusMonths(1), 5, 60));




        createQualities();


    }


    private void createQualities(){



        final String qualitiesString = "Alert\tGoedgehumeurd\tPraktisch\n" +
                "Ambitieus\tGul\tRealistisch\n" +
                "Analytisch\tHandig\tRelativeren\n" +
                "Artistiek\tHelder\tRespectvol\n" +
                "Attent\tHoffelijk\tRuimdenkend\n" +
                "Avontuurlijk\tHumoristisch\tRustig\n" +
                "Bedachtzaam\tIdealistisch\tSamenhang zien\n" +
                "Behulpzaam\tIJverig\tScherpzinnig\n" +
                "Beschaafd\tIndividualistisch\tSensitief\n" +
                "Bescheiden\tInitiatiefrijk\tSerieus\n" +
                "Beschouwend\tInlevingsvermogen\tSlim\n" +
                "Betrokken\tInspirerend\tSociaal\n" +
                "Betrouwbaar\tIntellectueel\tSpeels\n" +
                "Bezorgd\tIntelligent\tSpontaan\n" +
                "Charismatisch\tIntuïtief\tStrijdlustig\n" +
                "Communicatief\tKalm\tSympathiek\n" +
                "Consequent\tKrachtig\tSystematisch\n" +
                "Constructief\tLevendig\tTactvol\nDuidelijk\tNieuwsgierig\tVerdraagzaam\n" +
                "Durvend\tNuchter\tVerzorgd\n" +
                "Dynamisch\tObjectief\tVindingrijk\n" +
                "Economisch\tOnafhankelijk\tVisie hebbend\n" +
                "Eerlijk\tOndernemend\tVooruitdenkend\n" +
                "Energiek\tOnderscheidend\tVriendelijk\n" +
                "Enthousiast\tOpenhartig\tVrolijk\n" +
                "Evenwichtig\tOpkomen voor jezelf\tWaardig\n" +
                "Extravert\tOplettend\tWelsprekend\n" +
                "Fantasierijk\tOprecht\tZelfbewust\n" +
                "Filosofisch\tOptimistisch\tZelfstandig";

        final String[] qualities = qualitiesString.split("\\n|\\t");
        System.out.println("QUALITIES" + Arrays.toString(qualities));



        for(Supply s : supplies.allSupplies()){

            // profiel
            if(s.getSupplyType() == DemandSupplyType.PERSON_DEMANDSUPPLY){

                // get profiles
                for(Profile p: s.getProfiles()){

                    // personal profile
                    if(p.getType() == ProfileType.PERSON_PROFILE){

                        //get right element
                        for (ProfileElement e: p.getElements()){


                            if(e.getProfileElementType().equals(ProfileElementType.QUALITY_TAGS)){

                                final int amountOfQualities = 10;
                                for (int i = 0; i <amountOfQualities; i++) {
                                    int randomNumber = (int) (Math.random() * qualities.length-1);
                                    if(randomNumber < 0) randomNumber=0;


                                    tagHolders.createTagHolder(e, qualities[randomNumber], "kwaliteit", p.getOwnedBy());
                                }

                                break;


                            }
                        }


                        break;
                    }
                }

            }

        }


    }



    @Inject
    TagHolders tagHolders;


}
