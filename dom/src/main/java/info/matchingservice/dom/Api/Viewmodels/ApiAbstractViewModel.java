package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.MatchingMutableObject;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 02/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public abstract class ApiAbstractViewModel {

    public ApiAbstractViewModel(){

    }

    public ApiAbstractViewModel(final MatchingMutableObject o){
        this.id = o.getIdAsInt();
        this.uri = o.getUri();
    }

    //region > id (property)
    private Integer id;

    @MemberOrder(sequence = "0")
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
    //endregion

    //region > OID (property)
    private String uri;

    @MemberOrder(sequence = "0.1")
    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }
    //endregion

}
