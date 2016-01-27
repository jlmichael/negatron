package negatron.adaptor.impl;

import negatron.NegatronBrain;
import negatron.adaptor.NegatronAdaptor;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

public class IRCNegatron extends PircBot implements NegatronAdaptor {
    private NegatronBrain brain = new NegatronBrain(this);

    public IRCNegatron() {
        this.setName("Negatron");
    }

    @Override
    public void init() {
        try {
            connect("chat.freenode.net");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IrcException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        setVerbose(true);
        joinChannel("#negatron");
        sendMessage("#negatron", "Hi guys.  *sigh*");

    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        brain.onMessage(channel, sender, message);
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        brain.onPrivateMessage(sender, message);
    }

    @Override
    public void sendMessageToChannel(String channel, String message) {
        sendMessage(channel, message);
    }

    @Override
    public void sendMessageToUser(String user, String message) {
        sendMessage(user, message);
    }
}
