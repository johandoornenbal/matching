package info.matchingservice.fixture.actor;


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
                "Tekst van Gerard",
                "Junior schilder met penselen",
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

}
