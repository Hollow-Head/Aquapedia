<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="pt-BR">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AquapÃ©dia - Bem-vindo!</title>
        <link rel="stylesheet" href="./css/style.css">

        <!-- Importando a fonte do Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">

    </head>

    <body>

        <div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
        </div>

        <!-- Elementos animados no fundo da página -->
        <div class="sea-background">
            <div class="jellyfish j1"></div>
            <div class="jellyfish j2"></div>
            <div class="fish-group f1"></div>
        </div>


        <!-- Conteúdo principal da página -->
        <div class="container" id="page-content">
            <header>
                <nav>
                    <c:choose>
                        <c:when test="${not empty sessionScope.usuarioLogado}">
                            <!-- PRECISA COLOCAR AS COISAS AQUI QUANDO O USUARIO ESTÀ LOGADO -->
                            <h1>USUARIO LOGADO</h1>
                            <div class="nav-left">
                                <a href="./levels.html" class="nav-btn">Fases</a>
                                <a href="./about.html" class="nav-btn">Sobre</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- PRECISA COLOCAR AS COISAS AQUI QUANDO O USUARIO NÃO ESTÁ LOGADO ESTÀ LOGADO -->
                            <div class="nav-right">
                                <div class="dropdown">
                                    <button class="nav-btn">Conta</button>
                                    <div class="dropdown-content">
                                        <a href="${cp}/login.jsp">Entrar</a>
                                        <a href="${cp}/register.jsp">Criar Conta</a>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>


                </nav>
            </header>

            <main>
                <h1>AQUAPÉDIA</h1>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <div class="bubble"></div>
                <a href="levels.html" class="play-button">JOGAR</a>
            </main>
        </div>

    </body>

</html>
