package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.dao.TipoTramiteJpaController;
import com.mycompany.dao.TramiteJpaController;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.TasaAdministrativa;
import com.mycompany.modelo.TipoTramite;
import com.mycompany.modelo.Tramite;
import java.util.ArrayList;
import java.util.List;

public class GestionTramite {
   private final  TramiteJpaController tramiteDAO;
   private final TipoTramiteJpaController tipoTramiteDAO;
   
   public GestionTramite(){
       this.tramiteDAO=new TramiteJpaController(Conexion.getEmf());
       this.tipoTramiteDAO=new TipoTramiteJpaController(Conexion.getEmf());
   }
   
   public List<Tramite> buscarTramites(){
    List<Tramite> tramitesEncontrados = new ArrayList<>();
        for (Tramite tramRec : tramiteDAO.findTramiteEntities()) {
            tramitesEncontrados.add(tramRec);
        }        
        return tramitesEncontrados;    
    }
   
   public Tramite buscarUnTramite(String nombre){
       Tramite tramBuscado=null;
       for(Tramite tramRec: this.buscarTramites()){
           if(tramRec.getNombre().equals(nombre)){
               tramBuscado=tramRec;
               break;
           }
       }
       return tramBuscado;
   }
   
   public List<TipoTramite> buscarTipoTramites(){
    List<TipoTramite> tipoTramitesEncontrados = new ArrayList<>();
        for (TipoTramite tipoTramRec : tipoTramiteDAO.findTipoTramiteEntities()) {
            tipoTramitesEncontrados.add(tipoTramRec);
        }        
        return tipoTramitesEncontrados;    
    }
   
   public TipoTramite buscarUnTipoTramite(TipoTramite tipoTram){
       TipoTramite tipoTramBuscado=null;
       for(TipoTramite tipoTramRec : buscarTipoTramites()){
           if(tipoTramRec.getDescripcion().equals(tipoTram.getDescripcion())){
               tipoTramRec=tipoTramBuscado;
               break;
           }
       }
       return tipoTramBuscado;
   }
   
   public void crearUnTipoTramite(TipoTramite tipoTramAgregar) throws Exception{
       if(buscarUnTipoTramite(tipoTramAgregar)==null){
           tipoTramiteDAO.create(tipoTramAgregar);
       }else{
           throw new Exception("Ya existe un Tipo de Tramite con ese dominio");
       }
   }
   
   /*
   public void crearUnTramite(Tramite tramiteAgregar) throws Exception{
       if(buscarUnTramite(tramiteAgregar)==null){
           tramiteDAO.create(tramiteAgregar);
       }else{
           throw new Exception("Ya existe un Tramite con ese dominio");
       }
   }
   */
   
   public void asignarTasaAdministrativa(TasaAdministrativa tasa, Tramite tram) throws Exception{
       tram.setUnaTasa(tasa);
       tramiteDAO.edit(tram);
   }
   
   
   public List<TipoTramite> buscarTipoTramitesDepto(Departamento depto){
    List<TipoTramite> tipoTramitesEncontrados = new ArrayList<>();
        for (TipoTramite tipoTramRec : tipoTramiteDAO.findTipoTramiteEntities()) {
            if(tipoTramRec.getUnDepartamentoTT().equals(depto)){
                  tipoTramitesEncontrados.add(tipoTramRec);
            }
          
        }        
        return tipoTramitesEncontrados;    
    }
   
   public List<Tramite> buscarTramitesDepto(Departamento depto){
    List<Tramite> TramitesEncontrados = new ArrayList<>();
    List<TipoTramite> tipoTramitesEncontrados = new ArrayList<>();  
    for (Tramite TramRec : tramiteDAO.findTramiteEntities()) {
          for (TipoTramite tipoTramRec : tipoTramiteDAO.findTipoTramiteEntities()) {  
                if(tipoTramRec.getUnDepartamentoTT().equals(depto) && tipoTramRec.getTramites().contains(TramRec)){
                  TramitesEncontrados.add(TramRec);
            }
          
        }        
          
    }
     return TramitesEncontrados; 
   }
}
   
