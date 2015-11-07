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

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

/**
 * A Domain object that is secure checks who is the owner of an instance. Only
 * the owner and an admin of the app can edit the instance. Only an admin can
 * change the owner field. A domain object that is mutable and can be changed by
 * multiple users over time, and should therefore have optimistic locking
 * controls in place.
 * 
 * <p>
 * Subclasses must be annotated with:
 * 
 * <pre>
 * @javax.jdo.annotations.DatastoreIdentity(
 *     strategy = IdGeneratorStrategy.NATIVE,
 *     column = "id")  
 * @javax.jdo.annotations.Version(
 *     strategy=VersionStrategy.VERSION_NUMBER, 
 *     column="version")
 * public class MyDomainObject extends MatchingSecureMutableObject {
 *   ...
 * }
 * </pre>
 * 
 * <p>
 * Note however that if a subclass that has a supertype which is annotated with
 * {@link javax.jdo.annotations.Version} (eg <tt>CommunicationChannel</tt>) then
 * the subtype must not also have a <tt>Version</tt> annotation (otherwise JDO
 * will end up putting a <tt>version</tt> column in both tables, and they are
 * not kept in sync).
 */
public abstract class MatchingSecureMutableObject<T extends MatchingDomainObject<T>>
        extends MatchingMutableObject<T>
        implements GetOwnedBy {

    public MatchingSecureMutableObject(final String keyProperties) {
        super(keyProperties);
    }

    // /////// Security ////////////////////////////////////////////////

    /**
     * Methods should only be used by the owner or app admin
     * 
     * @param type
     * @return
     */
    public String disabled(Identifier.Type type) {
        // user is owner
        if (Objects.equal(getOwnedBy(), container.getUser().getName())) {
            return null;
        }
        // user is admin of matching app
        if (container.getUser().hasRole(".*matching-admin-role")) {
            return null;
        }
        // user is neither owner nor admin
        return "Not allowed to modify / Niet toegestaan te wijzigen";
    }

    private String ownedBy;

    @PropertyLayout(
            hidden = Where.EVERYWHERE
            )
    @Property(
            editing = Editing.DISABLED
            )
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    public void changeOwner(final String owner) {
        this.setOwnedBy(owner);
    }

    public boolean hideChangeOwner(final String owner) {
        // user is admin of matching app
        if (container.getUser().hasRole(".*matching-admin-role")) {
            return false;
        }
        return true;
    }

    /**
     * Viewerrights are derived from YodoTrustedContact
     * 
     */

    @PropertyLayout(hidden = Where.EVERYWHERE)
    public TrustLevel getViewerRights() {
        return trustedContacts.trustLevel(this.getOwnedBy(), container.getUser().getName());
    }

    /**
     * This method can be used in combination with hideXxx() or disableXxx()
     * method of a child e.g. hideXxx(){ return
     * super.allowedTrustLevel(TrustLevel.ENTRY_LEVEL); } returns 'true' and thus hides the Xxx field or
     * method for all levels under ENTRY_LEVEL (OUTER_CIRCLE and BANNED) and returns 'false' and thus
     * shows it for levels ENTRY_LEVEL and up (INNER_CIRCLE and INTIMATE)
     * 
     * Exceptions are when called by the owner of the instance or admin of the
     * app
     * 
     * @param trustlevel
     * @return
     */
    @ActionLayout(hidden = Where.EVERYWHERE)
    public boolean allowedTrustLevel(final TrustLevel trustlevel) {
        // user is owner
        if (Objects.equal(getOwnedBy(), container.getUser().getName())) {
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*matching-admin-role")) {
            return false;
        }
        // for fixtures
        System.out.println("*****************" + container.getUser().getName() + "*********************");
        if (container.getUser().getName()=="initialisation") {
            return false;
        }
        if (getViewerRights() != null && trustlevel.compareTo(getViewerRights()) <= 0) {
            return false;
        }
        return true;
    }
    
    // /////// Injects ////////////////////////////////////////////////

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    private MatchingTrustedContacts trustedContacts;

}
