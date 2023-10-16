package dev.lampirg;

import dev.lampirg.logic.StringHandler;
import dev.lampirg.logic.entities.Group;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    private Main() {}

    @SneakyThrows
    public static void main(String[] args) {
        Files.createDirectories(Path.of("src/main/resources"));
        InputStreamReader in = new InputStreamReader((Main.class.getResource("/" + args[0]).openStream()));
        try (BufferedReader br = new BufferedReader(in);
             PrintWriter out = new PrintWriter("src/main/resources/output.txt", StandardCharsets.UTF_8)) {
            List<Group> groups = StringHandler.getStat(br::lines);
            int count = 0;
            for (int i = 0; i < groups.size(); i++) {
                out.println("Группа " + (i + 1));
                groups.get(i).getLines().forEach(out::println);
                if (groups.get(i).getLines().size() > 1) {
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}
