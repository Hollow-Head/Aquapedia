<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Criar Conta - Aquapédia</title>
        <link rel="stylesheet" type="" href="./css/style.css">

        <!-- Importando a fonte do Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&family=Roboto:wght@400;700&display=swap" rel="stylesheet">

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
                        <!-- <a href="./levels.html" class="nav-btn">Fases</a> -->
                        <a href="${cp}/about.jsp" class="nav-btn">Sobre</a>
                    </div>
                    <!-- <div class="nav-right">
                        <a href="./account.html" class="nav-btn">Conta</a>
                    </div> -->
                </nav>
            </header>

            <main>
                <div class="register-panel">
                    <h2>Criar</h2>

                    <form method="post" action="${cp}/processaConta">
                        <!-- não tirem esse input -->
                        <input name="acao" type="hidden" value="cadastro"/>
                        <div>
                            <p>${erro}</p>
                        </div>
                        <div class="input-group">
                            <label for="email">Insira seu email</label>
                            <input type="email" id="email" name="email" placeholder="seuemail@gmail.com">

                        </div>

                        <div class="input-group">
                            <label for="nickname">Insira seu apelido</label>
                            <input type="text" id="nickname" name="nickname" placeholder="XxX_seuApelido_XxX">
                        </div>

                        <div class="input-group">
                            <label for="password">Insira sua senha (mí­nimo de 8 caracteres)</label>
                            <input type="password" id="password" name="password">
                        </div>

                        <div class="input-group">
                            <label for="confirm-password">Confirme sua senha</label>
                            <input type="password" id="confirm-password" name="confirm-password">
                        </div>

                        <button type="submit" class="submit-btn">Criar conta</button>

                        <div class="form-switch">
                            <span>Já possui cadastro? <a href="${cp}/login.jsp">Entrar na sua conta</a></span>
                        </div>
                    </form>
                </div>
            </main>
        </div>

    </body>
</html>

