import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class tst {
    public static void main(String[] args) throws IOException {
        List<String> users = Files.readAllLines(Path.of("resource/UserList"));
        System.out.println("k"+90);
        System.out.println(users.size());
    }
}
