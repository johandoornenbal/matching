/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package info.matchingservice.dom.Profile;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.ProfileAssessment;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Match.ProfileComparison;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Match.ProfileMatches;
import info.matchingservice.dom.Match.ProfileMatchingService;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Tags.TagCategories;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import java.util.*;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "allSupplyProfiles", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE supplyProfileOwner != null"),
        @javax.jdo.annotations.Query(
                name = "allSupplyProfilesOfType", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE supplyProfileOwner != null && profileType == :profileType"),
        @javax.jdo.annotations.Query(
                name = "allDemandProfiles", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE demandProfileOwner != null"),
        @javax.jdo.annotations.Query(
                name = "allDemandProfilesOfType", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE demandProfileOwner != null && profileType == :profileType"),
        @javax.jdo.annotations.Query(
                name = "allSupplyProfilesOfTypeByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE supplyProfileOwner != null && profileType == :profileType && ownedBy == :ownedBy"),
        @javax.jdo.annotations.Query(
                name = "searchNameOfProfilesOfTypeByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE demandOrSupply == :demandOrSupply && profileType == :profileType && ownedBy == :ownedBy && profileName.indexOf(:profileName) >= 0"),
        @javax.jdo.annotations.Query(
                name = "findProfileByUniqueItemId", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE uniqueItemId.matches(:uniqueItemId)"),
        @javax.jdo.annotations.Query(
                name = "findProfileByDemandProfileOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.Profile "
                        + "WHERE demandProfileOwner == :demandProfileOwner")
})
@DomainObject(editing = Editing.DISABLED)
public class Profile extends MatchingSecureMutableObject<Profile> {

    //** API: PROPERTIES **//

    //** profileName **//
    private String profileName;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(final String test) {
        this.profileName = test;
    }
    //-- profileName --//

    //** weight **//
    private Integer weight;

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    //-- weight --//

    //** profileStartDate **//
    private LocalDate profileStartDate;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public LocalDate getProfileStartDate() {
        return profileStartDate;
    }

    public void setProfileStartDate(LocalDate demandOrSupplyStartDate) {
        this.profileStartDate = demandOrSupplyStartDate;
    }
    //-- profileStartDate --//

    //** profileEndDate **//
    private LocalDate profileEndDate;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public LocalDate getProfileEndDate() {
        return profileEndDate;
    }

    public void setProfileEndDate(LocalDate demandOrSupplyEndDate) {
        this.profileEndDate = demandOrSupplyEndDate;
    }
    //-- profileEndDate --//

    //** uniqueItemId **//
    private UUID uniqueItemId;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }

    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //-- uniqueItemId --//

    //** profileType **//
    private ProfileType profileType;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public ProfileType getProfileType() {
        return profileType;
    }

    public void setProfileType(final ProfileType profileType) {
        this.profileType = profileType;
    }
    //-- profileType --//

    //** demandOrSupply **//
    private DemandOrSupply demandOrSupply;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public DemandOrSupply getDemandOrSupply() {
        return demandOrSupply;
    }

    public void setDemandOrSupply(final DemandOrSupply demandOrSupply) {
        this.demandOrSupply = demandOrSupply;
    }
    //-- demandOrSupply --//

    //** demandProfileOwner **//
    private Demand demandProfileOwner;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(hidden = Where.ALL_TABLES)
    public Demand getDemandProfileOwner() {
        return demandProfileOwner;
    }

    public void setDemandProfileOwner(final Demand demandProfileOwner) {
        this.demandProfileOwner = demandProfileOwner;
    }

    public boolean hideDemandProfileOwner() {
        if (getDemandProfileOwner() == null) {
            return true;
        }

        return false;
    }
    //-- demandProfileOwner --//

    //** supplyProfileOwner **//
    private Supply supplyProfileOwner;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(hidden = Where.ALL_TABLES)
    public Supply getSupplyProfileOwner() {
        return supplyProfileOwner;
    }

    public void setSupplyProfileOwner(final Supply supplyProfileOwner) {
        this.supplyProfileOwner = supplyProfileOwner;
    }

    public boolean hideSupplyProfileOwner() {
        if (getSupplyProfileOwner() == null) {
            return true;
        }

        return false;
    }
    //-- supplyProfileOwner --//

    //** actorOwner **//
    @PropertyLayout(hidden = Where.PARENTED_TABLES)
    public Actor getActorOwner() {
        if (this.getDemandOrSupply().equals(DemandOrSupply.DEMAND)) {
            return getDemandProfileOwner().getDemandOwner();
        } else {
            return getSupplyProfileOwner().getSupplyOwner();
        }
    }
    //-- actorOwner --//

    //-- API: PROPERTIES --//

    //** API: COLLECTIONS **//

    //** collectProfileElements **//
    private SortedSet<ProfileElement> collectProfileElements = new TreeSet<ProfileElement>();

    @CollectionLayout(render = RenderType.EAGERLY)
    @Persistent(mappedBy = "profileElementOwner", dependentElement = "true")
    public SortedSet<ProfileElement> getCollectProfileElements() {
        return collectProfileElements;
    }

    public void setCollectProfileElements(final SortedSet<ProfileElement> vac) {
        this.collectProfileElements = vac;
    }
    //-- collectProfileElements --//

    //** collectAssessments **//
    private SortedSet<ProfileAssessment> collectAssessments = new TreeSet<ProfileAssessment>();

    @CollectionLayout(render = RenderType.EAGERLY)
    @Persistent(mappedBy = "targetOfAssessment", dependentElement = "true")
    public SortedSet<ProfileAssessment> getCollectAssessments() {
        return collectAssessments;
    }

    public void setCollectAssessments(final SortedSet<ProfileAssessment> assessment) {
        this.collectAssessments = assessment;
    }

    public boolean hideCollectAssessments() {
        return this.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- collectAssessments --//

    //-- API: COLLECTIONS --//

    //** API: ACTIONS **//

    //** collectProfileComparisons**//

    @Inject
    ProfileMatchingService profileMatchingService;

    @Action(semantics = SemanticsOf.SAFE)
    public List<ProfileComparison> actionCollectProfileComparisons() {
        return profileMatchingService.collectProfileComparisons(this);
    }

    public boolean hideActionCollectProfileComparisons() {
        return this.getDemandOrSupply() != DemandOrSupply.DEMAND;
    }

    //-- collectProfileComparisons--//

    //** createPassionElement **//
    // Business rule:
    // alleen op profile van type PERSON of ORGANISATION
    // alleen op aanbod profiel
    // slechts 1 per profile
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public Profile createPassionElement(
            @ParameterLayout(named = "textValue")
            final String textValue,
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        profileElementTexts.createProfileElementText(
                "PASSION_ELEMENT",
                weight,
                textValue,
                ProfileElementType.PASSION,
                this);
        return this;
    }



    public boolean hideCreatePassionElement(
            final String passionText,
            final Integer weight
    ) {

        // alleen op profile van type PERSON of ORGANISATION
        // alleen op aanbod profiel
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE && this.getProfileType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.DEMAND) {
            return true;
        }

        // er  mag hooguit 1 Passie element zijn
        QueryDefault<ProfileElementText> query =
                QueryDefault.create(
                        ProfileElementText.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.PASSION,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }

        return false;
    }

    public String validateCreatePassionElement(
            final String passionText,
            final Integer weight
    ) {

        // alleen op profile van type PERSON of ORGANISATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE && this.getProfileType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.DEMAND) {
            return "ONLY_ON_PERSON_SUPPLY_AND_PERSON_OR_ORGANISATION_PROFILE";
        }

        // er  mag hooguit 1 Passie element zijn
        QueryDefault<ProfileElementText> query =
                QueryDefault.create(
                        ProfileElementText.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.PASSION,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        return null;
    }
    //-- createPassionElement --//


    //** createBooleanElement **//
    //TODO Businnes Rule, alleen student, niet bij Supplies??
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public Profile createBooleanElement(
            @ParameterLayout(named = "Eigen Vervoer?")
            final boolean vervoer,
            @ParameterLayout(named = "weight")
            final Integer weight) {

        profileElementBooleans.createProfileElementBoolean(
                "VERVOER_ELEMENT" ,
                weight,
                ProfileElementType.VERVOER,
                this);



        return this;


    }


    public boolean hideCreateBooleanElement(
            final boolean vervoer,
            final Integer weight
    ) {

        // alleen op profile van type PERSON of ORGANISATION
        // zowel demand als vraag -------???? not sure
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE && this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
            return true;
        }

//        // er  mag hooguit 1 vervoer element zijn
//        QueryDefault<ProfileElementBoolean> query =
//                QueryDefault.create(
//                        ProfileElementBoolean.class,
//                        "findProfileElementOfType",
//                        "profileElementType", ProfileElementType.VERVOER,
//                        "profileElementOwner", this);
//        if (container.firstMatch(query) != null) {
//            return true;
//        }

        return false;
    }

    public String validateCreateBooleanElement(
            final boolean vervoer,
            final Integer weight
    ) {

        // alleen op profile van type PERSON of ORGANISATION
        if (this.getProfileType() != ProfileType.PERSON_PROFILE && this.getProfileType() != ProfileType.ORGANISATION_PROFILE) {
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";  //TODO ..... ?? hoogstwaarschijnlijk incorect.
        }

        //TODO geeft fouten..hoe wel?
//        // er  mag hooguit 1 VERVOER element zijn
//        QueryDefault<ProfileElementBoolean> query =
//                QueryDefault.create(
//                        ProfileElementBoolean.class,
//                        "findProfileElementOfType",
//                        "profileElementType", ProfileElementType.VERVOER,
//                        "profileElementOwner", this);
//        if (container.firstMatch(query) != null) {
//            return "ONE_INSTANCE_AT_MOST";
//        }

        return null;
    }




    //-- createBooleanElement --//


    //** createPassionTagElement **//
    // Business rule:
    // only on profile van type PERSON
    // only on DEMAND PROFILE
    // At Most one
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createPassionTagElement(
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        return profileElementTags.createProfileElementTag(
                "PASSION_TAGS_ELEMENT",
                weight,
                ProfileElementType.PASSION_TAGS,
                this);
    }

    public boolean hideCreatePassionTagElement(final Integer weight) {
        // only on profile van type PERSON
        // only on DEMAND PROFILE
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY) {
            return true;
        }

        // At Most one
        QueryDefault<ProfileElementTag> query =
                QueryDefault.create(
                        ProfileElementTag.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.PASSION_TAGS,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }

        return false;
    }

    public String validateCreatePassionTagElement(final Integer weight) {
        // only on profile van type PERSON
        // only on DEMAND PROFILE
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY) {
            return "ONLY_ON_PERSON_DEMAND_AND_PERSON_PROFILE";
        }

        // At Most one
        QueryDefault<ProfileElementTag> query =
                QueryDefault.create(
                        ProfileElementTag.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.PASSION_TAGS,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        return null;
    }
    //-- createPassionTagElement --//

    //** createBrancheTagElement **//
    // Business rule:
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createBrancheTagElement(
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        return profileElementTags.createProfileElementTag(
                "BRANCHE_TAGS_ELEMENT",
                weight,
                ProfileElementType.BRANCHE_TAGS,
                this);
    }

    public boolean hideCreateBrancheTagElement(final Integer weight) {
        // only on profile of type PERSON and ORGANSATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
            return true;
        }

        // At Most one
        QueryDefault<ProfileElementTag> query =
                QueryDefault.create(
                        ProfileElementTag.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.BRANCHE_TAGS,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }

        return false;
    }

    public String validateCreateBrancheTagElement(final Integer weight) {
        // only on profile of type PERSON and ORGANSATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
        }

        // At Most one
        QueryDefault<ProfileElementTag> query =
                QueryDefault.create(
                        ProfileElementTag.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.BRANCHE_TAGS,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        return null;
    }
    //-- createBrancheTagElement --//

    //** createQualityTagElement **//

    // Business rule:
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    // 2 dezelfde kwaliteiten kiezen heeft geen zin => TODO: Deze moet in zijn algemeenheid worden opgelost bij tags denk ik

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createQualityTagElement(
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        return profileElementTags.createProfileElementTag(
                "QUALITY_TAGS_ELEMENT",
                weight,
                ProfileElementType.QUALITY_TAGS,
                this);
    }

    //TODO werkt ook niet
