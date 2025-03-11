
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class junit5ProcessadorContasTest {

    Fatura fatura;
    ProcessadorDeContas processador;

    @BeforeEach
    void setUp() {
        fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente A");
        processador = new ProcessadorDeContas();
    }

    // ==================================================
    // Testes Gerais
    // ==================================================

    @Test
    @DisplayName("Teste de fatura sem contas")
    void testFaturaSemContas() {
        processador.processarFatura(fatura, Collections.emptyList());
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    @Test
    @DisplayName("Teste de pagamento igual ao valor da fatura")
    void testPagamentoIgualAoValorDaFatura() {
        Conta conta = new Conta("B001", LocalDate.now(), 1000.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(1000.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    @DisplayName("Teste de pagamentos parciais")
    void testPagamentosParciais() {
        Conta conta1 = new Conta("B001", LocalDate.now(), 500.0);
        Conta conta2 = new Conta("B002", LocalDate.now(), 700.0);
        processador.processarFatura(fatura, Arrays.asList(conta1, conta2));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(2, fatura.getPagamentos().size());
        assertEquals(500.0, fatura.getPagamentos().get(0).getValor());
        assertEquals(700.0, fatura.getPagamentos().get(1).getValor());
    }

    @Test
    @DisplayName("Teste de pagamento com atraso")
    void testPagamentoComAtraso() {
        Conta conta = new Conta("B001", LocalDate.now().minusDays(1), 500.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(550.0, fatura.getPagamentos().get(0).getValor()); // 10% de acréscimo
    }

    // ==================================================
    // Testes de Análise de Valores Limite (AVL)
    // ==================================================

    @Test
    @DisplayName("Teste de pagamento com valor de boleto mínimo")
    void testPagamentoBoletoMinimo() {
        Conta conta = new Conta("B001", LocalDate.now(), 0.1);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(0.1, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    @DisplayName("Teste de pagamento com valor de boleto logo acima do mínimo")
    void testPagamentoBoletoLogoAcimaMinimo() {
        Conta conta = new Conta("B001", LocalDate.now(), 0.2);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(0.2, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    @DisplayName("Teste de pagamento com valor de boleto qualquer")
    void testPagamentoBoletoQualquer() {
        Conta conta = new Conta("B001", LocalDate.now(), 1000.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(1000.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    @DisplayName("Teste de pagamento com valor de boleto máximo")
    void testPagamentoBoletoMaximo() {
        Conta conta = new Conta("B001", LocalDate.now(), 5000.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(5000.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    @DisplayName("Teste de pagamento com valor de boleto logo abaixo do máximo")
    void testPagamentoBoletoLogoAbaixoMaximo() {
        Conta conta = new Conta("B001", LocalDate.now(), 4999.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(4999.0, fatura.getPagamentos().get(0).getValor());
    }

    // ==================================================
    // Testes de Partições de Equivalência
    // ==================================================

    @Test
    @DisplayName("Teste de fatura com valor válido")
    void testFaturaValorValido() {
        Fatura faturaValida = new Fatura(LocalDate.now(), 1000.0, "Cliente B");
        assertTrue(faturaValida.getValorTotal() > 0);
    }

    @Test
    @DisplayName("Teste de fatura com valor inválido (negativo)")
    void testFaturaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Fatura(LocalDate.now(), -100.0, "Cliente C");
        });
    }

    @Test
    @DisplayName("Teste de conta com valor válido")
    void testContaValorValido() {
        Conta conta = new Conta("C001", LocalDate.now(), 500.0);
        assertTrue(conta.getValorPago() > 0);
    }

    @Test
    @DisplayName("Teste de conta com valor inválido (negativo)")
    void testContaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Conta("C001", LocalDate.now(), -100.0);
        });
    }

    @Test
    @DisplayName("Teste de pagamento com boleto válido")
    void testPagamentoBoletoValido() {
        Conta conta = new Conta("B001", LocalDate.now(), 100.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(100.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    @DisplayName("Teste de pagamento com boleto inválido (abaixo do mínimo)")
    void testPagamentoBoletoInvalidoAbaixoMinimo() {
        Conta conta = new Conta("B001", LocalDate.now(), 0.05);
        assertThrows(IllegalArgumentException.class, () -> {
            processador.processarFatura(fatura, List.of(conta));
        });
    }

    @Test
    @DisplayName("Teste de pagamento com boleto inválido (acima do máximo)")
    void testPagamentoBoletoInvalidoAcimaMaximo() {
        Conta conta = new Conta("B001", LocalDate.now(), 5001.0);
        assertThrows(IllegalArgumentException.class, () -> {
            processador.processarFatura(fatura, List.of(conta));
        });
    }

    // ==================================================
    // Testes de Tabelas de Decisão
    // ==================================================

    @Test
    @DisplayName("Teste de pagamento com tipo de pagamento inválido")
    void testPagamentoTipoInvalido() {
        Conta conta = new Conta("X001", LocalDate.now(), 500.0);
        assertThrows(IllegalArgumentException.class, () -> {
            processador.processarFatura(fatura, List.of(conta));
        });
    }

    @Test
    @DisplayName("Teste de pagamento com cartão de crédito fora do prazo")
    void testPagamentoCartaoForaDoPrazo() {
        Conta conta = new Conta("C001", LocalDate.now().minusDays(10), 500.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    @Test
    @DisplayName("Teste de pagamento com cartão de crédito no prazo")
    void testPagamentoCartaoNoPrazo() {
        Conta conta = new Conta("C001", LocalDate.now().minusDays(16), 1500.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
    }

    @Test
    @DisplayName("Teste de pagamento com transferência bancária no prazo")
    void testPagamentoTransferenciaNoPrazo() {
        Conta conta = new Conta("T001", LocalDate.now(), 1000.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
    }

    @Test
    @DisplayName("Teste de pagamento com transferência bancária fora do prazo")
    void testPagamentoTransferenciaForaDoPrazo() {
        Conta conta = new Conta("T001", LocalDate.now().plusDays(1), 1000.0);
        processador.processarFatura(fatura, List.of(conta));
        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }
}