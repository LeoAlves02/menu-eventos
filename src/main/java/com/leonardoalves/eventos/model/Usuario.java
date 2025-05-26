package com.leonardoalves.eventos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private String nome;
    private String email;
    private String cidade;
    private List<Integer> eventosParticipando;

    public Usuario(String nome, String email, String cidade) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.eventosParticipando = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCidade() {
        return cidade;
    }

    public List<Integer> getEventosParticipando() {
        return eventosParticipando;
    }

    public void participarEvento(int idEvento) {
        if (!eventosParticipando.contains(idEvento)) {
            eventosParticipando.add(idEvento);
        }
    }

    public void cancelarParticipacao(int idEvento) {
        eventosParticipando.remove(Integer.valueOf(idEvento));
    }
}
