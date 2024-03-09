package TaskOne;

public class UserCrudAppTest {
    public static void main(String[] args) {
        UserCrudApp userCrudApp = new UserCrudApp();
//        System.out.println(UserCrudApp.createUser());
//          System.out.println(UserCrudApp.updateUserById());
//          System.out.println(userCrudApp.deleteUser(1));
            System.out.println(userCrudApp.getAllUsers());
            System.out.println(userCrudApp.getUserById(2));
            System.out.println(userCrudApp.getUserByUsername("Antonette"));


    }
}
