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

package info.matchingservice.webapp.custom_rest;

import org.apache.commons.codec.binary.Base64;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.runtime.authentication.AuthenticationManager;
import org.apache.isis.core.runtime.authentication.AuthenticationRequestPassword;
import org.apache.isis.core.runtime.authentication.exploration.AuthenticationRequestExploration;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.server.authentication.AuthenticationSessionStrategyBasicAuth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jodo on 17/05/15.
 */
public class CustomAuthenticationSessionStrategyBasicAuth extends AuthenticationSessionStrategyBasicAuth {

    private static Pattern USER_AND_PASSWORD_REGEX = Pattern.compile("^(.+):(.+)$");

    public CustomAuthenticationSessionStrategyBasicAuth() {
    }


    public AuthenticationSession lookupValid(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String digest = this.getBasicAuthDigest(httpServletRequest);
        String uri = httpServletRequest.getRequestURI();


        /********** ADAPT THESE ACCORDING TO DEPLOY ****************************************/

        String compareUriTo = "/simple/restful/v2/actions/register";
        String compareUriTo2 = "/simple/restful/v2/actions/login";
        String compareUriToLegacy = "/simple/restful/register";
        String compareUriToLegacy2 = "/simple/restful/authenticate";


//        String compareUriTo = "/restful/v2/action/register";
//        String compareUriTo2 = "/restful/v2/action/authenticate";
//        String compareUriToLegacy = "/restful/register";
//        String compareUriToLegacy2 = "/restful/authenticate";

        /********** ADAPT THESE ACCORDING TO DEPLOY ****************************************/

        RequestType requestType = RequestType.OTHER;

        /********** REGISTER USER ****************************************/
        String uriSubstring = uri;
        if (uri.length() > compareUriTo.length()) {
            uriSubstring = uri.substring(0, compareUriTo.length());
        }
        if (uriSubstring.equals(compareUriTo)) {
            requestType = RequestType.REGISTER;
        }
        /********** REGISTER USER ****************************************/

        /********** AUTHENTICATE USER ****************************************/
        String uriSubstring2 = uri;
        if (uri.length() > compareUriTo2.length()) {
            uriSubstring2 = uri.substring(0, compareUriTo2.length());
        }
        if (uriSubstring2.equals(compareUriTo2)) {
            requestType = RequestType.AUTHENTICATE;
        }
        /********** AUTHENTICATE USER ****************************************/

        /********** REGISTER USER LEGACY****************************************/
        String uriSubstringL = uri;
        if (uri.length() > compareUriToLegacy.length()) {
            uriSubstringL = uri.substring(0, compareUriToLegacy.length());
        }
        if (uriSubstringL.equals(compareUriToLegacy)) {
            requestType = RequestType.REGISTER;
        }
        /********** REGISTER USER ****************************************/

        /********** AUTHENTICATE USER LEGACY ****************************************/
        String uriSubstringL2 = uri;
        if (uri.length() > compareUriToLegacy2.length()) {
            uriSubstringL2 = uri.substring(0, compareUriToLegacy2.length());
        }
        if (uriSubstringL2.equals(compareUriToLegacy2)) {
            requestType = RequestType.AUTHENTICATE;
        }
        /********** AUTHENTICATE USER ****************************************/

        AuthenticationRequestExploration explorationRequest;
        switch (requestType) {

            case REGISTER:
                explorationRequest = new AuthenticationRequestExploration();
                return IsisContext.getAuthenticationManager().authenticate(explorationRequest);

            case AUTHENTICATE:
                explorationRequest = new AuthenticationRequestExploration();
                return IsisContext.getAuthenticationManager().authenticate(explorationRequest);

            case OTHER:

                /* default original code */
                String userAndPassword = this.unencoded(digest);
                Matcher matcher = USER_AND_PASSWORD_REGEX.matcher(userAndPassword);
                if(!matcher.matches()) {
                    return null;
                } else {
                    String user = matcher.group(1);
                    String password = matcher.group(2);
                    AuthenticationRequestPassword request = new AuthenticationRequestPassword(user, password);
                    AuthenticationSession authSession = this.getAuthenticationManager().authenticate(request);
                    return authSession;
                }
                /* default original code */

            default:
                return null;
        }

    }

    String getBasicAuthDigest(HttpServletRequest httpServletRequest) {
        String authStr = httpServletRequest.getHeader("Authorization");
        return authStr != null && authStr.startsWith("Basic ")?authStr.substring("Basic ".length()):null;
    }

    protected String unencoded(String encodedDigest) {
        return new String((new Base64()).decode(encodedDigest.getBytes()));
    }

    protected AuthenticationManager getAuthenticationManager() {
        return IsisContext.getAuthenticationManager();
    }

    private enum RequestType {
        REGISTER,
        AUTHENTICATE,
        OTHER;
    }
}
