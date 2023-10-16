import dev.lampirg.Main;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

class MeasureTimeTest {
    @Test
    void testAndMeasureMainFunction() {
        int jumps = 10;
        Instant start = Instant.now();
        for (int i = 0; i < jumps; i++) {
            Assertions.assertThatNoException().isThrownBy(() -> Main.main(new String[] {"lng-big.csv"}));
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).dividedBy(jumps).toSeconds());
    }
}
