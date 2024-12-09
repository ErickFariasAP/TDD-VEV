public class Lote {

    Long id;
    Ingresso[] ingressos;
    double desconto;

    public Lote(Long id, Ingresso[] ingressos, double desconto) {
        this.id = id;
        this.ingressos = ingressos;
        this.desconto = desconto;
    }

    public double getDesconto() {
        return desconto;
    }

    public Ingresso[] getIngressos() {
        return ingressos;
    }
}
