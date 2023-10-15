package dev.lampirg;

import dev.lampirg.logic.StringHandler;
import dev.lampirg.logic.entities.Group;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class Main {

    private Main() {}

    @SneakyThrows
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lng.txt"));
             PrintWriter out = new PrintWriter("src/main/resources/" + args[0])) {
            List<Group> groups = StringHandler.getStat(br::lines);
            int count = 0;
            for (int i = 0; i < groups.size(); i++) {
                out.println("Группа " + (i + 1));
                groups.get(i).getLines().forEach(out::println);
                if (groups.get(i).getLines().size() > 1) {
                    count++;
                }
            }
            log.info(String.valueOf(count));
        }
    }
}
