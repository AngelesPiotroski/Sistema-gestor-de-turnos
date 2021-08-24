package com.mycompany.controlador;

import com.mycompany.dao.ActividadEmpleadoJpaController;
import com.mycompany.dao.Conexion;
import com.mycompany.dao.EmpleadoJpaController;
import com.mycompany.dao.TipoActividadJpaController;
import com.mycompany.modelo.ActividadEmpleado;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.TipoActividad;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class GestionarActividad {
    private final TipoActividadJpaController TipoActividadDAO;
    private final ActividadEmpleadoJpaController ActividadDAO;
     private final EmpleadoJpaController EmpleadoDAO;
    
    public GestionarActividad() {
        this.TipoActividadDAO= new TipoActividadJpaController(Conexion.getEmf());
         this.EmpleadoDAO = new EmpleadoJpaController(Conexion.getEmf());
         this.ActividadDAO= new ActividadEmpleadoJpaController(Conexion.getEmf());
    }
    
    //TODO TipoActividad
    public List<TipoActividad> buscarTipoActividades(){
        List<TipoActividad> tipoActividadesEncontrados= new ArrayList<>();
        for(TipoActividad tipoActvRec : TipoActividadDAO.findTipoActividadEntities()){
            tipoActividadesEncontrados.add(tipoActvRec);
        }
        return tipoActividadesEncontrados;
    }
    
    public TipoActividad buscarUnTipoActividad(String descripcion){
        TipoActividad tipoActEncontrado=null;
        for(TipoActividad TiposActvs: TipoActividadDAO.findTipoActividadEntities()){
            if(TiposActvs.getDescripcion().equals(descripcion)){
                tipoActEncontrado =    TiposActvs;
                break; 
            }  
        }
        return tipoActEncontrado;
    }
    
    public void crearTipoActividad(String descripcion) throws Exception{
        TipoActividad tipoActvDuplicado=buscarUnTipoActividad(descripcion);
        if(tipoActvDuplicado==null){
            TipoActividad tipoActvNew = new TipoActividad(descripcion);
            TipoActividadDAO.create(tipoActvNew);
        }  else{
            throw new Exception("Ya existe un Tipo de Actividad con ese nombre");
        }
    }
    
    //TODO actividad empleado
    public void asignarActividadEmpleado(Timestamp fecha, String tipoActvidad, Empleado emple) throws Exception{
        TipoActividad tipoaux= this.buscarUnTipoActividad(tipoActvidad);
        ActividadEmpleado actEmple = new ActividadEmpleado(fecha,tipoaux,emple);
        emple.setUnaActividad(actEmple);
        ActividadDAO.create(actEmple);
        EmpleadoDAO.edit(emple);
    }
   
}
