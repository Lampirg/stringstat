import dev.lampirg.logic.StringHandler;
import dev.lampirg.logic.entities.Group;
import dev.lampirg.logic.entities.line.Column;
import dev.lampirg.logic.entities.line.Line;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
                "\"\";\"\"",
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
                Group.fromSingleValue(Line.line(List.of(empty(0), empty(1)))),
                Group.fromSingleValue(Line.line(List.of(empty(0), empty(1))))
        );
        List<Group> actual = StringHandler.getStat(input::stream);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void givenOneGroup() {
        List<String> input = List.of(
                "\"\";\"79292555495\";\"79089502715\";\"79298764951\";\"79965561738\"",
                "\"79091456279\";\"79815345729\";\"79451948366\";\"79513020603\";\"79965561738\";\"79952059257\";\"79710610940\";\"79189701474\";\"79455321178\";\"79986590996\"",
                "\"79968291348\";\"79296249560\";\"79105266708\";\"79298764951\";\"79370765713\";\"79301795506\""
        );
        List<Group> expected = List.of(
                Group.from(List.of(
                        lineFromStrings(-1, 79292555495L, 79089502715L, 79298764951L, 79965561738L),
                        lineFromStrings(79091456279L, 79815345729L, 79451948366L, 79513020603L, 79965561738L, 79952059257L, 79710610940L, 79189701474L, 79455321178L, 79986590996L),
                        lineFromStrings(79968291348L, 79296249560L, 79105266708L, 79298764951L, 79370765713L, 79301795506L)
                ))
        );
        List<Group> actual = StringHandler.getStat(input::stream);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void givenTwoLinesWithEmptyColumn() {
        List<String> input = List.of(
                "\"\";\"79292555495\"",
                "\"\";\"79815345729\""
        );
        List<Group> expected = List.of(
                Group.fromSingleValue(lineFromStrings(-1, 79292555495L)),
                Group.fromSingleValue(lineFromStrings(-1, 79815345729L))
        );
        List<Group> actual = StringHandler.getStat(input::stream);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void givenNoQuotes() {
        List<String> input = List.of(
                ";;",
                "\"\";\"79815345729\""
        );
        List<Group> expected = List.of(
                Group.fromSingleValue(lineFromStrings(-1, -1, -1)),
                Group.fromSingleValue(lineFromStrings(-1, 79815345729L))
        );
        List<Group> actual = StringHandler.getStat(input::stream);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void givenDoubles() {
        List<String> input = List.of(
                ";\"79815345729.5\";\"115.5\"",
                "\"\";\"79815345729.5\"",
                "\"\";\"79815345729.4\""
        );
        List<Group> expected = List.of(
                Group.from(List.of(
                        lineFromStrings(-1, 79815345729.5, 115.5),
                        lineFromStrings(-1, 79815345729.5)
                )),
                Group.fromSingleValue(lineFromStrings(-1, 79815345729.4))
        );
        List<Group> actual = StringHandler.getStat(input::stream);
        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    private static Line lineFromStrings(double... doubles) {
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < doubles.length; i++) {
            columns.add(of(doubles[i], i));
        }
        return Line.line(columns);
    }
}
