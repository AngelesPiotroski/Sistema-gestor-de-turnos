/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.dao.exceptions.NonexistentEntityException;
import com.mycompany.modelo.TasaAdministrativa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author piotr
 */
public class TasaAdministrativaJpaController implements Serializable {

    public TasaAdministrativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TasaAdministrativa tasaAdministrativa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tasaAdministrativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TasaAdministrativa tasaAdministrativa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tasaAdministrativa = em.merge(tasaAdministrativa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tasaAdministrativa.getId();
                if (findTasaAdministrativa(id) == null) {
                    throw new NonexistentEntityException("The tasaAdministrativa with id " + id + " no longer exists.");
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
            TasaAdministrativa tasaAdministrativa;
            try {
                tasaAdministrativa = em.getReference(TasaAdministrativa.class, id);
                tasaAdministrativa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tasaAdministrativa with id " + id + " no longer exists.", enfe);
            }
            em.remove(tasaAdministrativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TasaAdministrativa> findTasaAdministrativaEntities() {
        return findTasaAdministrativaEntities(true, -1, -1);
    }

    public List<TasaAdministrativa> findTasaAdministrativaEntities(int maxResults, int firstResult) {
        return findTasaAdministrativaEntities(false, maxResults, firstResult);
    }

    private List<TasaAdministrativa> findTasaAdministrativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TasaAdministrativa.class));
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

    public TasaAdministrativa findTasaAdministrativa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TasaAdministrativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTasaAdministrativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TasaAdministrativa> rt = cq.from(TasaAdministrativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
