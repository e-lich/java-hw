package hr.fer.oprpp1.hw04.db;

/**
 * Interface for getting field value from {@link StudentRecord}.
 */
public interface IFieldValueGetter {
    /**
     * @param record Student record from which to get field value
     * @return Field value from given student record
     */
    String get(StudentRecord record);
}
