/* ============================================================
   Seleção de cartas na listagem
   - Os botões "Apagar" e "Editar" NÃO fazem nada enquanto
     nenhuma carta estiver selecionada (ficam desabilitados).
   - Ao clicar em uma linha, ela vira a carta selecionada.
   - "Editar" envia para a tela de formulário (igual ao +Nova Carta)
     já preenchida com as especificações da carta selecionada.
   - "Apagar" confirma e envia o formulário POST para /delete.
   ============================================================ */
(function () {
    'use strict';

    var tabela = document.getElementById('tabelaCartas');
    var btnApagar = document.getElementById('btnApagar');
    var btnEditar = document.getElementById('btnEditar');
    var formDelete = document.getElementById('formDelete');
    var inputDeleteId = document.getElementById('deleteId');
    var infoSelecao = document.getElementById('infoSelecao');
    var contexto = formDelete ? formDelete.getAttribute('data-contexto') : '';

    // Estado da seleção: id da carta selecionada (null = nenhuma)
    var idSelecionado = null;

    function atualizarBotoes() {
        var temSelecao = idSelecionado !== null;

        [btnApagar, btnEditar].forEach(function (btn) {
            if (!btn) return;
            if (temSelecao) {
                btn.removeAttribute('disabled');
                btn.removeAttribute('aria-disabled');
                btn.title = '';
            } else {
                btn.setAttribute('disabled', 'disabled');
                btn.setAttribute('aria-disabled', 'true');
                btn.title = 'Selecione uma carta na lista primeiro';
            }
        });

        if (infoSelecao) {
            infoSelecao.textContent = temSelecao
                ? 'Carta selecionada: #' + idSelecionado
                : 'Nenhuma carta selecionada';
        }
    }

    function limparSelecaoVisual() {
        var sel = tabela ? tabela.querySelector('tr.selected') : null;
        if (sel) {
            sel.classList.remove('selected');
            sel.removeAttribute('aria-selected');
        }
    }

    function selecionarLinha(linha) {
        // Clicar novamente na mesma linha desmarca a carta
        if (linha.classList.contains('selected')) {
            limparSelecaoVisual();
            idSelecionado = null;
        } else {
            limparSelecaoVisual();
            linha.classList.add('selected');
            linha.setAttribute('aria-selected', 'true');
            idSelecionado = linha.getAttribute('data-id');
        }
        atualizarBotoes();
    }

    if (tabela) {
        tabela.addEventListener('click', function (evento) {
            var linha = evento.target.closest('tbody tr[data-id]');
            if (linha) selecionarLinha(linha);
        });

        // Acessibilidade: Enter ou Espaço também selecionam a linha em foco
        tabela.addEventListener('keydown', function (evento) {
            var linha = evento.target.closest('tbody tr[data-id]');
            if (!linha) return;
            if (evento.key === 'Enter' || evento.key === ' ' || evento.key === 'Spacebar') {
                evento.preventDefault();
                selecionarLinha(linha);
            }
        });
    }

    // Botão APAGAR: nada acontece sem seleção; com seleção pede confirmação
    if (btnApagar) {
        btnApagar.addEventListener('click', function (evento) {
            evento.preventDefault();
            if (idSelecionado === null) return; // protegido: não faz nada
            if (window.confirm('Tem certeza que deseja apagar a carta selecionada (#' + idSelecionado + ')?')) {
                inputDeleteId.value = idSelecionado;
                formDelete.submit();
            }
        });
    }

    // Botão EDITAR: nada acontece sem seleção; com seleção abre a tela de edição
    if (btnEditar) {
        btnEditar.addEventListener('click', function (evento) {
            evento.preventDefault();
            if (idSelecionado === null) return; // protegido: não faz nada
            window.location.href = contexto + '/edit?id=' + encodeURIComponent(idSelecionado);
        });
    }

    // Estado inicial: ambos os botões desabilitados
    atualizarBotoes();
})();
