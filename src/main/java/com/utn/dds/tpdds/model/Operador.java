package com.utn.dds.tpdds.model;

public enum Operador {
    FALSE {
        @Override
        public Boolean apply(Object x1, Object x2) {
            return !x1.equals(x2);
        }
    },
    TRUE {
        @Override
        public Boolean apply(Object x1, Object x2) {
            return x1.equals(x2);
        }
    },
    MAYOR {
        @Override
        public Boolean apply(Object x1, Object x2) {
            return ((Double)x1).compareTo((Double) x2) > 0;
        }
    },
    MENOR {
        @Override
        public Boolean apply(Object x1, Object x2) {
            return ((Double)x1).compareTo((Double) x2) > 0;
        }
    },
    IGUAL {
        @Override
        public Boolean apply(Object x1, Object x2) {
            return x1.equals(x2);
        }
    };

    public abstract Boolean apply(Object x1, Object x2);

}
