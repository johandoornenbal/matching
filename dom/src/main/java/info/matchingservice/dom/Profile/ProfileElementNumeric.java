package info.matchingservice.dom.Profile;

import javax.inject.Named;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Immutable;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementNumericByOwnerProfile", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementNumeric "
                    + "WHERE profileElementOwner == :profileElementOwner")
})
@Immutable
public class ProfileElementNumeric extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private Integer numericValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getNumericValue(){
        return numericValue;
    }
    
    public void setNumericValue(final Integer value){
        this.numericValue = value;
    }
    
    public ProfileElementNumeric EditNumericValue(
            @Named("Prijs (credits)")
            final Integer value
            ){
        this.setNumericValue(value);
        return this;
    }
    
    public Integer default0EditNumericValue(){
        return this.getNumericValue();
    }

}
