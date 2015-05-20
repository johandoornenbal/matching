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

package info.matchingservice.dom.TestLinkedInObjects;

import java.util.Iterator;
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = LinkedInCompanyObject.class)
@DomainServiceLayout(named = "RemoteProfiles")
public class LinkedInCompanyObjects {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "All LinkedIn Companies"
    )
    @MemberOrder(sequence = "1")
    public List<LinkedInCompanyObject> listAll() {
        return container.allInstances(LinkedInCompanyObject.class);
    }
    //endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    @Programmatic
    public LinkedInCompanyObject createLinkedInCompany(
            final LinkedInProfileObject parentObject,
            final Integer id,
            final String industry,
            final String name,
            final String type,
            final String size,
            final String summary
    ) {
        final LinkedInCompanyObject obj = container.newTransientInstance(LinkedInCompanyObject.class);
        obj.setId(id);
        obj.setIndustry(industry);
        obj.setName(name);
        obj.setType(type);
        obj.setSize(size);
        obj.setLinkedInProfile(parentObject);
        obj.setPositionSummary(summary);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    public void removeLinkedInCompanies(){
        for (Iterator<LinkedInCompanyObject> it = this.listAll().iterator(); it.hasNext();){

            container.removeIfNotAlready(it.next());
        }
    }

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
