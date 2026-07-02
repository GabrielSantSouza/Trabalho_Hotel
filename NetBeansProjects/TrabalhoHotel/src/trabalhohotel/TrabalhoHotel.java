/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabalhohotel;

import controle.Sistema;
import excecoes.EntidadeNaoEncontradaException;
import excecoes.RegraNegocioException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import modelos.Hospede;
import modelos.Quarto;
import modelos.Reserva;

/**
 *
 * @author gabriel
 */
public class TrabalhoHotel {

    private static Sistema sistema = new Sistema();
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        sistema.init();
        
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Escolha uma opcao: ");
            
            switch (opcao) {
                case 1:
                    menuHospedes();
                    break;
                case 2:
                    menuQuartos();
                    break;
                case 3:
                    menuReservas();
                    break;
                case 4:
                    menuFuncionarios();
                    break;
                case 0:
                    System.out.println("\n=== Saindo do sistema... ===");
                    System.out.println("Obrigado por utilizar o Sistema de Hotel!");
                    break;
                default:
                    System.out.println("\n[ERRO] Opcao invalida! Tente novamente.");
            }
            
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n+--------------------------------------------------+");
        System.out.println("|       SISTEMA DE GERENCIAMENTO DE HOTEL          |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("|  1. Gerenciar Hospedes                           |");
        System.out.println("|  2. Gerenciar Quartos                            |");
        System.out.println("|  3. Gerenciar Reservas                           |");
        System.out.println("|  4. Listar Funcionarios                          |");
        System.out.println("|  0. Sair                                         |");
        System.out.println("+--------------------------------------------------+");
    }

    // ==================== VALIDACOES E FORMATACAO ====================
    
    private static boolean validarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        
        if (cpfLimpo.length() != 11) {
            return false;
        }
        
