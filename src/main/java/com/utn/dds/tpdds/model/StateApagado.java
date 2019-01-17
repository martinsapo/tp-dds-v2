package com.utn.dds.tpdds.model;

import java.io.Serializable;

public class StateApagado implements DispositivoState, Serializable{

    private EstadoDeDispositivo estadoActual = EstadoDeDispositivo.APAGADO;

    @Override
    public void encender(DispositivoInteligente dispositivo) {
        dispositivo.agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.PRENDIDO);
        dispositivo.setState(new StateEncendido());
    }

    @Override
    public void apagar(DispositivoInteligente dispositivo) {
        System.out.println("Ya se encuentra Apagado");
    }

    @Override
    public void modoAhorro(DispositivoInteligente dispositivo) {
        dispositivo.agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.MODO_AHORRO);
        dispositivo.setState(new StateModoAhorro());
    }

    @Override
    public EstadoDeDispositivo getEstado() {
        return estadoActual;
    }


}
