package info.matchingservice.dom.Howdoido.Viewmodels;

import info.matchingservice.dom.Howdoido.BasicUser;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 01/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class UserViewModel {

    public UserViewModel(){}
    public UserViewModel(final BasicUser basicUser){
        this.userName = basicUser.getName();
    }

    //region > userName (property)
    private String userName;

    @MemberOrder(sequence = "1")
    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
    //endregion

}
