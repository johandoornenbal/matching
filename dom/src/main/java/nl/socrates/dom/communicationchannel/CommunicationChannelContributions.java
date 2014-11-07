/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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
package nl.socrates.dom.communicationchannel;

import java.util.List;
import java.util.SortedSet;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.Render.Type;
import nl.socrates.dom.SocratesService;
import nl.socrates.dom.geography.Countries;
import nl.socrates.dom.geography.Country;
import nl.socrates.dom.geography.State;
import nl.socrates.dom.geography.States;

/**
 * Domain service that contributes actions to create a new
 * {@link #newPostal(CommunicationChannelOwner, CommunicationChannelType, Country, State, String, String, String, String, String)
 * postal address},
 * {@link #newEmail(CommunicationChannelOwner, CommunicationChannelType, String)
 * email} or
 * {@link #newPhoneOrFax(CommunicationChannelOwner, CommunicationChannelType, String)
 * phone/fax}, and contributes a collection to list the
 * {@link #communicationChannels(CommunicationChannelOwner) communication
 * channels} of a particular {@link CommunicationChannelOwner}.
 */
@DomainService(menuOrder = "70")
@Hidden
public class CommunicationChannelContributions extends SocratesService<CommunicationChannelContributions> {

    public CommunicationChannelContributions() {
        super(CommunicationChannelContributions.class);
    }

    // //////////////////////////////////////

    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "CommunicationChannels", sequence = "1")
    @NotInServiceMenu
    @Named("Nieuw adres")
    // CHECKSTYLE.OFF: ParameterNumber - Wicket viewer does not support
    // aggregate value types
    public CommunicationChannelOwner newPostal(
            final @Named("Eigenaar") CommunicationChannelOwner owner,
            final @Named("Type") CommunicationChannelType type,
            final Country country,
            final @Optional State state,
            final @Named("Adres regel 1") String address1,
            final @Named("Adres regel 2") @Optional String address2,
            final @Named("Adres regel 3") @Optional String address3,
            final @Named("Postcode") String postalCode, final @Named("Plaats") String city
            ) {
        communicationChannels.newPostal(owner, type, address1, address2, null, postalCode, city, state, country);
        return owner;
    }

    // CHECKSTYLE.ON: ParameterNumber

    public List<CommunicationChannelType> choices1NewPostal() {
        return CommunicationChannelType.matching(PostalAddress.class);
    }

    public CommunicationChannelType default1NewPostal() {
        return choices1NewPostal().get(0);
    }

    public Country default2NewPostal() {
        return countries.allCountries().get(0);
    }

    public List<State> choices3NewPostal(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type,
            final Country country) {
        return states.findStatesByCountry(country);
    }

    public State default3NewPostal() {
        final Country country = default2NewPostal();
        final List<State> statesInCountry = states.findStatesByCountry(country);
        return statesInCountry.size() > 0 ? statesInCountry.get(0) : null;
    }

    // //////////////////////////////////////

    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "CommunicationChannels", sequence = "2")
    @NotInServiceMenu
    @Named("Nieuwe email")
    public CommunicationChannelOwner newEmail(
            final @Named("Eigenaar") CommunicationChannelOwner owner,
            final @Named("Type") CommunicationChannelType type,
            final @Named("Adres") String address) {
        communicationChannels.newEmail(owner, type, address);
        return owner;
    }

    public List<CommunicationChannelType> choices1NewEmail() {
        return CommunicationChannelType.matching(EmailAddress.class);
    }

    public CommunicationChannelType default1NewEmail() {
        return choices1NewEmail().get(0);
    }

    public String validateNewEmail(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type,
            final String address) {
        // TODO: validate email address format
        return null;
    }

    // //////////////////////////////////////

    @Named("Nieuw telefoonnummer")
    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "CommunicationChannels", sequence = "3")
    @NotInServiceMenu
    public CommunicationChannelOwner newPhoneOrFax(
            final @Named("Eigenaar") CommunicationChannelOwner owner,
            final @Named("Type") CommunicationChannelType type,
            final @Named("Nummer") String number) {
        communicationChannels.newPhoneOrFax(owner, type, number);
        return owner;
    }

    public List<CommunicationChannelType> choices1NewPhoneOrFax() {
        return CommunicationChannelType.matching(PhoneOrFaxNumber.class);
    }

    public CommunicationChannelType default1NewPhoneOrFax() {
        return choices1NewPhoneOrFax().get(0);
    }

    public String validateNewPhoneOrFax(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type,
            final String number) {
        // TODO: validate phone number format
        return null;
    }

    // //////////////////////////////////////

    @ActionSemantics(Of.SAFE)
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @Render(Type.EAGERLY)
    @Named("Communicatie kanalen")
    public SortedSet<CommunicationChannel> communicationChannels(final CommunicationChannelOwner owner) {
        return communicationChannels.findByOwner(owner);
    }

    /**
     * Hidden if the {@link org.estatio.dom.communicationchannel.CommunicationChannelOwner} is a {@link FixedAsset}.
     *
     * <p>
     * Why? because we intend to remove the ability to associate {@link org.estatio.dom.communicationchannel.CommunicationChannel}s with
     * {@link org.estatio.dom.asset.FixedAsset}s.  See <a href="https://stromboli.atlassian.net/browse/EST-421">EST-421</a> for
     * further discussion.
     * </p>
     */
//    public boolean hideCommunicationChannels(final CommunicationChannelOwner owner) {
//        return owner instanceof FixedAsset;
//    }

    // //////////////////////////////////////

    @NotContributed
    @Programmatic
    public CommunicationChannel findCommunicationChannelForType(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type) {
        final SortedSet<CommunicationChannel> communicationChannels = this.communicationChannels(owner);
        for (CommunicationChannel c : communicationChannels) {
            if (c.getType().equals(type)) {
                return c;
            }
        }
        return null;
    }

    // //////////////////////////////////////

    private CommunicationChannels communicationChannels;

    public void injectCommunicationChannels(final CommunicationChannels communicationChannels) {
        this.communicationChannels = communicationChannels;
    }

    private States states;

    public void injectStates(final States states) {
        this.states = states;
    }

    private Countries countries;

    public void injectCountries(final Countries countries) {
        this.countries = countries;
    }

}
