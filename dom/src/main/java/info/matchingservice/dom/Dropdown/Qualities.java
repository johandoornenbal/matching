package info.matchingservice.dom.Dropdown;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

@DomainService(menuOrder = "150", repositoryFor = Quality.class)
public class Qualities extends MatchingDomainService<Quality> {

    public Qualities() {
        super(Qualities.class, Quality.class);
    }

    public List<Quality> allQualities(){
        return allInstances(Quality.class);
    }
    
    public Quality newQuality(
            @Named("Kwaliteit")
            final String keyWord
            ){
        
       final Quality newQuality = newTransientInstance(Quality.class);
       newQuality.setKeyword(keyWord);
       persist(newQuality);
       return newQuality;
    }
    
    public List<Quality> findQualities(
            @Named("Keyword of gedeelte ervan")
            final String keyword
            ) {
        return allMatches("matchQualityByKeyWord", "keyword", keyword);
    }
   
}
