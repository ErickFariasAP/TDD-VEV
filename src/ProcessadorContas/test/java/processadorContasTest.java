import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class processadorContasTest {

    @Test
    public void testFaturaSemContas() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente A");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        processador.processarFatura(fatura, Collections.emptyList());

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    @Test
    public void testFaturaComPagamentoIgualAoValor() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente A");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta = new Conta("B123", LocalDate.now(), 1000.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(1, fatura.getPagamentos().size());
        assertEquals(1000.0, fatura.getPagamentos().get(0).getValor());
    }

    @Test
    public void testFaturaComPagamentosParciais() {
        Fatura fatura = new Fatura(LocalDate.now(), 1500.0, "Cliente B");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta1 = new Conta("B123", LocalDate.now(), 500.0);
        Conta conta2 = new Conta("B456", LocalDate.now(), 700.0);
        processador.processarFatura(fatura, Arrays.asList(conta1, conta2));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(2, fatura.getPagamentos().size());
        assertEquals(500.0, fatura.getPagamentos().get(0).getValor());
        assertEquals(700.0, fatura.getPagamentos().get(1).getValor());
    }

    @Test
    public void testFaturaComPagamentosComAtraso() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente C");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        // Pagamento com atraso
        Conta conta = new Conta("B789", LocalDate.now().plusDays(1), 500.0);
        processador.processarFatura(fatura, List.of(conta));

        // Verificando o status e o valor do pagamento
        assertEquals("PENDENTE", fatura.getStatus());
    }


    @Test
    public void testFaturaComDiversosTiposDePagamentos() {
        Fatura fatura = new Fatura(LocalDate.now(), 2000.0, "Cliente D");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta1 = new Conta("B123", LocalDate.now(), 1000.0);
        Conta conta2 = new Conta("C789", LocalDate.now().minusDays(16), 500.0);
        Conta conta3 = new Conta("T456", LocalDate.now(), 800.0);

        processador.processarFatura(fatura, Arrays.asList(conta1, conta2, conta3));

        assertEquals("PAGA", fatura.getStatus());
        assertEquals(3, fatura.getPagamentos().size());
    }


    @Test
    public void testPagamentoBoletoAtrasadoComValorNegativo() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente E");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta = new Conta("B123", LocalDate.now().plusDays(5), -500.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    @Test
    public void testPagamentoCartaoCreditoRecenteIgnorado() {
        Fatura fatura = new Fatura(LocalDate.now(), 1000.0, "Cliente F");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta = new Conta("C123", LocalDate.now().minusDays(10), 1000.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    @Test
    public void testPagamentoTransferenciaAposDataFaturaIgnorado() {
        Fatura fatura = new Fatura(LocalDate.now(), 1500.0, "Cliente G");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta = new Conta("T456", LocalDate.now().plusDays(1), 1000.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }

    @Test
    public void testPagamentoComZero() {
        Fatura fatura = new Fatura(LocalDate.now(), 500.0, "Cliente H");
        ProcessadorDeContas processador = new ProcessadorDeContas();

        Conta conta = new Conta("B123", LocalDate.now(), 0.0);
        processador.processarFatura(fatura, List.of(conta));

        assertEquals("PENDENTE", fatura.getStatus());
        assertEquals(0, fatura.getPagamentos().size());
    }
}