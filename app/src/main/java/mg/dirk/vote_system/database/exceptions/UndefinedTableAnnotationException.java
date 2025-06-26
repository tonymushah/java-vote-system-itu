package mg.dirk.vote_system.database.exceptions;

public class UndefinedTableAnnotationException extends Exception {
    private Class<? extends Object> targetClass;

    public Class<? extends Object> getTargetClass() {
        return targetClass;
    }

    public UndefinedTableAnnotationException(Class<? extends Object> targetClass) {
        super(String.format("The class %s doesn't have the @Table annotation", targetClass.getName()));
        this.targetClass = targetClass;
    }

}
