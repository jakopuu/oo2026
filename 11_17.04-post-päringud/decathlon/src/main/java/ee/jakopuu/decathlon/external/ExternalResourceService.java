package ee.jakopuu.decathlon.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class ExternalResourceService {

    private final RestClient restClient = RestClient.create();
    private final String judgesUrl;
    private final String locationsUrl;

    public ExternalResourceService(
            @Value("${decathlon.mockapi.judges-url:}") String judgesUrl,
            @Value("${decathlon.mockapi.locations-url:}") String locationsUrl
    ) {
        this.judgesUrl = judgesUrl;
        this.locationsUrl = locationsUrl;
    }

    public List<Map<String, Object>> getJudges() {
        return fetch(judgesUrl, List.of(
                Map.of("id", "1", "name", "Andres Saar", "country", "EST"),
                Map.of("id", "2", "name", "Liisa Kask", "country", "FIN")
        ));
    }

    public List<Map<String, Object>> getLocations() {
        return fetch(locationsUrl, List.of(
                Map.of("id", "1", "name", "Kalevi staadion", "city", "Tallinn"),
                Map.of("id", "2", "name", "Lauluväljaku staadion", "city", "Pärnu")
        ));
    }

    private List<Map<String, Object>> fetch(String url, List<Map<String, Object>> naiteAndmed) {
        // kui mockapi.io aadress on application.properties failis veel tühi, tagastame näidisandmed,
        // et endpointi saaks kohe testida ka enne mockapi.io seadistamist
        if (!StringUtils.hasText(url)) {
            return naiteAndmed;
        }

        try {
            List<Map<String, Object>> vastus = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return vastus == null ? List.of() : vastus;
        } catch (RestClientException ex) {
            throw new RuntimeException("Välise API päring ebaõnnestus: " + ex.getMessage());
        }
    }
}
