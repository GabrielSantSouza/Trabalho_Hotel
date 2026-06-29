/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author gabriel
 */
public class Quarto {
    private int numero;
    private int capacidade;
    private double valorDiaria;
    private boolean ocupado;
    
    public Quarto(int numero, int capacidade, double valorDiaria){
        this.numero = numero;
        this.capacidade = capacidade;
        this.valorDiaria = valorDiaria;
    }
    
    public int getNumero(){
        return numero;
        
    }
    
    public void setNumero(int numero){
        this.numero = numero;
    }
    
    public int getCapacidade(){
        return capacidade;
    }
    
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
        
    }
    
    public double getValorDiaria(){
        return valorDiaria;
    }
    
    public void setValorDiaria(){
        this.valorDiaria = valorDiaria;
    }
    
    public boolean isOcupado(){
        return ocupado;
    }
    
    public void setOcupado(boolean ocupado){
        this.ocupado =  ocupado;
    }
    
    
}
