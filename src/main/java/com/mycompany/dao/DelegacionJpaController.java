/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.dao.exceptions.IllegalOrphanException;
import com.mycompany.dao.exceptions.NonexistentEntityException;
import com.mycompany.modelo.Delegacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.modelo.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author piotr
 */
public class DelegacionJpaController implements Serializable {

    public DelegacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Delegacion delegacion) {
        if (delegacion.getEmpleados() == null) {
            delegacion.setEmpleados(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : delegacion.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            delegacion.setEmpleados(attachedEmpleados);
            em.persist(delegacion);
            for (Empleado empleadosEmpleado : delegacion.getEmpleados()) {
                Delegacion oldUnaDelegacionOfEmpleadosEmpleado = empleadosEmpleado.getUnaDelegacion();
                empleadosEmpleado.setUnaDelegacion(delegacion);
                empleadosEmpleado = em.merge(empleadosEmpleado);
                if (oldUnaDelegacionOfEmpleadosEmpleado != null) {
                    oldUnaDelegacionOfEmpleadosEmpleado.getEmpleados().remove(empleadosEmpleado);
                    oldUnaDelegacionOfEmpleadosEmpleado = em.merge(oldUnaDelegacionOfEmpleadosEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Delegacion delegacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delegacion persistentDelegacion = em.find(Delegacion.class, delegacion.getId());
            List<Empleado> empleadosOld = persistentDelegacion.getEmpleados();
            List<Empleado> empleadosNew = delegacion.getEmpleados();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadosOldEmpleado + " since its unaDelegacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Empleado> attachedEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado empleadosNewEmpleadoToAttach : empleadosNew) {
                empleadosNewEmpleadoToAttach = em.getReference(empleadosNewEmpleadoToAttach.getClass(), empleadosNewEmpleadoToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewEmpleadoToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            delegacion.setEmpleados(empleadosNew);
            delegacion = em.merge(delegacion);
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    Delegacion oldUnaDelegacionOfEmpleadosNewEmpleado = empleadosNewEmpleado.getUnaDelegacion();
                    empleadosNewEmpleado.setUnaDelegacion(delegacion);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                    if (oldUnaDelegacionOfEmpleadosNewEmpleado != null && !oldUnaDelegacionOfEmpleadosNewEmpleado.equals(delegacion)) {
                        oldUnaDelegacionOfEmpleadosNewEmpleado.getEmpleados().remove(empleadosNewEmpleado);
                        oldUnaDelegacionOfEmpleadosNewEmpleado = em.merge(oldUnaDelegacionOfEmpleadosNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = delegacion.getId();
                if (findDelegacion(id) == null) {
                    throw new NonexistentEntityException("The delegacion with id " + id + " no longer exists.");
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
            Delegacion delegacion;
            try {
                delegacion = em.getReference(Delegacion.class, id);
                delegacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The delegacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadosOrphanCheck = delegacion.getEmpleados();
            for (Empleado empleadosOrphanCheckEmpleado : empleadosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Delegacion (" + delegacion + ") cannot be destroyed since the Empleado " + empleadosOrphanCheckEmpleado + " in its empleados field has a non-nullable unaDelegacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(delegacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Delegacion> findDelegacionEntities() {
        return findDelegacionEntities(true, -1, -1);
    }

    public List<Delegacion> findDelegacionEntities(int maxResults, int firstResult) {
        return findDelegacionEntities(false, maxResults, firstResult);
    }

    private List<Delegacion> findDelegacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Delegacion.class));
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

    public Delegacion findDelegacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Delegacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDelegacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Delegacion> rt = cq.from(Delegacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
