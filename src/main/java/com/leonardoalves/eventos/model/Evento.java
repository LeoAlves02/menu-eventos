package com.leonardoalves.eventos.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Serializable {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime dataHora;
    private String descricao;
    private List<String> participantes;

    public Evento(String nome, String endereco, String categoria, String dataHora, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.descricao = descricao;
        this.participantes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.dataHora = LocalDateTime.parse(dataHora, formatter);
    }

    public void adicionarParticipante(String email) {
        if (!participantes.contains(email)) {
            participantes.add(email);
        }
    }

    public void removerParticipante(String email) {
        participantes.remove(email);
    }

    public boolean contemParticipante(String email) {
        return participantes.contains(email);
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public String resumoEvento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return nome + " - " + categoria + " - " + dataHora.format(formatter);
    }

    public String detalhesEvento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Nome: " + nome + "\nEndereço: " + endereco + "\nCategoria: " + categoria + "\nData: " + dataHora.format(formatter) + "\nDescrição: " + descricao;
    }

    public void atualizar(String nome, String endereco, String categoria, String dataHora, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.descricao = descricao;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.dataHora = LocalDateTime.parse(dataHora, formatter);
    }
}
