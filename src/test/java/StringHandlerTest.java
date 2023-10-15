import dev.lampirg.logic.StringHandler;
import dev.lampirg.logic.entities.Group;
import dev.lampirg.logic.entities.line.Line;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dev.lampirg.logic.entities.line.Column.empty;
import static dev.lampirg.logic.entities.line.Column.of;

class StringHandlerTest {
    @Test
    void givenSimpleStream() {
        List<String> input = List.of(
                "\"111\";\"123\";\"222\"",
                "\"300\";\"\";\"100\"",
                "\"200\";\"123\";\"100\"",
                "\"1\";\"1\"",
                "\"79855053897\"83100000580443402\";\"200000133000191\"",
                "\"79076513686\"",
                "\"\";\"\""
        );
        List<Group> expected = List.of(
                Group.from(List.of(
                        Line.line(List.of(of(111, 0), of(123, 1), of(222, 2))),
                        Line.line(List.of(of(200, 0), of(123, 1), of(100, 2))),
                        Line.line(List.of(of(300, 0), empty(1), of(100, 2)))
                )),
                Group.fromSingleValue(Line.line(List.of(of(1, 0), of(1, 1)))),
                Group.fromSingleValue(Line.line(List.of(of(79076513686L, 0)))),
                Group.fromSingleValue(Line.line(List.of(empty(0), empty(1))))
        );
        List<Group> actual = StringHandler.getStat(input::stream);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }
}
