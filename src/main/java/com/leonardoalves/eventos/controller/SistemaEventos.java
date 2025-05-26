package com.leonardoalves.eventos.controller;

import com.leonardoalves.eventos.model.Evento;
import com.leonardoalves.eventos.model.Usuario;
import com.leonardoalves.eventos.model.Participacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaEventos {
    private List<Usuario> usuarios;
    private List<Evento> eventos;
    private List<Participacao> participacoes;

    private final String ARQ_USUARIOS = "usuarios.data";
    private final String ARQ_EVENTOS = "eventos.data";
    private final String ARQ_PARTICIPACOES = "participacoes.data";

    public SistemaEventos() {
        this.usuarios = carregarUsuarios();
        this.eventos = carregarEventos();
        this.participacoes = carregarParticipacoes();
    }

    public void cadastrarUsuario(String nome, String email, String cidade) {
        usuarios.add(new Usuario(nome, email, cidade));
        salvarUsuarios();
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void cadastrarEvento(String nome, String endereco, String categoria, String dataHora, String descricao) {
        eventos.add(new Evento(nome, endereco, categoria, dataHora, descricao));
        salvarEventos();
        System.out.println("Evento cadastrado com sucesso!");
    }

    public void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }
        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.println("[" + i + "] " + e.resumoEvento());
            System.out.println("Descrição: " + e.getDescricao());
            System.out.println();
        }
    }

    public void participarDeEvento(int indexEvento, String email) {
        if (!validarIndexEvento(indexEvento)) return;
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        Evento evento = eventos.get(indexEvento);

        // Evitar duplicidade na lista de participantes e participacoes
        if (!evento.contemParticipante(email)) {
            evento.adicionarParticipante(email);
            salvarEventos();
        }

        Participacao participacao = new Participacao(email, indexEvento);
        if (!participacoes.contains(participacao)) {
            participacoes.add(participacao);
            salvarParticipacoes();
        }

        usuario.participarEvento(indexEvento);
        salvarUsuarios();

        System.out.println("Participação confirmada!");
    }

    public void cancelarParticipacao(int indexEvento, String email) {
        if (!validarIndexEvento(indexEvento)) return;
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        Evento evento = eventos.get(indexEvento);

        evento.removerParticipante(email);
        salvarEventos();

        Participacao participacao = new Participacao(email, indexEvento);
        participacoes.remove(participacao);
        salvarParticipacoes();

        usuario.cancelarParticipacao(indexEvento);
        salvarUsuarios();

        System.out.println("Participação cancelada.");
    }

    public void listarEventosDoUsuario(String email) {
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        List<Integer> participando = usuario.getEventosParticipando();
        if (participando.isEmpty()) {
            System.out.println("Você não está participando de nenhum evento.");
            return;
        }
        System.out.println("Seus eventos:");
        for (int i : participando) {
            if (i >= 0 && i < eventos.size()) {
                System.out.println(eventos.get(i).resumoEvento());
            }
        }
    }

    public void removerEvento(int indexEvento) {
        if (!validarIndexEvento(indexEvento)) return;

        // Remover evento
        eventos.remove(indexEvento);
        salvarEventos();

        // Atualizar participações relacionadas
        participacoes.removeIf(p -> p.getIdEvento() == indexEvento);
        salvarParticipacoes();

        // Atualizar eventosParticipando de cada usuário (ajustar índices)
        for (Usuario u : usuarios) {
            // Remove participação no evento removido
            u.cancelarParticipacao(indexEvento);
            // Ajusta índices maiores que o removido
            List<Integer> novosEventos = new ArrayList<>();
            for (int e : u.getEventosParticipando()) {
                if (e > indexEvento) {
                    novosEventos.add(e - 1);
                } else {
                    novosEventos.add(e);
                }
            }
            u.getEventosParticipando().clear();
            u.getEventosParticipando().addAll(novosEventos);
        }
        salvarUsuarios();

        System.out.println("Evento removido com sucesso.");
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("Lista de usuários:");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            System.out.println("[" + i + "] Nome: " + u.getNome() + ", Email: " + u.getEmail() + ", Cidade: " + u.getCidade());
        }
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    private boolean validarIndexEvento(int index) {
        if (index < 0 || index >= eventos.size()) {
            System.out.println("Evento não encontrado.");
            return false;
        }
        return true;
    }

    // --- Persistência dos dados ---

    @SuppressWarnings("unchecked")
    private List<Usuario> carregarUsuarios() {
        return (List<Usuario>) carregarArquivo(ARQ_USUARIOS);
    }

    @SuppressWarnings("unchecked")
    private List<Evento> carregarEventos() {
        return (List<Evento>) carregarArquivo(ARQ_EVENTOS);
    }

    @SuppressWarnings("unchecked")
    private List<Participacao> carregarParticipacoes() {
        return (List<Participacao>) carregarArquivo(ARQ_PARTICIPACOES);
    }

    private Object carregarArquivo(String nomeArquivo) {
        File file = new File(nomeArquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo " + nomeArquivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarUsuarios() {
        salvarArquivo(usuarios, ARQ_USUARIOS);
    }

    private void salvarEventos() {
        salvarArquivo(eventos, ARQ_EVENTOS);
    }

    private void salvarParticipacoes() {
        salvarArquivo(participacoes, ARQ_PARTICIPACOES);
    }

    private void salvarArquivo(Object obj, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
