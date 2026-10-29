<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Coleção de Cartas Yu-Gi-Oh!</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>🃏 Coleção Yu-Gi-Oh!</h1>
            <p>Gerencie suas cartas de forma rápida e organizada.</p>
        </header>

        <div class="actions-bar">
            <a href="${pageContext.request.contextPath}/new" class="btn btn-primary">+ Nova Carta</a>

            <%-- NOVOS BOTÕES: só funcionam quando uma carta da lista está selecionada --%>
            <button type="button" id="btnEditar" class="btn btn-warning" disabled aria-disabled="true">
                ✏️ Editar
            </button>
            <button type="button" id="btnApagar" class="btn btn-danger" disabled aria-disabled="true">
                🗑️ Apagar
            </button>

            <form action="${pageContext.request.contextPath}/list" method="GET" class="filter-form">
                <select name="tipo" onchange="this.form.submit()">
                    <option value="">Filtrar por Tipo</option>
                    <option value="Monstro" ${tipoAtual == 'Monstro' ? 'selected' : ''}>Monstro</option>
                    <option value="Magia" ${tipoAtual == 'Magia' ? 'selected' : ''}>Magia</option>
                    <option value="Armadilha" ${tipoAtual == 'Armadilha' ? 'selected' : ''}>Armadilha</option>
                </select>
                <c:if test="${not empty tipoAtual}">
                    <a href="${pageContext.request.contextPath}/list" class="btn btn-secondary">Limpar Filtro</a>
                </c:if>
            </form>

            <span id="infoSelecao" class="selected-info">Nenhuma carta selecionada</span>
        </div>

        <p class="hint">Clique em uma linha da tabela para selecionar a carta; depois use
            <strong>Editar</strong> (abre a carta na tela de cadastro para reescrever as especificações)
            ou <strong>Apagar</strong> (remove o registro). Sem seleção, os dois botões ficam desabilitados.</p>

        <table class="data-table" id="tabelaCartas">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome da Carta</th>
                    <th>Tipo</th>
                    <th>Raridade</th>
                    <th>Ataque</th>
                    <th>Defesa</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="carta" items="${cartas}">
                    <%-- data-id: usado pelo JS para saber qual carta foi selecionada --%>
                    <tr class="selectable" data-id="${carta.idCarta}" tabindex="0" role="row" aria-selected="false">
                        <td>${carta.idCarta}</td>
                        <td><strong><c:out value="${carta.nomeCarta}" /></strong></td>
                        <td><span class="badge tipo-${carta.tipo}">${carta.tipo}</span></td>
                        <td><c:out value="${carta.raridade}" /></td>
                        <td class="stat atk">${carta.bonusAtaque}</td>
                        <td class="stat def">${carta.bonusDefesa}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty cartas}">
                    <tr>
                        <td colspan="6" class="empty-message">Nenhuma carta encontrada. Que tal adicionar uma?</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <%-- Formulário oculto usado pelo botão "Apagar" (POST /delete com o id selecionado) --%>
        <form id="formDelete" action="${pageContext.request.contextPath}/delete" method="POST"
              data-contexto="${pageContext.request.contextPath}" style="display:none;">
            <input type="hidden" id="deleteId" name="id" value="">
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/js/lista-cartas.js"></script>
</body>
</html>