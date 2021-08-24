package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.dao.DepartamentoJpaController;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.TipoTramite;
import com.mycompany.modelo.Tramite;
import java.util.ArrayList;
import java.util.List;

public class GestionDepartamento {
    private final DepartamentoJpaController deptoDAO;
    
    public GestionDepartamento(){
        this.deptoDAO= new DepartamentoJpaController(Conexion.getEmf());
    }
    
    public List<Departamento> buscarDepartamentos(){
    List<Departamento> deptosEncontrados = new ArrayList<>();
        for (Departamento depRec : deptoDAO.findDepartamentoEntities()) {
            deptosEncontrados.add(depRec);
        }        
        return deptosEncontrados;    
    }
    
    public Departamento buscarUnDepartamento(Departamento dep){
        Departamento depEncontrado =null;
        for(Departamento depRec : buscarDepartamentos()){
            if(depRec.getNombre().equals(dep.getNombre()))
            {
                depEncontrado=depRec;
                break;
            }
        }
        return depEncontrado;
    }
    
   public Departamento buscarUnDepartamentoPorNombre(String nombre){
        Departamento depEncontrado =null;
        for(Departamento depRec : this.buscarDepartamentos()){
            if(depRec.getNombre().equals(nombre))
            {
                depEncontrado=depRec;
                break;
            }
        }
        return depEncontrado;
    }
    
    
    public void crearDepartamento(Departamento deptoAgregar) throws Exception{
        Departamento depDuplicado=buscarUnDepartamento(deptoAgregar);
        if(depDuplicado==null){
            deptoDAO.create(deptoAgregar);
        }else{
            throw new Exception("Ya existe un departamento con ese dominio");
        }
    }
    
    public void agregarUnEmpleado(Departamento depto, Empleado emple) throws Exception{
        if(!(depto.getEmpleados().contains(emple))){
            depto.addUnEmpleado(emple);
            deptoDAO.edit(depto);
        }
    }
    
    public void agregarUnTipoTramite(Departamento depto, TipoTramite tipotram) throws Exception{
        if(!(depto.getTipostramites().contains(tipotram))){
            depto.addUnTipoTramite(tipotram);
            deptoDAO.edit(depto);
        }
    }
}
