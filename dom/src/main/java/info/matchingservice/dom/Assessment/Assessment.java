package info.matchingservice.dom.Assessment;

import info.matchingservice.dom.MatchingSecureMutableObject;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Immutable
public class Assessment extends MatchingSecureMutableObject<Assessment> {

    public Assessment() {
        super("ownedBy");
    }
    
    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
    
    private String ownedBy;
    
    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    // Immutables //////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Should be overridden for use on specific Object Type
     */
    private Object target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("doel")
    public Object getTarget() {
        return target;
    }
    
    public void setTarget(final Object object) {
        this.target = object;
    }       
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder assessment")
    public void DeleteAssessment(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Assessment verwijderd");
    }
    
    public String validateDeleteAssessment(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
