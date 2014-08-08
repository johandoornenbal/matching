package nl.xtalus.dom.profile;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.value.Blob;

@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@PersistenceCapable()
public class BusinessProfile extends Profile {
    
    private String BusinessName;
    
    @Column(allowsNull = "true")
    @Named("Bedrijfsnaam")
    @MemberOrder(sequence = "2")
    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }

    //region > logo (property)
    // //////////////////////////////////////////////////////////////////////////
    @javax.jdo.annotations.Persistent(defaultFetchGroup = "false", columns = {
            @javax.jdo.annotations.Column(name = "picture_name"),
            @javax.jdo.annotations.Column(name = "picture_mimetype"),
            @javax.jdo.annotations.Column(name = "picture_bytes", jdbcType = "BLOB", sqlType = "BLOB")
            })
    private Blob logo;

    @Named("Bedrijfslogo")
    @MemberOrder(sequence = "2")
//    @javax.jdo.annotations.Column(allowsNull = "true")
//    @Hidden(where=Where.ALL_TABLES)
    @Optional
    public Blob getLogo() {
        return logo;
    }

    public void setLogo(final Blob logo) {
        this.logo = logo;
    }
    //endregion
    
    
}
