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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject
@DomainObjectLayout()
public class LinkedInProfileObject implements Comparable<LinkedInProfileObject> {

	public String title(){
		return "LinkedIn profiel van " + getFormattedName();
	}
	
	private String id;
	private String firstName;
	private String lastName;
	private String headline;
	private String emailAddress;
	private String formattedName;
	private String summary;
	public String industry;
	public String pictureUrl;
	public String publicProfileUrl;

		
	@javax.jdo.annotations.Column(allowsNull="true")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@javax.jdo.annotations.Column(allowsNull="true")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@javax.jdo.annotations.Column(allowsNull="true")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@javax.jdo.annotations.Column(allowsNull="true")
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getFormattedName() {
		return formattedName;
	}
	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}

	@javax.jdo.annotations.Column(allowsNull="true", length = 4094)
	@PropertyLayout(multiLine = 10)
	public String getSummary() {
		return summary;
	}
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(final String industry) {
		this.industry = industry;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(final String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getPublicProfileUrl() {
		return publicProfileUrl;
	}
	public void setPublicProfileUrl(final String publicProfileUrl) {
		this.publicProfileUrl = publicProfileUrl;
	}

	//Collections

	private SortedSet<LinkedInCompanyObject> companiesWorkedFor = new TreeSet<LinkedInCompanyObject>();

	@Persistent(mappedBy = "linkedInProfile", dependentElement = "true")
	@CollectionLayout(render= RenderType.EAGERLY)
	public SortedSet<LinkedInCompanyObject> getCompaniesWorkedFor() {
		return companiesWorkedFor;
	}

	public void setCompaniesWorkedFor(final SortedSet<LinkedInCompanyObject> companiesWorkedFor) {
		this.companiesWorkedFor = companiesWorkedFor;
	}

	@Override public int compareTo(final LinkedInProfileObject o) {
		{
			return ObjectContracts.compare(this, o, "id");
		}
	}
}
