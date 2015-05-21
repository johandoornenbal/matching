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

package info.matchingservice.dom.TestFacebookObjects;

import java.net.MalformedURLException;
import java.net.URL;
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

import org.isisaddons.services.remoteprofiles.FacebookProfile;
import org.isisaddons.services.remoteprofiles.FacebookProfileService;

@DomainService(repositoryFor = FacebookProfileObject.class)
@DomainServiceLayout(named = "RemoteProfiles")
public class FacebookProfileObjects {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "All Facebook Profiles"
    )
    @MemberOrder(sequence = "1")
    public List<FacebookProfileObject> listAll() {
        return container.allInstances(FacebookProfileObject.class);
    }
    //endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    @Programmatic
    public FacebookProfileObject createFacebookProfile(
            final @ParameterLayout(named="Facebook Token")  String token
    		) {
        final FacebookProfileObject obj = container.newTransientInstance(FacebookProfileObject.class);
        FacebookProfileService service = new FacebookProfileService();
        FacebookProfile profile = service.runFacebookService(token);
        if (profile.getId()!= null) {
            obj.setId(profile.getId());
        }
        if (profile.getFirst_name()!= null) {
            obj.setFirstName(profile.getFirst_name());
        }
        if (profile.getLast_name()!=null) {
            obj.setLastName(profile.getLast_name());
        }
        if (profile.getGender()!=null) {
            obj.setGender(profile.getGender());
        }
        if (profile.getEmail()!=null) {
            obj.setEmailAddress(profile.getEmail());
        }
        if (profile.getName()!=null) {
            obj.setName(profile.getName());
        }
        if (profile.getLink()!=null) {
            try {
                obj.setPublicProfileUrl(new URL(profile.getLink()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    public void removeFacebookProfiles(){
        for (Iterator<FacebookProfileObject> it = this.listAll().iterator(); it.hasNext();){

            container.removeIfNotAlready(it.next());
        }
    }

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
