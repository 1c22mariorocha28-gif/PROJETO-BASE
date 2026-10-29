package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartaYuGiOh;
import model.CartaYuGiOhDAO;

import java.io.IOException;
import java.util.List;

/**
 * Controller do projeto TrabalhoYuGiOh.
 * Mapeamento explícito das ações (Trabalho Prático 03).
 *
 * IMPORTANTE: este servlet NÃO pode ser mapeado em "/", pois isso faria o
 * Tomcat entregar TODAS as requisições a ele — inclusive /css/estilos.css,
 * que é um arquivo estático. Agora apenas as ações abaixo passam pelo servlet;
 * arquivos estáticos (css, js, imagens) são servidos pelo "default servlet"
 * nativo do Tomcat.
 */
@WebServlet(name = "YuGiOhController", urlPatterns = {
        "/list", "/new", "/form", "/insert", "/edit", "/update", "/delete"
})
public class YuGiOhController extends HttpServlet {

    private transient CartaYuGiOhDAO dao;

    // Inicialização única do DAO (Trabalho Prático 03)
    @Override
    public void init() throws ServletException {
        this.dao = new CartaYuGiOhDAO();
    }

    // Unificação de Requisições (Trabalho Prático 03)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Garante UTF-8 nas respostas (acentos corretos nos JSPs)
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo(); // ex.: "/list", "/new"...
        if (path == null || path.isEmpty() || "/".equals(path)) {
            path = request.getServletPath();
        }
        System.out.println("Caminho: " + path);

        // Roteamento de Ações (Trabalho Prático 03)
        switch (path) {
            case "/new":
            case "/form":
                showForm(request, response);
                break;
            case "/insert":
                insert(request, response);
                break;
            case "/list":
                list(request, response);
                break;
            case "/delete":
                delete(request, response);
                break;
            case "/edit":
                showEdit(request, response);
                break;
            case "/update":
                update(request, response);
                break;
            default:
                // Ação desconhecida: volta para a listagem (Trabalho Prático 03)
                list(request, response);
                break;
        }
    }

    // ===================== Ações de Visualização (Forward) =====================

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Filtro por tipo (ex.: /list?tipo=Monstro)
        String tipo = request.getParameter("tipo");
        List<CartaYuGiOh> cartas;
        if (tipo == null || tipo.isBlank()) {
            cartas = dao.listarCarta();
        } else {
            cartas = dao.listarPorTipo(tipo);
        }
        request.setAttribute("tipoAtual", tipo);
        request.setAttribute("cartas", cartas);
        request.getRequestDispatcher("/WEB-INF/views/lista-cartas.jsp")
                .forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/formulario-carta.jsp")
                .forward(request, response);
    }

    private void showEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Captura ID (ex: /edit?id=1)
        int id = Integer.parseInt(request.getParameter("id"));
        CartaYuGiOh carta = dao.buscarPorId(id);
        request.setAttribute("carta", carta);
        request.getRequestDispatcher("/WEB-INF/views/formulario-carta.jsp")
                .forward(request, response);
    }

    // ===================== Ações de Persistência (Redirect) =====================

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Recupera parâmetros com UTF-8 (acentos no nome da carta)
        request.setCharacterEncoding("UTF-8");

        String nomeCarta = request.getParameter("nomeCarta");
        String tipo = request.getParameter("tipo");
        String raridade = request.getParameter("raridade");
        String ataqueTexto = request.getParameter("bonusAtaque");
        String defesaTexto = request.getParameter("bonusDefesa");

        int ataque = (ataqueTexto == null || ataqueTexto.isBlank()) ? 0 : Integer.parseInt(ataqueTexto);
        int defesa = (defesaTexto == null || defesaTexto.isBlank()) ? 0 : Integer.parseInt(defesaTexto);

        CartaYuGiOh carta = new CartaYuGiOh();
        carta.setNomeCarta(nomeCarta);
        carta.setTipo(tipo);
        carta.setRaridade(raridade);
        carta.setBonusAtaque(ataque);
        carta.setBonusDefesa(defesa);

        dao.inserir(carta);

        // Redirecionar para evitar re-submissão (Trabalho Prático 03)
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.remover(id);
        response.sendRedirect("list");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String nomeCarta = request.getParameter("nomeCarta");
        String tipo = request.getParameter("tipo");
        String raridade = request.getParameter("raridade");
        String ataqueTexto = request.getParameter("bonusAtaque");
        String defesaTexto = request.getParameter("bonusDefesa");

        int ataque = (ataqueTexto == null || ataqueTexto.isBlank()) ? 0 : Integer.parseInt(ataqueTexto);
        int defesa = (defesaTexto == null || defesaTexto.isBlank()) ? 0 : Integer.parseInt(defesaTexto);

        CartaYuGiOh carta = new CartaYuGiOh();
        carta.setIdCarta(id);
        carta.setNomeCarta(nomeCarta);
        carta.setTipo(tipo);
        carta.setRaridade(raridade);
        carta.setBonusAtaque(ataque);
        carta.setBonusDefesa(defesa);

        dao.alterar(carta);
        response.sendRedirect("list");
    }
}