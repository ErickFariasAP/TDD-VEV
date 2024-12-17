import java.time.LocalDate;
import java.util.List;

public class ProcessadorDeContas {

    public void processarFatura(Fatura fatura, List<Conta> contas) {
        for (Conta conta : contas) {
            String tipoPagamento = determinarTipoPagamento(conta);
            double valor = conta.getValorPago();
            LocalDate dataPagamento = conta.getData();

            // Verificação para pagamento com atraso
            if (tipoPagamento.equals("BOLETO")) {
                if (valor < 0.01 || valor > 5000) {
                    continue; // Ignora valores inválidos
                }
                if (dataPagamento.isAfter(fatura.getData())) {
                    valor += valor * 0.10; // Acrescenta 10% por atraso
                }
            }

            // Cartão de crédito com menos de 15 dias da fatura deve ser ignorado
            if (tipoPagamento.equals("CARTAO_CREDITO") &&
                    dataPagamento.plusDays(15).isAfter(fatura.getData())) {
                continue;
            }

            // Ignora pagamentos após a data da fatura (para outros tipos de pagamento)
            if (!tipoPagamento.equals("CARTAO_CREDITO") && dataPagamento.isAfter(fatura.getData())) {
                continue;
            }

            // Registra o pagamento na fatura
            Pagamento pagamento = new Pagamento(valor, dataPagamento, tipoPagamento);
            fatura.adicionarPagamento(pagamento);
        }

        // Atualiza o status da fatura
        fatura.atualizarStatus();
    }

    private String determinarTipoPagamento(Conta conta) {
        // Exemplo simplificado para determinar o tipo de pagamento
        if (conta.getCodigo().startsWith("B")) {
            return "BOLETO";
        } else if (conta.getCodigo().startsWith("C")) {
            return "CARTAO_CREDITO";
        } else {
            return "TRANSFERENCIA_BANCARIA";
        }
    }
}
