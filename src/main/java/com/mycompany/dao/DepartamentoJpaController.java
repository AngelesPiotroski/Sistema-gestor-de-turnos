/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.dao.exceptions.IllegalOrphanException;
import com.mycompany.dao.exceptions.NonexistentEntityException;
import com.mycompany.modelo.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.modelo.Empleado;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.modelo.Turno;
import com.mycompany.modelo.TipoTramite;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author piotr
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getEmpleados() == null) {
            departamento.setEmpleados(new ArrayList<Empleado>());
        }
        if (departamento.getTurnos() == null) {
            departamento.setTurnos(new ArrayList<Turno>());
        }
        if (departamento.getTipostramites() == null) {
            departamento.setTipostramites(new ArrayList<TipoTramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : departamento.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            departamento.setEmpleados(attachedEmpleados);
            List<Turno> attachedTurnos = new ArrayList<Turno>();
            for (Turno turnosTurnoToAttach : departamento.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getId());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            departamento.setTurnos(attachedTurnos);
            List<TipoTramite> attachedTipostramites = new ArrayList<TipoTramite>();
            for (TipoTramite tipostramitesTipoTramiteToAttach : departamento.getTipostramites()) {
                tipostramitesTipoTramiteToAttach = em.getReference(tipostramitesTipoTramiteToAttach.getClass(), tipostramitesTipoTramiteToAttach.getId());
                attachedTipostramites.add(tipostramitesTipoTramiteToAttach);
            }
            departamento.setTipostramites(attachedTipostramites);
            em.persist(departamento);
            for (Empleado empleadosEmpleado : departamento.getEmpleados()) {
                Departamento oldUnDepartamentoOfEmpleadosEmpleado = empleadosEmpleado.getUnDepartamento();
                empleadosEmpleado.setUnDepartamento(departamento);
                empleadosEmpleado = em.merge(empleadosEmpleado);
                if (oldUnDepartamentoOfEmpleadosEmpleado != null) {
                    oldUnDepartamentoOfEmpleadosEmpleado.getEmpleados().remove(empleadosEmpleado);
                    oldUnDepartamentoOfEmpleadosEmpleado = em.merge(oldUnDepartamentoOfEmpleadosEmpleado);
                }
            }
            for (Turno turnosTurno : departamento.getTurnos()) {
                Departamento oldUnDepartamentoOfTurnosTurno = turnosTurno.getUnDepartamento();
                turnosTurno.setUnDepartamento(departamento);
                turnosTurno = em.merge(turnosTurno);
                if (oldUnDepartamentoOfTurnosTurno != null) {
                    oldUnDepartamentoOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldUnDepartamentoOfTurnosTurno = em.merge(oldUnDepartamentoOfTurnosTurno);
                }
            }
            for (TipoTramite tipostramitesTipoTramite : departamento.getTipostramites()) {
                Departamento oldUnDepartamentoTTOfTipostramitesTipoTramite = tipostramitesTipoTramite.getUnDepartamentoTT();
                tipostramitesTipoTramite.setUnDepartamentoTT(departamento);
                tipostramitesTipoTramite = em.merge(tipostramitesTipoTramite);
                if (oldUnDepartamentoTTOfTipostramitesTipoTramite != null) {
                    oldUnDepartamentoTTOfTipostramitesTipoTramite.getTipostramites().remove(tipostramitesTipoTramite);
                    oldUnDepartamentoTTOfTipostramitesTipoTramite = em.merge(oldUnDepartamentoTTOfTipostramitesTipoTramite);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getId());
            List<Empleado> empleadosOld = persistentDepartamento.getEmpleados();
            List<Empleado> empleadosNew = departamento.getEmpleados();
            List<Turno> turnosOld = persistentDepartamento.getTurnos();
            List<Turno> turnosNew = departamento.getTurnos();
            List<TipoTramite> tipostramitesOld = persistentDepartamento.getTipostramites();
            List<TipoTramite> tipostramitesNew = departamento.getTipostramites();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadosOldEmpleado + " since its unDepartamento field is not nullable.");
                }
            }
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnosOldTurno + " since its unDepartamento field is not nullable.");
                }
            }
            for (TipoTramite tipostramitesOldTipoTramite : tipostramitesOld) {
                if (!tipostramitesNew.contains(tipostramitesOldTipoTramite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoTramite " + tipostramitesOldTipoTramite + " since its unDepartamentoTT field is not nullable.");
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
            departamento.setEmpleados(empleadosNew);
            List<Turno> attachedTurnosNew = new ArrayList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getId());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            departamento.setTurnos(turnosNew);
            List<TipoTramite> attachedTipostramitesNew = new ArrayList<TipoTramite>();
            for (TipoTramite tipostramitesNewTipoTramiteToAttach : tipostramitesNew) {
                tipostramitesNewTipoTramiteToAttach = em.getReference(tipostramitesNewTipoTramiteToAttach.getClass(), tipostramitesNewTipoTramiteToAttach.getId());
                attachedTipostramitesNew.add(tipostramitesNewTipoTramiteToAttach);
            }
            tipostramitesNew = attachedTipostramitesNew;
            departamento.setTipostramites(tipostramitesNew);
            departamento = em.merge(departamento);
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    Departamento oldUnDepartamentoOfEmpleadosNewEmpleado = empleadosNewEmpleado.getUnDepartamento();
                    empleadosNewEmpleado.setUnDepartamento(departamento);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                    if (oldUnDepartamentoOfEmpleadosNewEmpleado != null && !oldUnDepartamentoOfEmpleadosNewEmpleado.equals(departamento)) {
                        oldUnDepartamentoOfEmpleadosNewEmpleado.getEmpleados().remove(empleadosNewEmpleado);
                        oldUnDepartamentoOfEmpleadosNewEmpleado = em.merge(oldUnDepartamentoOfEmpleadosNewEmpleado);
                    }
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Departamento oldUnDepartamentoOfTurnosNewTurno = turnosNewTurno.getUnDepartamento();
                    turnosNewTurno.setUnDepartamento(departamento);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldUnDepartamentoOfTurnosNewTurno != null && !oldUnDepartamentoOfTurnosNewTurno.equals(departamento)) {
                        oldUnDepartamentoOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldUnDepartamentoOfTurnosNewTurno = em.merge(oldUnDepartamentoOfTurnosNewTurno);
                    }
                }
            }
            for (TipoTramite tipostramitesNewTipoTramite : tipostramitesNew) {
                if (!tipostramitesOld.contains(tipostramitesNewTipoTramite)) {
                    Departamento oldUnDepartamentoTTOfTipostramitesNewTipoTramite = tipostramitesNewTipoTramite.getUnDepartamentoTT();
                    tipostramitesNewTipoTramite.setUnDepartamentoTT(departamento);
                    tipostramitesNewTipoTramite = em.merge(tipostramitesNewTipoTramite);
                    if (oldUnDepartamentoTTOfTipostramitesNewTipoTramite != null && !oldUnDepartamentoTTOfTipostramitesNewTipoTramite.equals(departamento)) {
                        oldUnDepartamentoTTOfTipostramitesNewTipoTramite.getTipostramites().remove(tipostramitesNewTipoTramite);
                        oldUnDepartamentoTTOfTipostramitesNewTipoTramite = em.merge(oldUnDepartamentoTTOfTipostramitesNewTipoTramite);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = departamento.getId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadosOrphanCheck = departamento.getEmpleados();
            for (Empleado empleadosOrphanCheckEmpleado : empleadosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Empleado " + empleadosOrphanCheckEmpleado + " in its empleados field has a non-nullable unDepartamento field.");
            }
            List<Turno> turnosOrphanCheck = departamento.getTurnos();
            for (Turno turnosOrphanCheckTurno : turnosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Turno " + turnosOrphanCheckTurno + " in its turnos field has a non-nullable unDepartamento field.");
            }
            List<TipoTramite> tipostramitesOrphanCheck = departamento.getTipostramites();
            for (TipoTramite tipostramitesOrphanCheckTipoTramite : tipostramitesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the TipoTramite " + tipostramitesOrphanCheckTipoTramite + " in its tipostramites field has a non-nullable unDepartamentoTT field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
