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

package org.isisaddons.services.remoteprofiles;

public class LinkedInResponse implements LinkedInProfile{

	//region > id (property)
	private String id;

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
	//endregion

	//region > firstName (property)
	private String firstName;

	@Override
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	//endregion

	//region > lastName (property)
	private String lastName;

	@Override
	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	//endregion

	//region > headline (property)
	private String headline;

	@Override
	public String getHeadline() {
		return headline;
	}

	public void setHeadline(final String headline) {
		this.headline = headline;
	}
	//endregion

	//region > success (property)
	private boolean success;

	@Override
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}
	//endregion

	//region > emailAddress (property)
	private String emailAddress;

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	//endregion

	//region > formattedName (property)
	private String formattedName;

	@Override
	public String getFormattedName() {
		return formattedName;
	}

	public void setFormattedName(final String formattedName) {
		this.formattedName = formattedName;
	}
	//endregion

	//region > industry (property)
	private String industry;

	@Override
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(final String industry) {
		this.industry = industry;
	}
	//endregion

	//region > location (property)
	private Object location;

	@Override
	public Object getLocation() {
		return location;
	}

	public void setLocation(final Object location) {
		this.location = location;
	}
	//endregion

	//region > pictureUrl (property)
	private String pictureUrl;

	@Override
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(final String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	//endregion

	//region > positions (property)
	private LinkedInPositionsResource positions;

	@Override
	public LinkedInPositionsResource getPositions() {
		return positions;
	}

	public void setPositions(final LinkedInPositionsResource positions) {
		this.positions = positions;
	}
	//endregion

	//region > publicProfileUrl (property)
	private String publicProfileUrl;

	@Override
	public String getPublicProfileUrl() {
		return publicProfileUrl;
	}

	public void setPublicProfileUrl(final String publicProfileUrl) {
		this.publicProfileUrl = publicProfileUrl;
	}
	//endregion

	//region > summary (property)
	private String summary;

	@Override
	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}
	//endregion


	
}
