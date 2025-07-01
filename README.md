# PAN Mask Library

A Java library for safely masking credit card numbers (PANs) in text.

## Overview

PAN Mask is a lightweight, high-performance Java library that helps you detect and mask credit card numbers within text content. 
This is especially useful for applications that need to handle or display credit card information while maintaining PCI DSS compliance.

## Features
- Fast detection of credit card numbers within text
- Intelligent masking that preserves BIN (first 6 digits) and last 4 digits
- Luhn algorithm validation to reduce false positives
- Support for different card issuers and their specific formats
- High-performance implementation (with JMH benchmarks)

## Project Structure

The project consists of two main modules:
- **pan-mask-core**: The core library containing the masking functionality
- **pan-mask-jmh**: Benchmarking module using JMH (Java Microbenchmark Harness)

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.x

### Installation

Add the following dependency to your Maven project:

``` xml
<dependency>
    <groupId>com.payneteasy</groupId>
    <artifactId>pan-mask-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Basic Usage

``` java
import com.payneteasy.panmask.CardMasker;
import com.payneteasy.panmask.CardIssuer;
import com.payneteasy.panmask.CardIssuerList;

// Create a masker with default card issuers
CardMasker masker = new CardMasker();

// Or initialize with custom card issuers
List<CardIssuer> customIssuers = new ArrayList<>();
customIssuers.add(new CardIssuer("visa", "4"));
CardMasker customMasker = new CardMasker(customIssuers);

// Mask card numbers in text
String masked = masker.maskCardNumbers("Your card 4111111111111111 will be charged");
// Result: "Your card 411111******1111 will be charged"
```

## Benchmarking

The project includes JMH benchmarks to measure performance:

``` bash
# Run benchmarks with the shaded JAR
mvn clean package -P jmh-shaded
java -jar pan-mask-jmh/target/pan-mask-jmh-shaded.jar
```

## License

Apache

## Acknowledgments
- [JMH](https://openjdk.java.net/projects/code-tools/jmh/) - Java Microbenchmark Harness
