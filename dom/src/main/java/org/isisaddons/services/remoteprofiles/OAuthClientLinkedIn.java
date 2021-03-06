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

package org.isisaddons.services.remoteprofiles;

import java.io.IOException;
import java.net.URL;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * From Simple example that shows how to get OAuth 2.0 access LinkedInToken from Linked In
 * using Amber OAuth 2.0 library
 *
 */
public class OAuthClientLinkedIn {

    public static URL LinkedInOAuth(
            final String clientId,
            final String redirectUri,
            final String state
    ) throws OAuthSystemException, IOException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationProvider(OAuthProviderType.LINKEDIN)
                .setClientId(clientId)
                .setRedirectURI(redirectUri)
                .setResponseType("code")
                .setState(state)
                .buildQueryMessage();

        System.out.println(request.getLocationUri());

        return java.net.URI.create(request.getLocationUri()).toURL();
    }

}