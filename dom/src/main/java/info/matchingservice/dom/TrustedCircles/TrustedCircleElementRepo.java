package info.matchingservice.dom.TrustedCircles;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;


@DomainService(repositoryFor = TrustedCircleElement.class, nature= NatureOfService.DOMAIN)
public class TrustedCircleElementRepo extends MatchingDomainService<TrustedCircleElement> {

    public TrustedCircleElementRepo() {
        super(TrustedCircleElementRepo.class, TrustedCircleElement.class);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public TrustedCircleElement createElement(
            final TrustedCircleConfig config,
            final String name,
            final Identifier.Type type,
            final String className,
            final TrustLevel hideFor){

        final TrustedCircleElement element = newTransientInstance(TrustedCircleElement.class);
        element.setTrustedCircleConfig(config);
        element.setName(name);
        element.setType(type);
        element.setClassName(className);
        element.setHideFor(hideFor);
        getContainer().persistIfNotAlready(element);
        return element;
    }

    @Action(semantics = SemanticsOf.SAFE)
    public TrustedCircleElement findTrustedCircleElementUnique(
            final TrustedCircleConfig config,
            final String name,
            final Identifier.Type type,
            final String className) {
        return uniqueMatch(
                "findTrustedCircleElementByConfigAndNameAndTypeAndClassName",
                "trustedCircleConfig", config,
                "name", name,
                "type", type,
                "className", className
                );
    }

}
