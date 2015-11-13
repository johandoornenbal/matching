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

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.MatchingSecureMutableObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.joda.time.LocalDateTime;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import java.util.UUID;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@DomainObject(editing=Editing.DISABLED, autoCompleteRepository = Assessments.class)
public abstract class Assessment extends MatchingSecureMutableObject<Assessment> {

    public Assessment() {
        super("description, ownedBy, timeStamp, uniqueItemId");
    }
	
	//** API: PROPERTIES **//
	
	//** uniqueItemId **//
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //-- uniqueItemId --//

    //region > timeStamp (property)
    private LocalDateTime timeStamp;

    @MemberOrder(sequence = "1")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    //endregion

    
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
    
    //** description **//
    private String description;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout()
    public String getDescription() {
        return description;
    }
    
    public void setDescription(final String description){
        this.description = description;
    }
    //-- description --//
    
    //** deleteAssessment **//
    @ActionLayout()
    public void deleteAssessment(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Assessment verwijderd");
    }
    
    public String validateDeleteAssessment(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    //-- deleteAssessment --//

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

    public String title() {
        return "Assessment " + " - " + getDescription();
    }

    @javax.inject.Inject
    private DomainObjectContainer container;

}

