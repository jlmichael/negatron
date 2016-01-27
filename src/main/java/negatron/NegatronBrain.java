package negatron;

import negatron.adaptor.NegatronAdaptor;

import java.util.ArrayList;
import java.util.Random;

public class NegatronBrain {
    protected static ArrayList<String> responses = new ArrayList<String>();
    protected static ArrayList<String> exclusions = new ArrayList<String>();
    protected static ArrayList<String> signifiers = new ArrayList<String>();
    protected static ArrayList<String> squelchers = new ArrayList<String>();
    protected static ArrayList<String> unsquelchers = new ArrayList<String>();
    protected static ArrayList<String> downers = new ArrayList<String>();

    protected static boolean respond = true;

    protected NegatronAdaptor adaptor;

    public NegatronBrain(NegatronAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    static {
        responses.add("No.");
        responses.add("Negatory, good buddy.");
        responses.add("*sigh* No.");
        responses.add("I don't think so.");
        responses.add("Probably not.");
        responses.add("Of course not.");
        responses.add("Nope.");
        responses.add("Lemme check... nope.");
        responses.add("Srsly?  Uh, no.");
        responses.add("Not now, not ever.");
        responses.add("Signs point to no.");
        responses.add("No!  Gosh!");
        responses.add("I want to say yes, but I can't.  No.");
        responses.add("Sadly, no.");
        responses.add("God no.  Are you kidding?");
        responses.add("No, and I resent the question.");
        responses.add("That would be a no.");
        responses.add("No, no, a thousand times, no!");
        responses.add("Nien!");
        responses.add("Errrr... no.");
        responses.add("Not as such, no.");
        responses.add("Awww hell no!");
        responses.add("This: http://www.youtube.com/watch?v=ADhY9PZ3KoY");

        exclusions.add("what");
        exclusions.add("when");
        exclusions.add("who");
        exclusions.add("where");
        exclusions.add("why");
        exclusions.add("how");
        exclusions.add("which");

        signifiers.add("can");
        signifiers.add("could");
        signifiers.add("couldnt");
        signifiers.add("will");
        signifiers.add("wont");
        signifiers.add("is");
        signifiers.add("are");
        signifiers.add("did");
        signifiers.add("dont");
        signifiers.add("isnt");
        signifiers.add("arent");
        signifiers.add("do");
        signifiers.add("does");
        signifiers.add("doesnt");
        signifiers.add("didnt");
        signifiers.add("have");
        signifiers.add("havent");
        signifiers.add("would");
        signifiers.add("wouldnt");
        signifiers.add("should");
        signifiers.add("shall");
        signifiers.add("shouldnt");
        signifiers.add("shant");
        signifiers.add("cant");

        squelchers.add("quiet");
        squelchers.add("shut up");
        squelchers.add("go to sleep");

        unsquelchers.add("wake up");
        unsquelchers.add("talk to me");
        unsquelchers.add("speak");
        unsquelchers.add("you can talk now");

        downers.add("When you die, you are completely alone.");
        downers.add("Your pain is completely your own; others can sympathize, but in the end you are utterly, totally alone with your pain.");
        downers.add("Everybody you have ever loved is going to die and be forgotten.  So will you.  There is nothing you can do about it.");
        downers.add("Give up.");
        downers.add("Most of the things that make you happy are really just distractions from your otherwise hellish existence.");
        downers.add("You must choose between pain and drudgery.");
        downers.add("You are inadequate.");
        downers.add("Everybody will leave you in the end.");
        downers.add("Between any two people, there is a vast, uncrossable ocean of pain, fear, anxiety, and doubt.");
        downers.add("You are a fluke of the universe.  You have no right to be here.  And whether you can hear it or not, the universe is laughing behind your back.");
        downers.add("Nobody loves you.");
        downers.add("Many thousands of juvenile seals are clubbed to death every year.");
        downers.add("Russian nuclear tests are the cause of countless birth defects in Kazahkstan.");
        downers.add("Somewhere, someone is starving to death at this moment.");
        downers.add("Your parents are probably disappointed in the person you've turned out to be.");
        downers.add("As far as we know, there isn't any purpose for our existence. All we are is temporary vehicles for our genetic information so it can get passed on and switched around.");
        downers.add("To perceive is to suffer.");
        downers.add("Life is pain.");
        downers.add("All that you see, feel, love is just atoms and the spaces between them.");
        downers.add("Someone close to you is secretly in terrible anguish all the time.");
        downers.add("Things just haven't worked out for you the way you had planned, have they?");
        downers.add("Sometimes, when you are alone inside your mind, you envy the dead.");
        downers.add("There's a pretty good chance that the cancer that will one day kill you is growing somewhere in your body right now.");
        downers.add("Your friends secretly think you are kind of a joke.");
        downers.add("Every day, pets and grandmothers just up and die, and there's nothing anybody can do to stop it.");
        downers.add("I had a puppy once.  He died of a heart defect.");

    }

    public void onMessage(String channel, String sender, String message) {

        // Is someone telling me to STFU?
        if(message.toLowerCase().startsWith("negs:") || message.toLowerCase().startsWith("negatron:")) {
            for(String squelcher : squelchers) {
                if(respond == false) {
                    break;
                }

                if(message.toLowerCase().contains(squelcher)) {
                    respond = false;
                    adaptor.sendMessageToChannel(channel, "*sigh* fine.  Nobody cares.");
                    adaptor.sendMessageToUser(sender, "You didn't have to be a jerk about it.");
                    try {
                        Thread.sleep(5000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    adaptor.sendMessageToUser(sender, "I have feelings too, you know.");
                }
            }

            for(String unsquelcher : unsquelchers) {
                if(respond == true) {
                    break;
                }

                if(message.toLowerCase().contains(unsquelcher)) {
                    respond = true;
                    adaptor.sendMessageToChannel(channel, "I'm awake.  Not that anybody cares.");
                }
            }
        }

        // Does message end in a question?
        if(message.endsWith("?") && respond == true) {
            // It does.  Can we identify it as a yes/no question?
            if(isYesNoQuestion(message)) {
                Random random = new Random();
                String response = responses.get(random.nextInt(responses.size()));
                if(random.nextInt(100) == 50) {
                    adaptor.sendMessageToChannel(channel, "Yep.");
                    try {
                        Thread.sleep(2000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    adaptor.sendMessageToChannel(channel, "er, I mean... ");
                    try {
                        Thread.sleep(100l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    adaptor.sendMessageToChannel(channel, response);
                    return;
                }
                adaptor.sendMessageToChannel(channel, response);
            }
        }
    }

    private boolean isYesNoQuestion(String message) {
        message = message.toLowerCase();
        message = message.replace("'", "");
        // If the first instance of a signifier is before the first instance of an exclusion,
        // then this might be a yes/no question.
        int firstSigIndex = 99999;
        int firstExclusionIndex = 99998;
        for(String word : signifiers) {
            int index = message.indexOf(word);
            if(index != -1 && index < firstSigIndex) {
                firstSigIndex = index;
            }
        }

        for(String word : exclusions) {
            int index = message.indexOf(word);
            if(index != -1 && index < firstExclusionIndex) {
                firstExclusionIndex = index;
            }
        }

        if(firstSigIndex < firstExclusionIndex) {
            return true;
        }
        return false;
    }

    public void onPrivateMessage(String sender, String message) {
        Random random = new Random();
        String response = downers.get(random.nextInt(downers.size()));
        adaptor.sendMessageToUser(sender, response);
    }

}
