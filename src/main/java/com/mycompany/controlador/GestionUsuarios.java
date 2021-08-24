package com.mycompany.controlador;

import com.mycompany.dao.Conexion;
import com.mycompany.dao.UsuarioJpaController;
import com.mycompany.modelo.Empleado;
import com.mycompany.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class GestionUsuarios {
    private final UsuarioJpaController UsuarioDAO;
    
    public GestionUsuarios() {
        this.UsuarioDAO= new UsuarioJpaController(Conexion.getEmf());
    }
    
    public Usuario buscarUsuario(String usuario){
        Usuario usuarioEnocntrado =null;
        for(Usuario us : UsuarioDAO.findUsuarioEntities()){
            if(us.getUsuario().equals(usuario)){
                usuarioEnocntrado = us;
                break;
            }
        }
        return usuarioEnocntrado;
    }
    
    public Usuario buscarUnUsuario (Usuario usua){
       Usuario usuBuscado=null;
       for(Usuario usuRec: buscarUsuarios()){
           if(usuRec.getUsuario().equals(usua.getUsuario()) && usuRec.getContrasena().equals(usua.getContrasena()) ){
               usuBuscado=usuRec;
               break;
           }
       }
       return usuBuscado;
   }
    
    
    public void crearUsuario(Usuario user, Empleado emple) throws Exception{
        Usuario usDuplicado = buscarUsuario(user.getUsuario());
        if(usDuplicado==null){
            user.setUnEmpleado(emple);
            UsuarioDAO.create(user);
        }else{
            throw new Exception("Ya existe un usuario con ese dominio");
        }  
    }
    
    
      public List<Usuario> buscarUsuarios(){
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuaRec : UsuarioDAO.findUsuarioEntities()) {
            usuariosEncontrados.add(usuaRec);
        }        
        return usuariosEncontrados;    
    }
}
