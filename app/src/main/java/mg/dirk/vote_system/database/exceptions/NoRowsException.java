package mg.dirk.vote_system.database.exceptions;

public class NoRowsException extends Exception {
    private Class<? extends Object> targetClass;

    public Class<? extends Object> getTargetClass() {
        return targetClass;
    }

    public NoRowsException(Class<? extends Object> targetClass) {
        super(String.format("No Rows available for %s", targetClass.getName()));
        this.targetClass = targetClass;
    }
}
