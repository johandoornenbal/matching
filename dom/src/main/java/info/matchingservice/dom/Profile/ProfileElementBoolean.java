package info.matchingservice.dom.Profile;

import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

/**
 * Created by jonathan on 22-4-15.
 */



@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findProfileElementOfType", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.ProfileElementBoolean"
                        + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
public class ProfileElementBoolean extends ProfileElement{


    private boolean booleanValue = false;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(multiLine=10)
    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }



}
