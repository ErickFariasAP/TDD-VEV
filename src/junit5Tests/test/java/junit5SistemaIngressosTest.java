import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class junit5SistemaIngressosTest {

    Show showMetallica;
    Show showQueen;
    Show showLinkinPark;

    @BeforeEach
    public void setUp() {
        this.showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.25);
        this.showQueen = new Show("25/12/2024", "Queen", 100000, 8000, 1, false, 10, 0.30);
        this.showLinkinPark = new Show("25/12/2024", "Metallica", 1000, 2000, 1, true, 10, 0.20);
    }

    @Test
    public void verificarJunit() {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos para um show")
    public void verificarQuantidadeIngressosZerados() {
        assertEquals(0, showMetallica.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos para um show")
    public void verificarQuantidadeIngressos() {
        showMetallica.cadastrarIngressos(1000);
        assertEquals(1000, showMetallica.getTotalIngressosCadastrados());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos MEIA-ENTRADA para um show")
    public void verificarQuantidadeIngressosMeia() {
        showMetallica.cadastrarIngressos(1000);
        assertEquals(100, showMetallica.getTotalIngressosMeia());
    }

    @Test
    @DisplayName("Teste para checar quantidade de ingressos VIP para um show")
    public void verificarQuantidadeIngressosVIP() {
        showMetallica.cadastrarIngressos(1000);
        assertEquals(250, showMetallica.getTotalIngressosVIP());
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
        showMetallica.cadastrarIngressosVIP(1);
        showMetallica.cadastrarIngressosMeia(1);
        showMetallica.cadastrarIngressosNormais(1);
        assertEquals(20, showMetallica.getIngresso(1L).getPreco());
        assertEquals(5, showMetallica.getIngresso(2L).getPreco());
        assertEquals(10, showMetallica.getIngresso(3L).getPreco());
    }

    @Test
    @DisplayName("Teste para checar sistema de lotes")
    public void verificarSistemaLotes() {
        showMetallica.cadastraLotes(0.15);
        assertEquals(0.15, showMetallica.getLote(1L).getDesconto());
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
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, true, 10, 0.25);
        assertEquals(9200, showMetallica.getDespesasInfra());
    }

    @Test
    @DisplayName("Teste checagem do preço para data normal")
    public void verificarDespesasInfra() {
        assertEquals(8000, showMetallica.getDespesasInfra());
    }

    /* esse teste em especifico necessitou muitas mudanças pois era necessário um setup previo do show para calcular a receita liquida */
    @Test
    @DisplayName("Teste receita bruta")
    public void verificarReceitaBruta() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.00);
        showMetallica.cadastrarIngressos(10);
        showMetallica.cadastraLotes(0.5);
        Ingresso[] ingressosLote = showMetallica.getLote(1L).getIngressos();;
        for (Ingresso ing : ingressosLote) {
            if (ing != null) {
                ing.venderIngresso();
            }
        }
        assertEquals(50, showMetallica.getReceitaBruta());
    }

    /* TESTE DA ESPECIFICAÇÃO */
    @Test
    @DisplayName("Teste geração de relatorio prejuizo")
    public void verificarGerarRelatorio() {
        showLinkinPark.cadastrarIngressos(500);
        showLinkinPark.cadastraLotes(0.15);
        Ingresso[] ingressosLote = showLinkinPark.getLote(1L).getIngressos();
        Integer count = 0;
        for (Ingresso ing : ingressosLote) {
            if (ing != null) {
                ing.venderIngresso();
            }
        }
        assertEquals("Ingressos normais vendidos: 350\n" +
                "Ingressos vip vendidos: 100\n" +
                "Ingressos meia vendidos: 50\n" +
                "Receita líquida: 1625.0\n" +
                "Status financeiro: LUCRO", showLinkinPark.gerarRelatorio());
    }

    //-----------------------------------------------------------------------
    // ANÁLISE DE VALORES LIMITES
    //-----------------------------------------------------------------------

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar lote mínimo")
    public void criarLoteMinimo() {
        Ingresso[] ingressosLote = new Ingresso[1];
        Lote lote = new Lote(1L, ingressosLote, 0);
        assertEquals(1, lote.getIngressos().length);
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar lote logo acima do mínimo")
    public void criarLoteAcimaMinimo() {
        Ingresso[] ingressosLote = new Ingresso[2];
        Lote lote = new Lote(1L, ingressosLote, 0);
        assertEquals(2, lote.getIngressos().length);
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar lote qualquer")
    public void criarLoteQualquer() {
        Ingresso[] ingressosLote = new Ingresso[10];
        Lote lote = new Lote(1L, ingressosLote, 0);
        assertEquals(10, lote.getIngressos().length);
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar lote maximo")
    //ERRO AO CRIAR LOTE (função cadastraLotes)
    public void criarLoteMaximo() {
        showMetallica.cadastraLotes(0.25);
        assertEquals(1001, showMetallica.getLote(1L).getIngressos().length);
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar lote logo abaixo do maximo")
    public void criarLoteAbaixoMaximo() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 0.25);
        assertEquals(1000, lote.getIngressos().length);
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar show vips mínimo")
    public void criarShowVipsMinimo() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        showMetallica.cadastrarIngressos(100);
        assertEquals(20, showMetallica.getTotalIngressosVIP());
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar show vips logo acima do mínimo")
    public void criarShowVipsAcimaMinimo() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.21);
        showMetallica.cadastrarIngressos(100);
        assertEquals(21, showMetallica.getTotalIngressosVIP());
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar show vips qualquer")
    public void criarShowVipsQualquer() {
        showMetallica.cadastrarIngressos(100);
        assertEquals(25, showMetallica.getTotalIngressosVIP());
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar show vips maximo")
    public void criarShowVipsMaximo() {
        Show showQueen = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.30);
        showQueen.cadastrarIngressos(100);
        assertEquals(30, showQueen.getTotalIngressosVIP());
    }

    @Test
    @Tag("ValoresLimites")
    @DisplayName("Teste criar show vips logo abaixo do máximo")
    public void criarShowVipsAbaixoMaximo() {
        Show showMetallica3 = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.29);
        showMetallica3.cadastrarIngressos(100);
        assertEquals(29, showMetallica3.getTotalIngressosVIP());
    }

    //-----------------------------------------------------------------------
    // PARTIÇÕES POR EQUIVALÊNCIA
    //-----------------------------------------------------------------------

    @Test
    @Tag("PartiçõesEquivalência")
    @DisplayName("Teste preço dos ingressos válidos")
    public void precoDosIngressosValidos() {
        showQueen.cadastrarIngressos(100);
        //ingressos vips 1-30
        assertEquals(20, showQueen.getIngresso(30L).getPreco());
        //ingressos meia 31-40
        assertEquals(5, showQueen.getIngresso(40L).getPreco());
        //ingressos comuns 41-100
        assertEquals(10, showQueen.getIngresso(100L).getPreco());
    }

    @Test
    @Tag("PartiçõesEquivalência")
    @DisplayName("Teste preço dos ingressos inválidos")
    public void precoDosIngressosInvalidos() {
        showQueen.cadastrarIngressos(100);
        if (showQueen.getIngresso(30L).getPreco() == 50) {
            fail("O preço do ingresso VIP deveria ser diferente de 50!");
        }

        if (showQueen.getIngresso(40L).getPreco() == 2) {
            fail("O preço do ingresso meia deveria ser diferente de 2!");
        }

        if (showQueen.getIngresso(100L).getPreco() == 12) {
            fail("O preço do ingresso comum deveria ser diferente de 12!");
        }
    }

    @Test
    @Tag("PartiçõesEquivalência")
    @DisplayName("Teste quantidade de ingressos válidos")
    public void qntDosIngressosValidos() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        showMetallica.cadastrarIngressos(100);
        //ingressos vips 1-20
        assertEquals(20, showMetallica.getTotalIngressosVIP());
        //ingressos meia 21-30
        assertEquals(10, showMetallica.getTotalIngressosMeia());
        //ingressos comuns 31-100
        assertEquals(70, showMetallica.getTotalIngressosNormais());
    }

    @Test
    @Tag("PartiçõesEquivalência")
    @DisplayName("Teste quantidade de ingressos invalidos")
    public void qntDosIngressosInvalidos() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        showMetallica.cadastrarIngressos(100);

        if (showMetallica.getTotalIngressosVIP() == 21) {
            fail("A quantidade de ingressos VIP deveria ser diferente de 21!");
        }

        if (showMetallica.getTotalIngressosMeia() == 9) {
            fail("A quantidade de ingressos meia deveria ser diferente de 9!");
        }

        if (showMetallica.getTotalIngressosNormais() == 71) {
            fail("A quantidade de ingressos comuns deveria ser diferente de 71!");
        }
    }

    @Test
    @Tag("PartiçõesEquivalência")
    @DisplayName("Teste desconto do lote válido")
    public void descontoLoteValido() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 25);
        assertEquals(25, lote.getDesconto());
    }

    @Test
    @Tag("PartiçõesEquivalência")
    @DisplayName("Teste desconto do lote inválido")
    //ERRO, O SISTEMA PERMITE DESCONTOS ACIMA DE 25% PARA OS LOTES
    public void descontoLoteInvalido() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 26);
        assertEquals(26, lote.getDesconto());
    }

    //-----------------------------------------------------------------------
    // TABELAS DE DECISÃO
    //-----------------------------------------------------------------------

    @Test
    @Tag("TabelasDecisão")
    @DisplayName("Regra 1")
    //ERRO, SISTEMA PERMITE MAIS INGRESSOS VIPS DO QUE DEVERIA
    public void regra1() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.31);
        showMetallica.cadastrarIngressos(100);
        assertEquals(31, showMetallica.getTotalIngressosVIP());
    }

    @Test
    @Tag("TabelasDecisão")
    @DisplayName("Regra 2")
    //ERRO, O SISTEMA PERMITE DESCONTOS ACIMA DE 25% PARA OS LOTES
    public void regra2() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 26);
        assertEquals(26, lote.getDesconto());
    }

    @Test
    @Tag("TabelasDecisão")
    @DisplayName("Regra 3")
    public void regra3() {
        showLinkinPark.cadastrarIngressos(500);
        showLinkinPark.cadastraLotes(0.15);
        Ingresso[] ingressosLote = showLinkinPark.getLote(1L).getIngressos();
        for (Ingresso ing : ingressosLote) {
            if (ing != null) {
                ing.venderIngresso();
            }
        }
        assertEquals("Ingressos normais vendidos: 350\n" +
                "Ingressos vip vendidos: 100\n" +
                "Ingressos meia vendidos: 50\n" +
                "Receita líquida: 1625.0\n" +
                "Status financeiro: LUCRO", showLinkinPark.gerarRelatorio());
    }

    @Test
    @Tag("TabelasDecisão")
    @DisplayName("Regra 4")
    public void regra4() {
        Show showMetallica = new Show("25/12/2024", "Metallica", 1000, 2000, 1, true, 1650, 0.20);
        showMetallica.cadastrarIngressos(500);
        showMetallica.cadastraLotes(0);
        Ingresso[] ingressosLote = showMetallica.getLote(1L).getIngressos();
        for (Ingresso ing : ingressosLote) {
            if (ing != null) {
                ing.venderIngresso();
            }
            break;
        }
        assertEquals("Ingressos normais vendidos: 0\n" +
                "Ingressos vip vendidos: 1\n" +
                "Ingressos meia vendidos: 0\n" +
                "Receita líquida: 0.0\n" +
                "Status financeiro: ESTÁVEL", showMetallica.gerarRelatorio());
    }

    @Test
    @Tag("TabelasDecisão")
    @DisplayName("Regra 5")
    public void regra5() {
        showLinkinPark.cadastrarIngressos(500);
        showLinkinPark.cadastraLotes(0);
        Ingresso[] ingressosLote = showLinkinPark.getLote(1L).getIngressos();
        assertEquals("Ingressos normais vendidos: 0\n" +
                "Ingressos vip vendidos: 0\n" +
                "Ingressos meia vendidos: 0\n" +
                "Receita líquida: -3300.0\n" +
                "Status financeiro: PREJUÍZO", showLinkinPark.gerarRelatorio());
    }
}
