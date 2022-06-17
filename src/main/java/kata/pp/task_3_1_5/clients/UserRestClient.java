package kata.pp.task_3_1_5.clients;

import kata.pp.task_3_1_5.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class UserRestClient {
    private static final String REQUEST_URI = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;
    private static String cookie;


    public UserRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAll() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(REQUEST_URI, User[].class);
        List<String> headers = responseEntity.getHeaders().get("Set-Cookie");
        for (String s : headers) {
            if (s.startsWith("JSESSIONID")) {
                cookie = s;
            }
        }
        System.out.println(cookie);

        return responseEntity.getBody() != null
                ? Arrays.asList(responseEntity.getBody())
                : Collections.emptyList();
    }

    public ResponseEntity<String> post(User user) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(REQUEST_URI, requestEntity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> put(User user) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(REQUEST_URI, HttpMethod.PUT, requestEntity, String.class);
        System.out.println(responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> delete(Long id) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        ResponseEntity<User> requestEntity = new ResponseEntity<>(headers, HttpStatus.OK);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(REQUEST_URI + "/{id}", HttpMethod.DELETE,
                        requestEntity, String.class, Long.toString(id));
        System.out.println(responseEntity);
        return responseEntity;
    }
}
