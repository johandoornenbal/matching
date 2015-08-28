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
package nl.matching.app.webapp.scheduler;

public class SchedulerServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SchedulerServiceException() {
        super();
    }

    public SchedulerServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SchedulerServiceException(String arg0) {
        super(arg0);
    }

    public SchedulerServiceException(Throwable arg0) {
        super(arg0);
    }
}