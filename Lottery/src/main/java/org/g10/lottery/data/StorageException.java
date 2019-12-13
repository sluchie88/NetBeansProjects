package org.g10.lottery.data;

public class StorageException extends Exception {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable innerEx) {
        super(message, innerEx);
    }
}
