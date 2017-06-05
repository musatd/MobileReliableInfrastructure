package infrastructure.reliable.org.reliableinfrastructure.general;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class Utilities {
    private static RestTemplate restTemplate = null;

    public static synchronized RestTemplate getRestTemplate() {

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        }

        return restTemplate;
    }

}
