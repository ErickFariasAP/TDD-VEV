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
    public void verificarQuantidadeIngressos() {
        Show novoShow = new Show(data, artista, cache, despesasInfra, qntLotes, dataEspecial);
        assertEquals(0, novoShow.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos para um show")
    public void verificarQuantidadeIngressos() {
        Show novoShow = new Show(data, artista, cache, despesasInfra, qntLotes, dataEspecial);
        novoShow.cadastrarIngressos(1000);
        assertEquals(1000, novoShow.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos MEIA-ENTRADA para um show")
    public void verificarQuantidadeIngressos() {
        Show novoShow = new Show(data, artista, cache, despesasInfra, qntLotes, dataEspecial);
        novoShow.cadastrarIngressos(1000);
        assertEquals(100, novoShow.getTotalIngressosMeia());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos VIP para um show")
    public void verificarQuantidadeIngressos() {
        Show novoShow = new Show(data, artista, cache, despesasInfra, qntLotes, dataEspecial);
        novoShow.cadastrarIngressos(1000);
        assertEquals(250, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste para checar status dos Ingressos")
    public void verificarQuantidadeIngressos() {
        Ingresso ing = new Ingresso(id, tipo, status);
        assertFalse(ing.getStatus());
    }

    @Test
    @DisplayName("Teste para checar preços dos Ingressos")
    public void verificarQuantidadeIngressos() {
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
        Show novoShow = new Show(data, artista, cache, despesasInfra, qntLotes, dataEspecial);
        novoShow.cadastraLotes(true);
        assertTrue(novoShow.getLotes(1).getDesconto());
    }

    @Test
    @DisplayName("Teste geração de relatorio")
    public void verificarGerarRelatorio() {
        Show novoShow = new Show(data, artista, cache, despesasInfra, qntLotes, dataEspecial);
        assertEquals("PREJUÍZO", novoShow.gerarRelatorio());
    }

    @Test
    @DisplayName("Teste checagem do preço para data especial")
    public void verificarGerarRelatorio() {
        Show novoShow = new Show(data, artista, cache, 100, qntLotes, true);
        assertEquals(150, novoShow.getDespesasInfra());
    }

    @Test
    @DisplayName("Teste receita liquida")
    public void verificarGerarRelatorio() {
        Show novoShow = new Show(data, artista, cache, despesaInfra, qntLotes, true);
        assertEquals(valorDaReceita, novoShow.getReceitaLiquida());
    }
}
