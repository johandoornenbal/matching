/**
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.matchingservice.dom.Tags;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Profile.ProfileElement;

import java.util.UUID;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findTagHolder", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Tags.TagHolder "
                    + "WHERE ownerElement == :ownerElement && tag == :tag")                   
})
@DomainObject(editing=Editing.DISABLED)
public class TagHolder extends MatchingSecureMutableObject<TagHolder> {
    
    public TagHolder() {
        super("ownerElement, tag, uniqueItemId");
    }
    
    //** ownedBy - Override for secure object **//
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.NOWHERE)
    @Property(editing=Editing.DISABLED)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
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
    
    private ProfileElement ownerElement;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElement getOwnerElement() {
        return ownerElement;
    }

    public void setOwnerElement(ProfileElement ownerElement) {
        this.ownerElement = ownerElement;    
    }
    
    private Tag tag;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;       
    }

    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public ProfileElement deleteTagHolder(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
    	// administration of tag usage
    	if (getTag().getNumberOfTimesUsed()!=null){
    		if(getTag().getNumberOfTimesUsed()>0){
    			getTag().setNumberOfTimesUsed(getTag().getNumberOfTimesUsed()-1);
    		}
    	}
    	// END administration of tag usage
        container.removeIfNotAlready(this);
        container.informUser("Element verwijderd");
        return getOwnerElement();
    }
    
    public String validateDeleteTagHolder(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
