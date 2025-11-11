/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package aquapedia.servlets;

import aquapedia.dao.UsuarioDAO;
import aquapedia.entidades.Usuario;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import aquapedia.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author Samuel
 */
@WebServlet(name = "ProcessaCadastro", urlPatterns = {"/processaCadastro"})
public class ProcessaCadastro extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("aaENTROU");
        String acao = request.getParameter("acao");
        UsuarioDAO dao = null;
        RequestDispatcher disp = null;

        try {

            dao = new UsuarioDAO();

            String nome = request.getParameter("nickname");
            String email = request.getParameter("email");
            String senha = request.getParameter("password");
            String confirmarSenha = request.getParameter("confirm-password");

            //validar o senha e confirma senha
            //validar senha e email e apelido
            String validaNome = StringValidator.isNicknameValid(nome);
            boolean validaEmail = StringValidator.isEmailValid(email);
            String validaSenha = StringValidator.isPasswordValid(senha);

            if (nome == null || email == null || senha == null || confirmarSenha == null) {
                request.setAttribute("erro", "Por Favor, Preencha todos os campos");
                disp = request.getRequestDispatcher("/register.jsp");
                disp.forward(request, response);
            } else if (validaNome != null) {
                request.setAttribute("erro", validaNome);
                disp = request.getRequestDispatcher("/");
                disp.forward(request, response);
            }

            try {
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setNome(nome);
                usuario.setSenha(senha);

                dao.salvar(usuario);

                disp = request.getRequestDispatcher(
                        "/");
            } catch (Exception e) {
                if (dao != null) {
                    try {
                        dao.fecharConexao();
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            //pegar erros das senhas, email e apelido
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
