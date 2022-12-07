package hr.fer.oprpp1.hw04.db;

/**
 * Interface for filtering student records.
 */
public interface IFilter {
    /**
     * @param record Student record to be checked
     * @return true if record satisfies the filter, false otherwise
     */
    boolean accepts(StudentRecord record);
}
