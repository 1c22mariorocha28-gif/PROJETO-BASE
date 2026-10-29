package model;

public class CartaYuGiOh {
    private int idCarta;
    private String nomeCarta;
    private String tipo;
    private String raridade;
    private int bonusAtaque;
    private int bonusDefesa;

    public CartaYuGiOh(){

    }

    public CartaYuGiOh(int idCarta, String nomeCarta, String tipo, String raridade, int bonusAtaque, int bonusDefesa) {
        this.idCarta = idCarta;
        this.nomeCarta = nomeCarta;
        this.tipo = tipo;
        this.raridade = raridade;
        this.bonusAtaque = bonusAtaque;
        this.bonusDefesa = bonusDefesa;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public String getNomeCarta() {
        return nomeCarta;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRaridade() {
        return raridade;
    }

    public int getBonusAtaque() {
        return bonusAtaque;
    }

    public int getBonusDefesa() {
        return bonusDefesa;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta; }

    public void setNomeCarta(String nomeCarta) {
        this.nomeCarta = nomeCarta; }

    public void setTipo(String tipo) {
        this.tipo = tipo; }

    public void setRaridade(String raridade) {
        this.raridade = raridade; }

    public void setBonusAtaque(int bonusAtaque) {
        this.bonusAtaque = bonusAtaque; }

    public void setBonusDefesa(int bonusDefesa) {
        this.bonusDefesa = bonusDefesa; }
}
