public class Ingresso {

    Long id;
    String tipo;
    Boolean status;
    Integer preco;

    public Ingresso(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.status = false;
        this.preco = 0;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public Integer getPreco() {
        return preco;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTipo() {
        return tipo;
    }

    public void venderIngresso() {
        if (status) {
            System.out.println("INGRESSO JÁ VENDIDO!");
        } else {
            status = true;
        }
    }

}
