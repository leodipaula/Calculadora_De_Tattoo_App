import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculadoraController {

    @FXML
    private TextField inputTamanho;

    @FXML
    private Label labelResultado;

    private final double PRECO_CM2 = 10.0;

    @FXML
    public void calcularPreco() {
        try {
            double tamanho = Double.parseDouble(inputTamanho.getText());
            double preco = tamanho * PRECO_CM2;
            labelResultado.setText(String.format("Preço: R$ %.2f", preco));
        } catch (NumberFormatException e) {
            labelResultado.setText("Digite um valor válido.");
        }
    }
}
