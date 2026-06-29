/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author gabriel
 */
public class Funcionario extends Usuario{
    private String cargo;
    
    public Funcionario(int id, String nome, String cpf, String cargo ){
        super(id, nome, cpf);
        this.cargo = cargo;
    }
    
    public String getCargo(){
        return cargo;
    }
    
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
}
