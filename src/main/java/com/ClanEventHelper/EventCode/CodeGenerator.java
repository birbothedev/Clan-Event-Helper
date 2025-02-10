package com.ClanEventHelper.EventCode;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class CodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6; // You can change this
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> generatedCodes = new HashSet<>();

    private static String lastGeneratedCode;

    public static String generateEventCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (generatedCodes.contains(code));

        generatedCodes.add(code);
        lastGeneratedCode = code;
        return code;
    }

    private static String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public static boolean isValidCode(String enteredCode) {
        return enteredCode != null && enteredCode.equals(lastGeneratedCode);
    }

    public static void linkCodes(String enteredCode) {
        if (isValidCode(enteredCode)) {
            System.out.println("Codes matched! Player is linked to the event.");
        } else {
            System.out.println("Invalid code. Try again.");
        }
    }

    public static String getGeneratedCode() {
        return lastGeneratedCode;
    }

    public static void clearCodes() {
        generatedCodes.clear();
        lastGeneratedCode = null;
    }
}
