package ru.alexenko.diplom.lib.iprsmart.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, отвечающий за генерацию JWT токена.
 */
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${api.ipr_smart.jwt.secret_key}")
    private String secretKey;

    /**
     * Метод генерирует JWT
     * @param clientId id клиента, для которого будет сгенерирован токен
     * @return строка являющаяся JWT токеном
     */
    public String generateToken(Integer clientId) {

        try {
            SecretKey secretKeyNotBase64 = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            return Jwts.builder()
                    .setClaims(getPayload(clientId))
                    .signWith(secretKeyNotBase64, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nНе удалось сгенерировать JWT токен");
            return null;
        }

    }

    /**
     * Метод создающий полезную нагрузку для токена
     * @param clientId id клиента
     * @return payload в виде Map<String, Object>
     */
    public Map<String, Object> getPayload(Integer clientId) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("client_id", clientId);
        payload.put("time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        payload.put("ip", "{}");

        return payload;
    }
}


