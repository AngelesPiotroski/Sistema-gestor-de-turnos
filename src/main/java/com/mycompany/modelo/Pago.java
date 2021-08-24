/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 *
 * @author piotr
 */
@Entity
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean pagado;
    
    private Timestamp fecha;
    
  
      @ManyToOne
    @JoinColumn(name = "fk_tasaPago", nullable = false, updatable = true)
    private TasaAdministrativa unaTasa;
    
     @ManyToOne
    @JoinColumn(name = "fk_tipoPagoyPago", nullable = false, updatable = true)
    private TipoPago unTipo;
      
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
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.modelo.Pago[ id=" + id + " ]";
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public TasaAdministrativa getUnaTasa() {
        return unaTasa;
    }

    public void setUnaTasa(TasaAdministrativa unaTasa) {
        this.unaTasa = unaTasa;
    }

    public TipoPago getUnTipo() {
        return unTipo;
    }

    public void setUnTipo(TipoPago unTipo) {
        this.unTipo = unTipo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
}
