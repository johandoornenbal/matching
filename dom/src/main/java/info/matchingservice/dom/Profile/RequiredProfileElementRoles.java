/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package info.matchingservice.dom.Profile;

import java.util.UUID;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jonathan on 22-4-15.
 */

@DomainService(repositoryFor = RequiredProfileElementRole.class)
public class RequiredProfileElementRoles extends MatchingDomainService<RequiredProfileElementRole> {


    public RequiredProfileElementRoles() {
        super(RequiredProfileElementRoles.class, RequiredProfileElementRole.class);
    }

    @Programmatic
    public RequiredProfileElementRole createRequiredProfileElementRole(
            final String description,
            final Integer weight,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final boolean student,
            final boolean professional,
            final boolean principal
    ){
        return createRequiredProfileElementRole(description, weight, profileElementType, profileOwner, student, professional, principal, currentUserName());
    }

    // without currentUser (for fixtures)
    @Programmatic
    public RequiredProfileElementRole createRequiredProfileElementRole(
            final String description,
            final Integer weight,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final boolean student,
            final boolean professional,
            final boolean principal,
            final String ownedBy
    ){
        final RequiredProfileElementRole newProfileElement = newTransientInstance(RequiredProfileElementRole.class);
        final UUID uuid=UUID.randomUUID();


        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setWeight(weight);
        newProfileElement.setProfileElementType(profileElementType);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setStudent(student);
        newProfileElement.setProfessional(professional);
        newProfileElement.setPrincipal(principal);
        newProfileElement.setOwnedBy(ownedBy);
        persist(newProfileElement);


        return newProfileElement;
    }


    // Region>helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
