/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aquapedia.testes;

import java.sql.Connection;
import java.sql.SQLException;
import aquapedia.jdbc.ConnectionFactory;

/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public class TesteConnectionFactory {

    public static void main(String[] args) {
        try {
            Connection conexao = ConnectionFactory.getConnection();
            System.out.println("Conexão criada com sucesso!");
        } catch (SQLException exc) {
            System.err.println("Erro ao tentar criar a conexão!");
            exc.printStackTrace();
        }
    }
}
