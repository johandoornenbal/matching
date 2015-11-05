package info.matchingservice.dom.Howdoido.Viewmodels;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 01/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class GsonTest {

    public GsonTest(){
        
    }
    
    public GsonTest(final String string, final String user){
        this.string = string;
        this.user = user;
    }

    //region > string (property)
    private String string;

    @MemberOrder(sequence = "1")
    public String getString() {
        return string;
    }

    public void setString(final String string) {
        this.string = string;
    }
    //endregion

    //region > user (property)
    private String user;

    @MemberOrder(sequence = "1")
    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }
    //endregion



}
