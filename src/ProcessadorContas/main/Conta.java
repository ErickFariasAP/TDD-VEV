import java.time.LocalDate;

public class Conta {
    private String codigo;
    private LocalDate data;
    private double valorPago;

    public Conta(String codigo, LocalDate data, double valorPago) {
        this.codigo = codigo;
        this.data = data;
        this.valorPago = valorPago;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValorPago() {
        return valorPago;
    }
}
