package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.dtos;

public class ErrorMessage {
    private String mensaje;

    public ErrorMessage(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}