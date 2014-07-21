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

import nl.xtalus.dom.user.Sex;
import nl.xtalus.dom.user.User;
import nl.xtalus.dom.user.Users;

public class UsersFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create
        create("Johan", "", "Doornenbal", new LocalDate(1962,7,16), Sex.MALE);
        create("Jeroen", "van der", "Wal", new LocalDate(1969,6,4), Sex.MALE);
        create("Henk", "", "Stormvogel", new LocalDate(1980,1,1), Sex.MALE);
        create("Inez", "", "Rippen", new LocalDate(1962,7,18), Sex.FEMALE);
    }

    // //////////////////////////////////////

    private User create(final String firstName, final String middleName, final String lastName, LocalDate dateOfBirth, final Sex sex) {
        return users.newUser(firstName, middleName, lastName, dateOfBirth, sex);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Users users;

}
