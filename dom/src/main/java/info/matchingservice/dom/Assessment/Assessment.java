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
package info.matchingservice.dom.Assessment;

import java.util.UUID;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(editing=Editing.DISABLED)
public class Assessment extends MatchingSecureMutableObject<Assessment> {
	
	//** API: PROPERTIES **//
	
	//** uniqueItemId **//
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //-- uniqueItemId --//
    
    //** target **//
    /**
     * Should be overridden for use on specific Object Type
     */
    private Object targetOfAssessment;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public Object getTargetOfAssessment() {
        return targetOfAssessment;
    }
    
    public void setTargetOfAssessment(final Object object) {
        this.targetOfAssessment = object;
    }
    //-- target --//
    
    //** targetOwnerActor **//
    private Actor targetOwnerActor;
    
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Actor getTargetOwnerActor(){
        return targetOwnerActor;
    }
    
    public void setTargetOwnerActor(final Actor owner) {
        this.targetOwnerActor = owner;
    }
    //-- targetOwnerActor --//	
    
    //** ownerActor **//
    private Actor assessmentOwnerActor;
    
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Actor getAssessmentOwnerActor(){
        return assessmentOwnerActor;
    }
    
    public void setAssessmentOwnerActor(final Actor owner) {
        this.assessmentOwnerActor = owner;
    }
    //-- ownerActor --//
    
    //** assessmentDescription **//
    private String assessmentDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout()
    public String getAssessmentDescription() {
        return assessmentDescription;
    }
    
    public void setAssessmentDescription(final String assessmentDescription){
        this.assessmentDescription = assessmentDescription;
    }
    //-- assessmentDescription --//

	//-- API: PROPERTIES --//
	//** API: COLLECTIONS **//
	//-- API: COLLECTIONS --//
	//** API: ACTIONS **//
    
    //** deleteAssessment **//
    @ActionLayout()
    public void deleteAssessment(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optional=Optionality.TRUE)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Assessment verwijderd");
    }
    
    public String validateDeleteAssessment(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    //-- deleteAssessment --//
    
	//-- API: ACTIONS --//
	//** GENERIC OBJECT STUFF **//
	//** constructor **//
    public Assessment() {
        super("assessmentDescription, ownedBy, uniqueItemId");
    }
	//** ownedBy - Override for secure object **//
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
	//-- GENERIC OBJECT STUFF --//
	//** HELPERS **//
    //** HELPERS: generic object helpers **//
    public String title() {
        return getTargetOfAssessment().toString() + " - " + getAssessmentDescription();
    }
	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
	//** INJECTIONS **//
    @javax.inject.Inject
    private DomainObjectContainer container;
	//-- INJECTIONS --//
	//** HIDDEN: PROPERTIES **//
	//-- HIDDEN: PROPERTIES --//
	//** HIDDEN: ACTIONS **//
	//-- HIDDEN: ACTIONS --//
}

