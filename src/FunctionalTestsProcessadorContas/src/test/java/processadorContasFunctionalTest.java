import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProcessadorContasFunctionalTest {

    // ==================================================
    // Testes de Análise de Valores Limite
    // ==================================================

    @Test
    void testPagamentoBoletoMinimo() {
        Fatura fatura = new Fatura(LocalDate.now(), 0.1, "Cliente A");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B001", LocalDate.now(), 0.1);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(0.1, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testPagamentoBoletoLogoAcimaDoMinimo() {
        Fatura fatura = new Fatura(LocalDate.now(), 0.2, "Cliente B");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B002", LocalDate.now(), 0.2);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(0.2, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testPagamentoBoletoQualquer() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente C");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B003", LocalDate.now(), 1000.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(1000.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testPagamentoBoletoMaximo() {
        Fatura fatura = new Fatura(LocalDate.now(), 5000.0, "Cliente D");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B004", LocalDate.now(), 5000.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(5000.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testPagamentoBoletoLogoAbaixoDoMaximo() {
        Fatura fatura = new Fatura(LocalDate.now(), 4999.0, "Cliente E");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B005", LocalDate.now(), 4999.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(4999.0, fatura.getPagamentos().get(0).getValor());
    }

    // ==================================================
    // Testes de Partições de Equivalência
    // ==================================================

    @Test
    void testFaturaValorValido() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente F");
        assertTrue(fatura.getValorTotal() > 0);
    }

    @Test
    void testFaturaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Fatura(LocalDate.now(), -100.0, "Cliente G");
        });
    }

    @Test
    void testContaValorValido() {
        Conta conta = new Conta("C001", LocalDate.now(), 500.0);
        assertTrue(conta.getValorPago() > 0);
    }

    @Test
    void testContaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Conta("C002", LocalDate.now(), -100.0);
        });
    }

    @Test
    void testPagamentoBoletoValido() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente H");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B001", LocalDate.now(), 100.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(100.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testPagamentoBoletoInvalido() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente I");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B002", LocalDate.now(), 0.05);

        assertThrows(IllegalArgumentException.class, () -> {
            processador.processarFatura(fatura, List.of(conta));
        });
    }

    @Test
    void testPagamentoComAtraso() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente J");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("T003", LocalDate.now().minusDays(1), 500.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(500.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testPagamentoCartaoForaDoPrazo() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente K");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("C001", LocalDate.now().minusDays(10), 500.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    // ==================================================
    // Testes de Tabelas de Decisão
    // ==================================================

    @Test
    void testRegra1_TipoPagamentoInvalido() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente L");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("X001", LocalDate.now(), 500.0);

        assertThrows(IllegalArgumentException.class, () -> {
            processador.processarFatura(fatura, List.of(conta));
        });
    }

    @Test
    void testRegra3_BoletoComAtraso() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente M");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("B001", LocalDate.now().minusDays(1), 500.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(550.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    void testRegra6_PagamentoNoPrazo() {
        Fatura fatura = new Fatura(LocalDate.now(), 1500.0, "Cliente N");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta1 = new Conta("B001", LocalDate.now(), 500.0);
        Conta conta2 = new Conta("T001", LocalDate.now(), 1000.0);
        processador.processarFatura(fatura, Arrays.asList(conta1, conta2));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(2, fatura.getPagamentos().size());
    }

    @Test
    void testRegra8_PagamentoCartaoNoPrazo() {
        Fatura fatura = new Fatura(LocalDate.now(), 1500.0, "Cliente O");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        Conta conta = new Conta("C001", LocalDate.now().minusDays(16), 1500.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
    }
}