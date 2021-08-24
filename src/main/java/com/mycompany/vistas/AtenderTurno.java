package com.mycompany.vistas;

import com.mycompany.controlador.GestionEmpleados;
import com.mycompany.controlador.GestionTurno;
import com.mycompany.controlador.GestionUsuarios;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.EstadoTurno;
import com.mycompany.modelo.Turno;
import com.mycompany.modelo.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

public class AtenderTurno extends javax.swing.JFrame {

    private final GestionUsuarios controlador;

    private final Usuario usuarioAuxiliar;
    private Empleado empleadoAuxiliar = null;
    private Turno turno;
    private final GestionEmpleados controladorEmp;
    private final GestionTurno controladorTurno;

    public AtenderTurno(Usuario usuario) {
        initComponents();
        this.controlador = new GestionUsuarios();
        this.usuarioAuxiliar = usuario;
        this.controladorEmp = new GestionEmpleados();
        this.controladorTurno = new GestionTurno();
        this.empleadoAuxiliar = usuarioAuxiliar.getUnEmpleado();
        cargarTablaTurnosEmpleado(empleadoAuxiliar);
        this.setTitle("Atender Turnos");
        this.setResizable(false);
        this.setSize(570, 410);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /*
    cargar tabla
     */
    private void cargarTablaTurnosEmpleado(Empleado emp) {

        List<Turno> turnos = controladorEmp.buscarTurnosAtenderEmpleado(emp);

        String matriz[][] = new String[turnos.size()][3];

        if (!turnos.isEmpty()) {
            int i = 0;
            //JOptionPane.showMessageDialog(null, "hola");
            for (Turno t : turnos) {
                if (t.getUnEstadoT().getDescripcion().equals("atender")) {
                    matriz[i][0] = t.getNro();
                    matriz[i][1] = t.getUnTramite().getNombre();
                    matriz[i][2] = t.getUnEstadoT().getDescripcion();
                    i++;

                }
            }
            tablaTurnosEmpleado.setModel(new DefaultTableModel(
                    matriz,
                    new String[]{
                        "NroTurno", "Tramite", "Estado"
                    }
            ));
        } else {
            JOptionPane.showMessageDialog(null, "No posee turnos para atender");
            tablaTurnosEmpleado.setModel(new DefaultTableModel(
                    null,
                    new String[]{
                        "NroTurno", "Tramite", "Estado"
                    }
            ));
           
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTurnosEmpleado = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAtender = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaTurnosEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NroTurno", "Tramite", "Estado"
            }
        ));
        tablaTurnosEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTurnosEmpleadoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTurnosEmpleado);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 490, 160));

        jLabel1.setText("Mis Turnos: ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 34, 240, 30));

        btnAtender.setText("Atender");
        btnAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderActionPerformed(evt);
            }
        });
        getContentPane().add(btnAtender, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 130, 40));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 120, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        if (empleadoAuxiliar.getUnDepartamento().getNombre().equals("inscripciones")) {
            MenuInscripcion volvermenu = new MenuInscripcion(usuarioAuxiliar);
            dispose();
        } else {
            MenuTramiteDNIPasaporte iralmenu = new MenuTramiteDNIPasaporte(usuarioAuxiliar);
            dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tablaTurnosEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTurnosEmpleadoMouseClicked
        int row = tablaTurnosEmpleado.rowAtPoint(evt.getPoint());
        if (row >= 0) {

            String nroturno = tablaTurnosEmpleado.getValueAt(row, 0).toString();
            if (nroturno.equals("") && nroturno == null) {

                JOptionPane.showMessageDialog(null, "Seleccione un turno");
                this.setVisible(true);
            } else {
                turno = controladorTurno.buscarTurnoPorNro(nroturno);

            }

        }
    }//GEN-LAST:event_tablaTurnosEmpleadoMouseClicked

    private void btnAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderActionPerformed
        if (turno == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un turno");
        } else {
            try {
                EstadoTurno estado = controladorTurno.buscarUnEstadoTurno("atendido");
                controladorTurno.cambiarEstadoTurno(turno, estado, empleadoAuxiliar);
                JOptionPane.showMessageDialog(null, "Se actualizo el turno: " + turno.getNro() + " con el estado: atendido");

                turno = null;
               
                empleadoAuxiliar= controladorEmp.buscarEmpleadoPorDNI(usuarioAuxiliar.getUnEmpleado().getDni());
                cargarTablaTurnosEmpleado(empleadoAuxiliar);
                this.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }//GEN-LAST:event_btnAtenderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtender;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTurnosEmpleado;
    // End of variables declaration//GEN-END:variables
}
