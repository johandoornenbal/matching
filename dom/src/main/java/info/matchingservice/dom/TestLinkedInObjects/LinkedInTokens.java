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

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

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

import org.isisaddons.services.remoteprofiles.OAuthClientLinkedIn;

@DomainService(repositoryFor = LinkedInToken.class)
@DomainServiceLayout(menuOrder = "10", named = "RemoteProfiles")
public class LinkedInTokens {

    private Map<String, String> properties;

    @Programmatic
    @PostConstruct
    public void init(final Map<String, String> properties) {
        this.properties = properties;
    }

    @Programmatic
    public String linkedInClientId() {
        String string = properties.get("LinkedInClientId");
        if (string == null) {
            return "LinkedInClientId not configured in isis.properties";
        }
        return string;
    }

    @Programmatic
    public String linkedInRedirectUri() {
        String string = properties.get("LinkedInRedirectUri");
        if (string == null) {
            return "LinkedInRedirectUri not configured in isis.properties";
        }
        return string;
    }

    @Programmatic
    public String linkedInState() {
        String string = properties.get("LinkedInState");
        if (string == null) {
            return "LinkedInState not configured in isis.properties";
        }
        return string;
    }

    @Programmatic
    public String linkedInClientSecret() {
        String string = properties.get("LinkedInClientSecret");
        if (string == null) {
            return "LinkedInClientSecret not configured in isis.properties";
        }
        return string;
    }


    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "All LinkedIn Tokens"
    )
    @MemberOrder(sequence = "1")
    public List<LinkedInToken> listAll() {
        return container.allInstances(LinkedInToken.class);
    }
    //endregion


    //region > create (action)
    @Programmatic
    public LinkedInToken create(
            final @ParameterLayout(named="LinkedInToken") String token) {
        final LinkedInToken obj = container.newTransientInstance(LinkedInToken.class);
        obj.setToken(token);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    @ActionLayout(named = "Get LinkedIn Profile")
    @MemberOrder(sequence = "3")
    public URL startLinkedOAuth() throws IOException, OAuthSystemException {
        try {
            return OAuthClientLinkedIn.LinkedInOAuth(this.linkedInClientId(), this.linkedInRedirectUri(), this.linkedInState());
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Programmatic
    public LinkedInProfileObject createLinkedInProfile(String token) {

        return linkedInProfileObjects.createLinkedInProfile(token);

    }

    public void removeAllTokens(){
        for (Iterator<LinkedInToken> it = this.listAll().iterator(); it.hasNext();){

            container.removeIfNotAlready(it.next());
        }
    }

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    @Inject
    LinkedInProfileObjects linkedInProfileObjects;

    //endregion
}
