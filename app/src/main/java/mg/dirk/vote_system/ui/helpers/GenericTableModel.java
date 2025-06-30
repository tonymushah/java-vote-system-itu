package mg.dirk.vote_system.ui.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import mg.dirk.vote_system.reflect.ReflectUtils;

public class GenericTableModel implements TableModel {
    private Object[] objects;

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public GenericTableModel(Object[] objects) {
        super();
        this.setObjects(objects);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'addTableModelListener'");
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (objects.length == 0) {
            return String.class;
        } else {
            return objects[0].getClass().getDeclaredFields()[columnIndex].getType();
        }
    }

    @Override
    public int getColumnCount() {
        if (objects.length == 0) {
            return 0;
        }
        return objects[0].getClass().getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return objects[0].getClass().getDeclaredFields()[columnIndex].getName();
    }

    @Override
    public int getRowCount() {
        return objects.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object to_use = objects[rowIndex];
        try {
            Method getter = ReflectUtils.getFieldGetter(to_use.getClass(),
                    to_use.getClass().getDeclaredFields()[columnIndex]);
            return getter.invoke(to_use);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'removeTableModelListener'");
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // throw new UnsupportedOperationException("Unimplemented method 'setValueAt'");
    }

}
