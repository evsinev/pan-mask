package com.payneteasy.datamasker;

public class CardLengthMask {
    private int mask = 0;
    private int minLength = -1;
    private int maxLength = -1;

    public void enableLength(int i) {
        mask |= (1 << (i - 1));
        if (minLength == -1 || i < minLength) minLength = i;
        if (maxLength == -1 || i > maxLength) maxLength = i;
    }

    public boolean isLengthEnabled(int i) {
        return (mask & (1 << (i - 1))) != 0;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public boolean isEmpty() {
        return minLength != -1;
    }
}
