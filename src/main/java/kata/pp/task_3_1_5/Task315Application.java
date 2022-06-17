package kata.pp.task_3_1_5;

import kata.pp.task_3_1_5.clients.UserRestClient;
import kata.pp.task_3_1_5.models.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Task315Application {
    private static UserRestClient client = new UserRestClient(new RestTemplate());

    public static void main(String[] args) {
        Task315Application.client.getAll();
        Task315Application.client.post(new User(3L, "James", "Brown", (byte) 30));
        Task315Application.client.put(new User(3L, "Thomas", "Shelby", (byte) 30));
        Task315Application.client.delete(3L);
    }
}
