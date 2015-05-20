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

package info.matchingservice.dom.TestLinkedInObjects;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Created by jodo on 20/05/15.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@DomainObject
@DomainObjectLayout()
public class LinkedInCompanyObject implements Comparable<LinkedInCompanyObject> {

    //region > linkedInProfile (property)
    private LinkedInProfileObject linkedInProfile;

    @javax.jdo.annotations.Column(allowsNull="true")
    public LinkedInProfileObject getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(final LinkedInProfileObject linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }
    //endregion

    //region > id (property)
    private Integer id;

    @javax.jdo.annotations.Column(allowsNull="true")
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
    //endregion

    //region > name (property)
    private String name;

    @javax.jdo.annotations.Column(allowsNull="true")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > industry (property)
    private String industry;

    @javax.jdo.annotations.Column(allowsNull="true")
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(final String industry) {
        this.industry = industry;
    }
    //endregion

    //region > size (property)
    private String size;

    @javax.jdo.annotations.Column(allowsNull="true")
    public String getSize() {
        return size;
    }

    public void setSize(final String size) {
        this.size = size;
    }
    //endregion

    //region > type (property)
    private String type;

    @javax.jdo.annotations.Column(allowsNull="true")
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override public int compareTo(final LinkedInCompanyObject o) {
        return ObjectContracts.compare(this, o, "id, name, type, size");
    }
    //endregion

    //region > positionSummary (property)
    private String positionSummary;

    @javax.jdo.annotations.Column(allowsNull="true", length = 4096)
    public String getPositionSummary() {
        return positionSummary;
    }

    public void setPositionSummary(final String positionSummary) {
        this.positionSummary = positionSummary;
    }
    //endregion

    //region > jobTitle (property)
    private String jobTitle;

    @javax.jdo.annotations.Column(allowsNull="true")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(final String jobTitle) {
        this.jobTitle = jobTitle;
    }
    //endregion

    //region > isCurrent (property)
    private boolean isCurrent;

    @javax.jdo.annotations.Column(allowsNull="true")
    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(final boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
    //endregion

    //region > startMonth (property)
    private Integer startMonth;

    @javax.jdo.annotations.Column(allowsNull="true")
    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(final Integer startMonth) {
        this.startMonth = startMonth;
    }
    //endregion

    //region > startYear (property)
    private Integer startYear;

    @javax.jdo.annotations.Column(allowsNull="true")
    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(final Integer startYear) {
        this.startYear = startYear;
    }
    //endregion

}
