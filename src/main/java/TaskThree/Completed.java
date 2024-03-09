package TaskThree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Completed {
    HttpClient client = HttpClient.newHttpClient();

    public void createJsonWithAllOpenToDosByUserId(int userId) throws IOException, InterruptedException
    {
        String allTodosJson = getAllTodosByUserId(userId);
        List<CompletedGetSet> allTodos = getOpenTodosFromJson(allTodosJson);
        String jsonFilePath = "src/main/resources/" + "user-" + userId + "-open_todos.json";
        createJsonWithTodos(allTodos, jsonFilePath);
        System.out.println("JSON filepath: " + jsonFilePath);
    }

    private String getAllTodosByUserId(int userId) throws IOException, InterruptedException
    {
        String uri = "https://jsonplaceholder.typicode.com/users/" + userId + "/todos";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private List<CompletedGetSet> getOpenTodosFromJson(String json)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        CompletedGetSet[] todosArray = gson.fromJson(json, CompletedGetSet[].class);
        List<CompletedGetSet> todosList = new ArrayList<>(Arrays.asList(todosArray));
        List<CompletedGetSet> openTodosList = new ArrayList<>();
        for (CompletedGetSet element : todosList)
        {
            if (!element.isCompleted())
            {
                openTodosList.add(element);
            }
        }
        return openTodosList;
    }

    private void createJsonWithTodos(List<CompletedGetSet> todos, String jsonFilePath)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String outputString = gson.toJson(todos.toArray());
        try (FileWriter output = new FileWriter(jsonFilePath))
        {
            output.write(outputString);
        }
        catch (IOException e)
        {
            e.getStackTrace();
        }

    }
}

