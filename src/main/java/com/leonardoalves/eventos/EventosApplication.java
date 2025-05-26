package com.leonardoalves.eventos;

import com.leonardoalves.eventos.controller.SistemaEventos;

import java.util.Scanner;

public class EventosApplication {
	public static void main(String[] args) {
		SistemaEventos sistema = new SistemaEventos();
		Scanner scanner = new Scanner(System.in);

		int opcao = -1;

		while (opcao != 0) {
			System.out.println("\n--- MENU ---");
			System.out.println("1 - Cadastrar usuário");
			System.out.println("2 - Listar usuários");
			System.out.println("3 - Cadastrar evento");
			System.out.println("4 - Listar eventos");
			System.out.println("5 - Participar de evento");
			System.out.println("6 - Cancelar participação");
			System.out.println("7 - Listar eventos do usuário");
			System.out.println("8 - Remover evento");
			System.out.println("0 - Sair");
			System.out.print("Opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
				case 1 -> {
					System.out.print("Nome: ");
					String nome = scanner.nextLine();
					System.out.print("Email: ");
					String email = scanner.nextLine();
					System.out.print("Cidade: ");
					String cidade = scanner.nextLine();
					sistema.cadastrarUsuario(nome, email, cidade);
				}

				case 2 -> sistema.listarUsuarios();

				case 3 -> {
					System.out.print("Nome do evento: ");
					String nomeEvento = scanner.nextLine();
					System.out.print("Endereço: ");
					String endereco = scanner.nextLine();
					System.out.print("Categoria: ");
					String categoria = scanner.nextLine();
					System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
					String dataHora = scanner.nextLine();
					System.out.print("Descrição: ");
					String descricao = scanner.nextLine();
					sistema.cadastrarEvento(nomeEvento, endereco, categoria, dataHora, descricao);
				}
				case 4 -> sistema.listarEventos();

				case 5 -> {
					sistema.listarEventos();
					System.out.print("Informe o índice do evento para participar: ");
					int idEvento = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Informe seu email: ");
					String email = scanner.nextLine();
					sistema.participarDeEvento(idEvento, email);
				}
				case 6 -> {
					sistema.listarEventos();
					System.out.print("Informe o índice do evento para cancelar participação: ");
					int idEvento = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Informe seu email: ");
					String email = scanner.nextLine();
					sistema.cancelarParticipacao(idEvento, email);
				}
				case 7 -> {
					System.out.print("Informe seu email: ");
					String email = scanner.nextLine();
					sistema.listarEventosDoUsuario(email);
				}
				case 8 -> {
					sistema.listarEventos();
					System.out.print("Informe o índice do evento a ser removido: ");
					int idEvento = scanner.nextInt();
					scanner.nextLine();
					sistema.removerEvento(idEvento);
				}

				case 0 -> System.out.println("Saindo...");
				default -> System.out.println("Opção inválida!");
			}
		}

		scanner.close();
	}
}
