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
import modelos.Funcionario;
import modelos.Hospede;
import modelos.Quarto;
import modelos.Reserva;

/**
 * Classe principal do sistema de gerenciamento de hotel
 * Contém o menu interativo e toda a interface com o usuário
 * 
 * @author gabriel
 */
public class TrabalhoHotel {

    // ==================== ATRIBUTOS ESTÁTICOS ====================
    
    /** Instância do sistema que gerencia os dados */
    private static Sistema sistema = new Sistema();
    
    /** Scanner para leitura de dados do usuário */
    private static Scanner scanner = new Scanner(System.in);
    
    /** Formato de data utilizado em todo o sistema (dd/MM/yyyy) */
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // ==================== MÉTODO PRINCIPAL ====================
    
    /**
     * Método principal - ponto de entrada do sistema
     * Inicializa o sistema e exibe o menu principal
     */
    public static void main(String[] args) {
        // Inicializar o sistema com dados padrão
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

    // ==================== MENU PRINCIPAL ====================
    
    /**
     * Exibe o menu principal do sistema
     */
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

    // ==================== MENU HOSPEDES ====================
    
    /**
     * Exibe o menu de gerenciamento de hospedes
     */
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

    /**
     * Cadastra um novo hospede no sistema
     */
    private static void cadastrarHospede() {
        System.out.println("\n--- CADASTRO DE HOSPEDE ---");
        try {
            int id = lerInteiro("ID do hospede: ");
            String nome = lerString("Nome: ");
            String cpf = lerString("CPF (formato: 000.000.000-00): ");
            String dtNascimento = lerString("Data de Nascimento (formato: dd/MM/yyyy): ");
            
            Hospede hospede = new Hospede(id, nome, cpf, dtNascimento);
            sistema.cadastrarHospede(hospede);
            System.out.println("\n[SUCESSO] Hospede cadastrado com sucesso!");
            
        } catch (RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[ERRO] Erro inesperado: " + e.getMessage());
        }
    }

    /**
     * Busca e exibe um hospede pelo CPF
     */
    private static void buscarHospede() {
        System.out.println("\n--- BUSCAR HOSPEDE ---");
        try {
            String cpf = lerString("Informe o CPF do hospede: ");
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

    /**
     * Atualiza os dados de um hospede existente
     */
    private static void atualizarHospede() {
        System.out.println("\n--- ATUALIZAR HOSPEDE ---");
        try {
            String cpf = lerString("Informe o CPF do hospede: ");
            String novoNome = lerString("Novo nome: ");
            String novaDtNascimento = lerString("Nova data de nascimento (dd/MM/yyyy): ");
            
            sistema.atualizarHospede(cpf, novoNome, novaDtNascimento);
            System.out.println("\n[SUCESSO] Hospede atualizado com sucesso!");
            
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    /**
     * Remove um hospede do sistema (com confirmacao)
     */
    private static void removerHospede() {
        System.out.println("\n--- REMOVER HOSPEDE ---");
        try {
            String cpf = lerString("Informe o CPF do hospede: ");
            
            // Confirmar remocao
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

    /**
     * Lista todos os hospedes cadastrados no sistema
     */
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
    
    /**
     * Exibe o menu de gerenciamento de quartos
     */
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

    /**
     * Cadastra um novo quarto no sistema
     */
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

    /**
     * Busca e exibe um quarto pelo numero
     */
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

    /**
     * Atualiza os dados de um quarto existente
     */
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

    /**
     * Remove um quarto do sistema (com confirmacao)
     */
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

    /**
     * Lista todos os quartos cadastrados no sistema
     */
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

    /**
     * Lista quartos filtrados por status (disponivel ou ocupado)
     */
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
    
    /**
     * Exibe o menu de gerenciamento de reservas
     */
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

    /**
     * Realiza o check-in de um hospede em um quarto
     */
    private static void realizarCheckin() {
        System.out.println("\n--- REALIZAR CHECK-IN ---");
        try {
            String cpf = lerString("CPF do hospede: ");
            int numQuarto = lerInteiro("Numero do quarto: ");
            
            System.out.print("Data de Check-in (dd/MM/yyyy): ");
            Date checkIn = sdf.parse(scanner.nextLine());
            
            System.out.print("Data de Check-out (dd/MM/yyyy): ");
            Date checkOut = sdf.parse(scanner.nextLine());
            
            sistema.realizarCheckin(cpf, numQuarto, checkIn, checkOut);
            System.out.println("\n[SUCESSO] Check-in realizado com sucesso!");
            
        } catch (ParseException e) {
            System.out.println("\n[ERRO] Formato de data invalido! Use dd/MM/yyyy");
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    /**
     * Realiza o check-out de uma reserva e calcula o total a pagar
     */
    private static void realizarCheckout() {
        System.out.println("\n--- REALIZAR CHECK-OUT ---");
        try {
            int idReserva = lerInteiro("ID da reserva: ");
            
            double totalPago = sistema.realizarCheckOut(idReserva);
            
            System.out.println("\n[SUCESSO] Check-out realizado com sucesso!");
            System.out.println("[VALOR] Total a pagar: R$ " + String.format("%.2f", totalPago));
            
            // Exibir detalhes da reserva finalizada
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

    /**
     * Busca e exibe uma reserva pelo ID
     */
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

    /**
     * Atualiza os dados de uma reserva existente
     */
    private static void atualizarReserva() {
        System.out.println("\n--- ATUALIZAR RESERVA ---");
        try {
            int idReserva = lerInteiro("ID da reserva: ");
            
            System.out.print("Nova data de Check-in (dd/MM/yyyy): ");
            Date novoCheckIn = sdf.parse(scanner.nextLine());
            
            System.out.print("Nova data de Check-out (dd/MM/yyyy): ");
            Date novoCheckOut = sdf.parse(scanner.nextLine());
            
            int novoQuarto = lerInteiro("Novo numero do quarto: ");
            
            sistema.atualizarReserva(idReserva, novoCheckIn, novoCheckOut, novoQuarto);
            System.out.println("\n[SUCESSO] Reserva atualizada com sucesso!");
            
        } catch (ParseException e) {
            System.out.println("\n[ERRO] Formato de data invalido! Use dd/MM/yyyy");
        } catch (EntidadeNaoEncontradaException | RegraNegocioException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    /**
     * Remove uma reserva do sistema (com confirmacao)
     */
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

    /**
     * Lista todas as reservas cadastradas no sistema
     */
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

    /**
     * Lista reservas filtradas por status (ativa ou finalizada)
     */
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
    
    /**
     * Exibe a lista de funcionarios cadastrados no sistema
     * Nota: Os funcionarios sao cadastrados apenas no init() e nao possuem CRUD completo
     */
    private static void menuFuncionarios() {
        System.out.println("\n--- LISTA DE FUNCIONARIOS ---");
        try {
            System.out.println("\n+-------+--------------------------------+-----------------+-----------------+");
            System.out.printf("| %-5s | %-30s | %-15s | %-15s |%n", "ID", "NOME", "CPF", "CARGO");
            System.out.println("+-------+--------------------------------+-----------------+-----------------+");
            
            /*
             * Nota: Os funcionarios sao cadastrados diretamente no metodo init() da classe Sistema
             * Como nao foi implementado um CRUD completo para funcionarios, exibimos os dados
             * que estao cadastrados no sistema.
             * 
             * Funcionarios cadastrados atualmente:
             * - ID: 1 | Marcos da Silva | 111.222.333-44 | Recepcao
             * - ID: 2 | Maria | 999.888.777-66 | Gerente
             */
            System.out.printf("| %-5d | %-30s | %-15s | %-15s |%n", 1, "Marcos da Silva", "111.222.333-44", "Recepcao");
            System.out.printf("| %-5d | %-30s | %-15s | %-15s |%n", 2, "Maria", "999.888.777-66", "Gerente");
            System.out.println("+-------+--------------------------------+-----------------+-----------------+");
            
            System.out.println("\n[INFO] Para listar funcionarios dinamicamente, seria necessario");
            System.out.println("       implementar o metodo listarFuncionarios() no Sistema.");
            
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            
        } catch (Exception e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    // ==================== METODOS AUXILIARES ====================
    
    /**
     * Le uma string do usuario
     * 
     * @param mensagem Mensagem a ser exibida antes da leitura
     * @return String lida do usuario
     */
    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    /**
     * Le um numero inteiro do usuario com validacao
     * 
     * @param mensagem Mensagem a ser exibida antes da leitura
     * @return Inteiro lido do usuario
     */
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

    /**
     * Le um numero double do usuario com validacao
     * Aceita tanto ponto (.) quanto virgula (,) como separador decimal
     * 
     * @param mensagem Mensagem a ser exibida antes da leitura
     * @return Double lido do usuario
     */
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
