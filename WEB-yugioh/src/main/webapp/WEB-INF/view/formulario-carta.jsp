<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>${not empty carta ? 'Editar' : 'Nova'} Carta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container form-container">
        <header>
            <h1>${not empty carta ? '✏️ Editar Carta' : '✨ Nova Carta'}</h1>
        </header>

        <c:set var="isEdit" value="${not empty carta}" />
        <form action="${pageContext.request.contextPath}/${isEdit ? 'update' : 'insert'}" method="POST" class="card-form">

            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${carta.idCarta}">
            </c:if>

            <div class="form-group">
                <label for="nomeCarta">Nome da Carta</label>
                <input type="text" id="nomeCarta" name="nomeCarta" value="${carta.nomeCarta}" required placeholder="Ex: Dragão Branco de Olhos Azuis">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="tipo">Tipo</label>
                    <select id="tipo" name="tipo" required>
                        <option value="">Selecione...</option>
                        <option value="Monstro" ${carta.tipo == 'Monstro' ? 'selected' : ''}>Monstro</option>
                        <option value="Magia" ${carta.tipo == 'Magia' ? 'selected' : ''}>Magia</option>
                        <option value="Armadilha" ${carta.tipo == 'Armadilha' ? 'selected' : ''}>Armadilha</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="raridade">Raridade</label>
                    <select id="raridade" name="raridade" required>
                        <option value="">Selecione...</option>
                        <option value="Comum" ${carta.raridade == 'Comum' ? 'selected' : ''}>Comum</option>
                        <option value="Rara" ${carta.raridade == 'Rara' ? 'selected' : ''}>Rara</option>
                        <option value="Super Rara" ${carta.raridade == 'Super Rara' ? 'selected' : ''}>Super Rara</option>
                        <option value="Ultra Rara" ${carta.raridade == 'Ultra Rara' ? 'selected' : ''}>Ultra Rara</option>
                        <option value="Secreta" ${carta.raridade == 'Secreta' ? 'selected' : ''}>Secreta</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="bonusAtaque">Bônus de Ataque</label>
                    <input type="number" id="bonusAtaque" name="bonusAtaque" value="${carta.bonusAtaque}" placeholder="0">
                </div>

                <div class="form-group">
                    <label for="bonusDefesa">Bônus de Defesa</label>
                    <input type="number" id="bonusDefesa" name="bonusDefesa" value="${carta.bonusDefesa}" placeholder="0">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">💾 Salvar Carta</button>
                <a href="${pageContext.request.contextPath}/list" class="btn btn-secondary">↩️ Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>