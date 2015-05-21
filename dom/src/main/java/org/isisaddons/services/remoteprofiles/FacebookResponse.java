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

public class FacebookResponse implements FacebookProfile{

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
	private String first_name;

	@Override
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(final String first_name) {
		this.first_name = first_name;
	}
	//endregion

	//region > lastName (property)
	private String last_name;

	@Override
	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(final String last_name) {
		this.last_name = last_name;
	}
	//endregion

	//region > headline (property)
	private String gender;

	@Override
	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
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
	private String email;

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//endregion

	//region > name (property)
	private String name;

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	//endregion

	//region > link (property)
	private String link;

	@Override
	public String getLink() {
		return link;
	}

	public void setLink(final String link) {
		this.link = link;
	}
	//endregion

	
}
