<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Aquapédia</title>
    <link rel="stylesheet" href="./css/style.css">
    
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
                <div class="nav-right">
                </div>
            </nav>
        </header>

        <main>
            <div class="login-panel">
                <h2>Entrar</h2>
                
                <form action="${cp}/processaConta" method="POST">
                    <input name="acao" type="hidden" value="login"/>
                    <div>
                        ${erro}
                    </div>
                    <div class="input-group">
                        <label for="email">Insira seu email</label>
                        <input type="email" id="email" name="email" placeholder="seuemail@gmail.com">
                    </div>
                    
                    <div class="input-group">
                        <label for="password">Insira sua senha</label>
                        <input type="password" id="password" name="password" placeholder="************">
                    </div>
                    
                    <div class="form-options">
                        <label class="checkbox-container">
                            <input type="checkbox" checked>
                            <span class="checkmark"></span>
                            Manter Conectado
                        </label>
                        <a href="./recover.html">Esqueci minha senha</a>
                    </div>
                    
                    <button type="submit" class="submit-btn">Entrar</button>
                    
                    <div class="form-switch">
                        <span>Não possui cadastro? <a href="./register.html">Criar conta</a></span>
                    </div>
                </form>
            </div>
        </main>
    </div>

</body>
</html>

