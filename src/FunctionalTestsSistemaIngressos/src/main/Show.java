import java.lang.Math;
import java.util.Arrays;

public class Show {

    String data;
    String artista;
    Integer cache;
    double despesasInfra;
    Integer qntLotes;
    Boolean dataEspecial;
    Integer ingressosCadastrados;
    Integer ingressosNormais;
    Integer ingressosVIP;
    Integer ingressosMeia;
    Long idIngresso;
    Ingresso[] ingressos;
    Integer precoIngresso;
    double porcentagemVIP;
    Long idLote;
    Lote[] lotes;
    Integer ingressosNormaisVendidos;
    Integer ingressosVIPVendidos;
    Integer ingressosMeiaVendidos;

    public Show(String data, String artista, Integer cache, double despesasInfra, Integer qntLotes, Boolean dataEspecial, Integer precoIngresso, double porcentagemVIP) {
        this.data = data;
        this.artista = artista;
        this.cache = cache;
        this.qntLotes = qntLotes;
        this.dataEspecial = dataEspecial;
        if (dataEspecial) {
            this.despesasInfra = despesasInfra * 1.15;
        } else {
            this.despesasInfra = despesasInfra;
        }
        this.ingressosCadastrados = 0;
        this.ingressosNormais = 0;
        this.ingressosVIP = 0;
        this.ingressosMeia = 0;
        this.idIngresso = 1L;
        this.idLote = 1L;
        /*para faciliar estou considerando 1000 como a capacidade do local de shows e que podemos ter no máximo 100 lotes*/
        this.ingressos = new Ingresso[1001];
        this.lotes = new Lote[100];
        this.precoIngresso = precoIngresso;
        this.porcentagemVIP = porcentagemVIP;
        this.ingressosMeiaVendidos = 0;
        this.ingressosNormaisVendidos = 0;
        this.ingressosVIPVendidos = 0;
    }

    public void cadastrarIngressos(Integer qntIngressos) {
        this.ingressosCadastrados += qntIngressos;
        this.ingressosVIP += (int) Math.ceil(qntIngressos * porcentagemVIP);
        cadastrarIngressosVIP(ingressosVIP);
        this.ingressosMeia += (int) Math.ceil(qntIngressos * 0.10);
        cadastrarIngressosMeia(ingressosMeia);
        this.ingressosNormais += (int) Math.ceil(qntIngressos * (1 - 0.1 - porcentagemVIP));
        cadastrarIngressosNormais(ingressosNormais);
    }

    public void cadastrarIngressosVIP(Integer qntIngressosVIP) {
        for (int i = 0; i < qntIngressosVIP; i++) {
            Ingresso ing = new Ingresso(idIngresso, "vip");
            ing.setPreco(this.precoIngresso * 2);
            ingressos[Math.toIntExact(idIngresso)] = ing;
            idIngresso++;
        }
    }

    public void cadastrarIngressosMeia(Integer qntIngressosMeia) {
        for (int i = 0; i < qntIngressosMeia; i++) {
            Ingresso ing = new Ingresso(idIngresso, "meia");
            ing.setPreco((int) Math.ceil(this.precoIngresso * 0.5));
            ingressos[Math.toIntExact(idIngresso)] = ing;
            idIngresso++;
        }
    }

    public void cadastrarIngressosNormais(Integer qntIngressosNormais) {
        for (int i = 0; i < qntIngressosNormais; i++) {
            Ingresso ing = new Ingresso(idIngresso, "normal");
            ing.setPreco(this.precoIngresso);
            ingressos[Math.toIntExact(idIngresso)] = ing;
            idIngresso++;
        }
    }

    public void cadastraLotes(double descontoLotes) {
        Ingresso[] ingressosValidos = Arrays.stream(ingressos)
                .filter(ing -> ing != null)
                .toArray(Ingresso[]::new);

        if (qntLotes == 1) {
            Lote lote = new Lote(idLote, ingressosValidos, descontoLotes);
            lotes[Math.toIntExact(idLote)] = lote;
        } else if (qntLotes > 1) {
            int lotesPorLote = ingressosValidos.length / qntLotes;
            int resto = ingressosValidos.length % qntLotes;

            int inicio = 0;

            for (int i = 0; i < qntLotes; i++) {
                int fim = inicio + lotesPorLote - 1;
                if (resto > 0) {
                    fim++;
                    resto--;
                }

                if (fim >= ingressosValidos.length) {
                    fim = ingressosValidos.length - 1;
                }

                Ingresso[] loteIngressos = Arrays.copyOfRange(ingressosValidos, inicio, fim + 1);
                Lote lote = new Lote(idLote, loteIngressos, descontoLotes);
                lotes[Math.toIntExact(idLote)] = lote;
                idLote++;
                inicio = fim + 1;
            }
        }
    }

    public String gerarRelatorio() {
        double resumoFinanceiro = getReceitaLiquida();
        String statusFinanceiro;
        if (resumoFinanceiro > 0) {
            statusFinanceiro = "LUCRO";
        } else if (resumoFinanceiro < 0) {
            statusFinanceiro = "PREJUÍZO";
        } else {
            statusFinanceiro = "ESTÁVEL";
        }
        return "Ingressos normais vendidos: " + ingressosNormaisVendidos +
                "\nIngressos vip vendidos: " + ingressosVIPVendidos +
                "\nIngressos meia vendidos: " + ingressosMeiaVendidos +
                "\nReceita líquida: " + resumoFinanceiro +
                "\nStatus financeiro: " + statusFinanceiro;
    }

    public Integer getTotalIngressosCadastrados() {
        return this.ingressosCadastrados;
    }
    public Integer getTotalIngressosNormais() {
        return this.ingressosNormais;
    }
    public Integer getTotalIngressosVIP() {
        return this.ingressosVIP;
    }
    public Integer getTotalIngressosMeia() {
        return this.ingressosMeia;
    }

    public double getDespesasInfra() {
        return this.despesasInfra;
    }

    public Ingresso getIngresso(Long id) {
        return ingressos[Math.toIntExact(id)];
    }

    public Ingresso[] getIngressos() {
        return ingressos;
    }

    public Lote getLote(Long id) {
        return lotes[Math.toIntExact(id)];
    }

    public double getReceitaBruta() {
        double receitaTotal = 0.0;
        for (Lote lote : lotes) {
            if (lote != null) {
                double desconto = lote.getDesconto();
                for (Ingresso ingresso : lote.getIngressos()) {
                    if (ingresso != null && ingresso.getStatus()) {
                        double precoComDesconto;
                        if (!ingresso.getTipo().equals("meia")) {
                            precoComDesconto = ingresso.getPreco() * (1 - desconto);
                        } else {
                            precoComDesconto = ingresso.getPreco();
                        }
                        receitaTotal += precoComDesconto;
                        if (ingresso.getTipo().equals("meia")) {
                            this.ingressosMeiaVendidos++;
                        } else if (ingresso.getTipo().equals("vip")) {
                            this.ingressosVIPVendidos++;
                        } else {
                            this.ingressosNormaisVendidos++;
                        }
                    }
                }
            }
        }

        return receitaTotal;
    }

    public double getReceitaLiquida() {
        return this.getReceitaBruta() - despesasInfra - cache;
    }


}