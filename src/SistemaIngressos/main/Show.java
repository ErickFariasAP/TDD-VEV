import java.lang.Math;

public class Show {

    String data;
    String artista;
    Integer cache;
    Integer despesasInfra;
    Integer qntLotes;
    Boolean dataEspecial;
    Integer ingressosCadastrados;
    Integer ingressosNormais;
    Integer ingressosVIP;
    Integer ingressosMeia;

    public Show(String data, String artista, Integer cache, Integer despesasInfra, Integer qntLotes, Boolean dataEspecial) {
        this.data = data;
        this.artista = artista;
        this.cache = cache;
        this.despesasInfra = despesasInfra;
        this.qntLotes = qntLotes;
        this.dataEspecial = dataEspecial;
        this.ingressosCadastrados = 0;
        this.ingressosNormais = 0;
        this.ingressosVIP = 0;
        this.ingressosMeia = 0;
    }

    public void cadastrarIngressos(Integer qntIngressos) {
        this.ingressosCadastrados += qntIngressos;
        this.ingressosVIP += (int) Math.ceil(qntIngressos * 0.25);
        this.ingressosMeia += (int) Math.ceil(qntIngressos * 0.10);
        this.ingressosNormais += (int) Math.ceil(qntIngressos * 0.65);
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

}