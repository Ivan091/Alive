package alive.entity;

public interface Visitable {

    default void accept(Visitor visitor) {
        visitor.visit(this);
    }
}