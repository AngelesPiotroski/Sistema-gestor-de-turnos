package com.mycompany.vistas;

import com.mycompany.controlador.GestionDepartamento;
import com.mycompany.controlador.GestionEmpleados;
import com.mycompany.controlador.GestionTramite;
import com.mycompany.controlador.GestionUsuarios;
import com.mycompany.controlador.GestionarActividad;

import com.mycompany.modelo.Usuario;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MenuInscripcion extends javax.swing.JFrame {

    private final GestionUsuarios controlador;

    private final Usuario usuarioAuxiliar;
    private final GestionEmpleados controladorEmp;
    private final GestionarActividad controladorActiv;

    public MenuInscripcion(Usuario usuario) {
        initComponents();
        this.controlador = new GestionUsuarios();
        this.usuarioAuxiliar = usuario;
        this.controladorEmp = new GestionEmpleados();
        this.controladorActiv = new GestionarActividad();

        this.setTitle("Menu Mesa de Entrada");
        this.setResizable(false);
        this.setSize(350, 420);
        this.setLocationRelativeTo(null);
        setVisible(true);
        this.panelmenu.setVisible(true);
        btnReanudarPausa.setEnabled(false);
        btnSolicitarPausa.setEnabled(true);
        btnInscripcion.setEnabled(true);
        btnCerrarSesion.setEnabled(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelmenu = new javax.swing.JPanel();
        btnInscripcion = new javax.swing.JButton();
        btnSolicitarPausa = new javax.swing.JButton();
        btnReanudarPausa = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelmenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnInscripcion.setText("Atender Turno");
        btnInscripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInscripcionActionPerformed(evt);
            }
        });
        panelmenu.add(btnInscripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 180, 50));

        btnSolicitarPausa.setText("Solicitar Pausa");
        btnSolicitarPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitarPausaActionPerformed(evt);
            }
        });
        panelmenu.add(btnSolicitarPausa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 180, 50));

        btnReanudarPausa.setText("Reanudar Pausa");
        btnReanudarPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReanudarPausaActionPerformed(evt);
            }
        });
        panelmenu.add(btnReanudarPausa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 180, 50));

        btnCerrarSesion.setText("Cerrar Sesion");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        panelmenu.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 180, 50));

        getContentPane().add(panelmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInscripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInscripcionActionPerformed
        AtenderTurno atender = new AtenderTurno(usuarioAuxiliar);
        dispose();
        
    }//GEN-LAST:event_btnInscripcionActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        try {
            usuarioAuxiliar.getUnEmpleado().setActivo(false);
            Timestamp fecha = new Timestamp(System.currentTimeMillis());
            controladorEmp.actualizarEmpleado(usuarioAuxiliar.getUnEmpleado());
            controladorActiv.asignarActividadEmpleado(fecha, "finsesion", usuarioAuxiliar.getUnEmpleado());
            
            Inicio iralinicio = new Inicio();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnSolicitarPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarPausaActionPerformed
        try {
            usuarioAuxiliar.getUnEmpleado().setActivo(false);
            Timestamp fecha = new Timestamp(System.currentTimeMillis());
            controladorEmp.actualizarEmpleado(usuarioAuxiliar.getUnEmpleado());
            controladorActiv.asignarActividadEmpleado(fecha, "pausa", usuarioAuxiliar.getUnEmpleado());

            btnReanudarPausa.setEnabled(true);
            btnSolicitarPausa.setEnabled(false);
            btnInscripcion.setEnabled(false);
            btnCerrarSesion.setEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnSolicitarPausaActionPerformed


    private void btnReanudarPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReanudarPausaActionPerformed
        try {
            usuarioAuxiliar.getUnEmpleado().setActivo(true);
            Timestamp fecha = new Timestamp(System.currentTimeMillis());
            controladorEmp.actualizarEmpleado(usuarioAuxiliar.getUnEmpleado());
            controladorActiv.asignarActividadEmpleado(fecha, "finpausa", usuarioAuxiliar.getUnEmpleado());
            
            btnSolicitarPausa.setEnabled(true);
            btnReanudarPausa.setEnabled(false);
            btnInscripcion.setEnabled(true);
            btnCerrarSesion.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnReanudarPausaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnInscripcion;
    private javax.swing.JButton btnReanudarPausa;
    private javax.swing.JButton btnSolicitarPausa;
    private javax.swing.JPanel panelmenu;
    // End of variables declaration//GEN-END:variables
}
