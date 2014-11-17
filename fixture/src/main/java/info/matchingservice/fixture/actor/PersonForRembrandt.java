package info.matchingservice.fixture.actor;


public class PersonForRembrandt extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "112", 
                "Rembrandt", 
                "van", 
                "Rijn",
                "Dit is Rembrandt",
                "Getal van R",
                13,
                "Tweede getal",
                23,
                "Tekst van R",
                "Bekend professioneel allround schilder dapper",
                "Figuranten voor de Nachtwacht",
                5,
                "Dikke admiraal",
                5,
                "Getal",
                11,
                5,
                "Lansdrager",
                15,
                5,
                "Soldaat",
                "held, marinier",
                5,
                "rembrandt",
                executionContext);
    }

}
