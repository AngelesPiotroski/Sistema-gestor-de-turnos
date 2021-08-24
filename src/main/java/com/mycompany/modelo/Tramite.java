/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.modelo;

import java.io.Serializable;
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
public class Tramite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "fk_tipoTramiteyTramite", nullable = false, updatable = true)
    private TipoTramite unTramite;
    
    @ManyToOne
    @JoinColumn(name = "fk_tasayTramite", nullable = false, updatable = true)
    private TasaAdministrativa unaTasa;
    
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
        if (!(object instanceof Tramite)) {
            return false;
        }
        Tramite other = (Tramite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.modelo.Tramite[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoTramite getUnTramite() {
        return unTramite;
    }

    public void setUnTramite(TipoTramite unTramite) {
        this.unTramite = unTramite;
    }

    public TasaAdministrativa getUnaTasa() {
        return unaTasa;
    }

    public void setUnaTasa(TasaAdministrativa unaTasa) {
        this.unaTasa = unaTasa;
    }
    
}
