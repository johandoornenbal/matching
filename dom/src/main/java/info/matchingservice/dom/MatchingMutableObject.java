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

import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

import javax.jdo.JDOHelper;

/**
 * A domain object that is mutable and can be changed by multiple users over time,
 * and should therefore have optimistic locking controls in place.
 * 
 * <p>
 * Subclasses must be annotated with:
 * <pre>
 * @javax.jdo.annotations.DatastoreIdentity(
 *     strategy = IdGeneratorStrategy.NATIVE,
 *     column = "id")
 * @javax.jdo.annotations.Version(
 *     strategy=VersionStrategy.VERSION_NUMBER, 
 *     column="version")
 * public class MyDomainObject extends MatchingMutableObject {
 *   ...
 * }
 * </pre>
 * 
 * <p>
 * Note however that if a subclass that has a supertype which is annotated 
 * with {@link javax.jdo.annotations.Version} (eg <tt>CommunicationChannel</tt>)
 * then the subtype must not also have a <tt>Version</tt> annotation (otherwise JDO
 * will end up putting a <tt>version</tt> column in both tables, and they are not 
 * kept in sync).
 */

public abstract class MatchingMutableObject<T extends MatchingDomainObject<T>>
    extends MatchingDomainObject<T>{

    public MatchingMutableObject(final String keyProperties) {
        super(keyProperties);
    }
    
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public String getId() {
        Object objectId = JDOHelper.getObjectId(this);
        if(objectId == null) {
            return "";
        }
        String objectIdStr = objectId.toString();
        final String id = objectIdStr.split("\\[OID\\]")[0];
        return id;
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    public Integer getIdAsInt() {
        Object objectId = JDOHelper.getObjectId(this);
        if(objectId == null) {
            return null;
        }
        String objectIdStr = objectId.toString();
        final Integer id = Integer.parseInt(objectIdStr.split("\\[OID\\]")[0]);
        return id;
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    public String getUri() {
        Object objectId = JDOHelper.getObjectId(this);
        if(objectId == null) {
            return null;
        }
        String objectIdStr = objectId.toString();
        final String prefix = "objects/";
        final String id = objectIdStr.split("\\[OID\\]")[0];
        final String objectRef = objectIdStr.split("\\[OID\\]")[1];
        final String uri = prefix.concat(objectRef).concat("/").concat(id);
        return uri;
    }

    // //////////////////////////////////////

    @PropertyLayout(hidden = Where.EVERYWHERE)
    public Long getVersionSequence() {
        final Long version = (Long) JDOHelper.getVersion(this);
        return version;
    }
    
}
