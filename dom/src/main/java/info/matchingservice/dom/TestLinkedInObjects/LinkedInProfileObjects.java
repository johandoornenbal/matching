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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.services.remoteprofiles.LinkedInPositionValueResource;
import org.isisaddons.services.remoteprofiles.LinkedInProfile;
import org.isisaddons.services.remoteprofiles.LinkedInProfileService;

@DomainService(repositoryFor = LinkedInProfileObject.class)
@DomainServiceLayout(named = "RemoteProfiles")
public class LinkedInProfileObjects {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "All LinkedIn Profiles"
    )
    @MemberOrder(sequence = "1")
    public List<LinkedInProfileObject> listAll() {
        return container.allInstances(LinkedInProfileObject.class);
    }
    //endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    @Programmatic
    public LinkedInProfileObject createLinkedInProfile(
            final @ParameterLayout(named="LinkedInToken")  String token
    		) {
        final LinkedInProfileObject obj = container.newTransientInstance(LinkedInProfileObject.class);
        LinkedInProfileService service = new LinkedInProfileService();
        LinkedInProfile profile = service.runLinkedInService(token);
        if (profile.getId()!= null) {
            obj.setId(profile.getId());
        }
        if (profile.getFirstName()!= null) {
            obj.setFirstName(profile.getFirstName());
        }
        if (profile.getLastName()!=null) {
            obj.setLastName(profile.getLastName());
        }
        if (profile.getHeadline()!=null) {
            obj.setHeadline(profile.getHeadline());
        }
        if (profile.getEmailAddress()!=null) {
            obj.setEmailAddress(profile.getEmailAddress());
        }
        if (profile.getFormattedName()!=null) {
            obj.setFormattedName(profile.getFormattedName());
        }
        if (profile.getSummary()!=null) {
            obj.setSummary(profile.getSummary());
        }
        if (profile.getIndustry()!=null) {
            obj.setIndustry(profile.getIndustry());
        }
        if (profile.getPictureUrl()!=null) {
            obj.setPictureUrl(profile.getPictureUrl());
        }
        if (profile.getPublicProfileUrl()!=null) {
            obj.setPublicProfileUrl(profile.getPublicProfileUrl());
        }

        for (Iterator<LinkedInPositionValueResource> it = profile.getPositions().getValues().iterator(); it.hasNext();) {

            LinkedInCompanyObject companyObject = container.newTransientInstance(LinkedInCompanyObject.class);
            LinkedInPositionValueResource resource = it.next();

            companyObject.setLinkedInProfile(obj);
            try {
                companyObject.setStartMonth(resource.getStartDate().getMonth());
            } catch (Exception e) {
                companyObject.setStartMonth(0);
            }
            try {
                companyObject.setStartYear(resource.getStartDate().getYear());
            } catch (Exception e) {
                companyObject.setStartYear(0);
            }
            try {
                companyObject.setIsCurrent(resource.getIsCurrent());
            } catch (Exception e) {
                companyObject.setIsCurrent(false);
            }
            try {
                companyObject.setJobTitle(resource.getTitle());
            } catch (Exception e) {
                companyObject.setJobTitle("");
            }
            try {
                companyObject.setPositionSummary(resource.getSummary());
            } catch (Exception e) {
                companyObject.setPositionSummary("");
            }
            try {
                companyObject.setPositionSummary(resource.getSummary());
            } catch (Exception e) {
                companyObject.setPositionSummary("");
            }
            try {
                companyObject.setName(resource.getCompany().getName());
            } catch (Exception e) {
                companyObject.setName("");
            }
            try {
                companyObject.setIndustry(resource.getCompany().getIndustry());
            } catch (Exception e) {
                companyObject.setIndustry("");
            }

            try {
                companyObject.setSize(resource.getCompany().getSize());
            } catch (Exception e) {
                companyObject.setSize("");
            }

            try {
                companyObject.setType(resource.getCompany().getType());
            } catch (Exception e) {
                companyObject.setType("");
            }

            try {
                companyObject.setId(resource.getCompany().getId());
            } catch (Exception e) {
                companyObject.setId(0);
            }

            container.persistIfNotAlready(companyObject);
        }

        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    public void removeLinkedInProfiles(){
        for (Iterator<LinkedInProfileObject> it = this.listAll().iterator(); it.hasNext();){

            container.removeIfNotAlready(it.next());
        }
    }

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
