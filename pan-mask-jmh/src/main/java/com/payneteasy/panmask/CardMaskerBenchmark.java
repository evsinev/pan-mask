package com.payneteasy.panmask;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class CardMaskerBenchmark {

    private CardMasker cardMasker;

    @Setup(Level.Trial)
    public void setup() {
        cardMasker = new CardMasker(CardIssuerList.ALL_ISSUERS);
    }

    private String replaceSubstring(String source, String str1, String str2) {
        return source.replace(str1, str2);
    }

    @Benchmark
    public void testMaskCardNumbers_validCard_replace() {
        String input = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A08512345000000000857135123450000000008D29092011920417300000F ]";
        String expected = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A085***********000857135***********0008D29092011920417300000F ]";
        String result = replaceSubstring(input, "5123450000000008", "5***********0008");
    }

    @Benchmark
    public void testMaskCardNumbers_validCard() {
        String input = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A08512345000000000857135123450000000008D29092011920417300000F ]";
        String expected = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A085***********000857135***********0008D29092011920417300000F ]";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_shortCardNumber() {
        String input = "5123450000000";
        String expected = "5123450000000";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_mastercard() {
        String input = "5123450000000008";
        String expected = "5***********0008";

        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_visaElectron_replace() {
        String input = "4026 1111 1111 1115";
        String expected = "4*** **** **** 1115";

        String result = replaceSubstring(input, "4026 1111 1111 1115", "4*** **** **** 1115");
    }

    @Benchmark
    public void testMaskCardNumbers_visaElectron() {
        String input = "4026 1111 1111 1115";
        String expected = "4*** **** **** 1115";

        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_separatedWithSpace() {
        String input = "5123 4500 0000 0008";
        String expected = "5*** **** **** 0008";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_separatedWithDash() {
        String input = "5123-4500-0000-0008";
        String expected = "5***-****-****-0008";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_skipHead() {
        String input = "12345678901_5123450000000008";
        String expected = "12345678901_5***********0008";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_skipTail() {
        String input = "5123450000000008_12345678901";
        String expected = "5***********0008_12345678901";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_mixed_replace() {
        String input = "CARDS=[5123 4500 0000 0008 5123450000000008 5123-4500-0000-0008]";
        String expected = "CARDS=[5*** **** **** 0008 5***********0008 5***-****-****-0008]";
        String result = replaceSubstring(input, "5123450000000008", "5***********0008");
    }

    @Benchmark
    public void testMaskCardNumbers_mixed() {
        String input = "CARDS=[5123 4500 0000 0008 5123450000000008 5123-4500-0000-0008]";
        String expected = "CARDS=[5*** **** **** 0008 5***********0008 5***-****-****-0008]";
        String result = cardMasker.maskCardNumbers(input);
        }

    @Benchmark
    public void testMaskCardNumbers_noCard() {
        String input = "123450000000008";
        String expected = "123450000000008";
        String result = cardMasker.maskCardNumbers(input);
    }

    @Benchmark
    public void testMaskCardNumbers_emptyString() {
        String input = "";
        String expected = "";
        String result = cardMasker.maskCardNumbers(input);
    }
}
