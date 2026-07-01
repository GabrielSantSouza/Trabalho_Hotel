/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.Date;

/**
 *
 * @author gabriel
 */
public class Reserva implements InterfaceTotalPago{
    
   private int idReserva;
   private Date dataCheckIn;
   private Date dataCheckOut;
   private boolean status;
   private Hospede hospede;
   private Quarto quarto;
    
   
   public Reserva(int idReserva, Date dataCheckIn, Date dataCheckOut, boolean status, Hospede hospede, Quarto quarto ){
       this.idReserva = idReserva;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.status = status;
        this.hospede = hospede;
        this.quarto = quarto;
          
   }
   
   
   @Override
   public double calcularTotalPago(){
        // Aq teve q ser long pq o valor tava estorando o tamanho do int, pq tava em milisegundos
        long diff = dataCheckOut.getTime() - dataCheckIn.getTime();
        
        long dias = diff / (1000 * 60 * 60 * 24);
        return dias * quarto.getValorDiaria();
        
   }
   
   public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Date getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(Date dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Date getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(Date dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    // Convenção para boolean: usa 'is' em vez de 'get'
    public boolean isAtiva() {
        return status;
    }

    public void setAtiva(boolean status) {
        this.status = status;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
    
}
