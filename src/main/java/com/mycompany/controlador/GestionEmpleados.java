package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.dao.EmpleadoJpaController;
import com.mycompany.dao.TurnoJpaController;
import com.mycompany.modelo.ActividadEmpleado;
import com.mycompany.modelo.Delegacion;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.Turno;
import com.mycompany.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class GestionEmpleados {

    private final EmpleadoJpaController EmpleadoDAO;
   private final TurnoJpaController turnoDAO;
    
    public GestionEmpleados() {
        this.EmpleadoDAO = new EmpleadoJpaController(Conexion.getEmf());
        this.turnoDAO= new TurnoJpaController(Conexion.getEmf());
    }

    public Empleado buscarEmpleadoPorDNI(String dniBuscado) {
        Empleado empleadoEncontrado = null;
        List<Empleado> empleadosEncontrados = EmpleadoDAO.findEmpleadoEntities();
        for (Empleado emp : empleadosEncontrados) {
            if (emp.getDni().equals(dniBuscado)) {
                empleadoEncontrado = emp;
                break;
            }
        }
        return empleadoEncontrado;
    }

    public Empleado buscarEmpleadoPorDepartamento(Departamento depto) {
        Empleado empleadoEncontrado = null;
        List<Empleado> empleadosEncontrados = EmpleadoDAO.findEmpleadoEntities();
        for (Empleado emp : empleadosEncontrados) {
            if (emp.getUnDepartamento().equals(depto)) {
                empleadoEncontrado = emp;
                break;
            }
        }
        return empleadoEncontrado;
    }

    public List<Empleado> buscarEmpleadosDeUnDepartamento(Departamento depto) {
        List<Empleado> empleados = new ArrayList<>();
        List<Empleado> empleadosEncontrados = EmpleadoDAO.findEmpleadoEntities();
        for (Empleado emp : empleadosEncontrados) {
            if (emp.getUnDepartamento().equals(depto)) {
                if(emp.isActivo()){
                    empleados.add(emp);
                   
                }
            }
        }
        return empleados;
    }
    
    public List<Turno> buscarTurnosAtenderEmpleado(Empleado emple) {
        List<Turno> turnos = new ArrayList<>();
        List<Turno> turnosEncontrados = turnoDAO.findTurnoEntities();
        for (Turno tur : turnosEncontrados) {
            if (emple.getTurnos().contains(tur)) {
                if(tur.getUnEstadoT().getDescripcion().equals("atender")){
                    turnos.add(tur);
                   
                 }
            }
        }
        return turnos;
    }

    public List<Empleado> buscarTodosEmpleadosDeUnDepartamento(Departamento depto) {
        List<Empleado> empleados = new ArrayList<>();
        List<Empleado> empleadosEncontrados = EmpleadoDAO.findEmpleadoEntities();
        for (Empleado emp : empleadosEncontrados) {
            if (emp.getUnDepartamento().equals(depto)) {
                empleados.add(emp);
                break;
            }
        }
        return empleados;
    }

    public List<Empleado> buscarEmpleados() {
        List<Empleado> empleadosEncontrados = new ArrayList<>();
        for (Empleado empleadoRec : EmpleadoDAO.findEmpleadoEntities()) {
            empleadosEncontrados.add(empleadoRec);
        }

        return empleadosEncontrados;
    }

    public void crearEmpleado(String dni, String nombre, String apellido, Delegacion delegacion, Departamento depa, Usuario user) {
        Empleado empDuplicado = buscarEmpleadoPorDNI(dni);
        if (empDuplicado == null) {
            Empleado nuevoEmple = new Empleado(dni, nombre, apellido, delegacion, depa);
            EmpleadoDAO.create(nuevoEmple);
        } else {
            System.out.println("Ya existe el empleado con DNI: " + dni);
        }
    }
    
    public void actualizarEmpleado (Empleado emple) throws Exception{
        EmpleadoDAO.edit(emple);
    }
    
  
}
