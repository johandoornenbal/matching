package info.matchingservice.fixture.actor;


public class PersonForMichiel extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "114", 
                "Michiel", 
                "de", 
                "Ruyter",
                "Dit is Michiel",
                "Michiels getal",
                11,
                "En nog een",
                4,
                "Michiels tekst",
                "Admiraal, marine, zeevaart, held",
                "Veldslag",
                5,
                "Soldaat",
                5,
                "Getal",
                30,
                4,
                "Matroos",
                18,
                6,
                "Steekwoorden",
                "ervaring, dapper",
                10,
                "michiel",
                executionContext);
    }

}
