/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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

package nl.xtalus.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.xtalus.dom.user.User;
import nl.xtalus.dom.user.Users;

public class UsersFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create
        create("Johan", new LocalDate(1962,7,16));
        create("Jeroen", new LocalDate(1969,6,4));
        create("Henk", new LocalDate(1980,1,1));
    }

    // //////////////////////////////////////

    private User create(final String name, LocalDate dateOfBirth) {
        return users.newUser(name, dateOfBirth);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Users users;

}
