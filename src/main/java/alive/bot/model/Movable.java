package alive.bot.model;


import alive.field.Field;

public interface Movable {

    /**
     * Calls when bot should make a move.
     *
     * @param field main field
     */
    void makeAMove(Field field);
}
