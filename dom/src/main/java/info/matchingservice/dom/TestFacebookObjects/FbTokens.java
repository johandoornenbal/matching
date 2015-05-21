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

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

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

import org.isisaddons.services.remoteprofiles.OAuthClientFacebook;

import info.matchingservice.dom.IsisPropertiesLookUpService;

@DomainService(repositoryFor = FbToken.class)
@DomainServiceLayout(menuOrder = "10", named = "RemoteProfiles")
public class FbTokens {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "All Facebook Tokens"
    )
    @MemberOrder(sequence = "1")
    public List<FbToken> listAll() {
        return container.allInstances(FbToken.class);
    }
    //endregion


    //region > create (action)
    @Programmatic
    public FbToken create(
            final @ParameterLayout(named="FbToken") String token) {
        final FbToken obj = container.newTransientInstance(FbToken.class);
        obj.setToken(token);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    @ActionLayout(named = "Get Facebook Profile")
    @MemberOrder(sequence = "3")
    public URL startFbOAuth() throws IOException, OAuthSystemException {
        try {
            return OAuthClientFacebook.FacebookOAuth(isisPropertiesLookUpService.FbClientId(), isisPropertiesLookUpService.FbRedirectUri(), isisPropertiesLookUpService.FbScope());
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Programmatic
    public FacebookProfileObject createFbProfile(String token) {

        return facebookProfileObjects.createFacebookProfile(token);

    }

    public void removeAllTokens(){
        for (Iterator<FbToken> it = this.listAll().iterator(); it.hasNext();){

            container.removeIfNotAlready(it.next());
        }
    }

    //region > injected services

    @Inject
    DomainObjectContainer container;

    @Inject
    FacebookProfileObjects facebookProfileObjects;

    @Inject
    IsisPropertiesLookUpService isisPropertiesLookUpService;

    //endregion
}
