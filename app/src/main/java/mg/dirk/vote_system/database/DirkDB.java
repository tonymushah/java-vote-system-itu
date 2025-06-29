package mg.dirk.vote_system.database;

import static mg.dirk.vote_system.reflect.ReflectUtils.getFieldSetter;
import static mg.dirk.vote_system.reflect.ReflectUtils.getFieldValues;
import static mg.dirk.vote_system.reflect.ReflectUtils.getFieldNames;
import static mg.dirk.vote_system.reflect.ReflectUtils.parseString;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.*;

import mg.dirk.vote_system.database.annotations.NoThrowOnParse;
import mg.dirk.vote_system.database.annotations.SkipDeserialization;
import mg.dirk.vote_system.database.annotations.SkipSerialization;
import mg.dirk.vote_system.database.annotations.Table;
import mg.dirk.vote_system.database.exceptions.InvalidForeignKeyTarget;
import mg.dirk.vote_system.database.exceptions.NoRowsException;
import mg.dirk.vote_system.database.exceptions.ReferredValueNotFoundException;
import mg.dirk.vote_system.database.exceptions.UndefinedPrimaryKeyException;
import mg.dirk.vote_system.database.exceptions.UndefinedTableAnnotationException;
import mg.dirk.vote_system.database.utils.DbUtils;
import mg.dirk.vote_system.database.utils.ForeignKeyInfo;

public class DirkDB {
    private HashMap<Class<? extends Object>, List<Object>> tables;

    public HashMap<Class<? extends Object>, List<Object>> getTables() {
        return tables;
    }

    public void setTables(HashMap<Class<? extends Object>, List<Object>> tables) {
        this.tables = tables;
    }

    public DirkDB() {
        this.setTables(new HashMap<Class<? extends Object>, List<Object>>());
    }

