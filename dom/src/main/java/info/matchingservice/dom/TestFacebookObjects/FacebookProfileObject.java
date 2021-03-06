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

package info.matchingservice.dom.TestFacebookObjects;

import java.net.URL;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
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
public class FacebookProfileObject implements Comparable<FacebookProfileObject> {

	public String title(){
		return "Facebook profiel van " + getName();
	}
	
	private String id;
	private String firstName;
	private String lastName;
	private String gender;
	private String emailAddress;
	private String name;
	private URL publicProfileUrl;

		
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@javax.jdo.annotations.Column(allowsNull="true")
	public URL getPublicProfileUrl() {
		return publicProfileUrl;
	}
	public void setPublicProfileUrl(final URL publicProfileUrl) {
		this.publicProfileUrl = publicProfileUrl;
	}


	@Override public int compareTo(final FacebookProfileObject o) {
		{
			return ObjectContracts.compare(this, o, "id");
		}
	}
}
