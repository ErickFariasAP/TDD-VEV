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
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        assertEquals(0, novoShow.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos para um show")
    public void verificarQuantidadeIngressos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        novoShow.cadastrarIngressos(1000);
        assertEquals(1000, novoShow.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos MEIA-ENTRADA para um show")
    public void verificarQuantidadeIngressosMeia() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        novoShow.cadastrarIngressos(1000);
        assertEquals(100, novoShow.getTotalIngressosMeia());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos VIP para um show")
    public void verificarQuantidadeIngressosVIP() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        novoShow.cadastrarIngressos(1000);
        assertEquals(250, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste para checar status dos Ingressos")
    public void verificarStatusIngressos() {
        Ingresso ing = new Ingresso(id, tipo, status);
        assertFalse(ing.getStatus());
    }

    @Test
    @DisplayName("Teste para checar preços dos Ingressos")
    public void verificarPrecosIngressos() {
        Ingresso ingNormal = new Ingresso(id, Normal, status);
        Ingresso ingVIP = new Ingresso(id, VIP, status);
        Ingresso ingMeia = new Ingresso(id, Meia, status);
        assertEquals(10, ingNormal.getPreco());
        assertEquals(20, ingVIP.getPreco());
        assertEquals(5, ingMeia.getPreco());
    }

    @Test
    @DisplayName("Teste para checar sistema de lotes")
    public void verificarSistemaLotes() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        novoShow.cadastraLotes(true);
        assertTrue(novoShow.getLotes(1).getDesconto());
    }

    @Test
    @DisplayName("Teste geração de relatorio")
    public void verificarGerarRelatorio() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        assertEquals("PREJUÍZO", novoShow.gerarRelatorio());
    }

    @Test
    @DisplayName("Teste checagem do preço para data especial")
    public void verificarDespesasInfra() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        assertEquals(150, novoShow.getDespesasInfra());
    }

    @Test
    @DisplayName("Teste receita liquida")
    public void verificarReceitaLiquida() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false);
        assertEquals(valorDaReceita, novoShow.getReceitaLiquida());
    }
}
