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
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nro;
    
    private Timestamp fecha;
    
     @ManyToOne
    @JoinColumn(name = "fk_turnoPago", nullable = true, updatable = true)
    private Pago unPago;
     
     @ManyToOne
    @JoinColumn(name = "fk_turnoDepto", nullable = false, updatable = true)
    private Departamento unDepartamento;
    
    @ManyToOne
    @JoinColumn(name = "fk_estadoturno", nullable = false, updatable = true)
    private EstadoTurno unEstadoT;
    
    
    @ManyToOne
    @JoinColumn(name = "fk_tramite", nullable = false, updatable = true)
    private Tramite unTramite;
    
    @ManyToOne
    @JoinColumn(name = "fk_turnoEmpleado", nullable = false, updatable = true)
    private Empleado unTurno;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Turno(String nro, Timestamp fecha, Departamento unDepartamento,EstadoTurno unEstadoT, Tramite unTramite, Empleado unTurno) {
        this.nro=nro;
        this.fecha = fecha;
        this.unDepartamento = unDepartamento;
        this.unEstadoT = unEstadoT;
        this.unTramite = unTramite;
        this.unTurno = unTurno;
    }

    public Turno() {
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
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.modelo.Turno[ id=" + id + " ]";
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

   

    public Departamento getUnDepartamento() {
        return unDepartamento;
    }

    public void setUnDepartamento(Departamento unDepartamento) {
        this.unDepartamento = unDepartamento;
    }

 

    public EstadoTurno getUnEstadoT() {
        return unEstadoT;
    }

    public void setUnEstadoT(EstadoTurno unEstadoT) {
        this.unEstadoT = unEstadoT;
    }

    public Tramite getUnTramite() {
        return unTramite;
    }

    public void setUnTramite(Tramite unTramite) {
        this.unTramite = unTramite;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Pago getUnPago() {
        return unPago;
    }

    public void setUnPago(Pago unPago) {
        this.unPago = unPago;
    }
    
    
    
}
