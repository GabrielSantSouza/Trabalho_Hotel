/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import excecoes.EntidadeNaoEncontradaException;
import excecoes.RegraNegocioException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelos.Funcionario;
import modelos.Hospede;
import modelos.Quarto;
import modelos.Reserva;

/**
 *
 * @author gabriel
 */
public class Sistema {
    private  List<Hospede> hospedes =  new ArrayList<>();
    private List<Quarto> quartos = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();
    
    private int geraIdReserva = 1;
    
    public void init(){
        quartos.add(new Quarto(505, 2, 150.00));
        quartos.add(new Quarto(306, 4, 250.00));
        quartos.add(new Quarto(103, 2,150.00));
        
        funcionarios.add(new Funcionario(1, "Marcos da Silva", "111.222.333-44", "Recepção"));
        funcionarios.add(new Funcionario(2, "Maria", "999.888.777-66", "Gerente"));
    }
    
     /*
        Hospede  --------------------------------------------------------------------------
    */
    
    public void cadastrarHospede(Hospede h) throws RegraNegocioException {
        for (Hospede hospede: hospedes){
            if(hospede.getCPF().equals(h.getCPF())){
                throw new RegraNegocioException("CPF já registrado no sistema");
            }
        }
        hospedes.add(h);
    }
    
    public Hospede buscarHospede(String cpf) throws EntidadeNaoEncontradaException{
        for (int i = 0; i < hospedes.size(); i++){
            if(hospedes.get(i).getCPF().equals(cpf)){
                return hospedes.get(i);
            }
        }
        
        throw new EntidadeNaoEncontradaException("Não encontramos nenhum hóspede com o CPF" + cpf);
    }
    
    public void atualizarHospede(String cpf, String novoNome, String novaDtNascimento) throws EntidadeNaoEncontradaException{
        Hospede h = buscarHospede(cpf);
        h.setNome(novoNome);
        h.setdtNascimento(novaDtNascimento);
    }
    
    public void removerHospede(String cpf) throws EntidadeNaoEncontradaException {
        Hospede h = buscarHospede(cpf);
        hospedes.remove(h);
    }
    
    public List<Hospede> listarHospedes()throws EntidadeNaoEncontradaException {
        if(hospedes.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem hospedes cadastrados");
        }
        return hospedes;
    }
    
    
    /*
        Quarto  --------------------------------------------------------------------------
    */
    
    public void cadastrarQuarto(Quarto q) throws RegraNegocioException{
        for(int i=0; i< quartos.size(); i++){
            if(quartos.get(i).getNumero() == q.getNumero()){
                throw new RegraNegocioException("Quarto já cadastrado");
            }
        }
    }
    
    public Quarto buscarQuarto(int numero) throws EntidadeNaoEncontradaException{
         for(int i=0; i< quartos.size(); i++){
             if(quartos.get(i).getNumero() == numero){
                 return quartos.get(i);
             }
         }
         
         throw new EntidadeNaoEncontradaException("Não foi encontrado quarto com o número: "+numero);
    }
    
    public void atualizarQuarto(int numero, int novaCapacidade, double novoValorDiaria) throws EntidadeNaoEncontradaException, RegraNegocioException{
        Quarto q = buscarQuarto(numero);
        if (q.isOcupado()) {
            throw new RegraNegocioException("Não é possível atualizar os dados do quarto " + numero + " pois ele encontra-se ocupado no momento.");
        }
        q.setCapacidade(novaCapacidade);
        q.setValorDiaria(novoValorDiaria);
    }
    
    public void removerQuarto(int numero) throws EntidadeNaoEncontradaException, RegraNegocioException{
        Quarto q = buscarQuarto(numero);
        
        if (q.isOcupado()) {
            throw new RegraNegocioException("Não é possível remover o quarto " + numero + " pois ele encontra-se ocupado.");
        }
        quartos.remove(q);
    }
    
    public List<Quarto> listarQuartos() throws EntidadeNaoEncontradaException{
        if(quartos.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem quartos cadastrados");
        }
        
        return quartos;
    }
    
    public List<Quarto> listarQuartosPorStatus(boolean ocupado)throws EntidadeNaoEncontradaException{
        List<Quarto> filtrados  = new ArrayList<>();
        
        for(int i=0; i<quartos.size(); i++){
            if(quartos.get(i).isOcupado() == ocupado){
                filtrados.add(quartos.get(i));
            }
        }
        
        if(filtrados.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem reservas no status solicitado");
        }
        return filtrados;
    }
    
     /*
        RESERVAS  --------------------------------------------------------------------------
    */
    
    public void realizarCheckin(String cpf, int numQuarto, Date checkIn, Date checkOut) throws EntidadeNaoEncontradaException, RegraNegocioException{
        Hospede hospede = buscarHospede(cpf);
        Quarto quarto = buscarQuarto(numQuarto);
        
        if(quarto.isOcupado()){
            throw new RegraNegocioException("o quarto "+numQuarto+"já está ocupado");
        }
        
        if(checkIn.after(checkOut)){
            throw new RegraNegocioException("Data de entrada maior do que data de saída");
                    
        }
        
        quarto.setOcupado(true);
        Reserva novaReserva = new Reserva(geraIdReserva++, checkIn, checkOut, true, hospede, quarto);
        reservas.add(novaReserva);
    }
    
    public Reserva buscarReserva(int idReserva) throws EntidadeNaoEncontradaException{
        for(int i=0; i<reservas.size();i++){
            if (reservas.get(i).getIdReserva() == idReserva){
                return reservas.get(i);
            }
        }
        
        throw new EntidadeNaoEncontradaException("Não foi encontrada reserva com id "+idReserva);
    }
    
    public double realizarCheckOut(int idReserva) throws EntidadeNaoEncontradaException, RegraNegocioException{
        Reserva reserva = buscarReserva(idReserva);
        
        if (!reserva.isAtiva()) {
            throw new RegraNegocioException("Esta reserva já foi finalizada anteriormente.");
        }
        
        reserva.getQuarto().setOcupado(false);
        reserva.setAtiva(false);
        
        return reserva.calcularTotalPago();
    }
    
    public List<Reserva> listarTodasReservas() throws EntidadeNaoEncontradaException{
        if(reservas.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem reservas cadastradas");
        }
        return reservas;
    }
    
    public List<Reserva> listarReservasPorStatus(boolean status)throws EntidadeNaoEncontradaException{
        List<Reserva> filtradas  = new ArrayList<>();
        
        for(int i=0; i<reservas.size(); i++){
            if(reservas.get(i).isAtiva() == status){
                filtradas.add(reservas.get(i));
            }
        }
        
        if(filtradas.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem reservas no status solicitado");
        }
        
        return filtradas;
    }
    
}
