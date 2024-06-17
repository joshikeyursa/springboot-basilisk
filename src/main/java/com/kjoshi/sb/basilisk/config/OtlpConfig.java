package com.kjoshi.sb.basilisk.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtlpConfig {
    @Bean
    OtlpHttpSpanExporter buildOtlpHttpSpanExporter(@Value("${tracing.url}")String tracingUrl){
        return OtlpHttpSpanExporter
                .builder()
                .setEndpoint(tracingUrl)
                .build();
    }
}
