package model;

public class MateriaisUsados {

    private int qtdRL;
    private int qtdRS;
    private int qtdMG;
    private double mlTinta;
    private double mlStencil;
    private int qtdPapelToalha;

    public MateriaisUsados(int qtdRL, int qtdRS, int qtdMG, double mlTinta, double mlStencil,
            int qtdPapelToalha) {
        this.qtdRL = qtdRL;
        this.qtdRS = qtdRS;
        this.qtdMG = qtdMG;
        this.mlTinta = mlTinta;
        this.mlStencil = mlStencil;
        this.qtdPapelToalha = qtdPapelToalha;
    }

    public int getQtdRL() {
        return qtdRL;
    }

    public int getQtdRS() {
        return qtdRS;
    }

    public int getQtdMG() {
        return qtdMG;
    }

    public double getMlTinta() {
        return mlTinta;
    }

    public double getMlStencil() {
        return mlStencil;
    }

    public int getQtdPapelToalha() {
        return qtdPapelToalha;
    }
}
