package info.matchingservice.dom.TrustedCircles;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import javax.inject.Inject;


@DomainService(repositoryFor = TrustedCircleConfig.class, nature= NatureOfService.DOMAIN)
public class TrustedCircleConfigRepo extends MatchingDomainService<TrustedCircleConfig> {

    public TrustedCircleConfigRepo() {
        super(TrustedCircleConfigRepo.class, TrustedCircleConfig.class);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public TrustedCircleConfig findOrCreateConfig(final String owner){
        if (findTrustedCircleConfigByOwner(owner)!=null){
            return findTrustedCircleConfigByOwner(owner);
        }
        final TrustedCircleConfig config = newTransientInstance(TrustedCircleConfig.class);
        config.setOwnedBy(owner);
        persistIfNotAlready(config);
        TrustedCircleElement elementDemands = trustedCircleElementRepo.createElement(config, "demands", Identifier.Type.PROPERTY_OR_COLLECTION, "Person", TrustLevel.INNER_CIRCLE);
        TrustedCircleElement elementAssessmentsGiven = trustedCircleElementRepo.createElement(config, "assessmentsGiven", Identifier.Type.PROPERTY_OR_COLLECTION, "Actor", TrustLevel.INTIMATE);
        TrustedCircleElement elementAssessmentsReceived = trustedCircleElementRepo.createElement(config, "assessmentsReceived", Identifier.Type.PROPERTY_OR_COLLECTION, "Actor", TrustLevel.INNER_CIRCLE);
        TrustedCircleElement elementPersonalContacts = trustedCircleElementRepo.createElement(config, "personalContacts", Identifier.Type.PROPERTY_OR_COLLECTION, "Person", TrustLevel.INNER_CIRCLE);
        return config;
    }

    @Action(semantics = SemanticsOf.SAFE)
    public TrustedCircleConfig findTrustedCircleConfigByOwner(final String owner){
        return uniqueMatch("findTrustedCircleConfigByOwner", "ownedBy", owner);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public TrustLevel isHiddenFor(
            final String ownedBy,
            final String name,
            final Identifier.Type type,
            final String className
    ){
        TrustedCircleConfig config = findTrustedCircleConfigByOwner(ownedBy);
        return trustedCircleElementRepo.findTrustedCircleElementUnique(config,name,type,className).getHideFor();
    }

    @Action(semantics = SemanticsOf.SAFE)
    public TrustLevel propertyOrCollectionIsHiddenFor(
            final String ownedBy,
            final String name,
            final String className
    ){
        TrustedCircleConfig config = findTrustedCircleConfigByOwner(ownedBy);
        return trustedCircleElementRepo.findTrustedCircleElementUnique(config,name, Identifier.Type.PROPERTY_OR_COLLECTION,className).getHideFor();
    }

    @Inject
    private TrustedCircleElementRepo trustedCircleElementRepo;
}
