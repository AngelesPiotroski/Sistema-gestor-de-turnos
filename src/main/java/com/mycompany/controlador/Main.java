/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.modelo.Delegacion;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.Usuario;
import com.mycompany.vistas.Inicio;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piotr
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("hola");

        new Conexion();

        Inicio vista = new Inicio();
       
       /*
        GestionDepartamento gd = new GestionDepartamento();
        Departamento d = gd.buscarUnDepartamentoPorNombre("tramitesDNIPasaporte");
        Departamento de = gd.buscarUnDepartamentoPorNombre("inscripciones");
        
        System.out.println(d.cantDisponibleTurnos());
         System.out.println(de.cantDisponibleTurnos());
         
          
       GestionTurno gt = new GestionTurno();
        gt.buscarTurnos();
        
        GestionDepartamento gd = new GestionDepartamento();
      
       
        for (Departamento dep : gd.buscarDepartamentos()) {
            System.out.println(dep.getNombre());
        }
        
        
       
        GestionEmpleados ge = new GestionEmpleados();
        
        for (Empleado em : ge.buscarEmpleados()) {
            System.out.println(em.getNombre());
             System.out.println(em.getUnDepartamento().getNombre());
        }
        GestionUsuarios gu = new GestionUsuarios();
         for (Usuario u : gu.buscarUsuarios()) {
            System.out.println(u.getUsuario() + u.getUnEmpleado().getUnDepartamento().getNombre());
        }*/

    }
}
