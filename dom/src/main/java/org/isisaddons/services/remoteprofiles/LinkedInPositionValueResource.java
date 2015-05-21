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

import org.apache.isis.applib.annotation.MemberOrder;

/**
 * Created by jodo on 20/05/15.
 */
public class LinkedInPositionValueResource {

    //region > company (property)
    private LinkedInCompanyResource company;
    
    public LinkedInCompanyResource getCompany() {
        return company;
    }

    public void setCompany(final LinkedInCompanyResource company) {
        this.company = company;
    }
    //endregion

    //region > summary (property)
    private String summary;
    
    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }
    //endregion

    //region > isCurrent (property)
    private boolean isCurrent;
    
    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(final boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
    //endregion

    //region > title (property)
    private String title;

    @MemberOrder(sequence = "1")
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
    //endregion

    //region > id (property)
    private Integer id;
    
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
    //endregion

    //region > startDate (property)
    private LinkedInDateResource startDate;

    @MemberOrder(sequence = "1")
    public LinkedInDateResource getStartDate() {
        return startDate;
    }

    public void setStartDate(final LinkedInDateResource startDate) {
        this.startDate = startDate;
    }
    //endregion

}
