package com.utn.dds.tpdds.model;

import java.math.BigDecimal;

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
            return ((BigDecimal)x1).compareTo((BigDecimal)x2) > 0;
        }
    },
    MENOR {
        @Override
        public Boolean apply(Object x1, Object x2) {
            return ((BigDecimal)x1).compareTo((BigDecimal)x2) > 0;
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
