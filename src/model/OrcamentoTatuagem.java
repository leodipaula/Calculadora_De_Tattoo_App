package model;

public class OrcamentoTatuagem {

    private double tamanhoCm2;
    private double horas;
    private boolean colorida;
    private String nivelDetalhe;
    private MateriaisUsados materiais;

    public OrcamentoTatuagem(double tamanhoCm2, double horas, String nivelDetalhe, boolean colorida,
            MateriaisUsados materiais) {
        this.tamanhoCm2 = tamanhoCm2;
        this.horas = horas;
        this.colorida = colorida;
        this.nivelDetalhe = nivelDetalhe;
        this.materiais = materiais;
    }

    public double calcularCustoTotal() {
        double custoBase = tamanhoCm2 * 10.0;

        double custoEnergia = horas * 1.13;

        double custoDetalhe = switch (nivelDetalhe.toLowerCase()) {
            case "médio" -> 20.0;
            case "detalhado" -> 40.0;
            default -> 0.0;
        };

        double custoTinta = materiais.getMlTinta() * (colorida ? 3.67 : 2.67);

        double custoMateriaisFixos = materiais.getQtdRL() * 6.0 + materiais.getQtdRS() * 6.0
                + materiais.getQtdMG() * 6.0 + materiais.getMlStencil() * 1.50
                + materiais.getQtdPapelToalha() * (2.00 / 20.0) + 0.06 + // 1 batoque
                0.07 + // 1 sulfite
                0.25 + // 1 papel hectográfico
                0.40 + // 10ml sabonete
                0.50 + // 1 máscara
                0.60 + // 3 luvas
                0.20 + // plástico clipcord
                0.28 + // 2g vaselina
                0.72 + // fita adesiva
                1.44 + // copinho + soro
                1.00; // álcool

        return custoBase + custoEnergia + custoTinta + custoDetalhe + custoMateriaisFixos;
    }
}
