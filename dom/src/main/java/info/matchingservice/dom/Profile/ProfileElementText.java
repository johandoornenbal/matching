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
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementText "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
public class ProfileElementText extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private String textValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true", length=2048)
    @Property(maxLength=2048)
    @PropertyLayout(multiLine=10)
    public String getTextValue(){
        return textValue;
    }
    
    public void setTextValue(final String value){
        this.textValue = value;
    }
    
    
    // BUSINESS RULE
    // Alleen op type PASSION (Anders komt deze ook bij bijvoorbeeld Postcode)
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public ProfileElement updatePassion(
    		@ParameterLayout(
    				named = "textValue",
    				multiLine = 8
    				)
            String textValue
            ){
        this.setTextValue(textValue);
        this.setDisplayValue(textValue);
        return this;
    }
    
    public String default0UpdatePassion() {
        return getTextValue();
    }
    
    public boolean hideUpdatePassion(String textValue){
    	if (getProfileElementType() == ProfileElementType.PASSION){
    		return false;
    	}
    	
    	return true;
    }
    
    // BUSINESS RULE
    // Alleen op type LPOCATION (Anders komt deze ook bij bijvoorbeeld Passie)
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public ProfileElement updateLocation(
    		@ParameterLayout(
    				named = "postcode"
    				)
    		@Parameter(regexPattern="^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")
            String textValue
            ){
        this.setTextValue(textValue);
        this.setDisplayValue(textValue);
        return this;
    }
    
    public String default0UpdateLocation() {
        return getTextValue();
    }
    
    public boolean hideUpdateLocation(String textValue){
    	if (getProfileElementType() == ProfileElementType.LOCATION){
    		return false;
    	}
    	
    	return true;
    }

}
