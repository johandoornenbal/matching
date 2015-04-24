package info.matchingservice.dom;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;

@DomainService
public class DummyServiceForBootstrapping {

    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementTimePeriod x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementUsePredicate x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(CommunicationChannel x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(Email x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(Address x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(Phone x) { }

}