package info.matchingservice.dom.TrustedCircles;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;

import javax.inject.Inject;
import java.util.List;


@DomainService(nature= NatureOfService.VIEW_MENU_ONLY)
public class TrustedCircleConfigMenu {

    @Action(restrictTo = RestrictTo.PROTOTYPING)
    public List<TrustedCircleConfig> allTrustedCircleConfigs() {
        return container.allInstances(TrustedCircleConfig.class);
    }

    @Inject
    private DomainObjectContainer container;

}
