package TaskThree;

import java.io.IOException;

public class CompletedTest {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Completed comp = new Completed();
        int userId = 1;
        comp.createJsonWithAllOpenToDosByUserId(userId);
    }
}

