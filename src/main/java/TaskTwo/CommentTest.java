package TaskTwo;

import java.io.IOException;

public class CommentTest {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        CommentApp comments = new CommentApp();
        int userId = 1;
        comments.createJsonWithAllCommentsFromLastPostByUserId(userId);
    }
}

