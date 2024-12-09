import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class sistemaIngressosTest {

    @Test
    public void verificarJunit() {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos para um show")
    public void verificarQuantidadeIngressosZerados() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        assertEquals(0, novoShow.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos para um show")
    public void verificarQuantidadeIngressos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressos(1000);
        assertEquals(1000, novoShow.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos MEIA-ENTRADA para um show")
    public void verificarQuantidadeIngressosMeia() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressos(1000);
        assertEquals(100, novoShow.getTotalIngressosMeia());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos VIP para um show")
    public void verificarQuantidadeIngressosVIP() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressos(1000);
        assertEquals(250, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste para checar status dos Ingressos")
    public void verificarStatusIngressos() {
        Ingresso ing = new Ingresso(1L, "vip");
        assertFalse(ing.getStatus());
    }

    @Test
    @DisplayName("Teste para checar preços dos Ingressos")
    public void verificarPrecosIngressos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressosVIP(1);
        novoShow.cadastrarIngressosMeia(1);
        novoShow.cadastrarIngressosNormais(1);
        assertEquals(20, novoShow.getIngresso(1L).getPreco());
        assertEquals(5, novoShow.getIngresso(2L).getPreco());
        assertEquals(10, novoShow.getIngresso(3L).getPreco());
    }

    @Test
    @DisplayName("Teste para checar sistema de lotes")
    public void verificarSistemaLotes() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastraLotes(true);
        assertTrue(novoShow.getLotes(1).getDesconto());
    }
    
    @Test
    @DisplayName("Teste de venda de ingresso")
    public void verificarVendaIngresso() {
        Ingresso ing = new Ingresso(1L, "vip");
        assertFalse(ing.getStatus());
        ing.venderIngresso();
        assertTrue(ing.getStatus());
    }

    @Test
    @DisplayName("Teste checagem do preço para data especial")
    public void verificarDespesasInfraDataEspecial() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, true, 10);
        assertEquals(9200, novoShow.getDespesasInfra());
    }

    @Test
    @DisplayName("Teste checagem do preço para data normal")
    public void verificarDespesasInfra() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        assertEquals(8000, novoShow.getDespesasInfra());
    }

    @Test
    @DisplayName("Teste geração de relatorio prejuizo")
    public void verificarGerarRelatorioPrejuizo() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        assertEquals("PREJUÍZO", novoShow.gerarRelatorio());
    }

    @Test
    @DisplayName("Teste receita liquida")
    public void verificarReceitaLiquida() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        assertEquals(valorDaReceita, novoShow.getReceitaLiquida());
    }

    @Test
    @DisplayName("Teste geração de relatorio lucro")
    public void verificarGerarRelatorioLucro() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 2000000, 0.20);
        assertEquals("LUCRO", novoShow.gerarRelatorio());
    }
}
