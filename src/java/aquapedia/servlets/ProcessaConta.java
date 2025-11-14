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
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.SQLException;

/**
 *
 * @author Samuel
 */
@WebServlet(name = "ProcessaConta", urlPatterns = {"/processaConta"})
public class ProcessaConta extends HttpServlet {

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

        String acao = request.getParameter("acao");
        UsuarioDAO dao = null;
        RequestDispatcher disp = null;

        if (acao.equals("cadastro")) {
            try {

                dao = new UsuarioDAO();

                String nome = request.getParameter("nickname");
                String email = request.getParameter("email");
                String senha = request.getParameter("password");
                String confirmarSenha = request.getParameter("confirm-password");

                //validar o senha e confirma senha
                //validar senha e email e apelido
                String validaNome = StringValidator.isNicknameValid(nome);
                String validaEmail = StringValidator.isEmailValid(email);
                String validaSenha = StringValidator.isPasswordValid(senha);
                String conferirSenhas = StringValidator.arePasswordsEqual(senha, confirmarSenha);

                String erro = null;

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                    erro = "Por Favor, Preencha todos os campos";
                } else if (conferirSenhas != null) {
                    erro = conferirSenhas;
                } else if (validaNome != null) {
                    erro = validaNome;
                } else if (validaEmail != null) {
                    erro = validaEmail;
                } else if (validaSenha != null) {
                    erro = validaSenha;
                }
                if (dao.verificarSeJáExisteUsuário(nome, email) != null) {
                    erro = dao.verificarSeJáExisteUsuário(nome, email);
                }

                if (erro != null) {
                    throw new Exception(erro);

                }
                try {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setNome(nome);

                    byte[] salt = PasswordEncoder.getNextSalt();
                    byte[] hash = PasswordEncoder.hash(senha, salt);

                    usuario.setSenha_hash(hash);
                    usuario.setSenha_salt(salt);

                    dao.salvar(usuario);

                    disp = request.getRequestDispatcher(
                            "/index.jsp");
                    disp.forward(request, response);
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
                System.out.println("deu erro ó " + e);
                request.setAttribute("erro", e.getMessage());
                disp = request.getRequestDispatcher("/register.jsp");
                disp.forward(request, response);
            }

        } else if (acao.equals("login")) {
            try {
                dao = new UsuarioDAO();

                String email = request.getParameter("email");
                String senha = request.getParameter("password");

                String erro = null;

                if (email == null || senha == null) {
                    erro = "Por Favor, Preencha todos os campos";

                }

                Usuario usuario = dao.buscarPorUsuario(email);

                if (usuario == null) {
                    erro = "Esse aquapédico não existe";

                } else {

                    byte[] saltDoBanco = usuario.getSenha_salt();

                    byte[] senhaCriptografada = PasswordEncoder.hash(senha, saltDoBanco);

                    byte[] hashDoBanco = usuario.getSenha_hash();

                    if (!MessageDigest.isEqual(senhaCriptografada, hashDoBanco)) {
                        erro = "E-mail ou senha inválidos";
                    }
                }

                if (erro == null) {

                    HttpSession sessao = request.getSession(true);
                    sessao.setAttribute("usuarioLogado", usuario);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    throw new Exception(erro);
                }
            } catch (Exception e) {
                request.setAttribute("erro", e.getMessage());
                disp = request.getRequestDispatcher("/login.jsp");
                disp.forward(request, response);
            }
        } else if (acao.equals("completarNivel")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try (PrintWriter out = response.getWriter()) {
                HttpSession sessao = request.getSession(false);
                Usuario usuarioLogado = (sessao != null) ? (Usuario) sessao.getAttribute("usuarioLogado") : null;

                if (usuarioLogado == null) {
                    out.print("{\success\": false, \"message\": \"Usuário não está logado.\"}");
                }

                int nivelCompletado = Integer.parseInt(request.getParameter("id"));
                int progressoAtual = usuarioLogado.getProgresso();

                if (nivelCompletado == progressoAtual) {
                    dao = new UsuarioDAO();
                    dao.atualizarProgresso(usuarioLogado.getId(), progressoAtual + 1);

                    usuarioLogado.setProgresso(progressoAtual + 1);
                    sessao.setAttribute("usuarioLogado", usuarioLogado);
                }

                out.print("{\"success\": true}");
            } catch (Exception e) {
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
                } catch (IOException ioex) {
                    ioex.printStackTrace(); // Erro ao escrever o erro...
                }

            }
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
