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
package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;

import javax.inject.Inject;
import java.util.List;

@DomainService(repositoryFor = Organisation.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(menuOrder="15")
public class Organisations extends MatchingDomainService<Organisation> {
    
    public Organisations() {
        super(Organisations.class, Organisation.class);
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Organisation createOrganisation(
            @ParameterLayout(named="organisationName")
            final String organisationName
            ) {
        return createOrganisation(organisationName, currentUserName()); // see region>helpers
    }
    
    @MemberOrder(sequence="5")
    @Action(semantics=SemanticsOf.SAFE)
    @ActionLayout(hidden=Where.ANYWHERE)
    public List<Organisation> activePersonsOrganisations() {
        return activePersonsOrganisations(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    @Action(semantics=SemanticsOf.SAFE)
    @ActionLayout(hidden=Where.ANYWHERE)
    public List<Organisation> allOrganisations() {
        return allInstances();
    }
    
       
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Organisation createOrganisation(
            @ParameterLayout(named="organisationName")
            final String organisationName,
            final String userName) {
        final Organisation organisation = newTransientInstance(Organisation.class);
        organisation.setOrganisationName(organisationName);
        organisation.setDateCreated(clockService.now());
        organisation.setOwnedBy(userName);
        persist(organisation);
        return organisation;
    }
    
    
    @Programmatic //userName can now also be set by fixtures
    public List<Organisation> activePersonsOrganisations(final String userName) {
        QueryDefault<Organisation> query = 
                QueryDefault.create(
                        Organisation.class, 
                    "findMyOrganisations", 
                    "ownedBy", userName);          
        return allMatches(query);
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

    @Inject
    private ClockService clockService;
}
