package com.payneteasy.datamasker;

import java.util.List;

public class CardIssuerList {

    public static final List<CardIssuer> ALL_ISSUERS = List.of(
            new CardIssuer("American Express").prefix(34, 37).length(15),
            new CardIssuer("Bankcard").prefix(5610).prefixRange(560221, 560225).length(16),
            new CardIssuer("China T-Union").prefix(31).length(19),
            new CardIssuer("China UnionPay").prefix(62).lengthRange(16, 19),
            new CardIssuer("Diners Club International").prefix(30, 36, 38, 39).lengthRange(14, 19),
            new CardIssuer("Diners Club United States & Canada").prefix(55).length(16),
            new CardIssuer("Discover Card").prefixRange(622126, 622925).lengthRange(16, 19),
            new CardIssuer("UkrCard").prefixRange(60400100, 60420099).lengthRange(16, 19),
            new CardIssuer("RuPay").prefix(60, 65, 81, 82, 508, 353, 356).length(16),
            new CardIssuer("InterPayment").prefix(636).lengthRange(16, 19),
            new CardIssuer("InstaPayment").prefixRange(637, 639).length(16),
            new CardIssuer("JCB").prefixRange(3528, 3589).lengthRange(16, 19),
            new CardIssuer("Laser").prefix(6304, 6706, 6771, 6709).lengthRange(16, 19),
            new CardIssuer("Maestro UK").prefix(6759, 676770, 676774).lengthRange(12, 19),
            new CardIssuer("Maestro").prefix(5018, 5020, 5038, 5893, 6304, 6759, 6761, 6762, 6763).lengthRange(12, 19),
            new CardIssuer("Dankort").prefix(5019, 4571).length(16),
            new CardIssuer("Mir").prefixRange(2200, 2204).lengthRange(16, 19),
            new CardIssuer("BORICA").prefix(2205).length(16),
            new CardIssuer("NPS Pridnestrovie").prefixRange(6054740, 6054744).length(16),
            new CardIssuer("MasterCard").prefixRange(51, 55).prefixRange(2221, 2720).length(16),
            new CardIssuer("Solo").prefix(6334, 6767).length(16, 18, 19),
            new CardIssuer("Switch").prefix(4903, 4905, 4911, 4936, 564182, 633110, 6333, 6759).length(16, 18, 19),
            new CardIssuer("Troy").prefix(65, 9792).length(16),
            new CardIssuer("Visa Electron").prefix(4026, 417500, 4508, 4844, 4913, 4917).length(16),
            new CardIssuer("Visa").prefix(4).length(13, 16, 19),
            new CardIssuer("Verve").prefixRange(506099, 506198).prefixRange(650002, 650027).prefixRange(507865, 507964).length(16, 18, 19),
            new CardIssuer("LankaPay").prefix(357111).length(16),
            new CardIssuer("UzCard").prefix(8600, 5614).length(16),
            new CardIssuer("Humo").prefix(9860).length(16),
            new CardIssuer("GPN").prefix(1946, 50, 56, 58).prefixRange(60, 63).length(16, 18, 19),
            new CardIssuer("Napas").prefix(9704).length(16, 19)
    );
}