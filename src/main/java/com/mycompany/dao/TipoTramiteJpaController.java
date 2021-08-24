/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.dao.exceptions.IllegalOrphanException;
import com.mycompany.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.TipoTramite;
import com.mycompany.modelo.Tramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author piotr
 */
public class TipoTramiteJpaController implements Serializable {

    public TipoTramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTramite tipoTramite) {
        if (tipoTramite.getTramites() == null) {
            tipoTramite.setTramites(new ArrayList<Tramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento unDepartamentoTT = tipoTramite.getUnDepartamentoTT();
            if (unDepartamentoTT != null) {
                unDepartamentoTT = em.getReference(unDepartamentoTT.getClass(), unDepartamentoTT.getId());
                tipoTramite.setUnDepartamentoTT(unDepartamentoTT);
            }
            List<Tramite> attachedTramites = new ArrayList<Tramite>();
            for (Tramite tramitesTramiteToAttach : tipoTramite.getTramites()) {
                tramitesTramiteToAttach = em.getReference(tramitesTramiteToAttach.getClass(), tramitesTramiteToAttach.getId());
                attachedTramites.add(tramitesTramiteToAttach);
            }
            tipoTramite.setTramites(attachedTramites);
            em.persist(tipoTramite);
            if (unDepartamentoTT != null) {
                unDepartamentoTT.getTipostramites().add(tipoTramite);
                unDepartamentoTT = em.merge(unDepartamentoTT);
            }
            for (Tramite tramitesTramite : tipoTramite.getTramites()) {
                TipoTramite oldUnTramiteOfTramitesTramite = tramitesTramite.getUnTramite();
                tramitesTramite.setUnTramite(tipoTramite);
                tramitesTramite = em.merge(tramitesTramite);
                if (oldUnTramiteOfTramitesTramite != null) {
                    oldUnTramiteOfTramitesTramite.getTramites().remove(tramitesTramite);
                    oldUnTramiteOfTramitesTramite = em.merge(oldUnTramiteOfTramitesTramite);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTramite tipoTramite) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTramite persistentTipoTramite = em.find(TipoTramite.class, tipoTramite.getId());
            Departamento unDepartamentoTTOld = persistentTipoTramite.getUnDepartamentoTT();
            Departamento unDepartamentoTTNew = tipoTramite.getUnDepartamentoTT();
            List<Tramite> tramitesOld = persistentTipoTramite.getTramites();
            List<Tramite> tramitesNew = tipoTramite.getTramites();
            List<String> illegalOrphanMessages = null;
            for (Tramite tramitesOldTramite : tramitesOld) {
                if (!tramitesNew.contains(tramitesOldTramite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tramite " + tramitesOldTramite + " since its unTramite field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unDepartamentoTTNew != null) {
                unDepartamentoTTNew = em.getReference(unDepartamentoTTNew.getClass(), unDepartamentoTTNew.getId());
                tipoTramite.setUnDepartamentoTT(unDepartamentoTTNew);
            }
            List<Tramite> attachedTramitesNew = new ArrayList<Tramite>();
            for (Tramite tramitesNewTramiteToAttach : tramitesNew) {
                tramitesNewTramiteToAttach = em.getReference(tramitesNewTramiteToAttach.getClass(), tramitesNewTramiteToAttach.getId());
                attachedTramitesNew.add(tramitesNewTramiteToAttach);
            }
            tramitesNew = attachedTramitesNew;
            tipoTramite.setTramites(tramitesNew);
            tipoTramite = em.merge(tipoTramite);
            if (unDepartamentoTTOld != null && !unDepartamentoTTOld.equals(unDepartamentoTTNew)) {
                unDepartamentoTTOld.getTipostramites().remove(tipoTramite);
                unDepartamentoTTOld = em.merge(unDepartamentoTTOld);
            }
            if (unDepartamentoTTNew != null && !unDepartamentoTTNew.equals(unDepartamentoTTOld)) {
                unDepartamentoTTNew.getTipostramites().add(tipoTramite);
                unDepartamentoTTNew = em.merge(unDepartamentoTTNew);
            }
            for (Tramite tramitesNewTramite : tramitesNew) {
                if (!tramitesOld.contains(tramitesNewTramite)) {
                    TipoTramite oldUnTramiteOfTramitesNewTramite = tramitesNewTramite.getUnTramite();
                    tramitesNewTramite.setUnTramite(tipoTramite);
                    tramitesNewTramite = em.merge(tramitesNewTramite);
                    if (oldUnTramiteOfTramitesNewTramite != null && !oldUnTramiteOfTramitesNewTramite.equals(tipoTramite)) {
                        oldUnTramiteOfTramitesNewTramite.getTramites().remove(tramitesNewTramite);
                        oldUnTramiteOfTramitesNewTramite = em.merge(oldUnTramiteOfTramitesNewTramite);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoTramite.getId();
                if (findTipoTramite(id) == null) {
                    throw new NonexistentEntityException("The tipoTramite with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTramite tipoTramite;
            try {
                tipoTramite = em.getReference(TipoTramite.class, id);
                tipoTramite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTramite with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tramite> tramitesOrphanCheck = tipoTramite.getTramites();
            for (Tramite tramitesOrphanCheckTramite : tramitesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoTramite (" + tipoTramite + ") cannot be destroyed since the Tramite " + tramitesOrphanCheckTramite + " in its tramites field has a non-nullable unTramite field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento unDepartamentoTT = tipoTramite.getUnDepartamentoTT();
            if (unDepartamentoTT != null) {
                unDepartamentoTT.getTipostramites().remove(tipoTramite);
                unDepartamentoTT = em.merge(unDepartamentoTT);
            }
            em.remove(tipoTramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTramite> findTipoTramiteEntities() {
        return findTipoTramiteEntities(true, -1, -1);
    }

    public List<TipoTramite> findTipoTramiteEntities(int maxResults, int firstResult) {
        return findTipoTramiteEntities(false, maxResults, firstResult);
    }

    private List<TipoTramite> findTipoTramiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTramite.class));
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

    public TipoTramite findTipoTramite(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTramite.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTramiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTramite> rt = cq.from(TipoTramite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
