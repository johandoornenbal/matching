package nl.xtalus.dom.profile;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

public class Profiles extends AbstractFactoryAndRepository {

    @ActionSemantics(Of.SAFE)
    public List<Profile> allUsers() {
        return allInstances(Profile.class);
    }

}
