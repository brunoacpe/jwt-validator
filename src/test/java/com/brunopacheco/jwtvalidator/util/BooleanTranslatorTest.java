package com.brunopacheco.jwtvalidator.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BooleanTranslatorTest {

    @Test
    void shouldTranslateTrueToVerdadeiro() {
        String result = BooleanTranslator.booleanToPortuguese(true);
        assertEquals("verdadeiro", result);
    }

    @Test
    void shouldTranslateFalseToFalso() {
        String result = BooleanTranslator.booleanToPortuguese(false);
        assertEquals("falso", result);
    }

    @Test
    void shouldTranslateBooleanObjectTrue() {
        Boolean input = Boolean.TRUE;
        String result = BooleanTranslator.booleanToPortuguese(input);
        assertEquals("verdadeiro", result);
    }

    @Test
    void shouldTranslateBooleanObjectFalse() {
        Boolean input = Boolean.FALSE;
        String result = BooleanTranslator.booleanToPortuguese(input);
        assertEquals("falso", result);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenInputIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> BooleanTranslator.booleanToPortuguese(null)
        );
    }
}
