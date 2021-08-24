
package com.mycompany.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre, apellido, dni;
  
    private boolean activo;
    
    @ManyToOne
    @JoinColumn(name = "fk_delegacion", nullable = false, updatable = true)
    private Delegacion unaDelegacion;
    
    @ManyToOne
    @JoinColumn(name = "fk_departamento", nullable = false, updatable = true)
    private Departamento unDepartamento;
    
    @OneToMany(mappedBy = "unTurno")
    private List<Turno> turnos;
    
    @OneToMany(mappedBy = "unEmpleado")
    private List<ActividadEmpleado> actividades;

    public Empleado(){
        
    }
    
    public Empleado(String dni, String nombre, String apellido, Delegacion delegacion, Departamento depa) {
        this.dni=dni;
        this.nombre=nombre;
        this.apellido=apellido;
        this.unaDelegacion=delegacion;
        this.unDepartamento=depa;
        this.activo=false;

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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.modelo.Empleado[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Delegacion getUnaDelegacion() {
        return unaDelegacion;
    }

    public void setUnaDelegacion(Delegacion unaDelegacion) {
        this.unaDelegacion = unaDelegacion;
    }

    public Departamento getUnDepartamento() {
        return unDepartamento;
    }

    public void setUnDepartamento(Departamento unDepartamento) {
        this.unDepartamento = unDepartamento;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
    
    public void addUnTurno(Turno turno){
        this.turnos.add(turno);
    }
    
    public List<ActividadEmpleado> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadEmpleado> actividades) {
        this.actividades = actividades;
    }

   public void setUnaActividad(ActividadEmpleado act){
       this.actividades.add(act);
   }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
   
   
}
