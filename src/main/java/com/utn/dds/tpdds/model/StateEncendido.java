package com.utn.dds.tpdds.model;

import java.io.Serializable;

public class StateEncendido implements DispositivoState, Serializable {

    private EstadoDeDispositivo estadoActual = EstadoDeDispositivo.PRENDIDO;

    @Override
    public void encender(DispositivoInteligente dispositivo) {
        System.out.println("Ya se encuentra encendido");
    }

    @Override
    public void apagar(DispositivoInteligente dispositivo) {
        dispositivo.setState(new StateApagado());
        dispositivo.agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.APAGADO);
    }

    @Override
    public void modoAhorro(DispositivoInteligente dispositivo) {
        dispositivo.setState(new StateModoAhorro());
        dispositivo.agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.MODO_AHORRO);
    }

    @Override
    public EstadoDeDispositivo getEstado() {
        return estadoActual;
    }
}