        boolean todosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpfLimpo.charAt(i) != cpfLimpo.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            return false;
        }
        
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpfLimpo.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }
        
        if ((cpfLimpo.charAt(9) - '0') != primeiroDigito) {
            return false;
        }
        
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpfLimpo.charAt(i) - '0') * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }
        
        if ((cpfLimpo.charAt(10) - '0') != segundoDigito) {
            return false;
        }
        
        return true;
    }
    
    private static String formatarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            return null;
        }
        return cpfLimpo.substring(0, 3) + "." + 
               cpfLimpo.substring(3, 6) + "." + 
               cpfLimpo.substring(6, 9) + "-" + 
               cpfLimpo.substring(9, 11);
    }
    
    private static boolean validarData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        
        try {
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    private static boolean validarDataNascimento(String dtNascimento) {
        if (!validarData(dtNascimento)) {
            return false;
        }
        
        try {
            sdf.setLenient(false);
            Date dataNascimento = sdf.parse(dtNascimento);
            Date dataAtual = new Date();
            
            if (dataNascimento.after(dataAtual)) {
                return false;
            }
            
            long diff = dataAtual.getTime() - dataNascimento.getTime();
            long idadeEmMilissegundos = 18L * 365 * 24 * 60 * 60 * 1000;
            
            if (diff < idadeEmMilissegundos) {
                return false;
            }
            
            return true;
            
        } catch (ParseException e) {
            return false;
        }
    }
    
    private static boolean validarPeriodoReserva(String checkIn, String checkOut) {
        if (!validarData(checkIn) || !validarData(checkOut)) {
            return false;
        }
        
        try {
            sdf.setLenient(false);
            Date dataCheckIn = sdf.parse(checkIn);
            Date dataCheckOut = sdf.parse(checkOut);
            
            return dataCheckIn.before(dataCheckOut);
            
        } catch (ParseException e) {
            return false;
        }
    }
    
    private static boolean validarCheckInFuturo(String checkIn) {
        if (!validarData(checkIn)) {
            return false;
        }
        
        try {
            sdf.setLenient(false);
            Date dataCheckIn = sdf.parse(checkIn);
            Date dataAtual = new Date();
            
            return dataCheckIn.after(dataAtual) || dataCheckIn.equals(dataAtual);
            
        } catch (ParseException e) {
            return false;
        }
    }

    // ==================== LEITURA COM FORMATACAO ====================
    
    private static String lerCPF(String mensagem) {
        System.out.print(mensagem);
        String cpf = scanner.nextLine();
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() == 11) {
            return formatarCPF(cpf);
        }
        return cpf;
    }
    
    private static String lerData(String mensagem) {
        System.out.print(mensagem);
        String data = scanner.nextLine();
        data = data.replaceAll("[^0-9]", "");
        
        if (data.length() == 8) {
            return data.substring(0, 2) + "/" + data.substring(2, 4) + "/" + data.substring(4, 8);
        }
        return data;
    }

    // ==================== MENU HOSPEDES ====================

    private static void menuHospedes() {
        int opcao;
        do {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("|         GERENCIAR HOSPEDES                      |");
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  1. Cadastrar Hospede                           |");
            System.out.println("|  2. Buscar Hospede por CPF                      |");
            System.out.println("|  3. Atualizar Hospede                           |");
            System.out.println("|  4. Remover Hospede                             |");
            System.out.println("|  5. Listar Todos os Hospedes                    |");
            System.out.println("|  0. Voltar                                      |");
            System.out.println("+--------------------------------------------------+");
            
            opcao = lerInteiro("Escolha uma opcao: ");
            
            switch (opcao) {
                case 1:
                    cadastrarHospede();
                    break;
                case 2:
                    buscarHospede();
                    break;
                case 3:
                    atualizarHospede();
                    break;
                case 4:
                    removerHospede();
                    break;
                case 5:
                    listarHospedes();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("\n[ERRO] Opcao invalida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarHospede() {
        System.out.println("\n--- CADASTRO DE HOSPEDE ---");
        try {
            int id = lerInteiro("ID do hospede: ");
            String nome = lerString("Nome: ");
            String cpf = lerCPF("CPF (Digite apenas os numeros): ");
            
            if (!validarCPF(cpf)) {
                System.out.println("\n[ERRO] CPF invalido! Digite 11 numeros.");
                return;
            }
            
            cpf = formatarCPF(cpf);
            System.out.println("CPF formatado: " + cpf);
            
            String dtNascimento = lerData("Data de Nascimento (Digite apenas os numeros): ");
            
            if (!validarDataNascimento(dtNascimento)) {
                System.out.println("\n[ERRO] Data de nascimento invalida! O hospede deve ter pelo menos 18 anos.");
                return;
            }
            
            System.out.println("Data formatada: " + dtNascimento);
            
            Hospede hospede = new Hospede(id, nome, cpf, dtNascimento);
            sistema.cadastrarHospede(hospede);
            System.out.println("\n[SUCESSO] Hospede cadastrado com sucesso!");
            
        } catch (RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[ERRO] Erro inesperado: " + e.getMessage());
        }
    }

    private static void buscarHospede() {
        System.out.println("\n--- BUSCAR HOSPEDE ---");
        try {
            String cpf = lerCPF("Informe o CPF do hospede (Digite apenas os numeros): ");
            
            if (!validarCPF(cpf)) {
                System.out.println("\n[ERRO] CPF invalido! Digite 11 numeros.");
                return;
            }
            
            cpf = formatarCPF(cpf);
            System.out.println("Buscando CPF: " + cpf);
            
            Hospede hospede = sistema.buscarHospede(cpf);
            
            System.out.println("\n[SUCESSO] Hospede encontrado:");
            System.out.println("ID: " + hospede.getId());
            System.out.println("Nome: " + hospede.getNome());
            System.out.println("CPF: " + hospede.getCPF());
            System.out.println("Data de Nascimento: " + hospede.getDtNascimento());
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void atualizarHospede() {
        System.out.println("\n--- ATUALIZAR HOSPEDE ---");
        try {
            String cpf = lerCPF("Informe o CPF do hospede (Digite apenas os numeros): ");
            
            if (!validarCPF(cpf)) {
                System.out.println("\n[ERRO] CPF invalido! Digite 11 numeros.");
                return;
            }
            
            cpf = formatarCPF(cpf);
            System.out.println("Buscando CPF: " + cpf);
            
            String novoNome = lerString("Novo nome: ");
            String novaDtNascimento = lerData("Nova data de nascimento (Digite apenas os numeros): ");
            
            if (!validarDataNascimento(novaDtNascimento)) {
                System.out.println("\n[ERRO] Data de nascimento invalida! O hospede deve ter pelo menos 18 anos.");
                return;
            }
            
            System.out.println("Data formatada: " + novaDtNascimento);
            
            sistema.atualizarHospede(cpf, novoNome, novaDtNascimento);
            System.out.println("\n[SUCESSO] Hospede atualizado com sucesso!");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void removerHospede() {
        System.out.println("\n--- REMOVER HOSPEDE ---");
        try {
            String cpf = lerCPF("Informe o CPF do hospede (Digite apenas os numeros): ");
            
            if (!validarCPF(cpf)) {
                System.out.println("\n[ERRO] CPF invalido! Digite 11 numeros.");
                return;
            }
            
            cpf = formatarCPF(cpf);
            System.out.println("Buscando CPF: " + cpf);
            
            System.out.print("Tem certeza que deseja remover este hospede? (S/N): ");
            String confirmacao = scanner.nextLine().toUpperCase();
            
            if (confirmacao.equals("S")) {
                sistema.removerHospede(cpf);
                System.out.println("\n[SUCESSO] Hospede removido com sucesso!");
            } else {
                System.out.println("\nOperacao cancelada.");
            }
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void listarHospedes() {
        System.out.println("\n--- LISTA DE HOSPEDES ---");
        try {
            List<Hospede> hospedes = sistema.listarHospedes();
            
            System.out.println("\nTotal de hospedes: " + hospedes.size());
            System.out.println("\n+-------+--------------------------------+-----------------+--------------+");
            System.out.printf("| %-5s | %-30s | %-15s | %-12s |%n", "ID", "NOME", "CPF", "NASCIMENTO");
            System.out.println("+-------+--------------------------------+-----------------+--------------+");
            
            for (Hospede h : hospedes) {
                System.out.printf("| %-5d | %-30s | %-15s | %-12s |%n", 
                    h.getId(), 
                    h.getNome().length() > 30 ? h.getNome().substring(0, 27) + "..." : h.getNome(),
                    h.getCPF(),
                    h.getDtNascimento()
                );
            }
            System.out.println("+-------+--------------------------------+-----------------+--------------+");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[INFO] " + e.getMessage());
        }
    }

    // ==================== MENU QUARTOS ====================

    private static void menuQuartos() {
        int opcao;
        do {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("|         GERENCIAR QUARTOS                        |");
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  1. Cadastrar Quarto                            |");
            System.out.println("|  2. Buscar Quarto por Numero                    |");
            System.out.println("|  3. Atualizar Quarto                            |");
            System.out.println("|  4. Remover Quarto                              |");
            System.out.println("|  5. Listar Todos os Quartos                     |");
            System.out.println("|  6. Listar Quartos por Status                   |");
            System.out.println("|  0. Voltar                                      |");
            System.out.println("+--------------------------------------------------+");
            
            opcao = lerInteiro("Escolha uma opcao: ");
            
            switch (opcao) {
                case 1:
                    cadastrarQuarto();
                    break;
                case 2:
                    buscarQuarto();
                    break;
                case 3:
                    atualizarQuarto();
                    break;
                case 4:
                    removerQuarto();
                    break;
                case 5:
                    listarQuartos();
                    break;
                case 6:
                    listarQuartosPorStatus();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("\n[ERRO] Opcao invalida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarQuarto() {
        System.out.println("\n--- CADASTRO DE QUARTO ---");
        try {
            int numero = lerInteiro("Numero do quarto: ");
            int capacidade = lerInteiro("Capacidade (quantidade de pessoas): ");
            double valorDiaria = lerDouble("Valor da diaria (R$): ");
            
            Quarto quarto = new Quarto(numero, capacidade, valorDiaria);
            sistema.cadastrarQuarto(quarto);
            System.out.println("\n[SUCESSO] Quarto cadastrado com sucesso!");
            
        } catch (RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void buscarQuarto() {
        System.out.println("\n--- BUSCAR QUARTO ---");
        try {
            int numero = lerInteiro("Informe o numero do quarto: ");
            Quarto quarto = sistema.buscarQuarto(numero);
            
            System.out.println("\n[SUCESSO] Quarto encontrado:");
            System.out.println("Numero: " + quarto.getNumero());
            System.out.println("Capacidade: " + quarto.getCapacidade() + " pessoas");
            System.out.println("Valor da diaria: R$ " + String.format("%.2f", quarto.getValorDiaria()));
            System.out.println("Status: " + (quarto.isOcupado() ? "OCUPADO" : "DISPONIVEL"));
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void atualizarQuarto() {
        System.out.println("\n--- ATUALIZAR QUARTO ---");
        try {
            int numero = lerInteiro("Informe o numero do quarto: ");
            int novaCapacidade = lerInteiro("Nova capacidade: ");
            double novoValorDiaria = lerDouble("Novo valor da diaria (R$): ");
            
            sistema.atualizarQuarto(numero, novaCapacidade, novoValorDiaria);
            System.out.println("\n[SUCESSO] Quarto atualizado com sucesso!");
            
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void removerQuarto() {
        System.out.println("\n--- REMOVER QUARTO ---");
        try {
            int numero = lerInteiro("Informe o numero do quarto: ");
            
            System.out.print("Tem certeza que deseja remover este quarto? (S/N): ");
            String confirmacao = scanner.nextLine().toUpperCase();
            
            if (confirmacao.equals("S")) {
                sistema.removerQuarto(numero);
                System.out.println("\n[SUCESSO] Quarto removido com sucesso!");
            } else {
                System.out.println("\nOperacao cancelada.");
            }
            
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void listarQuartos() {
        System.out.println("\n--- LISTA DE QUARTOS ---");
        try {
            List<Quarto> quartos = sistema.listarQuartos();
            
            System.out.println("\nTotal de quartos: " + quartos.size());
            System.out.println("\n+----------+--------------+-----------------+--------------+");
            System.out.printf("| %-8s | %-12s | %-15s | %-12s |%n", "NUMERO", "CAPACIDADE", "DIARIA (R$)", "STATUS");
            System.out.println("+----------+--------------+-----------------+--------------+");
            
            for (Quarto q : quartos) {
                System.out.printf("| %-8d | %-12d | %-15.2f | %-12s |%n", 
                    q.getNumero(),
                    q.getCapacidade(),
                    q.getValorDiaria(),
                    q.isOcupado() ? "OCUPADO" : "DISPONIVEL"
                );
            }
            System.out.println("+----------+--------------+-----------------+--------------+");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[INFO] " + e.getMessage());
        }
    }

    private static void listarQuartosPorStatus() {
        System.out.println("\n--- LISTAR QUARTOS POR STATUS ---");
        try {
            System.out.println("1. Disponiveis");
            System.out.println("2. Ocupados");
            int opcao = lerInteiro("Escolha o status: ");
            
            boolean ocupado = (opcao == 2);
            List<Quarto> quartos = sistema.listarQuartosPorStatus(ocupado);
            
            System.out.println("\nTotal de quartos " + (ocupado ? "OCUPADOS" : "DISPONIVEIS") + ": " + quartos.size());
            System.out.println("\n+----------+--------------+-----------------+");
            System.out.printf("| %-8s | %-12s | %-15s |%n", "NUMERO", "CAPACIDADE", "DIARIA (R$)");
            System.out.println("+----------+--------------+-----------------+");
            
            for (Quarto q : quartos) {
                System.out.printf("| %-8d | %-12d | %-15.2f |%n", 
                    q.getNumero(),
                    q.getCapacidade(),
                    q.getValorDiaria()
                );
            }
            System.out.println("+----------+--------------+-----------------+");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[INFO] " + e.getMessage());
        }
    }

    // ==================== MENU RESERVAS ====================

    private static void menuReservas() {
        int opcao;
        do {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("|         GERENCIAR RESERVAS                       |");
            System.out.println("+--------------------------------------------------+");
            System.out.println("|  1. Realizar Check-in                           |");
            System.out.println("|  2. Realizar Check-out                          |");
            System.out.println("|  3. Buscar Reserva por ID                       |");
            System.out.println("|  4. Atualizar Reserva                           |");
            System.out.println("|  5. Remover Reserva                             |");
            System.out.println("|  6. Listar Todas as Reservas                    |");
            System.out.println("|  7. Listar Reservas por Status                  |");
            System.out.println("|  0. Voltar                                      |");
            System.out.println("+--------------------------------------------------+");
            
            opcao = lerInteiro("Escolha uma opcao: ");
            
            switch (opcao) {
                case 1:
                    realizarCheckin();
                    break;
                case 2:
                    realizarCheckout();
                    break;
                case 3:
                    buscarReserva();
                    break;
                case 4:
                    atualizarReserva();
                    break;
                case 5:
                    removerReserva();
                    break;
                case 6:
                    listarTodasReservas();
                    break;
                case 7:
                    listarReservasPorStatus();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("\n[ERRO] Opcao invalida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void realizarCheckin() {
        System.out.println("\n--- REALIZAR CHECK-IN ---");
        try {
            String cpf = lerCPF("CPF do hospede (Digite apenas os numeros): ");
            
            if (!validarCPF(cpf)) {
                System.out.println("\n[ERRO] CPF invalido! Digite 11 numeros.");
                return;
            }
            
            cpf = formatarCPF(cpf);
            System.out.println("CPF formatado: " + cpf);
            
            int numQuarto = lerInteiro("Numero do quarto: ");
            
            String checkInStr = lerData("Data de Check-in (Digite apenas os numeros): ");
            String checkOutStr = lerData("Data de Check-out (Digite apenas os numeros): ");
            
            if (!validarPeriodoReserva(checkInStr, checkOutStr)) {
                System.out.println("\n[ERRO] Periodo de reserva invalido! Check-in deve ser anterior ao check-out.");
                return;
            }
            
            if (!validarCheckInFuturo(checkInStr)) {
                System.out.println("\n[ERRO] Data de check-in deve ser hoje ou no futuro.");
                return;
            }
            
            System.out.println("Check-in formatado: " + checkInStr);
            System.out.println("Check-out formatado: " + checkOutStr);
            
            Date checkIn = sdf.parse(checkInStr);
            Date checkOut = sdf.parse(checkOutStr);
            
            sistema.realizarCheckin(cpf, numQuarto, checkIn, checkOut);
            System.out.println("\n[SUCESSO] Check-in realizado com sucesso!");
            
        } catch (ParseException e) {
            System.out.println("\n[ERRO] Formato de data invalido! Use dd/MM/yyyy");
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void realizarCheckout() {
        System.out.println("\n--- REALIZAR CHECK-OUT ---");
        try {
            int idReserva = lerInteiro("ID da reserva: ");
            
            double totalPago = sistema.realizarCheckOut(idReserva);
            
            System.out.println("\n[SUCESSO] Check-out realizado com sucesso!");
            System.out.println("[VALOR] Total a pagar: R$ " + String.format("%.2f", totalPago));
            
            Reserva reserva = sistema.buscarReserva(idReserva);
            System.out.println("\n--- DETALHES DA RESERVA ---");
            System.out.println("Hospede: " + reserva.getHospede().getNome());
            System.out.println("Quarto: " + reserva.getQuarto().getNumero());
            System.out.println("Check-in: " + sdf.format(reserva.getDataCheckIn()));
            System.out.println("Check-out: " + sdf.format(reserva.getDataCheckOut()));
            
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void buscarReserva() {
        System.out.println("\n--- BUSCAR RESERVA ---");
        try {
            int idReserva = lerInteiro("Informe o ID da reserva: ");
            Reserva reserva = sistema.buscarReserva(idReserva);
            
            System.out.println("\n[SUCESSO] Reserva encontrada:");
            System.out.println("ID: " + reserva.getIdReserva());
            System.out.println("Hospede: " + reserva.getHospede().getNome());
            System.out.println("CPF: " + reserva.getHospede().getCPF());
            System.out.println("Quarto: " + reserva.getQuarto().getNumero());
            System.out.println("Check-in: " + sdf.format(reserva.getDataCheckIn()));
            System.out.println("Check-out: " + sdf.format(reserva.getDataCheckOut()));
            System.out.println("Status: " + (reserva.isAtiva() ? "ATIVA" : "FINALIZADA"));
            System.out.println("Valor total: R$ " + String.format("%.2f", reserva.calcularTotalPago()));
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void atualizarReserva() {
        System.out.println("\n--- ATUALIZAR RESERVA ---");
        try {
            int idReserva = lerInteiro("ID da reserva: ");
            
            String checkInStr = lerData("Nova data de Check-in (Digite apenas os numeros): ");
            String checkOutStr = lerData("Nova data de Check-out (Digite apenas os numeros): ");
            
            if (!validarPeriodoReserva(checkInStr, checkOutStr)) {
                System.out.println("\n[ERRO] Periodo de reserva invalido! Check-in deve ser anterior ao check-out.");
                return;
            }
            
            System.out.println("Check-in formatado: " + checkInStr);
            System.out.println("Check-out formatado: " + checkOutStr);
            
            Date novoCheckIn = sdf.parse(checkInStr);
            Date novoCheckOut = sdf.parse(checkOutStr);
            
            int novoQuarto = lerInteiro("Novo numero do quarto: ");
            
            sistema.atualizarReserva(idReserva, novoCheckIn, novoCheckOut, novoQuarto);
            System.out.println("\n[SUCESSO] Reserva atualizada com sucesso!");
            
        } catch (ParseException e) {
            System.out.println("\n[ERRO] Formato de data invalido! Use dd/MM/yyyy");
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void removerReserva() {
        System.out.println("\n--- REMOVER RESERVA ---");
        try {
            int idReserva = lerInteiro("ID da reserva: ");
            
            System.out.print("Tem certeza que deseja remover esta reserva? (S/N): ");
            String confirmacao = scanner.nextLine().toUpperCase();
            
            if (confirmacao.equals("S")) {
                sistema.removerReserva(idReserva);
                System.out.println("\n[SUCESSO] Reserva removida com sucesso!");
            } else {
                System.out.println("\nOperacao cancelada.");
            }
            
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private static void listarTodasReservas() {
        System.out.println("\n--- LISTA DE TODAS AS RESERVAS ---");
        try {
            List<Reserva> reservas = sistema.listarTodasReservas();
            
            System.out.println("\nTotal de reservas: " + reservas.size());
            System.out.println("\n+-------+---------------------------+----------+--------------+--------------+------------+");
            System.out.printf("| %-5s | %-25s | %-8s | %-12s | %-12s | %-10s |%n", 
                "ID", "HOSPEDE", "QUARTO", "CHECK-IN", "CHECK-OUT", "STATUS");
            System.out.println("+-------+---------------------------+----------+--------------+--------------+------------+");
            
            for (Reserva r : reservas) {
                String nomeHospede = r.getHospede().getNome();
                if (nomeHospede.length() > 25) {
                    nomeHospede = nomeHospede.substring(0, 22) + "...";
                }
                
                System.out.printf("| %-5d | %-25s | %-8d | %-12s | %-12s | %-10s |%n", 
                    r.getIdReserva(),
                    nomeHospede,
                    r.getQuarto().getNumero(),
                    sdf.format(r.getDataCheckIn()),
                    sdf.format(r.getDataCheckOut()),
                    r.isAtiva() ? "ATIVA" : "FINALIZADA"
                );
            }
            System.out.println("+-------+---------------------------+----------+--------------+--------------+------------+");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[INFO] " + e.getMessage());
        }
    }

    private static void listarReservasPorStatus() {
        System.out.println("\n--- LISTAR RESERVAS POR STATUS ---");
        try {
            System.out.println("1. Ativas");
            System.out.println("2. Finalizadas");
            int opcao = lerInteiro("Escolha o status: ");
            
            boolean status = (opcao == 1);
            List<Reserva> reservas = sistema.listarReservasPorStatus(status);
            
            System.out.println("\nTotal de reservas " + (status ? "ATIVAS" : "FINALIZADAS") + ": " + reservas.size());
            System.out.println("\n+-------+---------------------------+----------+--------------+--------------+");
            System.out.printf("| %-5s | %-25s | %-8s | %-12s | %-12s |%n", 
                "ID", "HOSPEDE", "QUARTO", "CHECK-IN", "CHECK-OUT");
            System.out.println("+-------+---------------------------+----------+--------------+--------------+");
            
            for (Reserva r : reservas) {
                String nomeHospede = r.getHospede().getNome();
                if (nomeHospede.length() > 25) {
                    nomeHospede = nomeHospede.substring(0, 22) + "...";
                }
                
                System.out.printf("| %-5d | %-25s | %-8d | %-12s | %-12s |%n", 
                    r.getIdReserva(),
                    nomeHospede,
                    r.getQuarto().getNumero(),
                    sdf.format(r.getDataCheckIn()),
                    sdf.format(r.getDataCheckOut())
                );
            }
            System.out.println("+-------+---------------------------+----------+--------------+--------------+");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[INFO] " + e.getMessage());
        }
    }

    // ==================== MENU FUNCIONARIOS ====================

    private static void menuFuncionarios() {
        System.out.println("\n--- LISTA DE FUNCIONARIOS ---");
        try {
            System.out.println("\n+-------+--------------------------------+-----------------+-----------------+");
            System.out.printf("| %-5s | %-30s | %-15s | %-15s |%n", "ID", "NOME", "CPF", "CARGO");
            System.out.println("+-------+--------------------------------+-----------------+-----------------+");
            
            System.out.printf("| %-5d | %-30s | %-15s | %-15s |%n", 1, "Marcos da Silva", "111.222.333-44", "Recepcao");
            System.out.printf("| %-5d | %-30s | %-15s | %-15s |%n", 2, "Maria", "999.888.777-66", "Gerente");
            System.out.println("+-------+--------------------------------+-----------------+-----------------+");
            
            System.out.println("\n[INFO] Funcionarios cadastrados no sistema.");
            
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            
        } catch (Exception e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    // ==================== METODOS AUXILIARES ====================

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um numero valido!");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um valor valido (use . ou , para decimais)!");
            }
        }
    }
}
