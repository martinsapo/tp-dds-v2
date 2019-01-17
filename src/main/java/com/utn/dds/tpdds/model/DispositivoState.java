package com.utn.dds.tpdds.model;

public interface DispositivoState{
    void encender(DispositivoInteligente dispositivo);
    void apagar(DispositivoInteligente dispositivo);
    void modoAhorro(DispositivoInteligente dispositivo);
    EstadoDeDispositivo getEstado();
}
