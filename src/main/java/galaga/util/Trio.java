package galaga.util;

import java.util.Objects;

/**
 * Container holding three objects of predefined types, otherwise known as a three elemental tuple.
 * @param <A> type of the first element
 * @param <B> type of the second element
 * @param <C> type of the third element
 */
public class Trio<A, B, C> {

    public A first;
    public B second;
    public C third;

    // overrides

    @Override
    public String toString() {
        return String.format("<%s, %s, %s>", this.first, this.second, this.third);
    }

    // constructors

    public Trio(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    // hash & equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trio<?, ?, ?> trio = (Trio<?, ?, ?>) o;
        return Objects.equals(this.first, trio.first) && Objects.equals(this.second, trio.second) && Objects.equals(this.third, trio.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second, this.third);
    }
}
