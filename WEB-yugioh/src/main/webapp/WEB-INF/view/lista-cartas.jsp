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
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome da Carta</th>
                    <th>Tipo</th>
                    <th>Raridade</th>
                    <th>Ataque</th>
                    <th>Defesa</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="carta" items="${cartas}">
                    <tr>
                        <td>${carta.idCarta}</td>
                        <td><strong>${carta.nomeCarta}</strong></td>
                        <td><span class="badge tipo-${carta.tipo}">${carta.tipo}</span></td>
                        <td>${carta.raridade}</td>
                        <td class="stat atk">${carta.bonusAtaque}</td>
                        <td class="stat def">${carta.bonusDefesa}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/edit?id=${carta.idCarta}" class="btn-icon edit" title="Editar">✏️</a>
                            <a href="${pageContext.request.contextPath}/delete?id=${carta.idCarta}" class="btn-icon delete" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir esta carta?');">🗑️</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty cartas}">
                    <tr>
                        <td colspan="7" class="empty-message">Nenhuma carta encontrada. Que tal adicionar uma?</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>