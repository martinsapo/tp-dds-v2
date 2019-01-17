package com.utn.dds.tpdds.model;

public class NoClienteResidencial extends ClienteResidencial {
    @Override
    public Boolean passwordMatch(String password) {
        return false;
    }
}
