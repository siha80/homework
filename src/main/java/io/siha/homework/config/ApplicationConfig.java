package io.siha.homework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan(excludeFilters = @ComponentScan.Filter(Configuration.class))
@Slf4j
public class ApplicationConfig {
}
