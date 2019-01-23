/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backnet;

import java.util.ArrayList;

/**
 *
 * @author keomas
 * 
 * Backpropagation RNA arquitetura 2-4-1 
 * 
 */
public class Net {
    
    public  ArrayList<Double> listaErro = new ArrayList<Double>();
    public ArrayList<Integer> listaEpocas = new ArrayList<Integer>();
    
   public int totalEp=0;
    //erro
    double Erro =0;
    double somaErro=0;
    double toleranceErro = 0.05;
    //taxa de aprendizagem 
    double alpha =0.2;
    //targets
   // public double t [] = new double [4];
    public double t_binario [] = {0,1,1,0};
    
    public double t_bipolar [] = {-1,1,1,-1};
    //entrada
    public double x [] = new double [2];
    //public double x [] = {0,1};
    //hidden layer
    
    public double z_in [] = new double [4];
    
    public double zj [] = new double [4];
    //public double zj [] = {1,1,1,1};
    //output
    public double y_in =0;
    
    public double yk =0;
    
    //erros
    //erro output layer
    public double phik =0;
    //erro  hidden layer
    public double phi_inj [] = new double [4];
    
    public double phi_j [] = new double [4];
    
    //Pesos
    //public double v [] [] = new double [2][4];
    public double v [] [] = {
        {0.1970, 0.3191 , -0.1448 , 0.3594 },
        {0.3099 , 0.1904 , -0.0347 , -0.4861 }
    };
    
    //public double bias_z [] = new double [4];
    public double bias_z [] = {-0.3378, 0.2771 , 0.2859 , -0.3329};
    //public double w  [] = new double [4];
    public double w  [] = {0.4919 ,-0.2913 , -0.3979 , 0.3581};
    
    public double bias_y= -0.1401;
    
    public double delta_w [] = new double [4];
    
    public double delta_bias_z [] = new double [4];
    
    public double delta_v [][] = new double [2] [4];
    
    public double delta_bias_y  =0;  

    Net(double tx, double er) {
        this.alpha = tx;
        this.Erro = er;
    }
    
    
    
    //construtor
    
   
    
    
//calcula função de ativação (sigmoid binarios)
    public double f_binario(double x){
        double f =0;
        f = 1  / (1+ Math.exp(-x));
    
        return f;
    }
    //calcula a derivada da função de ativação 
    public double f2_binario(double x){
        double f, f1 =0;
        f1 = f_binario(x);
        f = f1 * (1-f1);
    
        return f;
    }
    //calcula funcao de ativação (bipolar sigmoid)
    public double f_bipolar(double x){
        double f =0;
        f = (2  / (1+ Math.exp(-x))) - 1;
    
        return f;
    }
    //calcula a derivada da função de ativação bipolar 
    public double f2_bipolar(double x){
        double f, f1 =0;
        
        f1 = f_bipolar(x);
        
        f = ((1+f1)*(1-f1))*0.5;
    
        return f;
    }
    
    
    //caluca  z_in e armazena no vetor z_in
    public void calcula_zin(){
        double aux=0;
        for(int j=0; j<4;j++){
           for(int i=0;i<2;i++){
               
               aux= aux + this.x[i]*this.v[i][j];
           
           }
           this.z_in[j]=this.bias_z[j]+aux;
           aux=0;
        
        
        }
    
    }
    
    public void calcula_zj(char tipo){
        
        if(tipo == 'b'){
            for(int i=0;i<4;i++){
                this.zj[i]= f_binario(this.z_in[i]);
            }
        }else if(tipo == 'p'){
            for(int i=0;i<4;i++){
                this.zj[i]= f_bipolar(this.z_in[i]);
            }
        
        }else{
            System.out.println("ERROOO no calucla_zj");
        
        }
    }


    public void calcula_yin(){
        
        double aux=0;
        
        for(int i=0;i<4;i++){
               
               aux=aux+this.zj[i]*this.w[i];           
           }
           
           this.y_in=this.bias_y+aux;
        
    }
        
    public void calcula_y(char tipo){
        
        if(tipo == 'b'){
        
            this.yk=f_binario(this.y_in);
        }else if(tipo == 'p'){
            this.yk=f_bipolar(this.y_in);
        
        }else{
            System.out.println("Erro no calcula_y");
        }
    
    
    }


    
    public void calcula_phik(double target, char tipo){
        
        this.Erro=(target-this.yk);
        if(tipo == 'b'){    
            this.phik = (target-this.yk)*(f2_binario(this.y_in));
        }
        else if(tipo =='p'){
            this.phik = (target-this.yk)*(f2_bipolar(this.y_in));
        
        }
        else{
            System.out.println("Erro no calcula_phik");
        
        }
    }
    
    
    public void calcula_deltaw(){
    
         for(int i=0;i<4;i++){
               
               this.delta_w[i]=this.alpha*this.phik*this.zj[i];
               
           }
           this.delta_bias_y=this.alpha*this.phik;
    
    }
    
