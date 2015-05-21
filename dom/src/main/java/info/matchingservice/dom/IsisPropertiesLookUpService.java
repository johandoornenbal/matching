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

package info.matchingservice.dom;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(nature = NatureOfService.DOMAIN )
@DomainServiceLayout()
public class IsisPropertiesLookUpService {

    private Map<String, String> properties;

    @PostConstruct
    public void init(final Map<String, String> properties) {
        this.properties = properties;
    }


    public String linkedInClientId() {
        String string = properties.get("LinkedInClientId");
        if (string == null) {
            return "LinkedInClientId not configured in isis.properties";
        }
        return string;
    }


    public String linkedInRedirectUri() {
        String string = properties.get("LinkedInRedirectUri");
        if (string == null) {
            return "LinkedInRedirectUri not configured in isis.properties";
        }
        return string;
    }


    public String linkedInState() {
        String string = properties.get("LinkedInState");
        if (string == null) {
            return "LinkedInState not configured in isis.properties";
        }
        return string;
    }


    public String linkedInClientSecret() {
        String string = properties.get("LinkedInClientSecret");
        if (string == null) {
            return "LinkedInClientSecret not configured in isis.properties";
        }
        return string;
    }

    public String SignUpUri() {
        String string = properties.get("SignUpUri");
        if (string == null) {
            return "SignUpUri not configured in isis.properties";
        }
        return string;
    }

    public String FbClientId() {
        String string = properties.get("FbClientId");
        if (string == null) {
            return "FbClientId not configured in isis.properties";
        }
        return string;
    }


    public String FbRedirectUri() {
        String string = properties.get("FbRedirectUri");
        if (string == null) {
            return "FbRedirectUri not configured in isis.properties";
        }
        return string;
    }


    public String FbScope() {
        String string = properties.get("FbScope");
        if (string == null) {
            return "FbScope not configured in isis.properties";
        }
        return string;
    }


    public String FbClientSecret() {
        String string = properties.get("FbClientSecret");
        if (string == null) {
            return "FbClientSecret not configured in isis.properties";
        }
        return string;
    }

    public String FbAppSecretProof() {
        String string = properties.get("FbAppSecretProof");
        if (string == null) {
            return "FbAppSecretProof not configured in isis.properties";
        }
        return string;
    }

}
