package nl.yodo.dom;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.util.ObjectContracts;

public abstract class YodoDomainObject <T extends YodoDomainObject<T>>
    extends AbstractDomainObject
    implements Comparable<T> {
    
    private final String keyProperties;
    
    public YodoDomainObject(final String keyProperties) {
        this.keyProperties=keyProperties;
    }
    
    protected String keyProperties() {
        return keyProperties;
    }
    
    private ClockService clockService;
    protected ClockService getClockService() {
        return clockService;
    }
    public final void injectClockService(final ClockService clockService) {
        this.clockService = clockService;
    } 
    
    @Override
    public int compareTo(final T other) {
        return ObjectContracts.compare(this, other, keyProperties);
    }
}
