package info.matchingservice.dom;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.dom.Howdoido.BasicCategory;
import info.matchingservice.dom.Howdoido.BasicCategoryRelationshipTuple;
import info.matchingservice.dom.Howdoido.BasicUser;
import info.matchingservice.dom.Match.ProfileComparison;
import info.matchingservice.dom.Profile.ProfileElementChoice;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementLocation;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;
import info.matchingservice.dom.Profile.RequiredProfileElementRole;

@DomainService
public class DummyServiceForBootstrapping {

    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementTimePeriod x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementUsePredicate x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementLocation x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(RequiredProfileElementRole x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementDropDown x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(CommunicationChannel x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(Email x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(Address x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(Phone x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileComparison x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementChoice x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(ProfileElementTag x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(BasicCategory x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(BasicUser x) { }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public void ensureKnownEagerly(BasicCategoryRelationshipTuple x) { }

}