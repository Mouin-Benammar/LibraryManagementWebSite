package com.ensta.librarymanager.exception;

import java.sql.SQLException;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }
}
