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
import info.matchingservice.dom.Utils.StringUtils;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;


@DomainService(repositoryFor = Organisation.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(named="Organisaties", menuOrder="15")
public class Organisations extends MatchingDomainService<Organisation> {
    
    public Organisations() {
        super(Organisations.class, Organisation.class);
    }
    
    @ActionLayout(named="Maak een organisatie aan in het systeem", hidden=Where.EVERYWHERE)
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Organisation createOrganisation(
            @ParameterLayout(named="organisationName")
            final String organisationName
            ) {
        return createOrganisation(organisationName, currentUserName()); // see region>helpers
    }
    
    @MemberOrder(sequence="5")
    @Action(semantics=SemanticsOf.SAFE)
    @ActionLayout(named="Dit zijn jouw organisaties ...", hidden=Where.ANYWHERE)
    public List<Organisation> activePersonsOrganisations() {
        return activePersonsOrganisations(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    @Action(semantics=SemanticsOf.SAFE)
    @ActionLayout(named="Alle organisaties", hidden=Where.ANYWHERE)
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
        final UUID uuid=UUID.randomUUID();
        organisation.setUniqueItemId(uuid);
        organisation.setOrganisationName(organisationName);
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
}
