package controller;

import javafx.scene.shape.Arc;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.MateriaisUsados;
import model.OrcamentoTatuagem;

public class CalculadoraController {

    @FXML
    private TextField inputTamanho;

    @FXML
    private TextField inputHoras;

    @FXML
    private CheckBox checkColorida;

    @FXML
    private TextField inputRL;

    @FXML
    private TextField inputRS;

    @FXML
    private TextField inputMG;

    @FXML
    private TextField inputMlTinta;

    @FXML
    private TextField inputMlStencil;

    @FXML
    private TextField inputPapelToalha;

    @FXML
    private Label labelResultado;

    @FXML
    private VBox step1Content, step2Content, step3Content, resultContent;

    @FXML
    private Circle step1, step2, step3;

    @FXML
    private ChoiceBox<String> choiceDetalhe;

    @FXML
    private Circle leftEye;

    @FXML
    private Circle rightEye;

    @FXML
    private Arc mouth;

    @FXML
    private VBox mainContainer;

    private int currentStep = 1;

    @FXML
    public void calcularPreco() {
        try {
            double tamanho = Double.parseDouble(inputTamanho.getText());
            double horas = Double.parseDouble(inputHoras.getText());
            String detalhe = choiceDetalhe.getValue();
            boolean colorida = checkColorida.isSelected();

            int qtdRL = Integer.parseInt(inputRL.getText());
            int qtdRS = Integer.parseInt(inputRS.getText());
            int qtdMG = Integer.parseInt(inputMG.getText());
            double mlTinta = Double.parseDouble(inputMlTinta.getText());
            double mlStencil = Double.parseDouble(inputMlStencil.getText());
            int qtdPapelToalha = Integer.parseInt(inputPapelToalha.getText());

            MateriaisUsados materiais =
                    new MateriaisUsados(qtdRL, qtdRS, qtdMG, mlTinta, mlStencil, qtdPapelToalha);
            OrcamentoTatuagem orcamento =
                    new OrcamentoTatuagem(tamanho, horas, detalhe, colorida, materiais);

            double preco = orcamento.calcularCustoTotal();
            step3Content.setVisible(false);
            resultContent.setVisible(true);
            labelResultado.setText(String.format("Preço mínimo: R$ %.2f", preco));

        } catch (Exception e) {
            labelResultado.setText("Verifique os dados preenchidos.");
        }
    }

    // Add call to updateStep in navigation methods
    @FXML
    private void nextStep() {
        switch (currentStep) {
            case 1:
                if (validateStep1()) {
                    animateTransition(step1Content, step2Content);
                    step2.getStyleClass().add("step-active");
                    currentStep = 2;
                    updateStep(2);
                }
                break;
            case 2:
                if (validateStep2()) {
                    animateTransition(step2Content, step3Content);
                    step3.getStyleClass().add("step-active");
                    currentStep = 3;
                    updateStep(3);
                }
                break;
        }
    }

    @FXML
    private void previousStep() {
        switch (currentStep) {
            case 2:
                animateTransition(step2Content, step1Content);
                step2.getStyleClass().remove("step-active");
                currentStep = 1;
                break;
            case 3:
                animateTransition(step3Content, step2Content);
                step3.getStyleClass().remove("step-active");
                currentStep = 2;
                break;
        }
    }

    private void animateTransition(Node from, Node to) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), from);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), to);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> {
            from.setVisible(false);
            to.setVisible(true);
            fadeIn.play();
        });

        fadeOut.play();
    }

    // Modify validation methods to use showError instead of Alert
    private boolean validateStep1() {
        try {
            Double.parseDouble(inputTamanho.getText());
            Double.parseDouble(inputHoras.getText());
            return true;
        } catch (NumberFormatException e) {
            showError();
            return false;
        }
    }

    private boolean validateStep2() {
        if (choiceDetalhe.getValue() == null) {
            showError();
            return false;
        }
        return true;
    }

    @FXML
    private void resetForm() {
        // Reset form fields
        inputTamanho.clear();
        inputHoras.clear();
        checkColorida.setSelected(false);
        choiceDetalhe.getSelectionModel().clearSelection();

        // Reset steps
        step2.getStyleClass().remove("step-active");
        step3.getStyleClass().remove("step-active");
        currentStep = 1;

        // Show first step
        resultContent.setVisible(false);
        step1Content.setVisible(true);
        step2Content.setVisible(false);
        step3Content.setVisible(false);
    }

    @FXML
    private void initialize() {
        // Initialize choiceDetalhe items
        choiceDetalhe.getItems().addAll("simples", "médio", "detalhado");

        // Start blinking animation
        Timeline blink = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            leftEye.getStyleClass().add("blink");
            rightEye.getStyleClass().add("blink");
        }), new KeyFrame(Duration.seconds(3.3), e -> {
            leftEye.getStyleClass().remove("blink");
            rightEye.getStyleClass().remove("blink");
        }));
        blink.setCycleCount(Timeline.INDEFINITE);
        blink.play();
    }

    private void showError() {
        mainContainer.getStyleClass().add("shake");
        mouth.getStyleClass().add("sad");

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            mainContainer.getStyleClass().remove("shake");
            mouth.getStyleClass().remove("sad");
        });
        pause.play();
    }

    protected void updateStep(int step) {
        mainContainer.getStyleClass().removeAll("step2", "step3");
        if (step == 2) {
            mainContainer.getStyleClass().add("step2");
        } else if (step == 3) {
            mainContainer.getStyleClass().add("step3");
        }
    }
}
