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

package info.matchingservice.dom.TestObjects;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.services.remoteprofiles.LinkedInProfile;
import org.isisaddons.services.remoteprofiles.LinkedInProfileService;

@DomainService(repositoryFor = LinkedInProfileObject.class)
@DomainServiceLayout(menuOrder = "10")
public class LinkedInProfileObjects {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<LinkedInProfileObject> listAll() {
        return container.allInstances(LinkedInProfileObject.class);
    }
    //endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    public LinkedInProfileObject createLinkedInProfile(
            final @ParameterLayout(named="Token")  String token
    		) {
        final LinkedInProfileObject obj = container.newTransientInstance(LinkedInProfileObject.class);
        LinkedInProfileService service = new LinkedInProfileService();
        LinkedInProfile profile = service.runLinkedInService(token);
        obj.setId(profile.getId());
        obj.setFirstName(profile.getFirstName());
        obj.setLastName(profile.getLastName());
        obj.setHeadline(profile.getHeadline());
        
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
