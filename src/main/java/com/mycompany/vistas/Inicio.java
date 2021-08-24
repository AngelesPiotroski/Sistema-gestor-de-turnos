package com.mycompany.vistas;

import com.mycompany.controlador.GestionEmpleados;
import com.mycompany.controlador.GestionUsuarios;
import com.mycompany.controlador.GestionarActividad;
import com.mycompany.modelo.ActividadEmpleado;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.TipoActividad;
import com.mycompany.modelo.Usuario;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Inicio extends javax.swing.JFrame {

    private final GestionUsuarios controlador;
    private final Usuario usuarioAuxiliar;
    private final GestionEmpleados controladorEmp;
    private final GestionarActividad controladorActiv;

    public Inicio() {
        initComponents();
        this.controlador = new GestionUsuarios();
        this.controladorEmp = new GestionEmpleados();
        this.controladorActiv = new GestionarActividad();
        this.usuarioAuxiliar = new Usuario();
        this.setTitle("Inicio");
        this.setResizable(false);
        this.setSize(365, 270);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usuarioTEXT = new javax.swing.JTextField();
        contra = new javax.swing.JTextField();
        btnIniciarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(usuarioTEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 210, 30));

        contra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contraActionPerformed(evt);
            }
        });
        getContentPane().add(contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 210, 30));

        btnIniciarSesion.setText("Iniciar Sesion");
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });
        getContentPane().add(btnIniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 220, 40));

        jLabel1.setText("Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 200, 30));

        jLabel2.setText("Contraseña");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 190, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed

        if (!usuarioTEXT.getText().equals("") && !contra.getText().equals("")) {

            usuarioAuxiliar.setUsuario(usuarioTEXT.getText());
            usuarioAuxiliar.setContrasena(contra.getText());

            Usuario usuarioEncontrado = controlador.buscarUnUsuario(usuarioAuxiliar);

            if (usuarioEncontrado == null) {
                JOptionPane.showMessageDialog(null, "Debe ingresar e-mail y/o contraseña");
                setVisible(true);

            } else {

                JOptionPane.showMessageDialog(null, "Bienvenido/a " + usuarioEncontrado.getUsuario());
                usuarioEncontrado.getUnEmpleado().setActivo(true);
                try {
                    controladorEmp.actualizarEmpleado(usuarioEncontrado.getUnEmpleado());
                    Timestamp fecha = new Timestamp(System.currentTimeMillis());

                    controladorActiv.asignarActividadEmpleado(fecha, "iniciosesion", usuarioEncontrado.getUnEmpleado());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

                //  System.out.println(usuarioEncontrado.getUsuario() + usuarioEncontrado.getContrasena());
                //   System.out.println(usuarioEncontrado.getUnEmpleado().getNombre());
                if (usuarioEncontrado.getUnEmpleado().getUnDepartamento().getNombre().equals("caja")) {

                    MenuCaja iralmenucaja = new MenuCaja(usuarioEncontrado);
                    dispose();
                } else {
                    if (usuarioEncontrado.getUnEmpleado().getUnDepartamento().getNombre().equals("mesaentrada")) {

                        MenuMesaEntrada iralmenumesa = new MenuMesaEntrada(usuarioEncontrado);
                        dispose();

                    } else {

                        if (usuarioEncontrado.getUnEmpleado().getUnDepartamento().getNombre().equals("entregas")) {
                            MenuEntrega iralmenuenr = new MenuEntrega(usuarioEncontrado);
                            dispose();
                        } else {

                            if (usuarioEncontrado.getUnEmpleado().getUnDepartamento().getNombre().equals("tramitesDNIPasaporte")) {
                                MenuTramiteDNIPasaporte iralmenuettr = new MenuTramiteDNIPasaporte(usuarioEncontrado);
                                dispose();
                            } else {

                                if (usuarioEncontrado.getUnEmpleado().getUnDepartamento().getNombre().equals("inscripciones")) {
                                    MenuInscripcion iralmenueinc = new MenuInscripcion(usuarioEncontrado);
                                    dispose();
                                }
                            }
                        }
                    }
                }
            }

        }
    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    private void contraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JTextField contra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField usuarioTEXT;
    // End of variables declaration//GEN-END:variables
}
