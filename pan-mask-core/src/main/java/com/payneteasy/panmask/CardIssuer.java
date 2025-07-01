package com.payneteasy.panmask;

import java.util.HashSet;
import java.util.Set;

public class CardIssuer {

    String issuerName;

    final Set<String> cardNumberPrefixes;
    final CardLengthMask cardLengthMask;

    public CardIssuer(String name) {
        issuerName = name;
        cardNumberPrefixes = new HashSet<>();
        cardLengthMask = new CardLengthMask();
    }

    public CardIssuer prefix(int prefix) {
        cardNumberPrefixes.add(String.valueOf(prefix));
        return this;
    }

    public CardIssuer prefix(int... args) {
        for (int prefix : args) {
            cardNumberPrefixes.add(String.valueOf(prefix));
        }
        return this;
    }

    public CardIssuer prefixRange(int start, int end) {
        for (int prefix = start; prefix <= end; prefix++) {
            cardNumberPrefixes.add(String.valueOf(prefix));
        }
        return this;
    }

    public CardIssuer length(int len) {
        cardLengthMask.enableLength(len);
        return this;
    }

    public CardIssuer length(int... args) {
        for (int len : args) {
            cardLengthMask.enableLength(len);
        }
        return this;
    }

    public CardIssuer lengthRange(int start, int end) {
        for (int len = start; len <= end; len++) {
            cardLengthMask.enableLength(len);
        }
        return this;
    }
}
