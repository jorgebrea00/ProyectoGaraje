package model;

import java.time.LocalDateTime;

public class Sesion {
    private int id;
    private LocalDateTime inicioSesion;
    private LocalDateTime finSesion;
    private int idCliente;

    public Sesion(int id, int idCliente) {
        this.id = id;
        this.inicioSesion = LocalDateTime.now();
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(LocalDateTime inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public LocalDateTime getFinSesion() {
        return finSesion;
    }

    public void setFinSesion(LocalDateTime finSesion) {
        this.finSesion = finSesion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
