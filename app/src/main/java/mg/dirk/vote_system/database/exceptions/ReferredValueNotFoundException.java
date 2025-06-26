package mg.dirk.vote_system.database.exceptions;

import mg.dirk.vote_system.database.utils.ForeignKeyInfo;

public class ReferredValueNotFoundException extends Exception {
    public ReferredValueNotFoundException(ForeignKeyInfo foreignKeyInfo) {
        super(String.format("No referred value found for foreign key (%s %s) (%s %s)",
                foreignKeyInfo.getMainClass().getName(), foreignKeyInfo.getForeignKeyMainClassMethod().getName(),
                foreignKeyInfo.getTargetClass().getName(), foreignKeyInfo.getForeignKeyTargetClassMethod().getName()));
    }
}
