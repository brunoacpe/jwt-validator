package com.brunopacheco.jwtvalidator.utils;

public class BooleanTranslator {
    private BooleanTranslator(){}

    private static final String TRUE = "verdadeiro";
    private static final String FALSE = "falso";

    public static String booleanToPortuguese(Boolean inputBoolean) {
        return inputBoolean ? TRUE : FALSE;
    }
}
