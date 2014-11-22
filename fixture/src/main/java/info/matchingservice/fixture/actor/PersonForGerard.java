package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Dropdown.Qualities;


public class PersonForGerard extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "113", 
                "Gerard", 
                "", 
                "Dou",
                "Het profiel van Gerard",
                "My Magic figure",
                7,
                "And again",
                8,
                qualities.allQualities().get(0),
                "Schilder en schoonmaakproject",
                6,
                "Mooie schoonmaakster",
                10,
                "Getal gezocht",
                20,
                10,
                "Nog een getal gezocht",
                11,
                3,
                "Trefwoord",
                "Mooi, rondborstig, schoon",
                15,
                "gerard",
                executionContext);
    }
    
    @javax.inject.Inject
    private Qualities qualities;

}