    public DirkDB(Class<? extends Object>... classes) {
        this();
        for (Class<? extends Object> class1 : classes) {
            try {
                this.importClass(class1);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | UndefinedTableAnnotationException
                    | UndefinedPrimaryKeyException | IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void importClass(Class<? extends Object> class1) throws UndefinedTableAnnotationException,
            UndefinedPrimaryKeyException, IOException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
        DbUtils.isValidTable(class1);
        Table table = class1.getAnnotation(Table.class);
        CSVFormat format = CSVFormat.DEFAULT.builder().setDelimiter(";").setHeader().setIgnoreEmptyLines(true).get();
        File file = new File(table.file());
        if (!file.exists()) {
            System.out.println(String.format("to create: %s", file.getAbsolutePath()));
            file.createNewFile();
        }

        try (FileReader file_reader = new FileReader(file);
                BufferedReader reader = new BufferedReader(file_reader);
                CSVParser parser = format.parse(reader);) {
            ArrayList<Object> rows = new ArrayList<>();
            for (CSVRecord csvRecord : parser) {
                Object row = class1.getConstructor().newInstance();
                for (Field field : class1.getDeclaredFields()) {
                    if (field.isAnnotationPresent(SkipDeserialization.class)) {
                        continue;
                    }
                    Method setter = getFieldSetter(class1, field);

                    if (field.isAnnotationPresent(NoThrowOnParse.class)) {
                        try {
                            setter.invoke(row, parseString(csvRecord.get(field.getName()), field.getType()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                rows.add(row);
            }
            this.tables.put(class1, rows);
        }

    }

    public void syncFromFiles() throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException, ParseException {
        List<Class<? extends Object>> tables_set = this.tables.keySet().stream().collect(Collectors.toList());
        for (Class<? extends Object> table_class : tables_set) {
            this.importClass(table_class);
        }
    }

    public void saveToFile(Class<? extends Object> class1)
            throws UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        DbUtils.isValidTable(class1);
        Table table = class1.getAnnotation(Table.class);
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(getFieldNames(class1, SkipSerialization.class))
                .setDelimiter(";").get();
        File file = new File(table.file());
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter writer = new FileWriter(file);
                BufferedWriter buf_writer = new BufferedWriter(writer);
                CSVPrinter printer = new CSVPrinter(buf_writer, format);) {
            for (Object record : this.tables.get(class1)) {
                printer.printRecord(getFieldValues(record, SkipSerialization.class));
            }
            printer.flush();
        }
    }

    public void saveToFile() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException, UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException {
        for (Class<? extends Object> table_class : this.tables.keySet()) {
            this.saveToFile(table_class);
        }
    }

    public void insert(Object to_insert) throws UndefinedTableAnnotationException, UndefinedPrimaryKeyException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
        this.insert(to_insert, true);
    }

    public void insertBatch(Object... objects)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException {
        for (Object object : objects) {
            this.insert(object, false);
        }
        this.saveToFile();
    }

    public void insertBatchNoSave(Object... objects)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IOException {
        for (Object object : objects) {
            this.insert(object, false);
        }
    }

    private int get_to_insert_index(Object to_insert) throws NullPointerException, UndefinedPrimaryKeyException,
            UndefinedTableAnnotationException, IllegalAccessException, InvocationTargetException {
        Class<? extends Object> to_insert_class = to_insert.getClass();
        List<Object> maybeRow = this.tables.get(to_insert_class);
        Method primaryKey = DbUtils.getPrimaryKeyMethod(to_insert_class);
        if (maybeRow != null) {
            for (int i = 0; i < maybeRow.size(); i++) {
                if (primaryKey.invoke(to_insert).equals(maybeRow.get(i))) {
                    return i;
                }
            }
        }
        throw new NullPointerException(String.format("No table inserted for %s", to_insert_class.getName()));
    }

    public void insert(Object to_insert, boolean save)
            throws UndefinedTableAnnotationException, UndefinedPrimaryKeyException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
        Class<? extends Object> to_insert_class = to_insert.getClass();
        List<Object> maybeRow = this.tables.get(to_insert_class);
        if (maybeRow != null) {
            try {
                int index = get_to_insert_index(to_insert_class);
                maybeRow.set(index, to_insert);
            } catch (NullPointerException e) {
                maybeRow.add(to_insert);
            }
        } else {
            DbUtils.isValidTable(to_insert_class);
            List<Object> default_ = new ArrayList<>();
            default_.add(to_insert);
            this.tables.put(to_insert_class, default_);
        }
        if (save) {
            this.saveToFile(to_insert_class);
        }
    }

    public <T extends Object> List<T> select(Class<T> to_select_class) throws NoRowsException {
        List<T> list = (List<T>) this.getTables().get(to_select_class);
        if (list == null) {
            throw new NoRowsException(to_select_class);
        } else {
            return list;
        }
    }

    public <T extends Object, U extends Object> List<U> get_relationships(T mainObject, Class<U> targetClass,
            String foreignKey)
            throws NoSuchMethodException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException,
            InvalidForeignKeyTarget, IllegalAccessException, InvocationTargetException, InvalidClassException {
        List<U> toReturns = new ArrayList<U>();
        ForeignKeyInfo foreignKeyInfo = new ForeignKeyInfo(targetClass, foreignKey);
        if (foreignKeyInfo.getTargetClass() != mainObject.getClass()) {
            throw new InvalidClassException(
                    String.format("The target class and the main object class is not equals (%s != %s)",
                            foreignKeyInfo.getTargetClass().getName(), mainObject.getClass().getName()));
        }
        List<U> rows = (List<U>) this.getTables().get(foreignKeyInfo.getMainClass());
        for (U u : rows) {
            Object refData = foreignKeyInfo.getForeignKeyTargetClassMethod().invoke(mainObject);
            assert u.getClass() == foreignKeyInfo.getMainClass();
            Object innerData = foreignKeyInfo.getForeignKeyMainClassMethod().invoke(u);
            if (refData.equals(innerData)) {
                toReturns.add(u);
            }
        }
        return toReturns;
    }

    public <T extends Object, U extends Object> List<U> get_relationships(T[] mainObjects, Class<U> targetClass,
            String foreignKey)
            throws NoSuchMethodException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException,
            InvalidForeignKeyTarget, IllegalAccessException, InvocationTargetException, InvalidClassException {
        List<U> rows = new ArrayList<U>();
        for (T t : mainObjects) {
            rows.addAll(this.get_relationships(t, targetClass, foreignKey));
        }
        return rows;
    }

    public <T extends Object, U extends Object> List<U> get_relationships(List<T> mainObjects, Class<U> targetClass,
            String foreignKey)
            throws NoSuchMethodException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException,
            InvalidForeignKeyTarget, IllegalAccessException, InvocationTargetException, InvalidClassException {
        List<U> rows = new ArrayList<U>();
        for (T t : mainObjects) {
            rows.addAll(this.get_relationships(t, targetClass, foreignKey));
        }
        return rows;
    }

    public <T extends Object, U extends Object> U get_reference(T mainObject, Class<U> targetClass,
            String foreignKey)
            throws NoSuchMethodException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException,
            InvalidForeignKeyTarget, IllegalAccessException, InvocationTargetException, InvalidClassException,
            ReferredValueNotFoundException {
        ForeignKeyInfo foreignKeyInfo = new ForeignKeyInfo(mainObject.getClass(), foreignKey);
        if (foreignKeyInfo.getTargetClass() != targetClass) {
            throw new InvalidClassException(String.format(
                    "The target class and the main object foreign key target class is not equals (%s != %s)",
                    foreignKeyInfo.getTargetClass().getName(), targetClass.getName()));
        }
        for (Object maybeRefObject : tables.get(targetClass)) {
            Object refData = foreignKeyInfo.getForeignKeyTargetClassMethod().invoke(maybeRefObject);
            Object innerData = foreignKeyInfo.getForeignKeyMainClassMethod().invoke(mainObject);
            if (refData.equals(innerData)) {
                return (U) maybeRefObject;
            }
        }
        throw new ReferredValueNotFoundException(foreignKeyInfo);
    }

    public <T extends Object, U extends Object> List<U> get_reference(T[] mainObjects, Class<U> targetClass,
            String foreignKey)
            throws NoSuchMethodException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException,
            InvalidForeignKeyTarget, IllegalAccessException, InvocationTargetException, InvalidClassException,
            ReferredValueNotFoundException {
        List<U> rets = new ArrayList<U>();
        for (T mainObject : mainObjects) {
            rets.add(this.get_reference(mainObject, targetClass, foreignKey));
        }
        return rets;
    }

    public <T extends Object, U extends Object> List<U> get_reference(List<T> mainObjects, Class<U> targetClass,
            String foreignKey)
            throws NoSuchMethodException, UndefinedPrimaryKeyException, UndefinedTableAnnotationException,
            InvalidForeignKeyTarget, IllegalAccessException, InvocationTargetException, InvalidClassException,
            ReferredValueNotFoundException {
        List<U> rets = new ArrayList<U>();
        for (T mainObject : mainObjects) {
            rets.add(this.get_reference(mainObject, targetClass, foreignKey));
        }
        return rets;
    }

    public void truncate(Class<? extends Object> targetClass) {
        List<Object> rows = this.tables.get(targetClass);
        if (rows != null) {
            rows.clear();
        }
    }

    public void clear() {
        this.tables.clear();
    }

    public <T extends Object> T get(Class<T> class1, Object key) throws UndefinedTableAnnotationException,
            UndefinedPrimaryKeyException, IllegalAccessException, InvocationTargetException {
        DbUtils.isValidTable(class1);
        List<T> data = (List<T>) this.getTables().get(class1);
        Method primaryKey = DbUtils.getPrimaryKeyMethod(class1);
        if (data != null) {
            for (T t : data) {
                if (primaryKey.invoke(t).equals(key)) {
                    return t;
                }
            }
        }
        return null;
    }
}
