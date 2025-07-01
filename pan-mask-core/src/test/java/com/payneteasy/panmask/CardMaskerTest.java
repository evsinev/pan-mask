package com.payneteasy.panmask;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardMaskerTest {

    private final CardMasker cardMasker = new CardMasker(CardIssuerList.ALL_ISSUERS);

    @Test
    public void testMaskCardNumbers_multiple() {
        String input = "A293829382784954948617664024007135532710245822471900104421954555213359663459907994231874223879880B";
        String expected = "A29382938******4948617664*****************************4421954555213359663459*******31874223879880B";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_validCard() {
        String input = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A08444433332222111157135123450000000008D29092011920417300000F ]";
        String expected = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A08444433**************512345******0008D29092011920417300000F ]";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_shortCardNumber() {
        String input = "5123450000000";
        String expected = "5123450000000";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_mastercard() {
        String input = "5123450000000008";
        String expected = "512345******0008";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_visaElectron() {
        String input = "4026 1111 1111 1115";
        String expected = "4026 11** **** 1115";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_maestro_12() {
        String input = "676123456789";
        String expected = "67******6789";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_maestro_13() {
        String input = "5018000000007";
        String expected = "501******0007";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_maestro_14() {
        String input = "50180000000009";
        String expected = "5018******0009";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_maestro_15() {
        String input = "501800000000007";
        String expected = "5018*******0007";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_separatedWithSpace() {
        String input = "5123 4500 0000 0008";
        String expected = "5123 45** **** 0008";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_separatedWithDash() {
        String input = "5123-4500-0000-0008";
        String expected = "5123-45**-****-0008";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_skipHead() {
        String input = "12345678901_5123450000000008";
        String expected = "12345678901_512345******0008";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_skipTail() {
        String input = "5123450000000008_12345678901";
        String expected = "512345******0008_12345678901";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_mixed() {
        String input = "CARDS=[5123 4500 0000 0008 5123450000000008 5123-4500-0000-0008] ";
        String expected = "CARDS=[5123 45** **** **08 512345********08 5123-45**-****-0008] ";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_noCard() {
        String input = "123450000000008";
        String expected = "123450000000008";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_too_short() {
        String input = "444455556666";
        String expected = "444455556666";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMaskCardNumbers_emptyString() {
        String input = "";
        String expected = "";

        String result = cardMasker.maskCardNumbers(input);
        assertEquals(expected, result);
    }
}
