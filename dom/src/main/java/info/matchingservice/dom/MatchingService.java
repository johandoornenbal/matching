package info.matchingservice.dom;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.eventbus.EventBusService;
import org.apache.isis.applib.services.memento.MementoService;

public abstract class MatchingService<T> extends AbstractService {
    
    private final Class<? extends MatchingService<T>> serviceType;
    
    protected MatchingService(final Class<? extends MatchingService<T>> serviceType){
        this.serviceType = serviceType;
    }

    @Override
    public String getId() {
        // eg "agreementRoles";
//        return StringExtensions.asCamelLowerFirst(serviceType.getSimpleName());
        return serviceType.getSimpleName();
    }

    public String iconName() {
        // eg "AgreementRole";
        return serviceType.getSimpleName();
    }

    // //////////////////////////////////////
    protected Class<? extends MatchingService<T>> getServiceType() {
        return serviceType;
    }
    
    // //////////////////////////////////////
    
    /**
     * a default value is used to prevent null pointers for objects 
     * being initialized where the service has not yet been injected into.
     */
//    private EventBusService eventBusService = EventBusService.NOOP;
    protected EventBusService getEventBusService() {
        return eventBusService;
    }
    /**
     * Unlike domain objects, domain services ARE automatically registered
     * with the {@link EventBusService}; Isis guarantees that there will be
     * an instance of each domain service in memory when events are {@link EventBusService#post(Object) post}ed.
     */
//    public void injectEventBusService(final EventBusService eventBusService) {
//        this.eventBusService = eventBusService;
//        eventBusService.register(this);
//    }
    
    @Programmatic
    @PostConstruct
    public void postConstruct() {
        eventBusService.register(this);
    }
    @Programmatic
    @PreDestroy
    public void preDestroy() {
        eventBusService.unregister(this);
    }

    @javax.inject.Inject
    private EventBusService eventBusService;

    private MementoService mementoService;
    protected MementoService getMementoService() {
        return mementoService;
    }

    final public void injectMementoService(final MementoService mementoService) {
        this.mementoService = mementoService;
    }
    
    private BookmarkService bookmarkService;
    protected BookmarkService getBookmarkService() {
        return bookmarkService;
    }
    public final void injectBookmarkService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

}
