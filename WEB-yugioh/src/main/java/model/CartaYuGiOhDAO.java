package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartaYuGiOhDAO {

    public void inserir(CartaYuGiOh carta) {
        String sql = "INSERT INTO cartayugioh (nomeCarta, tipo, raridade, bonusAtaque, bonusDefesa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, carta.getNomeCarta());
            stmt.setString(2, carta.getTipo());
            stmt.setString(3, carta.getRaridade());
            stmt.setInt(4, carta.getBonusAtaque());
            stmt.setInt(5, carta.getBonusDefesa());
            stmt.execute();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    carta.setIdCarta(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir carta", e);
        }
    }

    public List<CartaYuGiOh> listarCarta() {
        List<CartaYuGiOh> cartas = new ArrayList<>();
        String sql = "SELECT idCarta, nomeCarta, tipo, raridade, bonusAtaque, bonusDefesa FROM cartayugioh";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CartaYuGiOh c = new CartaYuGiOh();
                c.setIdCarta(rs.getInt("idCarta"));
                c.setNomeCarta(rs.getString("nomeCarta"));
                c.setTipo(rs.getString("tipo"));
                c.setRaridade(rs.getString("raridade"));
                c.setBonusAtaque(rs.getInt("bonusAtaque"));
                c.setBonusDefesa(rs.getInt("bonusDefesa"));
                cartas.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cartas", e);
        }
        return cartas;
    }

    // ✅ NOVO: buscar por ID (usado no /edit)
    public CartaYuGiOh buscarPorId(int id) {
        String sql = "SELECT * FROM cartayugioh WHERE idCarta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CartaYuGiOh c = new CartaYuGiOh();
                    c.setIdCarta(rs.getInt("idCarta"));
                    c.setNomeCarta(rs.getString("nomeCarta"));
                    c.setTipo(rs.getString("tipo"));
                    c.setRaridade(rs.getString("raridade"));
                    c.setBonusAtaque(rs.getInt("bonusAtaque"));
                    c.setBonusDefesa(rs.getInt("bonusDefesa"));
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar carta por ID", e);
        }
        return null;
    }

    // ✅ NOVO: alterar (usado no /update)
    public void alterar(CartaYuGiOh carta) {
        String sql = "UPDATE cartayugioh SET nomeCarta=?, tipo=?, raridade=?, bonusAtaque=?, bonusDefesa=? WHERE idCarta=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carta.getNomeCarta());
            stmt.setString(2, carta.getTipo());
            stmt.setString(3, carta.getRaridade());
            stmt.setInt(4, carta.getBonusAtaque());
            stmt.setInt(5, carta.getBonusDefesa());
            stmt.setInt(6, carta.getIdCarta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar carta", e);
        }
    }

    // ✅ NOVO: remover (usado no /delete)
    public void remover(int id) {
        String sql = "DELETE FROM cartayugioh WHERE idCarta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover carta", e);
        }
    }

    // ✅ NOVO: listar por tipo (filtro da listagem)
    public List<CartaYuGiOh> listarPorTipo(String tipo) {
        List<CartaYuGiOh> cartas = new ArrayList<>();
        String sql = "SELECT * FROM cartayugioh WHERE tipo = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartaYuGiOh c = new CartaYuGiOh();
                    c.setIdCarta(rs.getInt("idCarta"));
                    c.setNomeCarta(rs.getString("nomeCarta"));
                    c.setTipo(rs.getString("tipo"));
                    c.setRaridade(rs.getString("raridade"));
                    c.setBonusAtaque(rs.getInt("bonusAtaque"));
                    c.setBonusDefesa(rs.getInt("bonusDefesa"));
                    cartas.add(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cartas por tipo", e);
        }
        return cartas;
    }
}
