/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aquapedia.testes;

import aquapedia.entidades.Usuario;
import aquapedia.servicos.UsuarioServices;
import java.util.List;

/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public class TesteListarUsuarios {
    public static void main(String[] args) {
        try {
            UsuarioServices services = new UsuarioServices();
            List<Usuario> lista = services.getTodos();
            
            System.out.println("Usuários Cadastrados:");
            
            for (int i = 0; i < lista.size(); i++) {
                System.out.print("Nome: " + lista.get(i).getNome() + "\nEmail: " + lista.get(i).getEmail() + "\n\n");
            }
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }
}
