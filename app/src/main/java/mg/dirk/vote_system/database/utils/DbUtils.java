package mg.dirk.vote_system.database.utils;

import java.lang.reflect.Method;

import mg.dirk.vote_system.database.annotations.Table;
import mg.dirk.vote_system.database.annotations.PrimaryKey;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;

public class DbUtils {
    public static void isValidTable(Class<? extends Object> class1)
            throws UndefinedTableAnnotationException, UndefinedPrimaryKeyException {
        if (!class1.isAnnotationPresent(Table.class)) {
            throw new UndefinedTableAnnotationException(class1);
        } else {
            int primary_keys = 0;
            for (Method method : class1.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PrimaryKey.class)) {
                    primary_keys = primary_keys + 1;
                }
            }
            if (primary_keys != 1) {
                throw new UndefinedPrimaryKeyException(class1);
            }
        }
    }

    public static Method getPrimaryKeyMethod(Class<? extends Object> targetClass)
            throws UndefinedPrimaryKeyException, UndefinedTableAnnotationException {
        isValidTable(targetClass);
        for (Method method : targetClass.getMethods()) {
            if (method.isAnnotationPresent(PrimaryKey.class)) {
                return method;
            }
        }
        throw new UndefinedPrimaryKeyException(targetClass);
    }

    public static Method getForeignKeyMethod(Class<? extends Object> targetClass, String key) {
        throw new RuntimeException("Not yet implemented");
    }
}
