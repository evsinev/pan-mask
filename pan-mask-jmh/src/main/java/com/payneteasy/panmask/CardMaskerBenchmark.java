package com.payneteasy.panmask;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

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
    public void testMaskCardNumbers_validCard_replace(Blackhole aBlackhole) {
        // expected = "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A085***********000857135***********0008D29092011920417300000F ]";
        String result = replaceSubstring("[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A08512345000000000857135123450000000008D29092011920417300000F ]", "5123450000000008", "5***********0008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_validCard(Blackhole aBlackhole) {
        // "[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A085***********000857135***********0008D29092011920417300000F ]";
        String result = cardMasker.maskCardNumbers("[Binder:8356_1]  [on=onOnlineProc() ] Pan And Track [tlv=5A08512345000000000857135123450000000008D29092011920417300000F ]");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_shortCardNumber(Blackhole aBlackhole) {
        // expected = "5123450000000";
        String result = cardMasker.maskCardNumbers("5123450000000");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_mastercard(Blackhole aBlackhole) {
        // expected = "5***********0008";
        String result = cardMasker.maskCardNumbers("5123450000000008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_visaElectron_replace(Blackhole aBlackhole) {
        // expected = "4*** **** **** 1115";
        String result = replaceSubstring("4026 1111 1111 1115", "4026 1111 1111 1115", "4*** **** **** 1115");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_visaElectron(Blackhole aBlackhole) {
        // expected = "4*** **** **** 1115";
        String result = cardMasker.maskCardNumbers("4026 1111 1111 1115");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_separatedWithSpace(Blackhole aBlackhole) {
        // expected = "5*** **** **** 0008";
        String result = cardMasker.maskCardNumbers("5123 4500 0000 0008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_separatedWithDash(Blackhole aBlackhole) {
        // expected = "5***-****-****-0008";
        String result = cardMasker.maskCardNumbers("5123-4500-0000-0008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_skipHead(Blackhole aBlackhole) {
        // expected = "12345678901_5***********0008";
        String result = cardMasker.maskCardNumbers("12345678901_5123450000000008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_skipTail(Blackhole aBlackhole) {
        // expected = "5***********0008_12345678901";
        String result = cardMasker.maskCardNumbers("5123450000000008_12345678901");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_mixed_replace(Blackhole aBlackhole) {
        // expected = "CARDS=[5*** **** **** 0008 5***********0008 5***-****-****-0008]";
        String result = replaceSubstring("CARDS=[5123 4500 0000 0008 5123450000000008 5123-4500-0000-0008]", "5123450000000008", "5***********0008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_mixed(Blackhole aBlackhole) {
        // expected = "CARDS=[5*** **** **** 0008 5***********0008 5***-****-****-0008]";
        String result = cardMasker.maskCardNumbers("CARDS=[5123 4500 0000 0008 5123450000000008 5123-4500-0000-0008]");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_noCard(Blackhole aBlackhole) {
        // expected = "123450000000008";
        String result = cardMasker.maskCardNumbers("123450000000008");
        aBlackhole.consume(result);
    }

    @Benchmark
    public void testMaskCardNumbers_emptyString(Blackhole aBlackhole) {
        // expected = "";
        String result = cardMasker.maskCardNumbers("");
        aBlackhole.consume(result);
    }
}
