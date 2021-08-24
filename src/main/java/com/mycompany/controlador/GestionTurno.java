package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.dao.DepartamentoJpaController;
import com.mycompany.dao.EmpleadoJpaController;
import com.mycompany.dao.EstadoTurnoJpaController;
import com.mycompany.dao.TurnoJpaController;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.EstadoTurno;
import com.mycompany.modelo.Pago;
import com.mycompany.modelo.Turno;
import java.util.ArrayList;
import java.util.List;

public class GestionTurno {
    private final TurnoJpaController turnoDAO;
    private final EstadoTurnoJpaController estadoTurnoDAO;
    private final EmpleadoJpaController empleadoDAO;
    private final DepartamentoJpaController deptoDAO;

    public GestionTurno() {
        this.empleadoDAO= new EmpleadoJpaController(Conexion.getEmf());
        this.turnoDAO= new TurnoJpaController(Conexion.getEmf());
        this.estadoTurnoDAO= new EstadoTurnoJpaController(Conexion.getEmf());
        this.deptoDAO= new DepartamentoJpaController(Conexion.getEmf());
    }
    
     public Integer buscarUltimoNroTurno(){
	Integer nro=turnoDAO.getTurnoCount();
        if(nro==null){
            nro=0;
        }else{
            nro=nro+1;
        }
        return nro;
    }
    
    
    
    public List<Turno> buscarTurnos(){
        List<Turno> turnosEncontrados = new ArrayList<>();
        for (Turno turnoRec : turnoDAO.findTurnoEntities()) {
            turnosEncontrados.add(turnoRec);
        }
        
        return turnosEncontrados;
    }
    
    public Turno buscarTurno(Turno turno){
        Turno turnoBuscado =null;
        for(Turno tur: this.buscarTurnos())
        {
            if(tur.getNro().equals(turno.getNro()))
            {
                turnoBuscado=tur;
                break;
            }
        }
        return turnoBuscado;
    }
    
    public Turno buscarTurnoPorNro(String nroturno){
        Turno turnoBuscado =null;
        for(Turno tur: this.buscarTurnos())
        {
            if(tur.getNro().equals(nroturno))
            {
                turnoBuscado=tur;
                break;
            }
        }
        return turnoBuscado;
    }
    
    public EstadoTurno buscarUnEstadoTurno(String estado){
        EstadoTurno EstadoBuscado =null;
        for(EstadoTurno estur: this.buscarEstadoTurnos())
        {
            if(estur.getDescripcion().equals(estado))
            {
                EstadoBuscado=estur;
                break;
            }
        }
        return EstadoBuscado;
    }
    
    public List<EstadoTurno> buscarEstadoTurnos(){
        List<EstadoTurno> estadoTurnosEncontrados = new ArrayList<>();
        for (EstadoTurno EstTurnoRec : estadoTurnoDAO.findEstadoTurnoEntities()) {
            estadoTurnosEncontrados.add(EstTurnoRec);
        }
        
        return estadoTurnosEncontrados;
    }
    
    public void crearTurno(Turno turnoAgregar, Departamento deptoAsignado) throws Exception{
        Turno turnoDuplicado = buscarTurno(turnoAgregar);
        //si el departamento tiene cupo para atenderlo
        if((turnoDuplicado == null)&&(deptoAsignado.cantDisponibleTurnos()>0)){
            turnoDAO.create(turnoAgregar);
            deptoAsignado.addUnTurno(turnoAgregar);
            deptoDAO.edit(deptoAsignado);
        }else{
            throw new Exception("El departamento no posee cupo para atender otro tramite");
        }
    }
    
    public void cambiarEstadoTurno(Turno turno,EstadoTurno estadoNuevo, Empleado empleadoRealizador) throws Exception{
        
        for (Turno turnosEmp : empleadoRealizador.getTurnos()) {
            if(turnosEmp.equals(turno)){
                empleadoRealizador.getTurnos().remove(turno);
                turno.setUnEstadoT(estadoNuevo);
                turnoDAO.edit(turno);
                empleadoRealizador.getTurnos().add(turno);
                empleadoDAO.edit(empleadoRealizador);
            }
        }
        
       
    }
    
    public List<Turno> turnosDeUnEstadoDepto(Departamento depto,EstadoTurno estadoTurnoBuscado){
        List<Turno> turnosEnEseEstadoTurno = new ArrayList<>();
        for(Turno tur : depto.getTurnos()){
            if(tur.getUnEstadoT().getDescripcion().equals(estadoTurnoBuscado.getDescripcion())){
                turnosEnEseEstadoTurno.add(tur);
            }
        }
        return turnosEnEseEstadoTurno;
    }
    
    public void registrarPagoTurno(Turno turno, Pago pago) throws Exception{
        turno.setUnPago(pago);
        turnoDAO.edit(turno);
    }
}
