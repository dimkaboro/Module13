package TaskOne;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class UserCrudApp {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final Gson GSON = new Gson();

    public static User createUser() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            String requestBody =  " {\"id\": 50,\n" +
                    "    \"name\": \"Thomas Anderson\",\n" +
                    "    \"username\": \"Neo\",\n" +
                    "    \"email\": \"Thomas_Ander@annie.ca\",\n" +
                    "    \"address\": {\n" +
                    "      \"street\": \" Silent Hill\",\n" +
                    "      \"suite\": \"Suite 551\",\n" +
                    "      \"city\": \"Rosewood\",\n" +
                    "      \"zipcode\": \"77263\",\n" +
                    "      \"geo\": {\n" +
                    "        \"lat\": \"-111.6578\",\n" +
                    "        \"lng\": \"23.5342\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"phone\": \"(254)654-1189\",\n" +
                    "    \"website\": \"matrix.info\",\n" +
                    "    \"company\": {\n" +
                    "      \"name\": \"DACL Plaza\",\n" +
                    "      \"catchPhrase\": \"User-centric fault-tolerant solution\",\n" +
                    "      \"bs\": \"revolutionize end-to-end systems\"}}";

            try(OutputStream outputStream = connection.getOutputStream()){
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_CREATED == responseCode){
                return getUserInfo(connection);
            }
        } catch (IOException e) {
            throw new RuntimeException("Something goes wrong");
        }
        return null;
    }

    public static User updateUserById(int id){
        try{
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            String requestBody = "{\n" +
                    "    \"id\": 5,\n" +
                    "    \"name\": \"Chelsey Dietrich\",\n" +
                    "    \"username\": \"Kamren\",\n" +
                    "    \"email\": \"Lucio_Hettinger@annie.ca\",\n" +
                    "    \"address\": {\n" +
                    "      \"street\": \"Skiles Walks\",\n" +
                    "      \"suite\": \"Suite 351\",\n" +
                    "      \"city\": \"Roscoeview\",\n" +
                    "      \"zipcode\": \"33263\",\n" +
                    "      \"geo\": {\n" +
                    "        \"lat\": \"-31.8129\",\n" +
                    "        \"lng\": \"62.5342\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"phone\": \"(254)954-1289\",\n" +
                    "    \"website\": \"demarco.info\",\n" +
                    "    \"company\": {\n" +
                    "      \"name\": \"Keebler LLC\",\n" +
                    "      \"catchPhrase\": \"User-centric fault-tolerant solution\",\n" +
                    "      \"bs\": \"revolutionize end-to-end systems\"\n" +
                    "    }\n" +
                    "  }";

            try(OutputStream outputStream = connection.getOutputStream()){
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_CREATED == responseCode){
                return getUserInfo(connection);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public int deleteUser(int id)
    {
        try
        {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public List<User> getAllUsers()
    {
        try
        {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode)
            {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
                {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    Type userListType = new TypeToken<List<User>>()
                    {
                    }.getType();
                    return GSON.fromJson(response.toString(), userListType);
                }
            }
            connection.disconnect();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<User> getUserById(int id)
    {
        try
        {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
                {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    User user = GSON.fromJson(response.toString(), User.class);
                    return Optional.ofNullable(user);
                }
            }
            connection.disconnect();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> getUserByUsername(String username)
    {
        try
        {
            URL url = new URL(BASE_URL + "?username=" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode)
            {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
                {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    Type userListType = new TypeToken<List<User>>()
                    {
                    }.getType();
                    List<User> users = GSON.fromJson(response.toString(), userListType);
                    if (!users.isEmpty())
                    {
                        return Optional.of(users.get(0));
                    }
                }
            }
            connection.disconnect();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }



    private static User getUserInfo(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return GSON.fromJson(response.toString(), User.class);
        }
    }
}

