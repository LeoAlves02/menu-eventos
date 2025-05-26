package com.leonardoalves.eventos.model;

import java.io.Serializable;
import java.util.Objects;

public class Participacao implements Serializable {
    private String emailUsuario;
    private int idEvento;

    public Participacao(String emailUsuario, int idEvento) {
        this.emailUsuario = emailUsuario;
        this.idEvento = idEvento;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public int getIdEvento() {
        return idEvento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participacao)) return false;
        Participacao that = (Participacao) o;
        return idEvento == that.idEvento && emailUsuario.equalsIgnoreCase(that.emailUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailUsuario.toLowerCase(), idEvento);
    }
}
