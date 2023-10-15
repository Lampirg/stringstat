package dev.lampirg;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lng.csv"));
             PrintWriter out = new PrintWriter("src/main/resources/output.txt")) {
            List<Group> groups = StringHandler.getStat(br::lines);
            groups.sort(Comparator.comparingInt((Group group) -> group.getLines().size()).reversed());
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
