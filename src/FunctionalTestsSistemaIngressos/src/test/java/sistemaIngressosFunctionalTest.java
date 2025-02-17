import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class sistemaIngressosFunctionalTest {

    @Test
    public void verificarJunit() {
        Assertions.assertTrue(true);
    }

    //-----------------------------------------------------------------------
    // ANÁLISE DE VALORES LIMITES
    //-----------------------------------------------------------------------

    @Test
    @DisplayName("Teste criar lote mínimo")
    public void criarLoteMinimo() {
        Ingresso[] ingressosLote = new Ingresso[1];
        Lote lote = new Lote(1L, ingressosLote, 0);
        assertEquals(1, lote.getIngressos().length);
    }

    @Test
    @DisplayName("Teste criar lote logo acima do mínimo")
    public void criarLoteAcimaMinimo() {
        Ingresso[] ingressosLote = new Ingresso[2];
        Lote lote = new Lote(1L, ingressosLote, 0);
        assertEquals(2, lote.getIngressos().length);
    }

    @Test
    @DisplayName("Teste criar lote qualquer")
    public void criarLoteQualquer() {
        Ingresso[] ingressosLote = new Ingresso[10];
        Lote lote = new Lote(1L, ingressosLote, 0);
        assertEquals(10, lote.getIngressos().length);
    }

    @Test
    @DisplayName("Teste criar lote maximo")
    //ERRO AO CRIAR LOTE (função cadastraLotes)
    public void criarLoteMaximo() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.25);
        novoShow.cadastraLotes(0.25);
        assertEquals(1001, novoShow.getLote(1L).getIngressos().length);
    }

    @Test
    @DisplayName("Teste criar lote logo abaixo do maximo")
    public void criarLoteAbaixoMaximo() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 0.25);
        assertEquals(1000, lote.getIngressos().length);
    }

    @Test
    @DisplayName("Teste criar show vips mínimo")
    public void criarShowVipsMinimo() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressos(100);
        assertEquals(20, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste criar show vips logo acima do mínimo")
    public void criarShowVipsAcimaMinimo() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.21);
        novoShow.cadastrarIngressos(100);
        assertEquals(21, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste criar show vips qualquer")
    public void criarShowVipsQualquer() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.25);
        novoShow.cadastrarIngressos(100);
        assertEquals(25, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste criar show vips maximo")
    public void criarShowVipsMaximo() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.30);
        novoShow.cadastrarIngressos(100);
        assertEquals(30, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Teste criar show vips logo abaixo do máximo")
    public void criarShowVipsAbaixoMaximo() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.29);
        novoShow.cadastrarIngressos(100);
        assertEquals(29, novoShow.getTotalIngressosVIP());
    }

    //-----------------------------------------------------------------------
    // PARTIÇÕES POR EQUIVALÊNCIA
    //-----------------------------------------------------------------------

    @Test
    @DisplayName("Teste preço dos ingressos válidos")
    public void precoDosIngressosValidos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.30);
        novoShow.cadastrarIngressos(100);
        //ingressos vips 1-30
        assertEquals(20, novoShow.getIngresso(30L).getPreco());
        //ingressos meia 31-40
        assertEquals(5, novoShow.getIngresso(40L).getPreco());
        //ingressos comuns 41-100
        assertEquals(10, novoShow.getIngresso(100L).getPreco());
    }

    @Test
    @DisplayName("Teste preço dos ingressos inválidos")
    public void precoDosIngressosInvalidos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.30);
        novoShow.cadastrarIngressos(100);
        //ingressos vips 1-30
        assertEquals(50, novoShow.getIngresso(30L).getPreco());
        //ingressos meia 31-40
        assertEquals(2, novoShow.getIngresso(40L).getPreco());
        //ingressos comuns 41-100
        assertEquals(12, novoShow.getIngresso(100L).getPreco());
    }

    @Test
    @DisplayName("Teste quantidade de ingressos válidos")
    public void qntDosIngressosValidos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressos(100);
        //ingressos vips 1-20
        assertEquals(20, novoShow.getTotalIngressosVIP());
        //ingressos meia 21-30
        assertEquals(10, novoShow.getTotalIngressosMeia());
        //ingressos comuns 31-100
        assertEquals(70, novoShow.getTotalIngressosNormais());
    }

    @Test
    @DisplayName("Teste quantidade de ingressos invalidos")
    public void qntDosIngressosInvalidos() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.20);
        novoShow.cadastrarIngressos(100);
        //ingressos vips 1-30
        assertEquals(21, novoShow.getTotalIngressosVIP());
        //ingressos meia 31-40
        assertEquals(9, novoShow.getTotalIngressosMeia());
        //ingressos comuns 41-100
        assertEquals(71, novoShow.getTotalIngressosNormais());
    }

    @Test
    @DisplayName("Teste desconto do lote válido")
    public void descontoLoteValido() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 25);
        assertEquals(25, lote.getDesconto());
    }

    @Test
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
    @DisplayName("Regra 1")
    //ERRO, SISTEMA PERMITE MAIS INGRESSOS VIPS DO QUE DEVERIA
    public void regra1() {
        Show novoShow = new Show("25/12/2024", "Metallica", 100000, 8000, 1, false, 10, 0.31);
        novoShow.cadastrarIngressos(100);
        assertEquals(31, novoShow.getTotalIngressosVIP());
    }

    @Test
    @DisplayName("Regra 2")
    //ERRO, O SISTEMA PERMITE DESCONTOS ACIMA DE 25% PARA OS LOTES
    public void regra2() {
        Ingresso[] ingressosLote = new Ingresso[1000];
        Lote lote = new Lote(1L, ingressosLote, 26);
        assertEquals(26, lote.getDesconto());
    }

    @Test
    @DisplayName("Regra 3")
    public void regra3() {
        Show novoShow = new Show("25/12/2024", "Metallica", 1000, 2000, 1, true, 10, 0.20);
        novoShow.cadastrarIngressos(500);
        novoShow.cadastraLotes(0.15);
        Ingresso[] ingressosLote = novoShow.getLote(1L).getIngressos();
        for (Ingresso ing : ingressosLote) {
            if (ing != null) {
                ing.venderIngresso();
            }
        }
        assertEquals("Ingressos normais vendidos: 350\n" +
                "Ingressos vip vendidos: 100\n" +
                "Ingressos meia vendidos: 50\n" +
                "Receita líquida: 1625.0\n" +
                "Status financeiro: LUCRO", novoShow.gerarRelatorio());
    }

    @Test
    @DisplayName("Regra 4")
    public void regra4() {
        Show novoShow = new Show("25/12/2024", "Metallica", 1000, 2000, 1, true, 1650, 0.20);
        novoShow.cadastrarIngressos(500);
        novoShow.cadastraLotes(0);
        Ingresso[] ingressosLote = novoShow.getLote(1L).getIngressos();
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
                "Status financeiro: ESTÁVEL", novoShow.gerarRelatorio());
    }

    @Test
    @DisplayName("Regra 5")
    public void regra5() {
        Show novoShow = new Show("25/12/2024", "Metallica", 1000, 2000, 1, true, 10, 0.20);
        novoShow.cadastrarIngressos(500);
        novoShow.cadastraLotes(0);
        Ingresso[] ingressosLote = novoShow.getLote(1L).getIngressos();
        assertEquals("Ingressos normais vendidos: 0\n" +
                "Ingressos vip vendidos: 0\n" +
                "Ingressos meia vendidos: 0\n" +
                "Receita líquida: -3300.0\n" +
                "Status financeiro: PREJUÍZO", novoShow.gerarRelatorio());
    }
}
