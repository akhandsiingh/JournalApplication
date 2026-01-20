package com.example.AkSB.service;

import com.example.AkSB.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    public static final String apiKey="";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;
    public static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
     public WeatherResponse getWeather(String city){
         WeatherResponse weatherResponse = redisService.get("weather_of_ " + city, WeatherResponse.class);
         if(weatherResponse!=null){
             return weatherResponse;
         }else {
             String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
             ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
             WeatherResponse body = response.getBody();
             if(body!=null) {
                 redisService.set("weather_of_"+city, body, 300L);
             }
             return body;
         }
     }
}