    public void calcula_phi_inj(){
    
        for(int i=0; i <4; i++){
        
            this.phi_inj[i]=this.w[i]*this.phik;
        }
    
    }
    
    
    public void calcula_phij(char tipo){
    
        if(tipo == 'b'){
            for(int i=0; i <4; i++){
                this.phi_j[i]=this.phi_inj[i]*f2_binario(this.z_in[i]);
        }
            
        }else if(tipo == 'p'){
            for(int i=0; i <4; i++){
                this.phi_j[i]=this.phi_inj[i]*f2_bipolar(this.z_in[i]);
        }
        
        }else{
            System.out.println("Erro no calcula_phij");
        
        }
    
    }
    
    public void calcula_deltav(){
       double aux=0;
        for(int i=0;i<2;i++){
            for(int j=0;j<4;j++){
                this.delta_v[i][j]=this.alpha*this.phi_j[j]*this.x[i];
            
            }
        
        }
         for(int i=0; i <4; i++){
                this.delta_bias_z[i]=this.alpha * this.phi_j[i];
        }
        
    }
    
    
    public void updateW(){
        double old1=0;
        double old2=0;
         
        for(int i =0;i<4;i++){
            old1=this.w[i];
            this.w[i]=old1+this.delta_w[i];
        
        }
        old2=this.bias_y;
        this.bias_y=old2+this.delta_bias_y;
       
    }
    
    public void updateV(){
        double old1=0;
        double old2=0;
        for(int i=0;i<2;i++){
            for(int j=0;j<4;j++){
                old1=this.v[i][j];
                this.v[i][j]= old1 + this.delta_v[i][j];
            }
        
        }
         for(int i=0; i <4; i++){
                old2=this.bias_z[i];
                this.bias_z[i]=old2+this.delta_bias_z[i];
                
        }
        
    }
    
    
    public  void get_input(double input []){
    
        this.x[0]=input[0];
        this.x[1]=input[1];
      //  System.out.println("X1 :"+this.x[0]+ " X2 "+this.x[1]);
    
    }
    
    
    public void training_binario(){
       
        boolean flag =false;
        int count =0;
        this.totalEp =0;
       //entradas
        double input [][] = {{0,0},
       {0,1},
       {1,0},
       {1,1}
        };
        while(!flag){
            count++;
            listaEpocas.add(count);
            this.somaErro=0;
            for(int j=0;j<4;j++){
                //FeedForward
                ////////////////////
                //calcula z_in e Z para todas as unidades Zi com dados binarios
                this.get_input(input[j]);
                this.calcula_zin();

                this.calcula_zj('b');

                this.calcula_yin();

                this.calcula_y('b');


                //Backpropagatoin
                //////////////////////////////
                //calcula componente do erro da saida Y
                this.calcula_phik(this.t_binario[j], 'b');

                this.calcula_deltaw();

                //calcula componente de erro deltaj  para todas as unidades Zi
                this.calcula_phi_inj();
                this.calcula_phij('b');
                this.calcula_deltav();
                this.somaErro=this.somaErro+this.Erro*this.Erro;


                //Updateting

                this.updateW();
                this.updateV();
               // System.out.println("Erro acumulado :"+this.somaErro);
                }
                
              //  System.out.println("Epoca :"+count);
               
           if(this.somaErro < this.toleranceErro){
               flag = true;
           }
           listaErro.add(this.somaErro);
           
      }
    
        this.totalEp=count;
        
    }
    
    
    public void training_bipolar(){
         
        this.totalEp =0;
        boolean flag =false;
        int count =0;
       //entradas
        double input [][] = {{-1,-1},
       {-1,1},
       {1,-1},
       {1,1}
        };
        while(!flag){
            count++;
            listaEpocas.add(count);
            this.somaErro=0;
            for(int j=0;j<4;j++){
                //FeedForward
                ////////////////////
                //calcula z_in e Z para todas as unidades Zi com dados binarios
                this.get_input(input[j]);
                this.calcula_zin();

                this.calcula_zj('p');

                this.calcula_yin();

                this.calcula_y('p');


                //Backpropagatoin
                //////////////////////////////
                //calcula componente do erro da saida Y
                this.calcula_phik(this.t_bipolar[j], 'p');

                this.calcula_deltaw();

                //calcula componente de erro deltaj  para todas as unidades Zi
                this.calcula_phi_inj();
                this.calcula_phij('p');
                this.calcula_deltav();
                this.somaErro=this.somaErro+this.Erro*this.Erro;


                //Updateting

                this.updateW();
                this.updateV();
               // System.out.println("Erro acumulado :"+this.somaErro);
                }
                
               // System.out.println("Epoca :"+count);
               
           if(this.somaErro < this.toleranceErro){
               flag = true;
           }
           listaErro.add(this.somaErro);
      }
        
        this.totalEp=count;
    
    

    }


}