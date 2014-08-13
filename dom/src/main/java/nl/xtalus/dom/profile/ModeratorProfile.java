package nl.xtalus.dom.profile;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@PersistenceCapable()
public class ModeratorProfile extends Profile {
   
    
}
