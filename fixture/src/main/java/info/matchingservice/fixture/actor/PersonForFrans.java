package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Dropdown.Qualities;


public class PersonForFrans extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "111", 
                "Frans", 
                "", 
                "Hals",
                "Profiel van Frans",
                "Getal van Frans",
                5,
                "Nog een keer",
                15,
                qualities.allQualities().get(0),
                "Schilderproject",
                10,
                "Junior hulpschilder",
                4,
                "getal",
                7,
                10,
                "nog een getal",
                16,
                5,
                "woorden",
                "schilder, schilderij, junior",
                20,
                "frans",
                executionContext);
    }
    
    @javax.inject.Inject
    private Qualities qualities;

}
