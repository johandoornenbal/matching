package nl.xtalus.dom.profile;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Named;

import nl.xtalus.dom.user.User;

public class Profiles extends AbstractFactoryAndRepository {

    @ActionSemantics(Of.SAFE)
    public List<Profile> allUsers() {
        return allInstances(Profile.class);
    }

    @Named("Voeg profiel toe")
    public Profile addProfile(
            User user,
            ProfileType profileType) {
        Profile profile = newTransientInstance(profileType.cls());
        profile.setOwner(user);
        profile.setType(profileType);
        persist(profile);
        return profile;
    }

    @Named("Voeg Student profiel toe")
    public StudentProfile addStudentProfile(
            User user,
            @Named("School") String school) {
        StudentProfile profile = (StudentProfile) newTransientInstance(ProfileType.STUDENT.cls());
        profile.setType(ProfileType.STUDENT);
        profile.setOwner(user);
        profile.setSchool(school);
        persist(profile);
        return profile;
    }

}
