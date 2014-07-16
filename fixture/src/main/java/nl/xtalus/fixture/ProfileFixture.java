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

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.xtalus.dom.profile.ProfileType;
import nl.xtalus.dom.profile.StudentProfile;
import nl.xtalus.dom.user.User;
import nl.xtalus.dom.user.Users;

public class ProfileFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new UsersFixture(), executionContext);

        User user = users.allUsers().get(0);

        StudentProfile profile = (StudentProfile) user.addProfile(ProfileType.STUDENT);

        profile.setSchool("Basisschool");

    }

    @Inject
    Users users;
}
