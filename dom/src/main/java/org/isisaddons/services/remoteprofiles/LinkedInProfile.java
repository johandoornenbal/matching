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

public interface LinkedInProfile {
	
	public boolean getSuccess();
	public String getId();
	public String getFirstName();
	public String getLastName();
	public String getHeadline();
	public String getEmailAddress();
	public String getFormattedName();
	public String getIndustry();
	public Object getLocation();
	public String getPictureUrl();
	public LinkedInPositionsResource getPositions();
	public String getPublicProfileUrl();
	public String getSummary();
}
