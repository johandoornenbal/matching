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

package info.matchingservice.dom.Dropdown;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Profile.ProfileElementType;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "matchDropDownByKeyWord", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Dropdown.DropDownForProfileElement "
                    + "WHERE type == :type && value.indexOf(:value) >= 0")
})
@DomainObject(editing=Editing.DISABLED)
public class DropDownForProfileElement extends MatchingDomainObject<DropDownForProfileElement>{
    
    public DropDownForProfileElement(){
        super("value, category");
    }
    
    public String title(){
        return getValue();
    }
    
    private ProfileElementType type;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElementType getType(){
        return type;
    }
    
    public void setType(final ProfileElementType category){
        this.type = category;
    }
    
    private String value;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getValue(){
        return value;
    }
    
    public void setValue(final String value){
        this.value = value;
    }
}
