package com.utn.dds.tpdds.model;

import java.util.Comparator;

public class RegistroDeCambioDeEstadoComparator implements Comparator<RegistroDeCambioDeEstadoDeDispositivo> {
    @Override
    public int compare(RegistroDeCambioDeEstadoDeDispositivo firstDate, RegistroDeCambioDeEstadoDeDispositivo secondDate) {
        return firstDate.getTimestamp().compareTo(secondDate.getTimestamp());
    }
}
