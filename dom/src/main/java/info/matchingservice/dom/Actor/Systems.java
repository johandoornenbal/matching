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
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;

import javax.inject.Inject;
import java.util.List;


@DomainService(repositoryFor = System.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(menuOrder="18")
public class Systems extends MatchingDomainService<System> {
    
    public Systems() {
        super(Systems.class, System.class);
    }
    
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public System createSystem(
            @ParameterLayout(named="systemName")
            final String systemName
            ) {
        return createSystem(systemName, currentUserName()); // see region>helpers
    }
    
    public boolean hideCreateSystem() {
        return hideCreateSystem(currentUserName());
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateCreateSystem(
            final String systemName
            ) {
        return validateCreateSystem(systemName, currentUserName());
    }
    
    @MemberOrder(sequence="5")
    @ActionLayout()
    public List<System> activePersonsSystems() {
        return activePersonsSystems(currentUserName());
    }
    
    @Programmatic
    public List<System> allSystems() {
        return allInstances();
    }
    
    //endregion
    
   
    
    @Programmatic
    public List<System> findSystems(
            @ParameterLayout(named="systemName")
            final String systemName
            ) {
        return allMatches("matchSystemBySystemName", "systemName", StringUtils.wildcardToCaseInsensitiveRegex(systemName));
    }
    
    @Programmatic
    public List<System> findSystemnameContains(
            @ParameterLayout(named="systemName")
            final String systemName
            ) {
        return allMatches("matchSystemBySystemNameContains", "systemName", systemName);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public System createSystem(           
            final String systemName,
            final String userName) {
        final System system = newTransientInstance(System.class);
        system.setSystemName(systemName);
        system.setDateCreated(clockService.now());
        system.setOwnedBy(userName);
        persist(system);
        return system;
    }
    
    @Programmatic //userName can now also be set by fixtures
    public boolean hideCreateSystem(String userName) {
        QueryDefault<System> query = 
                QueryDefault.create(
                        System.class, 
                    "findSystemUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        true        
        :false;        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateCreateSystem(
            final String systemName,
            final String userName) {
        
        QueryDefault<System> query = 
                QueryDefault.create(
                        System.class, 
                    "findSystemUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public List<System> activePersonsSystems(final String userName) {
        QueryDefault<System> query = 
                QueryDefault.create(
                        System.class, 
                    "findSystemUnique", 
                    "ownedBy", userName);          
        return allMatches(query);
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

    @Inject
    private ClockService clockService;
}
