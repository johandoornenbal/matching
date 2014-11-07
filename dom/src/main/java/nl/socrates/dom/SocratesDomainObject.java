/*
 *
 *  Copyright 2014 YODO international projects and consultancy
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

package nl.socrates.dom;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.util.ObjectContracts;

public abstract class SocratesDomainObject <T extends SocratesDomainObject<T>> 
    extends AbstractDomainObject 
    implements Comparable<T>{
    
    private final String keyProperties;
    
    public SocratesDomainObject(final String keyProperties) {
        this.keyProperties = keyProperties;
    }

    protected String keyProperties() {
        return keyProperties;
    }
 
    private ClockService clockService;
    protected ClockService getClockService() {
        return clockService;
    }
    public final void injectClockService(final ClockService clockService) {
        this.clockService = clockService;
    } 
    
    @Override
    public int compareTo(final T other) {
        return ObjectContracts.compare(this, other, keyProperties);
    }

}
