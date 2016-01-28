package negatron.adaptor.impl;

import com.fasterxml.jackson.databind.JsonNode;
import flowctrl.integration.slack.SlackClientFactory;
import flowctrl.integration.slack.rtm.Event;
import flowctrl.integration.slack.rtm.EventListener;
import flowctrl.integration.slack.rtm.SlackRealTimeMessagingClient;
import flowctrl.integration.slack.type.DirectMessageChannel;
import flowctrl.integration.slack.type.User;
import flowctrl.integration.slack.webapi.SlackWebApiClient;
import negatron.NegatronBrain;
import negatron.adaptor.NegatronAdaptor;

import java.util.List;

public class SlackNegatron implements NegatronAdaptor {

    private static final String SLACK_TOKEN_KEY = "NEGATRON_SLACK_TOKEN";
    private static final String DEFAULT_SLACK_TOKEN = "Well this will never work...";

    private String slackToken;
    private NegatronBrain brain = new NegatronBrain(this);
    private SlackRealTimeMessagingClient rtmClient;
    private SlackWebApiClient webApiClient;
    private String myId;

    @Override
    public void init() {
        slackToken = System.getenv().getOrDefault(SLACK_TOKEN_KEY, DEFAULT_SLACK_TOKEN);
        webApiClient = SlackClientFactory.createWebApiClient(slackToken);
        for (User user : webApiClient.getUserList()) {
            if(user.getName().equals("negatron")) {
                myId = user.getId();
            }
        }
        rtmClient = SlackClientFactory.createSlackRealTimeMessagingClient(slackToken);
        rtmClient.addListener(Event.MESSAGE, new EventListener() {
            @Override
            public void handleMessage(JsonNode message) {
                System.out.println(message.toString());
                String type = message.get("type").asText();
                String channel = message.get("channel").asText();
                String user = message.get("user").asText();
                String text = message.get("text").asText();

                // Skip messages from us.
                if (user.equals(myId)) {
                    return;
                }
                boolean isIM = false;
                if (type.equals("message")) {
                    // Check if this is a direct/im channel.
                    List<DirectMessageChannel> ims = webApiClient.getDirectMessageChannelList();
                    for (DirectMessageChannel im : ims) {
                        if (im.getId().equals(channel)) {
                            brain.onPrivateMessage(user, text);
                            isIM = true;
                            break;
                        }
                    }
                    if (!isIM) {
                        brain.onMessage(channel, user, text);
                    }
                }
            }
        });
        rtmClient.connect();
    }

    @Override
    public void sendMessageToChannel(String channel, String message) {
        webApiClient.postMessage(channel, message, "Negatron", true);
    }

    @Override
    public void sendMessageToUser(String user, String message) {
        webApiClient.postMessage(user, message, "Negatron", true);
    }
}
