import java.lang.Math;

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

    public Show(String data, String artista, Integer cache, Integer despesasInfra, Integer qntLotes, Boolean dataEspecial, Integer precoIngresso) {
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
        /*para faciliar estou considerando 1 milhão como a capacidade do local de shows*/
        this.ingressos = new Ingresso[1000000];
        this.precoIngresso = precoIngresso;
    }

    public void cadastrarIngressos(Integer qntIngressos) {
        this.ingressosCadastrados += qntIngressos;
        this.ingressosVIP += (int) Math.ceil(qntIngressos * 0.25);
        cadastrarIngressosVIP(ingressosVIP);
        this.ingressosMeia += (int) Math.ceil(qntIngressos * 0.10);
        cadastrarIngressosMeia(ingressosMeia);
        this.ingressosNormais += (int) Math.ceil(qntIngressos * 0.65);
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

    public Ingresso getIngresso(Long id) {
        return ingressos[Math.toIntExact(id)];
    }

}