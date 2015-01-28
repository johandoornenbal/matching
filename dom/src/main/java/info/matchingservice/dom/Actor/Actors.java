package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Programmatic;


@DomainService(repositoryFor = Actor.class)
@DomainServiceLayout(named="Actoren", menuOrder="10")
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
