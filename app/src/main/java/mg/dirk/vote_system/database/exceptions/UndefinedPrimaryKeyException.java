package mg.dirk.vote_system.database.exceptions;

public class UndefinedPrimaryKeyException extends Exception {
    private Class<? extends Object> targetClass;

    public Class<? extends Object> getTargetClass() {
        return targetClass;
    }

    public UndefinedPrimaryKeyException(Class<? extends Object> targetClass) {
        super(String.format("The class %s need to define one @PrimaryKey method", targetClass.getName()));
        this.targetClass = targetClass;
    }
}
