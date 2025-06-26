package mg.dirk.vote_system.database.utils;

import java.lang.reflect.Method;

import mg.dirk.vote_system.database.annotations.ForeignKey;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;

public class ForeignKeyInfo {
    private Class<? extends Object> mainClass;
    private Class<? extends Object> targetClass;
    private Method foreignKeyMainClassMethod;
    private Method foreignKeyTargetClassMethod;

    public Class<? extends Object> getMainClass() {
        return mainClass;
    }

    public Class<? extends Object> getTargetClass() {
        return targetClass;
    }

    public Method getForeignKeyMainClassMethod() {
        return foreignKeyMainClassMethod;
    }

    public Method getForeignKeyTargetClassMethod() {
        return foreignKeyTargetClassMethod;
    }

    public ForeignKeyInfo(Class<? extends Object> mainClass, String key) throws NoSuchMethodException,
            UndefinedPrimaryKeyException, UndefinedTableAnnotationException, InvalidForeignKeyTarget {
        Method maybeKey = mainClass.getMethod(key);
        if (maybeKey.isAnnotationPresent(ForeignKey.class)) {
            ForeignKey foreignKey = maybeKey.getAnnotation(ForeignKey.class);
            Method foreignKeyTargetClassMethod;
            try {
                if (foreignKey.getter().isEmpty()) {
                    foreignKeyTargetClassMethod = DbUtils.getPrimaryKeyMethod(foreignKey.targetClass());
                } else {
                    foreignKeyTargetClassMethod = foreignKey.targetClass().getMethod(foreignKey.getter());
                }
            } catch (NoSuchMethodException e) {
                throw new InvalidForeignKeyTarget(mainClass, foreignKey);
            }
            this.foreignKeyMainClassMethod = maybeKey;
            this.foreignKeyTargetClassMethod = foreignKeyTargetClassMethod;
            this.targetClass = foreignKey.targetClass();
            this.mainClass = mainClass;
        } else {
            throw new NoSuchMethodException();
        }
    }
}
