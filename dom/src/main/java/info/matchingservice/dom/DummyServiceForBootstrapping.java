package info.matchingservice.dom;

import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Where;

@DomainService
public class DummyServiceForBootstrapping {

    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementTimePeriod x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementUsePredicate x) { }

}