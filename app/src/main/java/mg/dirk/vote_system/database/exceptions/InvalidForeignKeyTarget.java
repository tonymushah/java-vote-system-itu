package mg.dirk.vote_system.database.exceptions;

import mg.dirk.vote_system.database.annotations.ForeignKey;

public class InvalidForeignKeyTarget extends Exception {
    private Class<? extends Object> mainClass;
    private ForeignKey key;

    public Class<? extends Object> getMainClass() {
        return mainClass;
    }

    public ForeignKey getKey() {
        return key;
    }

    public InvalidForeignKeyTarget(Class<? extends Object> mainClass, ForeignKey key) {
        super(String.format("Invalid foreign key for %s to %s with key %s", mainClass.getName(),
                key.targetClass().getName(), key.getter()));
        this.key = key;
        this.mainClass = mainClass;
    }
}
