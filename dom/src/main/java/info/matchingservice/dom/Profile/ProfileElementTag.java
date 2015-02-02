/**
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.matchingservice.dom.Profile;

import java.util.SortedSet;
import java.util.TreeSet;

import info.matchingservice.dom.Tags.TagHolder;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.RenderType;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementTag "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})    
@DomainObject(editing=Editing.DISABLED)
public class ProfileElementTag extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    private SortedSet<TagHolder> tagHolders = new TreeSet<TagHolder>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "ownerElement", dependentElement = "true")
    public SortedSet<TagHolder> getTagHolders() {
        return tagHolders;
    }

    public void setTagHolders(SortedSet<TagHolder> tagholders) {
        this.tagHolders = tagholders;
        
    }

}
