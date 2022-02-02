package restful;

import excepciones.*;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 * Clase que contiene los metodos CRUD de las entidades utilizando el
 * EntityManager
 *
 * @author Aritz, Daniel, Jonathan y Mikel
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    //LOGGER
    private static final Logger LOG = Logger.getLogger(AbstractFacade.class.getName());

    private Class<T> entityClass;

    /**
     * Constructor
     *
     * @param entityClass entidad
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * EntityManager
     *
     * @return devuelve el EntityManager
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Crear
     * @param entity entidad
     * @throws CreateException 
     */
    public void create(T entity) throws CreateException {
        try {
            LOG.info("GESREserver/AbstractFacade: Creando nuevo elemento en '" + entity.toString() + "'");
            //getEntityManager().persist(entity);
            if (!getEntityManager().contains(entity)) {
                getEntityManager().merge(entity);
            }
            getEntityManager().flush();
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Actualizar
     *
     * @param entity entidad
     */
    public void edit(T entity) throws UpdateException {
        try {
            LOG.info("GESREserver/AbstractFacade: Actualizando un elemento en '" + entity.toString() + "'");
            getEntityManager().merge(entity);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }

    }

    /**
     * Borrar
     *
     * @param entity entidad
     */
    public void remove(T entity) throws DeleteException {
        try {
            LOG.info("GESREserver/AbstractFacade: Borrando un elemento de '" + entity.toString() + "'");
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Buscar por Id
     *
     * @param id id de la entidad
     * @return devuelve la entidad con dicho id
     */
    public T find(Object id) throws ReadException {
        try {
            LOG.info("GESREserver/AbstractFacade: Buscando por ID '" + id + "' en la entidad '" + entityClass.getSimpleName() + "'");
            return getEntityManager().find(entityClass, id);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Buscar toda la informacion de una entidad
     *
     * @return devuelve una lista de toda la entidad
     */
    public List<T> findAll() throws ReadException {
        try {
            LOG.info("GESREserver/AbstractFacade: Buscando toda la información de '" + entityClass.getSimpleName() + "'");
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Buscar por rango
     *
     * @param range
     * @return
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Count
     *
     * @return
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
