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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementDropDownByOwnerProfileAndDropDownValue", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementDropDown "
                    + "WHERE profileElementOwner == :profileElementOwner && dropDownValue == :dropDownValue"),
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementUsePredicate "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileElementDropDown extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private DropDownForProfileElement dropDownValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public DropDownForProfileElement getDropDownValue(){
        return dropDownValue;
    }
    
    public void setDropDownValue(final DropDownForProfileElement value){
        this.dropDownValue = value;
    }
}
