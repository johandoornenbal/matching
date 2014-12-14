package info.matchingservice.dom.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementNumericByOwnerProfile", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementnumeric "
                    + "WHERE profileElementOwner == :profileElementOwner")
})
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

}
