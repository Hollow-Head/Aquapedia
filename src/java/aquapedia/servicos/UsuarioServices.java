/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aquapedia.servicos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aquapedia.dao.UsuarioDAO;
import aquapedia.entidades.Usuario;

/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public class UsuarioServices {
    public List<Usuario> getTodos() {
        List<Usuario> lista = new ArrayList<>();
        UsuarioDAO dao = null;
        
        try {
            dao = new UsuarioDAO();
            lista = dao.listarTodos();
        } catch( SQLException exc ) {
            exc.printStackTrace();
        } finally {
            if ( dao != null ) {
                try {
                    dao.fecharConexao();
                } catch( SQLException exc ) {
                    exc.printStackTrace();
                }
            }
        }
        
        return lista;
    }
}
