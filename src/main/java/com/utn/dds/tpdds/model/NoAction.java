package com.utn.dds.tpdds.model;

class NoAction extends Accion{
    void apply(DispositivoInteligente dispositivoInteligente){
    }

    public NoAction() {
        super(AccionesPosibles.NADA);
    }
}
