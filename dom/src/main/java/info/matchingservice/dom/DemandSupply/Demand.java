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

package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.DemandAssessment;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.value.Blob;


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
            name = "findDemandByOwnedByAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Demand "
                    + "WHERE ownedBy == :ownedBy && demandType == :demandType")                  
})
@DomainObject(editing=Editing.DISABLED)
public class Demand extends MatchingSecureMutableObject<Demand> {

    public Demand() {
        super("demandDescription, weight, ownedBy, uniqueItemId");
    }
    
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }

    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
  
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    //Immutables /////////////////////////////////////////////////////////////////////////////////////
    
    private Actor demandOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(named="Eigenaar")
    public Actor getDemandOwner() {
        return demandOwner;
    }
    
    public void setDemandOwner(final Actor needOwner) {
        this.demandOwner = needOwner;
    }
    
    @Programmatic
    public Actor getProfileOwnerIsOwnedBy(){
        return getDemandOwner();
    }
    
    private DemandSupplyType demandType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public DemandSupplyType getDemandType(){
        return demandType;
    }
    
    public void setDemandType(final DemandSupplyType demandType){
        this.demandType = demandType;
    }
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String demandDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getDemandDescription(){
        return demandDescription;
    }
    
    public void setDemandDescription(final String description) {
        this.demandDescription = description;
    }
    
    private String demandSummary;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(multiLine=4)
    public String getDemandSummary() {
		return demandSummary;
	}

	public void setDemandSummary(String demandSummary) {
		this.demandSummary = demandSummary;
	}
	

	private String demandStory;
    
	@javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(multiLine=8)
	public String getDemandStory() {
		return demandStory;
	}

	public void setDemandStory(String demandStory) {
		this.demandStory = demandStory;
	}
	
	private Blob demandAttachment;
	
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "demandAttachment_name"),
            @javax.jdo.annotations.Column(name = "demandAttachment_mimetype"),
            @javax.jdo.annotations.Column(name = "demandAttachment_bytes", jdbcType = "BLOB", sqlType = "BLOB")
    })
    @Property(
            optional = Optionality.TRUE
    )
	public Blob getDemandAttachment(){
		return demandAttachment;
	}
	
	public void setDemandAttachment(Blob demandAttachment) {
		this.demandAttachment = demandAttachment;
	}

    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }

    //ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////

    public Demand editDemand(
            @ParameterLayout(named="demandDescription")
            final String demandDescription,
            @ParameterLayout(named="demandSummary", multiLine=3)
            @Parameter(optional=Optionality.TRUE)
            final String demandSummary,
            @ParameterLayout(named="demandStory", multiLine=8)
            @Parameter(optional=Optionality.TRUE)
            final String demandStory,
            @ParameterLayout(named="demandAttachment")
            @Parameter(optional=Optionality.TRUE)
            final Blob demandAttachment         
            ){
        this.setDemandDescription(demandDescription);
        this.setDemandSummary(demandSummary);
        this.setDemandStory(demandStory);
        this.setDemandAttachment(demandAttachment);
        return this;
    }
    
    public String default0EditDemand(){
        return this.getDemandDescription();
    }
    
    public String default1EditDemand(){
        return this.getDemandSummary();
    }
    
    public String default2EditDemand(){
        return this.getDemandStory();
    }
    
    public Blob default3EditDemand(){
        return this.getDemandAttachment();
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Demand editWeight(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        this.setWeight(weight);
        return this;
    }
    
    public Integer default0EditWeight(){
        return this.getWeight();
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @ActionLayout(named="Vraag verwijderen")
    public Actor DeleteDemand(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Demand deleted");
        return getDemandOwner();
    }
    
    public String validateDeleteDemand(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////

    
    // Region> Vacancies
    
    private SortedSet<Profile> demandProfiles = new TreeSet<Profile>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demandProfileOwner", dependentElement = "true")
    public SortedSet<Profile> getDemandProfiles() {
        return demandProfiles;
    }
    
    public void setDemandProfiles(final SortedSet<Profile> vac){
        this.demandProfiles = vac;
    }
    
    //XTALUS 
    //Nieuwe cursus gezocht
    @ActionLayout(named="Nieuwe cursus zoeken")
    public Profile newCourseDemandProfile(
            @ParameterLayout(named="profileName", multiLine=4)
            final  String demandProfileDescription
            ){
        return newDemandProfile(demandProfileDescription, 10, ProfileType.COURSE_PROFILE, this, currentUserName());
    }
    
    // BUSINESS RULE voor hide en validate van de aktie 'nieuw cursus gezocht'
    // alleen tonen op demand van type cursus
    
    public boolean hideNewCourseDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
            return true;
        }
        
        return false;
    }
    
    public String validateNewCourseDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
            return "Alleen op type CURSUS";
        }
        
        return null;
    }    

    //XTALUS 
    //Nieuwe persoon gezocht
    @ActionLayout(named="Nieuwe persoon zoeken")
    public Profile newPersonDemandProfile(
            @ParameterLayout(named="profileName")
            final  String demandProfileDescription
            ){
        return newDemandProfile(demandProfileDescription, 10, ProfileType.PERSON_PROFILE, this, currentUserName());
    }
    
    // BUSINESS RULE voor hide en validate van de aktie 'nieuw persoon gezocht'
    // alleen tonen op demand van type persoon
    
    public boolean hideNewPersonDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return true;
        }
        
        return false;
    }
    
    public String validateNewPersonDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return "Alleen op type PERSOON";
        }
        
        return null;
    }    
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Profile newDemandProfile(
            final  String demandProfileDescription,
            final Integer weight 
            ) {
        return newDemandProfile(demandProfileDescription, weight, ProfileType.PERSON_PROFILE, this, currentUserName());
    }
    
    
    @Programmatic
    public Profile newDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final ProfileType profileType,
            final Demand demandProfileOwner, 
            final String ownedBy) {
        return allDemandProfiles.newDemandProfile(demandProfileDescription, weight, profileType, demandProfileOwner, ownedBy);
    }
    
    // Region> Assessments
    
    private SortedSet<DemandAssessment> assessments = new TreeSet<DemandAssessment>();
    
    @CollectionLayout(render=RenderType.EAGERLY, named="Assessments")
    @Persistent(mappedBy = "target", dependentElement = "true")
    public SortedSet<DemandAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<DemandAssessment> assessment) {
        this.assessments = assessment;
    }
    
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  

    // Helpers
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getDemandDescription() + " - " + getDemandOwner().title();
    }
    
    // Injects

	@javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Profiles allDemandProfiles;
    
}
