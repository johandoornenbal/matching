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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.runtime.authentication.AuthenticationRequestPassword;
import org.apache.isis.core.runtime.authentication.exploration.AuthenticationRequestExploration;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.server.authentication.AuthenticationSessionStrategyBasicAuth;

/**
 * Created by jodo on 17/05/15.
 */
public class CustomAuthenticationSessionStrategyBasicAuth extends AuthenticationSessionStrategyBasicAuth {

    private static Pattern USER_AND_PASSWORD_REGEX = Pattern.compile("^(.+):(.+)$");

    public CustomAuthenticationSessionStrategyBasicAuth() {}

    public AuthenticationSession lookupValid(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String authStr = httpServletRequest.getHeader("Authorization");

        //TODO: does not work because there is no session for this request. Caught in a loop ;-)
//        final IsisPropertiesLookUpService isisPropertiesLookUpService =
//                IsisContext.getPersistenceSession().getServicesInjector().lookupService(IsisPropertiesLookUpService.class);
//        final String signUpUri = isisPropertiesLookUpService.SignUpUri();

        //extension by yodo for signup through REST api
        //TODO: parameterize '/simple/restful/register' (This is dependent on deploy!)
        String uri = httpServletRequest.getRequestURI();
        String uriSubstring = uri.substring(0, 24); /*NOTA BENE: NEEDS TO MATCH LENGHT OF uri /simple/restful/register*/
        System.out.println(uri);
//        System.out.println("from isis properties: " + signUpUri);

        if (uriSubstring.equals("/simple/restful/register")) {

            System.out.println("passes for uri starts with '/simple/restful/register' ");
            AuthenticationRequestExploration request = new AuthenticationRequestExploration();
            return IsisContext.getAuthenticationManager().authenticate(request);


        } else {

            // original code

            if (authStr != null && authStr.startsWith("Basic ")) {
                String digest = authStr.substring(6);
                String userAndPassword = new String((new Base64()).decode(digest.getBytes()));
                Matcher matcher = USER_AND_PASSWORD_REGEX.matcher(userAndPassword);
                if (!matcher.matches()) {
                    return null;
                } else {
                    String user = matcher.group(1);
                    String password = matcher.group(2);
                    AuthenticationSession authSession = this.getAuthenticationManager().authenticate(new AuthenticationRequestPassword(user, password));
                    return authSession;
                }
            } else {

                return null;

            }
        }
    }
}
