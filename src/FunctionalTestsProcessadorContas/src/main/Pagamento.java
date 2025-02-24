import java.time.LocalDate;

public class Pagamento {
    private double valor;
    private LocalDate data;
    private String tipo;

    public Pagamento(double valor, LocalDate data, String tipo) {
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }
}
