package dev.lampirg;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

class LinkedStrings {

    final List<IdentityKey> string;
    Collection<LinkedStrings> values;

    public LinkedStrings(List<IdentityKey> string) {
        this.string = string;
        values = new HashSet<>();
        values.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedStrings that = (LinkedStrings) o;
        return string.equals(that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

    @Override
    public String toString() {
        return string.stream().map(IdentityKey::toString).collect(joining(";"));
    }
}
