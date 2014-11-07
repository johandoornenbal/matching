package nl.yodo.dom;

import java.util.List;

import javax.jdo.Query;

import org.apache.isis.applib.RepositoryException;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public abstract class YodoDomainService<T> extends YodoService<T> {
    
    private final Class<T> entityType;
    
    protected YodoDomainService(
            final Class<? extends YodoDomainService<T>> serviceType, 
            final Class<T> entityType) {
        super(serviceType);
        this.entityType = entityType;
    }
    
    @Override
    public String iconName() {
        // eg "AgreementRole";
        return entityType.getSimpleName();
    }

    // //////////////////////////////////////

    protected Class<T> getEntityType() {
        return entityType;
    }
    
    protected QueryDefault<T> newQueryDefault(final String queryName, final Object... paramArgs) {
        return new QueryDefault<T>(getEntityType(), queryName, paramArgs);
    }
    
    // //////////////////////////////////////

    protected T newTransientInstance() {
        return newTransientInstance(getEntityType());
    }
    
    protected T firstMatch(final String queryName, final Object... paramArgs) {
        return firstMatch(newQueryDefault(queryName, paramArgs));
    }
    
    protected T uniqueMatch(final String queryName, final Object... paramArgs) {
        return uniqueMatch(newQueryDefault(queryName, paramArgs));
    }
    
    protected List<T> allMatches(final String queryName, final Object... paramArgs) {
        return allMatches(newQueryDefault(queryName, paramArgs));
    }

    protected List<T> allInstances() {
        return allInstances(getEntityType());
    }


    // //////////////////////////////////////
    
    protected T mustMatch(final String queryName, final String param, final String arg) {
        final T obj = firstMatch(queryName, param, arg);
        if(obj == null) {
            throw new RepositoryException(getClassName() + " '"  + arg + "' does not exist");
        }
        return obj;
    }

    // //////////////////////////////////////
    
    protected Query newQuery(final String jdoql) {
        return isisJdoSupport.getJdoPersistenceManager().newQuery(jdoql);
    }

    // //////////////////////////////////////

    protected IsisJdoSupport isisJdoSupport;
    public final void injectIsisJdoSupport(final IsisJdoSupport isisJdoSupport) {
        this.isisJdoSupport = isisJdoSupport;
    }
    
}
