package com.mycompany.vistas;

import com.mycompany.controlador.GestionDepartamento;
import com.mycompany.controlador.GestionEmpleados;
import com.mycompany.controlador.GestionTramite;
import com.mycompany.controlador.GestionTurno;
import com.mycompany.controlador.GestionUsuarios;
import com.mycompany.modelo.Departamento;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.EstadoTurno;
import com.mycompany.modelo.TipoTramite;
import com.mycompany.modelo.Tramite;
import com.mycompany.modelo.Turno;
import com.mycompany.modelo.Usuario;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static java.util.Optional.empty;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RegistrarTurno extends javax.swing.JFrame {

    private final GestionUsuarios controlador;
    private final GestionEmpleados controladorEmpleado;
    private final Usuario usuarioAuxiliar;
    private final GestionTramite controladorTramite;
    private final GestionDepartamento controladorDepto;
    private final GestionTurno controladorTurno;
    private Departamento departamentoEncontrado;
    private Empleado empleadoSeleccionado;

    public RegistrarTurno(Usuario usuario) {
        initComponents();
        this.controlador = new GestionUsuarios();
        this.usuarioAuxiliar = usuario;
        this.controladorEmpleado = new GestionEmpleados();
        this.controladorTramite = new GestionTramite();
        this.controladorDepto = new GestionDepartamento();
        this.controladorTurno = new GestionTurno();
        // this.empleadoSeleccionado= null;

        this.setTitle("Registrar Turno");
        this.setResizable(false);
        this.panelturnos.setSize(530, 570);

        this.setLocationRelativeTo(null);
        setVisible(true);
        this.panelturnos.setVisible(true);
        cargarComboDepartamentos();

    }

    private void cargarComboTipoTramite(Departamento depto) {

        for (TipoTramite tt : controladorTramite.buscarTipoTramitesDepto(depto)) {
            comboTipoTram.addItem(tt.getDescripcion());
        }

    }

    private void cargarComboTramite(Departamento depto) {

        for (Tramite t : controladorTramite.buscarTramitesDepto(depto)) {
            comboTramite.addItem(t.getNombre());
        }

    }

    private void cargarComboDepartamentos() {

        for (Departamento d : controladorDepto.buscarDepartamentos()) {
            if (d.getNombre().equals("tramitesDNIPasaporte") || d.getNombre().equals("inscripciones")) {
                combodepto.addItem(d.getNombre());
            }
        }

    }

    /*
    cargar tabla
     */
    private void mostrarTablaEmpleadoActivos(Departamento depto) {
        List<Empleado> empleados = controladorEmpleado.buscarEmpleadosDeUnDepartamento(depto);

        String matriz[][] = new String[empleados.size()][3];

        if (!empleados.isEmpty()) {
            int i = 0;
            //JOptionPane.showMessageDialog(null, "hola");
            for (Empleado e : empleados) {

                matriz[i][0] = e.getNombre();
                matriz[i][1] = e.getApellido();
                matriz[i][2] = e.getDni();
                i++;
            }
            tablaEmpleadoActivos.setModel(new DefaultTableModel(
                    matriz,
                    new String[]{
                        "Nombre", "Apellido", "DNI"
                    }
            ));
        } else {
            //JOptionPane.showMessageDialog(null, "adios");
            tablaEmpleadoActivos.setModel(new DefaultTableModel(
                    null,
                    new String[]{
                        "Nombre", "Apellido", "DNI"
                    }
            ));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelturnos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleadoActivos = new javax.swing.JTable();
        comboTipoTram = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTramite = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        combodepto = new javax.swing.JComboBox<>();
        btnCrearTurno = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelturnos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaEmpleadoActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "DNI"
            }
        ));
        tablaEmpleadoActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpleadoActivosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEmpleadoActivos);

        panelturnos.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 190, 150));

        panelturnos.add(comboTipoTram, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 170, 30));

        jLabel2.setText("Tipo Tramite:");
        panelturnos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 130, 30));

        jLabel3.setText("Tramite:");
        panelturnos.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 120, 30));

        panelturnos.add(comboTramite, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 170, 30));

        jLabel4.setText("Empleados Activos");
        panelturnos.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 190, 30));

        jLabel1.setText("Departamento:");
        panelturnos.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 120, 30));

        combodepto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combodeptoItemStateChanged(evt);
            }
        });
        combodepto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combodeptoMouseClicked(evt);
            }
        });
        panelturnos.add(combodepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 180, 30));

        btnCrearTurno.setText("Crear Turno");
        btnCrearTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearTurnoActionPerformed(evt);
            }
        });
        panelturnos.add(btnCrearTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 430, 130, 60));

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelturnos.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 120, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelturnos, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelturnos, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaEmpleadoActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEmpleadoActivosMouseClicked
        int row = tablaEmpleadoActivos.rowAtPoint(evt.getPoint());
        if (row >= 0) {
            String dni = tablaEmpleadoActivos.getValueAt(row, 2).toString();
            if (dni.equals("")) {
                empleadoSeleccionado = null;
                JOptionPane.showMessageDialog(null, "Seleccione el empleado");
            } else {

                empleadoSeleccionado = controladorEmpleado.buscarEmpleadoPorDNI(dni);
                JOptionPane.showMessageDialog(null, "Selecciono a " + empleadoSeleccionado.getNombre() + " " + empleadoSeleccionado.getApellido());
            }
        }

    }//GEN-LAST:event_tablaEmpleadoActivosMouseClicked

    private void combodeptoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combodeptoItemStateChanged
        comboTramite.removeAllItems();
        comboTipoTram.removeAllItems();
        if (combodepto.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar Departamento");
        } else {
            String nombredepto = combodepto.getSelectedItem().toString();
            departamentoEncontrado = controladorDepto.buscarUnDepartamentoPorNombre(nombredepto);

            if (departamentoEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el departamento seleccionado.");
            } else {
                if (departamentoEncontrado.getCantmaxturnos() == 0) {
                    JOptionPane.showMessageDialog(null, "No puede registrar mas turnos, cantidad de Turnos superada.");
                } else {
                    this.panelturnos.setVisible(true);
                    cargarComboTipoTramite(departamentoEncontrado);
                    cargarComboTramite(departamentoEncontrado);
                    mostrarTablaEmpleadoActivos(departamentoEncontrado);
                    this.panelturnos.setVisible(true);
                }
            }
        }

    }//GEN-LAST:event_combodeptoItemStateChanged

    private void combodeptoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combodeptoMouseClicked
        comboTramite.removeAllItems();
        comboTipoTram.removeAllItems();


    }//GEN-LAST:event_combodeptoMouseClicked

    private void btnCrearTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearTurnoActionPerformed
        if (!combodepto.getSelectedItem().equals("") && !comboTipoTram.getSelectedItem().equals("") && !comboTramite.getSelectedItem().equals("")) {
            if (empleadoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro al empleado seleccionado.");
            } else {
                try {
                    Tramite tramite = controladorTramite.buscarUnTramite(comboTramite.getSelectedItem().toString());
                    Timestamp fecha = new Timestamp(System.currentTimeMillis());
                    EstadoTurno estado = controladorTurno.buscarUnEstadoTurno("atender");
                    String nroturno = controladorTurno.buscarUltimoNroTurno().toString();

                    Turno nuevoTurno = new Turno(nroturno, fecha, departamentoEncontrado, estado, tramite, empleadoSeleccionado);
                    controladorTurno.crearTurno(nuevoTurno, departamentoEncontrado);
                    JOptionPane.showMessageDialog(null, "Turno creado: Nro Turno: " + nroturno + " fecha: " + fecha);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el departamento, tramite, tipo tramite y empleado para crear un turno.");
        }


    }//GEN-LAST:event_btnCrearTurnoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        MenuMesaEntrada iralmemuu = new MenuMesaEntrada(usuarioAuxiliar);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearTurno;
    private javax.swing.JComboBox<String> comboTipoTram;
    private javax.swing.JComboBox<String> comboTramite;
    private javax.swing.JComboBox<String> combodepto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelturnos;
    private javax.swing.JTable tablaEmpleadoActivos;
    // End of variables declaration//GEN-END:variables
}
