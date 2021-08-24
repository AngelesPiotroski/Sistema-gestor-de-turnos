/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author piotr
 */
@Entity
public class Departamento implements Serializable {

    public Departamento(){
        
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    
    private Integer cantmaxturnos;
    
    @ManyToOne
    @JoinColumn(name = "fk_delegacion", nullable = false, updatable = true)
    private Delegacion uunaDelegacion;
    
     @OneToMany(mappedBy = "unDepartamento")
    private List<Empleado> empleados;
    
    @OneToMany(mappedBy = "unDepartamento")
    private List<Turno> turnos;
     
    @OneToMany(mappedBy = "unDepartamentoTT")
    private List<TipoTramite> tipostramites;

    public Departamento( String nombre, Integer cantmaxturnos) {
      
        this.nombre = nombre;
        this.cantmaxturnos = cantmaxturnos;
 
    }
    public Departamento( String nombre) {
        this.nombre = nombre;
        
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
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.modelo.Departamento[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantmaxturnos() {
        return cantmaxturnos;
    }

    public void setCantmaxturnos(Integer cantmaxturnos) {
        this.cantmaxturnos = cantmaxturnos;
    }

    public Delegacion getUunaDelegacion() {
        return uunaDelegacion;
    }

    public void setUunaDelegacion(Delegacion uunaDelegacion) {
        this.uunaDelegacion = uunaDelegacion;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void addUnEmpleado(Empleado emple){
        empleados.add(emple);
    }
    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public void addUnTurno(Turno turno){
        turnos.add(turno);
    }
    public List<TipoTramite> getTipostramites() {
        return tipostramites;
    }

    public void setTipostramites(List<TipoTramite> tipostramites) {
        this.tipostramites = tipostramites;
    }
 
    public void addUnTipoTramite(TipoTramite tipotram){
        tipostramites.add(tipotram);
    }
    
    public int cantDisponibleTurnos(){
        List<Turno> turnosActivos = new ArrayList<>();
        for(Turno tur : this.turnos){
            //todos los turnos no finalizados
            if(!(tur.getUnEstadoT().getDescripcion().equals("atendido")) && !(tur.getUnEstadoT().getDescripcion().equals("finalizado"))){
                turnosActivos.add(tur);
            }
        }
        return (cantmaxturnos-turnosActivos.size());
    }
}
