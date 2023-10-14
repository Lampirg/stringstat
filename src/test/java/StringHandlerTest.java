import dev.lampirg.Group;
import dev.lampirg.StringHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class StringHandlerTest {
    @Test
    void givenSimpleStream() {
        Stream<String> input = Stream.of(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";\"\";\"100\"",
                "\"1\";\"1\"",
                "\"79855053897\"83100000580443402\";\"200000133000191\"",
                "\"79076513686\""
        );
        List<Group> expected = List.of(
                Group.from(List.of(
                        "\"111\";\"123\";\"222\"",
                        "\"200\";\"123\";\"100\"",
                        "\"300\";\"\";\"100\""
                )),
                Group.fromSingleValue("\"1\";\"1\""),
                Group.fromSingleValue("\"79076513686\"")
        );
        List<Group> actual = StringHandler.getStat(input);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }
}
