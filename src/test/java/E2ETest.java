import dev.lampirg.Main;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class E2ETest {
    @Test
    void testMainFunction() {
        Assertions.assertThatNoException().isThrownBy(() -> Main.main(new String[] {"lng-big.csv"}));
    }
}
