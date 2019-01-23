/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backnet;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 *
 * @author keoma
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    
    @FXML
    private Button btnBin;
    
    @FXML
    private Button btnBip;
    
    @FXML
    private Button btnBinBip;
    
    
    @FXML
    private TextField TxLearn;
    
    @FXML
    private TextField ErroQ;
    
    @FXML
    private AnchorPane tela;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        //instanciado RNA    
        
        Net net = new Net(Double.parseDouble(TxLearn.getText()), Double.parseDouble(ErroQ.getText()));
     
      //  //define eixos
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Numero de Épocas");
        yAxis.setLabel("Erro");
        //define elemento grafico 
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
        
        
        
        
        if(event.getSource() == btnBin){
                
                net.training_binario();
             //cria grafico para trenamento binario
             lineChart.setTitle("Erros x Épocas (Binario)");
             //define forma da serie Y vs X
            XYChart.Series series = new XYChart.Series();
            series.setName("α:  "+TxLearn.getText()+"  Erro Quadrático:  "+ErroQ.getText()+" Total de Epocas:  "+String.valueOf(net.totalEp));
                
         
            //definido tipo dos dados
           
        
            
         //populando grafico
            for (int i = 0; i < net.listaEpocas.size(); i++) {
                series.getData().add(new XYChart.Data<>(net.listaEpocas.get(i),net.listaErro.get(i)));
            }
        
            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);
            
        }
        else if(event.getSource() == btnBip){
            net.listaEpocas.clear();
            net.listaErro.clear();
            
            net.training_bipolar();
            
            lineChart.setTitle("Erros x Épocas (Bipolar)");

             //define forma da serie Y vs X
            XYChart.Series series = new XYChart.Series();
            series.setName("α:  "+TxLearn.getText()+" Erro Quadrático : "+ErroQ.getText()+" Total de Epocas:  "+String.valueOf(net.totalEp));
                
         
            //definido tipo dos dados
            
        
            
         //populando grafico
             for (int i = 0; i < net.listaEpocas.size(); i++) {
                series.getData().add(new XYChart.Data<>(net.listaEpocas.get(i),net.listaErro.get(i)));
            }
        
            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);
            
            
            
        }
        
        Stage stage2 = new Stage();
        stage2.setTitle("Analise de treinamento");
        stage2.setScene(new Scene(lineChart,800,600));
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initOwner(btnBin.getScene().getWindow());
        stage2.showAndWait();
    
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }        
}
