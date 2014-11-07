/*
 *
 *  Copyright 2014 YODO international projects and consultancy
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
package nl.socrates.dom.party;

import java.util.List;

import com.google.common.collect.Lists;

import nl.socrates.dom.utils.StringUtils;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import nl.socrates.dom.SocratesDomainService;

@DomainService(menuOrder = "20", repositoryFor = SocParty.class)
@Named("Contacten")
public class SocParties extends SocratesDomainService<SocParty>{
    public SocParties() {
        super(SocParties.class, SocParty.class);
    }
    
    // //////////////////////////////////////

    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "6")
    @Named("Vind contacten")
    public List<SocParty> findParties(
            final @Named("Referentie of Naam") @DescribedAs("Je kunt wildcards '*' and '?' gebruiken.") 
            String referenceOrName) {
        return allMatches("matchByReferenceOrName", 
                "referenceOrName", StringUtils.wildcardToCaseInsensitiveRegex(referenceOrName));
    }

    @Hidden
    @ActionSemantics(Of.SAFE)
    public SocParty matchPartyByReferenceOrName(final String referenceOrName) {
        return firstMatch("matchByReferenceOrName", 
                "referenceOrName", StringUtils.wildcardToCaseInsensitiveRegex(referenceOrName));
    }

    @Hidden
    @ActionSemantics(Of.SAFE)
    public SocParty findPartyByReference(final String reference) {
        return mustMatch("findByReference", "reference", reference);
    }

    @Hidden
    @ActionSemantics(Of.SAFE)
    public SocParty findPartyByReferenceOrNull(final String reference) {
        return firstMatch("findByReference", "reference", reference);
    }

    // //////////////////////////////////////

    @Hidden
    public List<SocParty> autoComplete(final String searchPhrase) {
        return searchPhrase.length() > 2
                ? findParties("*" + searchPhrase + "*")
                : Lists.<SocParty> newArrayList();
    }

    // //////////////////////////////////////

    @Prototype
    @ActionSemantics(Of.SAFE)
    @MemberOrder(name = "Contacten", sequence = "99")
    @Named("Alle Contacten")
    public List<SocParty> allParties() {
        return allInstances();
    }
}
