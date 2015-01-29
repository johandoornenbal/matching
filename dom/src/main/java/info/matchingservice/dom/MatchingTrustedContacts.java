/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = MatchingTrustedContact.class)
@DomainServiceLayout(named="Contacts", menuOrder="40")
public class MatchingTrustedContacts extends MatchingDomainService<MatchingTrustedContact> {
    
    public MatchingTrustedContacts() {
        super(MatchingTrustedContacts.class, MatchingTrustedContact.class);
    }
    
    @MemberOrder(sequence = "30")
    public List<MatchingTrustedContact> allContacts() {
        return allInstances();
    }
    
    //hide except for admin
    public boolean hideAllContacts() {
        if (container.getUser().hasRole(".*matching-admin")) {
            return false;
        }
        return true;
    }
    
    TrustLevel trustLevel(String ownedBy, String userName) {
        QueryDefault<MatchingTrustedContact> q =
                QueryDefault.create(
                        MatchingTrustedContact.class, 
                        "findMatchingTrustedUniqueContact",
                        "ownedBy", ownedBy,
                        "contact", userName);
                if (container.allMatches(q).isEmpty()) {
                    return null;
                }
                if (!container.allMatches(q).isEmpty()) {
                    TrustLevel rights = container.firstMatch(q).getTrustLevel();
                    return rights;
                }
        return null;
    }


    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
