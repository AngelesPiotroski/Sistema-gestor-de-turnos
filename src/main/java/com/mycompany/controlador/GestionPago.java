package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.dao.PagoJpaController;
import com.mycompany.dao.TurnoJpaController;
import com.mycompany.modelo.Pago;
import com.mycompany.modelo.Turno;
import java.util.ArrayList;
import java.util.List;

public class GestionPago {
    private final PagoJpaController pagoDAO;
    private final TurnoJpaController turnoDAO;
    
    public GestionPago(){
        this.pagoDAO= new PagoJpaController(Conexion.getEmf());
        this.turnoDAO= new TurnoJpaController(Conexion.getEmf());
    }
    
    public List<Pago> buscarPagos(){
    List<Pago> pagosEncontrados = new ArrayList<>();
        for (Pago pagoRec : pagoDAO.findPagoEntities()) {
            pagosEncontrados.add(pagoRec);
        }        
        return pagosEncontrados;    
    }
    
    public Pago buscarUnPago(Pago pago){
        Pago pagoEnc=null;
        for(Pago pagRec : buscarPagos()){
            if(pagRec.equals(pago)){
                pagoEnc=pagRec;
                break;
            }
        }
        return pagoEnc;
    }
    
    public void crearUnPago(Pago pagoCrear) throws Exception{
        if((buscarUnPago(pagoCrear))==null){
            pagoDAO.create(pagoCrear);
        }else{
            throw new Exception("Ya existe un pago con los mismos campos de pago");
        }
    }
    
    public void asignarPago(Pago pago, Turno turnoAsig) throws Exception{
        turnoAsig.setUnPago(pago);
        turnoDAO.edit(turnoAsig);
    }
}
