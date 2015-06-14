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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.ProfileElementType;

@DomainService(repositoryFor = DropDownForProfileElement.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class DropDownForProfileElements extends MatchingDomainService<DropDownForProfileElement> {

    public DropDownForProfileElements() {
        super(DropDownForProfileElements.class, DropDownForProfileElement.class);
    }
    
    @Programmatic
    public List<DropDownForProfileElement> allProfileElementDropDowns(){
        return allInstances(DropDownForProfileElement.class);
    }
    
    @Programmatic
    public List<DropDownForProfileElement> findDropDowns(
            final String value,
            final ProfileElementType profileElementType
            ) {
        return allMatches("matchDropDownByKeyWord", "type", profileElementType, "value", value);
    }
    
    @Programmatic
    public DropDownForProfileElement createDropDownForProfileELements(
            final ProfileElementType category,
            final String value
    ){
        final DropDownForProfileElement dropDownForProfileElement = newTransientInstance(DropDownForProfileElement.class);
        dropDownForProfileElement.setType(category);
        dropDownForProfileElement.setValue(value.toLowerCase());
        persist(dropDownForProfileElement);
        container.informUser("Dropdown element " + value + " created");
        return dropDownForProfileElement;
    }

    
    @Inject
    DropDownForProfileElements dropDownForProfileElements;

    @Inject
    DomainObjectContainer container;
}
