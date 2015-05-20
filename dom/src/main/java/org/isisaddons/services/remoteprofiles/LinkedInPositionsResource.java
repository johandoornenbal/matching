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

import java.util.List;

/**
 * Created by jodo on 20/05/15.
 */
public class LinkedInPositionsResource {

    //region > _total (property)
    private Integer _total;

    public Integer getTotal() {
        return _total;
    }

    public void setTotal(final Integer _total) {
        this._total = _total;
    }
    //endregion

    //region > values (property)
    private List<LinkedInPositionValueResource> values;

    public List<LinkedInPositionValueResource> getValues() {
        return values;
    }

    public void setValues(final List<LinkedInPositionValueResource> values) {
        this.values = values;
    }
    //endregion

}
