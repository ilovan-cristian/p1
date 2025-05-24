package copilot12354.mpp.utils;

public class NetworkingException extends RuntimeException
{
    public NetworkingException()
    {
        super();
    }

    public NetworkingException(String message)
    {
        super(message);
    }

    public NetworkingException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
