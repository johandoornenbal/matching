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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementTag "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType"),
    @javax.jdo.annotations.Query(
            name = "findProfileElementNumericByOwnerProfile", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementNumeric "
                    + "WHERE profileElementOwner == :profileElementOwner")
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileElementNumeric extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private Integer numericValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getNumericValue(){
        return numericValue;
    }
    
    public void setNumericValue(final Integer value){
        this.numericValue = value;
    }
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public ProfileElementNumeric updateAge(
            @ParameterLayout(named="Age")
            final Integer value,
            @ParameterLayout(named="Weight")
            final Integer weight
            ){
        this.setNumericValue(value);
        this.setDisplayValue(value.toString());
        this.setWeight(weight);
        return this;
    }
    
    public Integer default0UpdateAge(){
        return this.getNumericValue();
    }
    
    public Integer default1UpdateAge(){
        return this.getWeight();
    }
    
    public boolean hideUpdateAge() {
    	
    	if (this.getProfileElementType() == ProfileElementType.AGE){
    		return false;
    	}
    	
    	return true;
    }

    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public ProfileElementNumeric updateHourlyRate(
            @ParameterLayout(named="Hourly rate")
            final Integer value,
            @ParameterLayout(named="Weight")
            final Integer weight
    ){
        this.setNumericValue(value);
        this.setDisplayValue(value.toString());
        this.setWeight(weight);
        return this;
    }

    public Integer default0UpdateHourlyRate(){
        return this.getNumericValue();
    }

    public Integer default1UpdateHourlyRate(){
        return this.getWeight();
    }

    public boolean hideUpdateHourlyRate() {

        if (this.getProfileElementType() == ProfileElementType.HOURLY_RATE){
            return false;
        }

        return true;
    }


}
