package jp.co.cyberagent.aeromock.brew

/**
 * Exception when failed to execute task.
 * @author stormcat24
 */
class AeromockBrewJobFailedException extends RuntimeException {

    AeromockBrewJobFailedException(String message) {
        super(message)
    }

    AeromockBrewJobFailedException(String message, Throwable throwable) {
        super(message, throwable)
    }
}
