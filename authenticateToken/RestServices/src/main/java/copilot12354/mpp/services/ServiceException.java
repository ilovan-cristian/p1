package copilot12354.mpp.services;

public class ServiceException extends RuntimeException
{
    public ServiceException(Exception e)
    {
        super(e);
    }
    public ServiceException(String message)
    {
        super(message);
    }
}
