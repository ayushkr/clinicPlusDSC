package in.srisrisri.clinic.exception;

public class CustomException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CustomException()
    {
        super();
    }

    public CustomException(final String message)
    {
        super(message);
    }

    public CustomException(final Throwable databaseExceptionThrowable)
    {
        super(databaseExceptionThrowable);
    }

    public CustomException(final String message, final Throwable throwable)
    {
        super(message, throwable);
    }
}
