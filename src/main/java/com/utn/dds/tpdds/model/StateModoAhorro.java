package com.utn.dds.tpdds.model;

import java.io.Serializable;

public class StateModoAhorro implements DispositivoState, Serializable {

    private EstadoDeDispositivo estadoActual = EstadoDeDispositivo.MODO_AHORRO;

    @Override
    public void encender(DispositivoInteligente dispositivo) {
        dispositivo.agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.PRENDIDO);
        dispositivo.setState(new StateEncendido());
    }

    @Override
    public void apagar(DispositivoInteligente dispositivo) {
        dispositivo.setState(new StateApagado());
        dispositivo.agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.APAGADO);
    }

    @Override
    public void modoAhorro(DispositivoInteligente dispositivo) {
        System.out.println("Ya se encuentra ahorro de energia");
    }

    @Override
    public EstadoDeDispositivo getEstado() {
        return estadoActual;
    }
}
