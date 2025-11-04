/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aquapedia.dao;

import aquapedia.entidades.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public class UsuarioDAO extends DAO<Usuario> {

    public UsuarioDAO() throws SQLException {
    }

    
    @Override
    public void salvar(Usuario obj) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(
                """
                INSERT INTO USUARIOS
                (USR_NOME, USR_EMAIL, USR_SENHA)
                VALUES
                (?, ?, ?);
                """
        );
        
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getEmail());
        stmt.setString(3, obj.getSenha());
        
        stmt.executeUpdate();
    }

    @Override
    public void atualizar(Usuario obj) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(
                """
                UPDATE USUARIOS
                SET
                USR_NOME = ?,
                USR_EMAIL = ?, 
                USR_SENHA = ?, 
                USR_PROGRESSO = ?, 
                USR_FOTO = ?
                WHERE
                USR_ID = ?;
                """
        );
        
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getEmail());
        stmt.setString(3, obj.getSenha());
        stmt.setInt(4, obj.getProgresso());
        stmt.setInt(5, obj.getFoto());
        
        stmt.setInt(6, obj.getId());
        
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public void excluir(Usuario obj) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(
                """
                DELETE FROM USUARIOS
                WHERE USR_ID = ?;
                """
        );
        
        stmt.setInt(1, obj.getId());
        
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        
        PreparedStatement stmt = conexao.prepareStatement(
                """
                SELECT * FROM USUARIOS
                ORDER BY USR_NOME;
                """
        );
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Usuario usuario = new Usuario();
            
            usuario.setId(rs.getInt("USR_ID"));
            usuario.setNome(rs.getString("USR_NOME"));
            usuario.setEmail(rs.getString("USR_EMAIL"));
            usuario.setProgresso(rs.getInt("USR_PROGRESSO"));
            usuario.setFoto(rs.getInt("USR_FOTO"));
            
            lista.add(usuario);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }

    @Override
    public Usuario obterPorId(int id) throws SQLException {
        Usuario usuario = null;
        
        PreparedStatement stmt = conexao.prepareStatement(
                """
                SELECT * FROM USUARIOS
                WHERE USR_ID = ?;
                """
        );
        
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            usuario = new Usuario();
            
            usuario.setId(rs.getInt("USR_ID"));
            usuario.setNome(rs.getString("USR_NOME"));
            usuario.setEmail(rs.getString("USR_EMAIL"));
            usuario.setProgresso(rs.getInt("USR_PROGRESSO"));
            usuario.setFoto(rs.getInt("USR_FOTO"));
        }
        
        rs.close();
        stmt.close();
        
        return usuario;
    }
    
    
}
