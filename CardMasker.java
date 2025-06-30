package com.payneteasy.datamasker;

import java.util.List;

public class CardMasker {

    private static final int MIN_NUMBER_LENGTH = 12;
    private static final int CARD_PREFIX_LENGTH = 6;
    private static final char MASK_CHARACTER = '*';

    private static final int[] MASKED_PREFIX_LENGTHS = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  // 0-11
        2, 3, 4, 4, 6, 6, 6, 6               // 12-19
    };

    private static final int[] MASKED_SUFFIX_LENGTHS = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  // 0-11
        4, 4, 4, 4, 4, 4, 4, 4               // 12-19
    };

    private final BinTrieNode root = new BinTrieNode();

    public CardMasker() {
        this(CardIssuerList.ALL_ISSUERS);
    }

    public CardMasker(List<CardIssuer> issuers) {
        for (CardIssuer issuer : issuers) {
            for (String prefix : issuer.cardNumberPrefixes) {
                addPrefix(prefix, issuer);
            }
        }
    }

    public String maskCardNumbers(String input) {
        int length = input.length();

        int digitStart = -1;
        int digitIndex = -1;
        int digitCount = 0;
        int[] digits = new int[length];
        int[] indexes = new int[length];

        boolean[] masked = new boolean[length];
        boolean changed = false;

        for (int i = 0; i <= length; i++) {
            char ch = (i == length) ? '\0' : input.charAt(i);
            if (ch >= '0' && ch <= '9') {
                digitIndex++;
                digitCount++;
                if (digitStart == -1) {
                    digitStart = digitIndex;
                }
                digits[digitIndex] = ch - '0';
                indexes[digitIndex] = i;
            } else if (ch != ' ' && ch != '-') {
                if (digitCount >= MIN_NUMBER_LENGTH - 1) {
                    int start = digitStart;
                    int maxStart = digitIndex - MIN_NUMBER_LENGTH + 1;
                    int len;
                    while (start <= maxStart) {
                        CardLengthMask cardLengthMask = getCardLengthMask(digits, start);
                        if (cardLengthMask != null && (digitCount >= cardLengthMask.getMinLength()) && (len = checkLuhn(digits, start, cardLengthMask)) > 0) {
                            changed = true;
                            int startMask = start + MASKED_PREFIX_LENGTHS[len];
                            int endMask = start + len - MASKED_SUFFIX_LENGTHS[len];
                            if (masked[startMask - 2]) {
                                startMask--;
                            }
                            for (int j = startMask; j < endMask; j++) {
                                masked[j] = true;
                            }
                        }
                        start++;
                        digitCount--;
                    }
                }
                if (length - i < MIN_NUMBER_LENGTH) {
                    break;
                }
                if (digitStart >= 0) {
                    digitStart = -1;
                    digitCount = 0;
                }
            }
        }

        if (changed) {
            char[] array = input.toCharArray();
            for (int i = 0; i < length; i++) {
                if (masked[i]) {
                    array[indexes[i]] = MASK_CHARACTER;
                }
            }
            return new String(array);
        } else {
            return input;
        }
    }

    private void addPrefix(String prefix, CardIssuer issuer) {
        BinTrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - '0';
            BinTrieNode nextNode = node.children[index];
            if (nextNode == null) {
                nextNode = new BinTrieNode();
                node.children[index] = nextNode;
            }
            node = nextNode;
        }
        node.lengthMask = issuer.cardLengthMask;
    }

    private CardLengthMask getCardLengthMask(int[] array, int start) {
        CardLengthMask result = null;
        BinTrieNode node = root;

        int len = Math.min(array.length, start + CARD_PREFIX_LENGTH);
        for (int i = start; i < len; i++) {
            int digit = array[i];
            if ((node = node.children[digit]) == null) {
                break;
            }
            result = node.lengthMask;
        }
        return result;
    }

    private int checkLuhn(int[] array, int start, CardLengthMask lengthMask) {
        int sumEven = 0;
        int sumOdd  = 0;

        boolean candidateEven = false;
        int candidateLength = 1;

        int end = Math.min(array.length, start + lengthMask.getMaxLength());
        for (int i = start; i < end; i++) {
            int digit = array[i];
            int processed = digit * 2;
            if (processed > 9) {
                processed -= 9;
            }

            if (candidateEven) {
                sumEven += digit;
                sumOdd  += processed;
            } else {
                sumEven += processed;
                sumOdd  += digit;
            }

            if (lengthMask.isLengthEnabled(candidateLength)) {
                if (candidateEven) {
                    if (sumEven % 10 == 0) {
                        return candidateLength;
                    }
                } else {
                    if (sumOdd % 10 == 0) {
                        return candidateLength;
                    }
                }
            }
            candidateEven = !candidateEven;
            candidateLength++;
        }
        return 0;
    }

    private static class BinTrieNode {
        BinTrieNode[] children = new BinTrieNode[10];
        CardLengthMask lengthMask;
    }
}
