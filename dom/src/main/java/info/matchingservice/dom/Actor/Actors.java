package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Programmatic;


@DomainService(menuOrder = "10", repositoryFor = Actor.class)
@Named("Actoren")
public class Actors extends MatchingDomainService<Actor> {
    
    public Actors() {
        super(Actors.class, Actor.class);
    }
    
    @Programmatic
    @NotInServiceMenu
    public List<Actor> allActors() {
        return allInstances();
    }

}
