package com.utn.dds.tpdds.model;

public enum AccionesPosibles {
    APAGAR {
        @Override
        public void apply(Driver driver) {
            driver.apagar();
        }
    },
    PRENDER {
        @Override
        public void apply(Driver driver) {
            driver.encender();
        }
    },
    MODO_AHORRO {
        @Override
        public void apply(Driver driver) {
            driver.cambiarAModoAhorroDeEnergia();
        }
    },
    NADA {
        @Override
        public void apply(Driver driver) {

        }
    };

    public abstract void apply(Driver driver);
}
