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


}
