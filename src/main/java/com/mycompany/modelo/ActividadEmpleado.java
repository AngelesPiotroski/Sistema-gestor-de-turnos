/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ActividadEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

     private Timestamp fecha;
    
    @ManyToOne
    @JoinColumn(name = "fk_actividadEmpleado", nullable = false, updatable = true)
    private Empleado unEmpleado;
    
    @ManyToOne
    @JoinColumn(name = "fk_actividadEmpl", nullable = false, updatable = true)
    private TipoActividad unTipo;

     public ActividadEmpleado(){
         
     }
     
    public ActividadEmpleado(Timestamp fecha, TipoActividad tipoActv, Empleado emple) {
        this.fecha=fecha;
        this.unTipo=tipoActv;
        this.unEmpleado=emple;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadEmpleado)) {
            return false;
        }
        ActividadEmpleado other = (ActividadEmpleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.modelo.ActividadEmpleado[ id=" + id + " ]";
    }

    public Empleado getUnEmpleado() {
        return unEmpleado;
    }

    public void setUnEmpleado(Empleado unEmpleado) {
        this.unEmpleado = unEmpleado;
    }

    public TipoActividad getUnTipo() {
        return unTipo;
    }

    public void setUnTipo(TipoActividad unTipo) {
        this.unTipo = unTipo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    
}
