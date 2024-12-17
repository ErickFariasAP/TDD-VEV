import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fatura {
    private LocalDate data;
    private double valorTotal;
    private String cliente;
    private String status;

    private List<Pagamento> pagamentos;

    public Fatura(LocalDate data, double valorTotal, String cliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.status = "PENDENTE";
        this.pagamentos = new ArrayList<>();
    }

    public void adicionarPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void atualizarStatus() {
        double somaPagamentos = pagamentos.stream()
                .mapToDouble(Pagamento::getValor)
                .sum();

        if (somaPagamentos >= valorTotal) {
            this.status = "PAGA";
        } else {
            this.status = "PENDENTE";
        }
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
}
