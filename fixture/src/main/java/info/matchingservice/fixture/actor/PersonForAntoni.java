package info.matchingservice.fixture.actor;


public class PersonForAntoni extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "115", 
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                "Leeuwe Profiel",
                "En mijn getal is",
                3,
                "en mijn tweede getal is",
                17,
                "En mijn tekst is",
                "Wetenschapper, wetenschappelijk onderzoek, universiteit",
                "Wetenschappelijk onderzoek",
                3,
                "Schilder",
                3,
                "Getal",
                23,
                5,
                "Nog een getal",
                22,
                5,
                "woorden om te testen",
                "penseel, penselen, ervaring",
                30,
                "antoni",
                executionContext);
    }

}
