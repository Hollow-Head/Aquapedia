package aquapedia.dao;

import aquapedia.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public abstract class DAO<Tipo> {
    Connection conexao;
    
    public DAO() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }
    
    public Connection getConnection() {
        return conexao;
    }
    
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
    
    public abstract void salvar( Tipo obj ) throws SQLException;
    
    public abstract void atualizar( Tipo obj ) throws SQLException;
    
    public abstract void excluir( Tipo obj ) throws SQLException;
    
    public abstract List<Tipo> listarTodos() throws SQLException;
    
    public abstract Tipo obterPorId( int id ) throws SQLException;
}
