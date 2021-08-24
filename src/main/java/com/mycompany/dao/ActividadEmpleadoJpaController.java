/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.dao.exceptions.NonexistentEntityException;
import com.mycompany.modelo.ActividadEmpleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.modelo.Empleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author piotr
 */
public class ActividadEmpleadoJpaController implements Serializable {

    public ActividadEmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadEmpleado actividadEmpleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado unEmpleado = actividadEmpleado.getUnEmpleado();
            if (unEmpleado != null) {
                unEmpleado = em.getReference(unEmpleado.getClass(), unEmpleado.getId());
                actividadEmpleado.setUnEmpleado(unEmpleado);
            }
            em.persist(actividadEmpleado);
            if (unEmpleado != null) {
                unEmpleado.getActividades().add(actividadEmpleado);
                unEmpleado = em.merge(unEmpleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadEmpleado actividadEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadEmpleado persistentActividadEmpleado = em.find(ActividadEmpleado.class, actividadEmpleado.getId());
            Empleado unEmpleadoOld = persistentActividadEmpleado.getUnEmpleado();
            Empleado unEmpleadoNew = actividadEmpleado.getUnEmpleado();
            if (unEmpleadoNew != null) {
                unEmpleadoNew = em.getReference(unEmpleadoNew.getClass(), unEmpleadoNew.getId());
                actividadEmpleado.setUnEmpleado(unEmpleadoNew);
            }
            actividadEmpleado = em.merge(actividadEmpleado);
            if (unEmpleadoOld != null && !unEmpleadoOld.equals(unEmpleadoNew)) {
                unEmpleadoOld.getActividades().remove(actividadEmpleado);
                unEmpleadoOld = em.merge(unEmpleadoOld);
            }
            if (unEmpleadoNew != null && !unEmpleadoNew.equals(unEmpleadoOld)) {
                unEmpleadoNew.getActividades().add(actividadEmpleado);
                unEmpleadoNew = em.merge(unEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = actividadEmpleado.getId();
                if (findActividadEmpleado(id) == null) {
                    throw new NonexistentEntityException("The actividadEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadEmpleado actividadEmpleado;
            try {
                actividadEmpleado = em.getReference(ActividadEmpleado.class, id);
                actividadEmpleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadEmpleado with id " + id + " no longer exists.", enfe);
            }
            Empleado unEmpleado = actividadEmpleado.getUnEmpleado();
            if (unEmpleado != null) {
                unEmpleado.getActividades().remove(actividadEmpleado);
                unEmpleado = em.merge(unEmpleado);
            }
            em.remove(actividadEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadEmpleado> findActividadEmpleadoEntities() {
        return findActividadEmpleadoEntities(true, -1, -1);
    }

    public List<ActividadEmpleado> findActividadEmpleadoEntities(int maxResults, int firstResult) {
        return findActividadEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<ActividadEmpleado> findActividadEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadEmpleado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ActividadEmpleado findActividadEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadEmpleado> rt = cq.from(ActividadEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
