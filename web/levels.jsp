<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<%@ page import="aquapedia.entidades.Usuario" %>
<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {

        response.sendRedirect(request.getContextPath() + "/login.jsp");

        return;
    }

    pageContext.setAttribute("usuario", usuarioLogado);
%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Fases - Aquapédia</title>

        <!-- Importando a fonte do Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="./css/style.css">


    </head>
    <body>

        <!-- Elementos animados no fundo da página -->
        <div class="sea-background">
            <div class="jellyfish j1"></div>
            <div class="jellyfish j2"></div>
            <div class="fish-group f1"></div>
        </div>

        <!-- Conteúdo principal da página -->
        <div class="container">
            <header>
                <nav>
                    <div class="nav-left">
                        <a href="#" class="nav-btn active">Fases</a>
                        <a href="${cp}/about.jsp" class="nav-btn">Sobre</a>
                    </div>
                    <div class="nav-right">
                        <div class="dropdown">
                            <!-- Botão de conta no estado "logado" -->
                            <button class="nav-btn">
                                <img src="https://mergulholivre.com.br/wp-content/uploads/2023/07/Tubarao-Mako-peixe-de-agua-salgada-foto-6.webp" alt="Avatar do UsuÃ¡rio" class="user-avatar">
                                <span>Apelido</span>
                            </button>
                            <div class="dropdown-content">
                                <a href="${cp}/account.jsp">Alterar Foto</a>
                                <a href="${cp}/account.jsp">Alterar Apelido</a>
                                <a href="${cp}/account.jsp">Alterar Senha</a>
                                <a href="${cp}/account.jsp">Excluir Conta</a>
                                <a href="${cp}/account.jsp">Sair</a>
                            </div>
                        </div>
                    </div>
                </nav>
            </header>

            <main>
                <div class="phases-panel">
                    <h2>Fases</h2>
                    <div class="phase-grid">
                        <a href="${cp}/playing-level.jsp?id=1" class="phase-item">Oceano Atlântico</a>

                        <c:choose>
                            <c:when test="${usuario.progresso >= 2}">
                                <a href="${cp}/playing-level.jsp?id=2" class="phase-item">Oceano Pací­fico</a>
                            </c:when>
                            <c:otherwise>
                                <span class="phase-item locked">Oceano Pacífico</span>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${usuario.progresso >= 3}">
                                <a href="${cp}/playing-level.jsp?id=3" class="phase-item">Oceano Índico</a>
                            </c:when>
                            <c:otherwise>
                                <span class="phase-item locked">Oceano Índico</span>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${usuario.progresso >= 4}">
                                <a href="${cp}/playing-level.jsp?id=4" class="phase-item">Oceano Ártico</a>
                            </c:when>
                            <c:otherwise>
                                <span class="phase-item locked">Oceano Ártico</span>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${usuario.progresso >= 5}">
                                <a href="${cp}/playing-level.jsp?id=5" class="phase-item">Oceano Antártico</a>
                            </c:when>
                            <c:otherwise>
                                <span class="phase-item locked">Oceano Antártico</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </main>
        </div>

    </body>
</html>