//
//    public boolean hideCreateQualityTagElement(final Integer weight) {
//        // only on profile of type PERSON and ORGANSATION
//        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
//            return true;
//        }
//
//        // At Most one
//        QueryDefault<ProfileElementTag> query =
//                QueryDefault.create(
//                        ProfileElementTag.class,
//                        "findProfileElementOfType",
//                        "profileElementType", ProfileElementType.QUALITY_TAGS,
//                        "profileElementOwner", this);
//        if (container.firstMatch(query) != null) {
//            return true;
//        }
//
//        return false;
//    }


    //TODO Werkt ook niet
//    public String validateCreateQualityTagElement(final Integer weight) {
//        // only on profile of type PERSON and ORGANSATION
//        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
//            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
//        }
//
//        // At Most one
//        QueryDefault<ProfileElementTag> query =
//                QueryDefault.create(
//                        ProfileElementTag.class,
//                        "findProfileElementOfType",
//                        "profileElementType", ProfileElementType.QUALITY_TAGS,
//                        "profileElementOwner", this);
//        if (container.firstMatch(query) != null) {
//            return "ONE_INSTANCE_AT_MOST";
//        }
//
//        return null;
//    }
    //-- createQualityTagElement --//


    //** createOpleidingTagElement **//

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createQualificationTagElement(
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        return profileElementTags.createProfileElementTag(
                "QUALIFICATION_TAGS_ELEMENT",
                weight,
                ProfileElementType.QUALIFICATION_TAGS,
                this);
    }




    //** createWeekdayTagElement **//

    // Business rule:
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    // 2 dezelfde kwaliteiten kiezen heeft geen zin => TODO: Deze moet in zijn algemeenheid worden opgelost bij tags denk ik

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createWeekDayTagElement(
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        return profileElementTags.createProfileElementTag(
                "WEEKDAY_TAGS_ELEMENT",
                weight,
                ProfileElementType.WEEKDAY_TAGS,
                this);
    }

    public boolean hideCreateWeekDayTagElement(final Integer weight) {
        // only on profile of type PERSON and ORGANSATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
            return true;
        }

        // At Most one
        QueryDefault<ProfileElementTag> query =
                QueryDefault.create(
                        ProfileElementTag.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.WEEKDAY_TAGS,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }

        return false;
    }

    public String validateCreateWeekDayTagElement(final Integer weight) {
        // only on profile of type PERSON and ORGANSATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)) {
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
        }

        // At Most one
        QueryDefault<ProfileElementTag> query =
                QueryDefault.create(
                        ProfileElementTag.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.WEEKDAY_TAGS,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        return null;
    }
    //-- createWeekDayTagElement --//

    //** createLocationElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // textValue moet een geldig postcode formaat zijn (4 cijfers , al of niet een spatie, 2 hoofdletters)

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Profile createLocationElement(
            @ParameterLayout(named = "postcode")
            @Parameter(regexPattern = "^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")
            final String textValue,
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        profileElementTexts.createProfileElementText("LOCATION_ELEMENT", weight, textValue, ProfileElementType.LOCATION, this);
        return this;
    }

    public boolean hideCreateLocationElement(final String textValue, final Integer weight) {

        QueryDefault<ProfileElementText> query =
                QueryDefault.create(
                        ProfileElementText.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.LOCATION);
        if (container.firstMatch(query) != null) {
            return true;
        }

        // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
        if (this.profileType == ProfileType.PERSON_PROFILE || this.profileType == ProfileType.ORGANISATION_PROFILE) {
            return false;
        }

        return true;
    }

    public String validateCreateLocationElement(final String textValue, final Integer weight) {

        QueryDefault<ProfileElementText> query =
                QueryDefault.create(
                        ProfileElementText.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.LOCATION);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
        if (this.profileType != ProfileType.PERSON_PROFILE && this.profileType != ProfileType.ORGANISATION_PROFILE) {
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
        }

        //textValue moet een geldig postcode formaat zijn


        return null;
    }
    //-- createLocationElement --//

    //** createAgeElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op demands
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Profile createAgeElement(
            @ParameterLayout(named = "age")
            final Integer age,
            @ParameterLayout(named = "weight")
            final Integer weight
    ) {
        profileElementNumerics.createProfileElementNumeric("AGE_ELEMENT", weight, age, ProfileElementType.AGE, this);

        return this;
    }

    public boolean hideCreateAgeElement(final Integer age, final Integer weight) {

        QueryDefault<ProfileElementNumeric> query =
                QueryDefault.create(
                        ProfileElementNumeric.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.AGE);

        if (
            // alleen op demands
                this.getDemandOrSupply() == DemandOrSupply.DEMAND

                        &&

                        // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
                        (this.getProfileType() == ProfileType.PERSON_PROFILE || this.getProfileType() == ProfileType.ORGANISATION_PROFILE)

                        &&

                        // er mag er hooguit een van zijn
                        container.firstMatch(query) == null
                ) {

            return false;
        }

        return true;
    }

    public String validateCreateAgeElement(final Integer age, final Integer weight) {

        QueryDefault<ProfileElementNumeric> query =
                QueryDefault.create(
                        ProfileElementNumeric.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.AGE);

        if (container.firstMatch(query) != null) {

            return "ONE_INSTANCE_AT_MOST";

        }

        if (this.profileType != ProfileType.PERSON_PROFILE && this.profileType != ProfileType.ORGANISATION_PROFILE) {

            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";

        }

        return null;
    }
    //-- createAgeElement --//

    //** createUseAgeElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op supplies
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Profile createUseAgeElement(
            @ParameterLayout(named = "weight")
            final Integer weight) {

        profileElementUsePredicates.createProfileElementUsePredicate("USE_AGE_ELEMENT", weight, false, true, ProfileElementType.USE_AGE, this);

        return this;
    }

    public boolean hideCreateUseAgeElement(final Integer weight) {

        QueryDefault<ProfileElementUsePredicate> query =
                QueryDefault.create(
                        ProfileElementUsePredicate.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.USE_AGE);

        if (
            // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
                (this.profileType == ProfileType.PERSON_PROFILE || this.profileType == ProfileType.ORGANISATION_PROFILE)

                        &&

                        // alleen op demands
                        this.getDemandOrSupply() == DemandOrSupply.SUPPLY

                        &&

                        // er mag er hooguit een van zijn
                        container.firstMatch(query) == null
                ) {
            return false;
        }

        return true;
    }

    public String validateCreateUseAgeElement(final Integer weight) {

        QueryDefault<ProfileElementUsePredicate> query =
                QueryDefault.create(
                        ProfileElementUsePredicate.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.USE_AGE);

        if (container.firstMatch(query) != null) {

            return "ONE_INSTANCE_AT_MOST";

        }

        if (this.profileType != ProfileType.PERSON_PROFILE && this.profileType != ProfileType.ORGANISATION_PROFILE) {

            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";

        }

        if (this.getDemandOrSupply() != DemandOrSupply.SUPPLY) {

            return "ONLY_ON_SUPPLY";

        }

        return null;

    }
    //-- createUseAgeElement --//

    //** createTimePeriodElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op demands
    // de 'gewone' datum regels gelden
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Profile createTimePeriodElement(
            @ParameterLayout(named = "startDate")
            final LocalDate startDate,
            @ParameterLayout(named = "endDate")
            final LocalDate endDate,
            @ParameterLayout(named = "weight")
            final Integer weight) {

        profileElementTimePeriods.createProfileElementTimePeriod("TIME_PERIOD_ELEMENT", weight, startDate, endDate, ProfileElementType.TIME_PERIOD, this);

        return this;
    }

    public boolean hideCreateTimePeriodElement(final LocalDate startDate, final LocalDate endDate, final Integer weight) {

        QueryDefault<ProfileElementTimePeriod> query =
                QueryDefault.create(
                        ProfileElementTimePeriod.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.TIME_PERIOD);

        if (
            // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
                (this.profileType == ProfileType.PERSON_PROFILE || this.profileType == ProfileType.ORGANISATION_PROFILE)

                        &&

                        // alleen op demands
                        this.getDemandOrSupply() == DemandOrSupply.DEMAND

                        &&

                        // er mag er hooguit een van zijn
                        container.firstMatch(query) == null
                ) {
            return false;
        }

        return true;
    }

    public String validateCreateTimePeriodElement(final LocalDate startDate, final LocalDate endDate, final Integer weight) {

        QueryDefault<ProfileElementTimePeriod> query =
                QueryDefault.create(
                        ProfileElementTimePeriod.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.TIME_PERIOD);

        if (container.firstMatch(query) != null) {

            return "ONE_INSTANCE_AT_MOST";

        }

        if (this.profileType != ProfileType.PERSON_PROFILE && this.profileType != ProfileType.ORGANISATION_PROFILE) {

            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";

        }

        if (this.getDemandOrSupply() != DemandOrSupply.DEMAND) {

            return "ONLY_ON_DEMAND";

        }

        //Date validation

        final LocalDate today = LocalDate.now();
        if (endDate != null && endDate.isBefore(today)) {
            return "ENDDATE_BEFORE_TODAY";
        }

        if (
                endDate != null

                        &&

                        startDate != null

                        &&

                        endDate.isBefore(startDate)

                ) {
            return "ENDDATE_BEFORE_STARTDATE";
        }


        return null;
    }

    //-- createTimePeriodElement --//

    //** createUseTimePeriodElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op supplies
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Profile createUseTimePeriodElement(
            @ParameterLayout(named = "weight")
            final Integer weight) {

        profileElementUsePredicates.createProfileElementUsePredicate("USE_TIME_PERIOD_ELEMENT", weight, true, false, ProfileElementType.USE_TIME_PERIOD, this);

        return this;
    }

    public boolean hideCreateUseTimePeriodElement(final Integer weight) {

        QueryDefault<ProfileElementUsePredicate> query =
                QueryDefault.create(
                        ProfileElementUsePredicate.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.USE_TIME_PERIOD);

        if (
            // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
                (this.profileType == ProfileType.PERSON_PROFILE || this.profileType == ProfileType.ORGANISATION_PROFILE)

                        &&

                        // alleen op demands
                        this.getDemandOrSupply() == DemandOrSupply.SUPPLY

                        &&

                        // er mag er hooguit een van zijn
                        container.firstMatch(query) == null
                ) {
            return false;
        }

        return true;
    }

    public String validateCreateUseTimePeriodElement(final Integer weight) {

        QueryDefault<ProfileElementUsePredicate> query =
                QueryDefault.create(
                        ProfileElementUsePredicate.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.USE_TIME_PERIOD);

        if (container.firstMatch(query) != null) {

            return "ONE_INSTANCE_AT_MOST";

        }

        if (this.profileType != ProfileType.PERSON_PROFILE && this.profileType != ProfileType.ORGANISATION_PROFILE) {

            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";

        }

        if (this.getDemandOrSupply() != DemandOrSupply.SUPPLY) {

            return "ONLY_ON_SUPPLY";

        }

        return null;

    }
    //-- createUseTimePeriodElement --//

    //** createPriceElement **//
    // Business rule:
    // Er kan maar een prijs element zijn
    // Alleen op cursusprofiel

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public ProfileElementNumeric createPriceElement(
            @ParameterLayout(named = "numericValue")
            final Integer numericValue
    ) {
        return profileElementNumerics.createProfileElementNumeric(
                "PRICE_ELEMENT",
                10,
                numericValue,
                ProfileElementType.NUMERIC,
                this);
    }

    public boolean hideCreatePriceElement(
            final Integer numericValue
    ) {

        if (this.getProfileType() != ProfileType.COURSE_PROFILE) {
            return true;
        }

        QueryDefault<ProfileElementNumeric> query =
                QueryDefault.create(
                        ProfileElementNumeric.class,
                        "findProfileElementNumericByOwnerProfile",
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }

        return false;
    }

    public String validateCreatePriceElement(
            final Integer numericValue
    ) {

        if (this.getProfileType() != ProfileType.COURSE_PROFILE) {
            return "ONLY_ON_COURSE_PROFILE";
        }

        QueryDefault<ProfileElementNumeric> query =
                QueryDefault.create(
                        ProfileElementNumeric.class,
                        "findProfileElementNumericByOwnerProfile",
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        return null;
    }
    //-- createPriceElement --//

    //** updateProfile **//
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Profile updateProfile(
            @ParameterLayout(named = "profileName")
            String newString,
            @ParameterLayout(named = "weight")
            Integer newInteger,
            @ParameterLayout(named = "profileStartDate")
            LocalDate profileStartDate,
            @ParameterLayout(named = "profileEndDate")
            LocalDate profileEndDate
    ) {
        this.setProfileName(newString);
        this.setWeight(newInteger);
        this.setProfileStartDate(profileStartDate);
        this.setProfileEndDate(profileEndDate);
        return this;
    }

    public String default0UpdateProfile() {
        return getProfileName();
    }

    public Integer default1UpdateProfile() {
        return getWeight();
    }

    public LocalDate default2UpdateProfile() {
        return getProfileStartDate();
    }

    public LocalDate default3UpdateProfile() {
        return getProfileEndDate();
    }
    //-- updateProfile --//

    //** deleteProfile **//
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Demand deleteProfile(
            @ParameterLayout(named = "confirmDelete")
            @Parameter(optionality = Optionality.OPTIONAL)
            boolean confirmDelete) {
        // first delete related data: all persisted matches
        for (Iterator<ProfileMatch> it = profileMatches.findProfileMatchesByDemandProfile(this).iterator(); it.hasNext(); ) {
            container.remove(it.next());
            container.informUser("ProfileMatch deleted");
        }
        container.removeIfNotAlready(this);
        container.informUser("Profile deleted");
        return this.getDemandProfileOwner();

    }

    public String validateDeleteProfile(boolean confirmDelete) {
        return confirmDelete ? null : "CONFIRM_DELETE";
    }

    public boolean hideDeleteProfile(boolean confirmDelete) {

        if (this.getDemandOrSupply() == DemandOrSupply.DEMAND) {
            return false;
        }
        return true;
    }

    //-- deleteProfile --//


    //-- API: ACTIONS --//

    //** GENERIC OBJECT STUFF **//
    //** constructor **//
    public Profile() {
        super("profileName, profileType, ownedBy, uniqueItemId");
    }

    //** ownedBy - Override for secure object **//
    private String ownedBy;

    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    //-- GENERIC OBJECT STUFF --//

    //** HELPERS **//
    //** HELPERS: generic object helpers **//
    public String toString() {
        return "Profiel: " + this.profileName;
    }
    //-- HELPERS: generic object helpers --//
    //** HELPERS: programmatic actions **//

    @Programmatic
    // help for fixtures
    public List<ProfileElement> findProfileElementByOwnerProfileAndDescription(final String profileElementDescription) {
        return profileElements.findProfileElementByOwnerProfileAndDescription(profileElementDescription, this);
    }

    //-- HELPERS: programmatic actions --//
    //-- HELPERS --//

    //** INJECTIONS **//

    @javax.inject.Inject
    private DomainObjectContainer container;

    @Inject
    DropDownForProfileElements dropDownForProfileElements;

    @Inject
    ProfileElementDropDowns profileElementDropDowns;

    @Inject
    ProfileElementTags profileElementTags;

    @Inject
    ProfileElementTimePeriods profileElementTimePeriods;

    @Inject
    ProfileElementUsePredicates profileElementUsePredicates;

    @Inject
    Tags tags;

    @Inject
    TagCategories tagCategories;

    @Inject
    ProfileElementDropDownAndTexts profileElementDropDownsAndTexts;

    @Inject
    ProfileElementTexts profileElementTexts;

    @Inject
    ProfileElementNumerics profileElementNumerics;

    @Inject
    ProfileElements profileElements;

    @Inject
    ProfileMatches profileMatches;


    @Inject
    ProfileElementBooleans profileElementBooleans;
    //-- INJECTIONS --//

    //** HIDDEN: PROPERTIES **//

    private ProfileTypeMatchingRule profileTypeMatchingRule;

    @javax.jdo.annotations.Column(allowsNull = "true")
//    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileTypeMatchingRule getProfileTypeMatchingRule() {
        return profileTypeMatchingRule;
    }

    public void setProfileTypeMatchingRule(ProfileTypeMatchingRule canMatchProfileOfType) {
        this.profileTypeMatchingRule = canMatchProfileOfType;
    }

    //-- HIDDEN: PROPERTIES --//

    //** HIDDEN: ACTIONS **//

    @ActionLayout(hidden = Where.EVERYWHERE)
    public Profile newProfileElementDropDown(
            final Integer weight,
            final DropDownForProfileElement dropDown
    ) {
        profileElementDropDowns.createProfileElementDropDown(
                "kwaliteit " + dropDown.title(),
                weight,
                dropDown,
                ProfileElementType.QUALITY,
                this);
        return this;
    }

    public List<DropDownForProfileElement> autoComplete1NewProfileElementDropDown(String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }


    @ActionLayout(hidden = Where.EVERYWHERE)
    public ProfileElementDropDownAndText newProfileElementDropDownAndText(
            final String description,
            final Integer weight,
            @Parameter(optionality = Optionality.OPTIONAL)
            final DropDownForProfileElement dropDown,
            @Parameter(optionality = Optionality.OPTIONAL)
            final String text
    ) {
        return profileElementDropDownsAndTexts.createProfileElementDropDownAndText(
                description,
                weight,
                dropDown,
                text,
                ProfileElementType.QUALITY,
                this);
    }

    public List<DropDownForProfileElement> autoComplete2NewProfileElementDropDownAndText(String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }

    @ActionLayout(hidden = Where.EVERYWHERE)
    public ProfileElementText newProfileElementText(
            final String description,
            final Integer weight,
            final String textValue
    ) {
        return profileElementTexts.createProfileElementText(
                description,
                weight,
                textValue,
                ProfileElementType.TEXT,
                this);
    }

    @ActionLayout(hidden = Where.EVERYWHERE)
    public ProfileElementNumeric newProfileElementNumeric(
            final String description,
            final Integer weight,
            final Integer numericValue
    ) {
        return profileElementNumerics.createProfileElementNumeric(
                description,
                weight,
                numericValue,
                ProfileElementType.NUMERIC,
                this);
    }


    //-- HIDDEN: ACTIONS --//

}