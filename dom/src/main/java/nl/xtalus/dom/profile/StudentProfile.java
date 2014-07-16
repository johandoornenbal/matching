package nl.xtalus.dom.profile;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@PersistenceCapable()
public class StudentProfile extends Profile {

    private String school;

    @Column(allowsNull = "true")
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}
