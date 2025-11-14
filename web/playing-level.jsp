<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<%@ page import="aquapedia.entidades.Usuario" %> <%-- Importe sua classe Usuario --%>
<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {

        response.sendRedirect(request.getContextPath() + "/login.jsp");

        return;
    }

    pageContext.setAttribute("usuario", usuarioLogado);
    
    String actualLevelString = request.getParameter("id");

    int actualLevelInt = 1;
    
    if (actualLevelString != null) {
        actualLevelInt = Integer.parseInt(actualLevelString);
    } else {
        actualLevelInt = usuarioLogado.getProgresso();
    }
    
    if (usuarioLogado.getProgresso() < actualLevelInt) {
        response.sendRedirect(request.getContextPath() + "/playing-level.jsp?id=" + usuarioLogado.getProgresso());

        return;
    }
    
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Fase: Aquapédia</title>
  <link rel="stylesheet" href="./css/style.css">

  <!-- Importando a fonte do Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Fredoka+One&family=Roboto:wght@400;700&display=swap"
    rel="stylesheet">

</head>

<body>
  <div class="container">
    <header>
      <nav>
        <div class="nav-left">
          <a href="${cp}/levels.jsp" class="nav-btn active">Fases</a>
          <a href="${cp}/about.jsp" class="nav-btn">Sobre</a>
        </div>
        <div class="nav-right">
          <a href="${cp}/account.jsp" class="nav-btn">
            <img
              src="https://mergulholivre.com.br/wp-content/uploads/2023/07/Tubarao-Mako-peixe-de-agua-salgada-foto-6.webp"
              alt="[Avatar do Usuário]" class="user-avatar">
            <span>Apelido</span>
          </a>
        </div>
      </nav>
    </header>

    <main>
      <!-- Seção 1: Leitura do Texto -->
      <section id="reading-section" class="game-section active">
        <div class="reading-panel">
        <h3>Fase Nº - Nome Fase</h3>
          <p>
              Texto da Fase
          </p>
        
          <div class="inventory-area">
            <!-- <h4>Itens Coletados:</h4> -->
            <ul id="inventory-list"></ul>
          </div>
          <button id="proceed-btn" class="nav-btn" disabled>Coletar todos os itens para continuar</button>
        </div>
      </section>

      <!-- Seção 2: Questionário -->
      <section id="questionnaire-section" class="game-section">
        <div class="game-panel">
          <div class="interactive-area">
            <div class="fill-in-blanks">
            </div>

            <div class="image-slots">
              <div class="image-text">
                <img class="quest-image" src="./img/baleia-azul.jpg" alt="baleia-azul">
                <span class="blank-space" data-slot="baleia-azul"></span>
              </div>
              <div class="image-text">
                <img class="quest-image" src="./img/baleia-azul.jpg" alt="baleia-azul">
                <span class="blank-space" data-slot="baleia-azul"></span>
              </div>
              <div class="image-text">
                <img class="quest-image" src="./img/baleia-azul.jpg" alt="baleia-azul">
                <span class="blank-space" data-slot="baleia-azul"></span>
              </div>
              <div class="image-text">
                <img class="quest-image" src="./img/baleia-azul.jpg" alt="baleia-azul">
                <span class="blank-space" data-slot="baleia-azul"></span>
              </div>
            </div>
          </div>

          <div id="word-bank-questionnaire"></div>
          <button id="check-btn" class="nav-btn">Checar Tentativa</button>
          <div id="result-message"></div>
        </div>
      </section>
    </main>
  </div>

  <script src="./scripts/playing_level.js"></script>

</body>

</html>
