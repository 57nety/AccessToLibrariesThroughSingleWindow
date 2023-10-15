package ru.alexenko.diplom.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.alexenko.diplom.web.secutiry.CustomInterceptor;

/**
 * Конфигурационный класс.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Метод создаёт Bean webClient с расширенным размером буфера.
     * @return webClient
     */
    @Bean
    public WebClient webClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }

    /**
     * Метод создаёт Bean gson, для парсинга строк в объекты.
     * @return gson
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }

    /**
     * Метод создаёт новый Interceptor для перехвата запросов.
     * @param registry хранит Interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor());
    }
}
