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
import com.mycompany.modelo.Delegacion;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.ActividadEmpleado;
import com.mycompany.modelo.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author piotr
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getActividades() == null) {
            empleado.setActividades(new ArrayList<ActividadEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delegacion unaDelegacion = empleado.getUnaDelegacion();
            if (unaDelegacion != null) {
                unaDelegacion = em.getReference(unaDelegacion.getClass(), unaDelegacion.getId());
                empleado.setUnaDelegacion(unaDelegacion);
            }
            Departamento unDepartamento = empleado.getUnDepartamento();
            if (unDepartamento != null) {
                unDepartamento = em.getReference(unDepartamento.getClass(), unDepartamento.getId());
                empleado.setUnDepartamento(unDepartamento);
            }
            List<ActividadEmpleado> attachedActividades = new ArrayList<ActividadEmpleado>();
            for (ActividadEmpleado actividadesActividadEmpleadoToAttach : empleado.getActividades()) {
                actividadesActividadEmpleadoToAttach = em.getReference(actividadesActividadEmpleadoToAttach.getClass(), actividadesActividadEmpleadoToAttach.getId());
                attachedActividades.add(actividadesActividadEmpleadoToAttach);
            }
            empleado.setActividades(attachedActividades);
            em.persist(empleado);
            if (unaDelegacion != null) {
                unaDelegacion.getEmpleados().add(empleado);
                unaDelegacion = em.merge(unaDelegacion);
            }
            if (unDepartamento != null) {
                unDepartamento.getEmpleados().add(empleado);
                unDepartamento = em.merge(unDepartamento);
            }
            for (ActividadEmpleado actividadesActividadEmpleado : empleado.getActividades()) {
                Empleado oldUnEmpleadoOfActividadesActividadEmpleado = actividadesActividadEmpleado.getUnEmpleado();
                actividadesActividadEmpleado.setUnEmpleado(empleado);
                actividadesActividadEmpleado = em.merge(actividadesActividadEmpleado);
                if (oldUnEmpleadoOfActividadesActividadEmpleado != null) {
                    oldUnEmpleadoOfActividadesActividadEmpleado.getActividades().remove(actividadesActividadEmpleado);
                    oldUnEmpleadoOfActividadesActividadEmpleado = em.merge(oldUnEmpleadoOfActividadesActividadEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Delegacion unaDelegacionOld = persistentEmpleado.getUnaDelegacion();
            Delegacion unaDelegacionNew = empleado.getUnaDelegacion();
            Departamento unDepartamentoOld = persistentEmpleado.getUnDepartamento();
            Departamento unDepartamentoNew = empleado.getUnDepartamento();
            List<ActividadEmpleado> actividadesOld = persistentEmpleado.getActividades();
            List<ActividadEmpleado> actividadesNew = empleado.getActividades();
            List<String> illegalOrphanMessages = null;
            for (ActividadEmpleado actividadesOldActividadEmpleado : actividadesOld) {
                if (!actividadesNew.contains(actividadesOldActividadEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ActividadEmpleado " + actividadesOldActividadEmpleado + " since its unEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unaDelegacionNew != null) {
                unaDelegacionNew = em.getReference(unaDelegacionNew.getClass(), unaDelegacionNew.getId());
                empleado.setUnaDelegacion(unaDelegacionNew);
            }
            if (unDepartamentoNew != null) {
                unDepartamentoNew = em.getReference(unDepartamentoNew.getClass(), unDepartamentoNew.getId());
                empleado.setUnDepartamento(unDepartamentoNew);
            }
            List<ActividadEmpleado> attachedActividadesNew = new ArrayList<ActividadEmpleado>();
            for (ActividadEmpleado actividadesNewActividadEmpleadoToAttach : actividadesNew) {
                actividadesNewActividadEmpleadoToAttach = em.getReference(actividadesNewActividadEmpleadoToAttach.getClass(), actividadesNewActividadEmpleadoToAttach.getId());
                attachedActividadesNew.add(actividadesNewActividadEmpleadoToAttach);
            }
            actividadesNew = attachedActividadesNew;
            empleado.setActividades(actividadesNew);
            empleado = em.merge(empleado);
            if (unaDelegacionOld != null && !unaDelegacionOld.equals(unaDelegacionNew)) {
                unaDelegacionOld.getEmpleados().remove(empleado);
                unaDelegacionOld = em.merge(unaDelegacionOld);
            }
            if (unaDelegacionNew != null && !unaDelegacionNew.equals(unaDelegacionOld)) {
                unaDelegacionNew.getEmpleados().add(empleado);
                unaDelegacionNew = em.merge(unaDelegacionNew);
            }
            if (unDepartamentoOld != null && !unDepartamentoOld.equals(unDepartamentoNew)) {
                unDepartamentoOld.getEmpleados().remove(empleado);
                unDepartamentoOld = em.merge(unDepartamentoOld);
            }
            if (unDepartamentoNew != null && !unDepartamentoNew.equals(unDepartamentoOld)) {
                unDepartamentoNew.getEmpleados().add(empleado);
                unDepartamentoNew = em.merge(unDepartamentoNew);
            }
            for (ActividadEmpleado actividadesNewActividadEmpleado : actividadesNew) {
                if (!actividadesOld.contains(actividadesNewActividadEmpleado)) {
                    Empleado oldUnEmpleadoOfActividadesNewActividadEmpleado = actividadesNewActividadEmpleado.getUnEmpleado();
                    actividadesNewActividadEmpleado.setUnEmpleado(empleado);
                    actividadesNewActividadEmpleado = em.merge(actividadesNewActividadEmpleado);
                    if (oldUnEmpleadoOfActividadesNewActividadEmpleado != null && !oldUnEmpleadoOfActividadesNewActividadEmpleado.equals(empleado)) {
                        oldUnEmpleadoOfActividadesNewActividadEmpleado.getActividades().remove(actividadesNewActividadEmpleado);
                        oldUnEmpleadoOfActividadesNewActividadEmpleado = em.merge(oldUnEmpleadoOfActividadesNewActividadEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ActividadEmpleado> actividadesOrphanCheck = empleado.getActividades();
            for (ActividadEmpleado actividadesOrphanCheckActividadEmpleado : actividadesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the ActividadEmpleado " + actividadesOrphanCheckActividadEmpleado + " in its actividades field has a non-nullable unEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Delegacion unaDelegacion = empleado.getUnaDelegacion();
            if (unaDelegacion != null) {
                unaDelegacion.getEmpleados().remove(empleado);
                unaDelegacion = em.merge(unaDelegacion);
            }
            Departamento unDepartamento = empleado.getUnDepartamento();
            if (unDepartamento != null) {
                unDepartamento.getEmpleados().remove(empleado);
                unDepartamento = em.merge(unDepartamento);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
