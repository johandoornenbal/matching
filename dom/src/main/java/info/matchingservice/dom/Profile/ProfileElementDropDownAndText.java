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

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@DomainObject(editing=Editing.DISABLED)
public class ProfileElementDropDownAndText extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private DropDownForProfileElement optionalDropDownValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(optional=Optionality.TRUE)
    public DropDownForProfileElement getOptionalDropDownValue(){
        return optionalDropDownValue;
    }
    
    public void setOptionalDropDownValue(final DropDownForProfileElement value){
        this.optionalDropDownValue = value;
    }
    
    private String text;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(optional=Optionality.TRUE)
    public String getText(){
        return text;
    }
    
    public void setText(final String text){
        this.text = text;
    }

}
