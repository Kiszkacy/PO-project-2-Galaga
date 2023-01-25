package galaga.util;

import java.util.Objects;

/**
 * Container holding two objects of predefined types, otherwise known as a two elemental tuple.
 * @param <A> type of the first element
 * @param <B> type of the second element
 */
public class Pair<A, B> {

    public A first;
    public B second;

    // overrides

    @Override
    public String toString() {
        return String.format("<%s, %s>", this.first, this.second);
    }

    // constructors

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    // hash & equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(this.first, pair.first) && Objects.equals(this.second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }
}
