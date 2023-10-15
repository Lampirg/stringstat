import dev.lampirg.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

class E2ETests {
    @Test
    void testMainFunction() {
        int jumps = 10;
        Instant start = Instant.now();
        for (int i = 0; i < jumps; i++) {
            Main.main(new String[] {"output.txt"});
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).dividedBy(jumps).toSeconds());
    }
}
