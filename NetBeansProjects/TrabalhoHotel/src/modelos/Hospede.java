/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author gabriel
 */
public class Hospede extends Usuario{
    private String dtNascimento;
    
    public Hospede(int id, String nome, String cpf, String dtNascimento){
        super(id, nome, cpf);
        this.dtNascimento = dtNascimento;
    }
    
    public String getDtNascimento(){
        return dtNascimento;
    }
    
    public void setdtNascimento(String dtNascimento){
        this.dtNascimento = dtNascimento;
    }
}
