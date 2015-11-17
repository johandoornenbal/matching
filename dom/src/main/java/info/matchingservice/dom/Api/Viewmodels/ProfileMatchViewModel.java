package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Match.ProfileMatch;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 01/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class ProfileMatchViewModel extends ApiAbstractViewModel {

    public ProfileMatchViewModel(){}

    public ProfileMatchViewModel(final ProfileMatch profileMatch){
        super(profileMatch);

        this.ownerUri = profileMatch.getOwner().getUri();
        this.ownerClassType = profileMatch.getOwner().getClass().getSimpleName();
        this.owner = profileMatch.getOwner().getIdAsInt();
        this.ownerFullName = profileMatch.getOwner().title();
        this.ownerImageUrl = profileMatch.getOwner().getImageUrl();

        this.supplyCandidateUri = profileMatch.getSupplyCandidate().getUri();
        this.supplyCandidateClassType = profileMatch.getSupplyCandidate().getClass().getSimpleName();
        this.supplyCandidate = profileMatch.getSupplyCandidate().getIdAsInt();
        this.supplyCandidateFullName = profileMatch.getSupplyCandidate().title();
        this.supplyCandidateImageUrl = profileMatch.getSupplyCandidate().getImageUrl();

        this.profile = profileMatch.getProfile().getUri();
        this.profileId = profileMatch.getProfile().getIdAsInt();
        this.profileClassType = profileMatch.getProfile().getClass().getSimpleName();

        this.matchingProfile = profileMatch.getMatchingSupplyProfile().getUri();
        this.matchingProfileId = profileMatch.getMatchingSupplyProfile().getIdAsInt();
        this.matchingProfileClassType = profileMatch.getMatchingSupplyProfile().getClass().getSimpleName();

        this.candidateStatus = profileMatch.getCandidateStatus().toString();

    }

    //region > owner (property)
    private String ownerUri;

    @MemberOrder(sequence = "1")
    public String getOwnerUri() {
        return ownerUri;
    }

    public void setOwnerUri(final String ownerUri) {
        this.ownerUri = ownerUri;
    }
    //endregion

    //region > ownerClassType (property)
    private String ownerClassType;

    @MemberOrder(sequence = "1")
    public String getOwnerClassType() {
        return ownerClassType;
    }

    public void setOwnerClassType(final String ownerClassType) {
        this.ownerClassType = ownerClassType;
    }
    //endregion

    //region > owner (property)
    private Integer owner;

    @MemberOrder(sequence = "1")
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(final Integer owner) {
        this.owner = owner;
    }
    //endregion

    //region > ownerImageUrl (property)
    private String ownerImageUrl;

    @MemberOrder(sequence = "1")
    public String getOwnerImageUrl() {
        return ownerImageUrl;
    }

    public void setOwnerImageUrl(final String ownerImageUrl) {
        this.ownerImageUrl = ownerImageUrl;
    }
    //endregion

    //region > ownerFullName (property)
    private String ownerFullName;

    @MemberOrder(sequence = "1")
    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(final String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }
    //endregion

    //region > supplyCandidateUri (property)
    private String supplyCandidateUri;

    @MemberOrder(sequence = "1")
    public String getSupplyCandidateUri() {
        return supplyCandidateUri;
    }

    public void setSupplyCandidateUri(final String supplyCandidateUri) {
        this.supplyCandidateUri = supplyCandidateUri;
    }
    //endregion

    //region > supplyCandidateClassType (property)
    private String supplyCandidateClassType;

    @MemberOrder(sequence = "1")
    public String getSupplyCandidateClassType() {
        return supplyCandidateClassType;
    }

    public void setSupplyCandidateClassType(final String supplyCandidateClassType) {
        this.supplyCandidateClassType = supplyCandidateClassType;
    }
    //endregion

    //region > supplyCandidate (property)
    private Integer supplyCandidate;

    @MemberOrder(sequence = "1")
    public Integer getSupplyCandidate() {
        return supplyCandidate;
    }

    public void setSupplyCandidate(final Integer supplyCandidate) {
        this.supplyCandidate = supplyCandidate;
    }
    //endregion

    //region > supplyCandidateImageUrl (property)
    private String supplyCandidateImageUrl;

    @MemberOrder(sequence = "1")
    public String getSupplyCandidateImageUrl() {
        return supplyCandidateImageUrl;
    }

    public void setSupplyCandidateImageUrl(final String supplyCandidateImageUrl) {
        this.supplyCandidateImageUrl = supplyCandidateImageUrl;
    }
    //endregion

    //region > supplyCandidateFullName (property)
    private String supplyCandidateFullName;

    @MemberOrder(sequence = "1")
    public String getSupplyCandidatieFullName() {
        return supplyCandidateFullName;
    }

    public void setSupplyCandidatieFullName(final String supplyCandidateFullName) {
        this.supplyCandidateFullName = supplyCandidateFullName;
    }
    //endregion

    //region > candidateStatus (property)
    private String candidateStatus;

    @MemberOrder(sequence = "1")
    public String getCandidateStatus() {
        return candidateStatus;
    }

    public void setCandidateStatus(final String candidateStatus) {
        this.candidateStatus = candidateStatus;
    }
    //endregion

    //region > profile (property)
    private String profile;

    @MemberOrder(sequence = "1")
    public String getProfile() {
        return profile;
    }

    public void setProfile(final String profile) {
        this.profile = profile;
    }
    //endregion

    //region > profileId (property)
    private Integer profileId;

    @MemberOrder(sequence = "1")
    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(final Integer profileId) {
        this.profileId = profileId;
    }
    //endregion

    //region > profileClassType (property)
    private String profileClassType;

    @MemberOrder(sequence = "1")
    public String getProfileClassType() {
        return profileClassType;
    }

    public void setProfileClassType(final String profileClassType) {
        this.profileClassType = profileClassType;
    }
    //endregion

    //region > matchingProfile (property)
    private String matchingProfile;

    @MemberOrder(sequence = "1")
    public String getMatchingProfile() {
        return matchingProfile;
    }

    public void setMatchingProfile(final String matchingProfile) {
        this.matchingProfile = matchingProfile;
    }
    //endregion

    //region > matchingProfileId (property)
    private Integer matchingProfileId;

    @MemberOrder(sequence = "1")
    public Integer getMatchingProfileId() {
        return matchingProfileId;
    }

    public void setMatchingProfileId(final Integer matchingProfileId) {
        this.matchingProfileId = matchingProfileId;
    }
    //endregion

    //region > matchingProfileClassType (property)
    private String matchingProfileClassType;

    @MemberOrder(sequence = "1")
    public String getMatchingProfileClassType() {
        return matchingProfileClassType;
    }

    public void setMatchingProfileClassType(final String matchingProfileClassType) {
        this.matchingProfileClassType = matchingProfileClassType;
    }
    //endregion



}
