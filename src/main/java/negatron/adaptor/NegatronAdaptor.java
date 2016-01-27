package negatron.adaptor;

public interface NegatronAdaptor {

    public void init();
    public void sendMessageToChannel(String channel, String message);
    public void sendMessageToUser(String user, String message);
}
