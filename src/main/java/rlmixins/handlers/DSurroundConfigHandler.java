package rlmixins.handlers;

import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DSurroundConfigHandler {

    private static File configFolder = null;
    private static File dSurroundChatFile = null;
    private static File dSurroundChatTimeFile = null;

    private static final Map<String, String> dSurroundChatMap = new HashMap<>();
    private static final Map<String, int[]> dSurroundChatTimeMap = new HashMap<>();

    private static boolean initDirectory() {
        configFolder = new File("config", RLMixins.MODID);
        if(!configFolder.exists() || !configFolder.isDirectory()) {
            if(!configFolder.mkdir()) {
                RLMixins.LOGGER.log(Level.ERROR, RLMixins.MODID + ": " + "Could not create the folder for configuration.");
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static Map<String, String> getDSurroundChatMap(String lang) {
        if(dSurroundChatFile==null && !initDSurroundChatMap(lang)) return null;
        return dSurroundChatMap;
    }

    @Nullable
    public static Map<String, int[]> getDSurroundChatTimeMap() {
        if(dSurroundChatTimeFile==null && !initDSurroundChatTimeMap()) return null;
        return dSurroundChatTimeMap;
    }

    private static boolean initDSurroundChatMap(String lang) {
        if(configFolder==null && !initDirectory()) return false;
        String fileName = "dsurround_chat" + ((lang.equals("en_us") || lang.isEmpty()) ? "" : ("_" + lang)) + ".cfg";
        File dSurroundLangChatFile = new File(configFolder, fileName);
        if(dSurroundLangChatFile.exists()) dSurroundChatFile = dSurroundLangChatFile;
        else dSurroundChatFile = new File(configFolder, "dsurround_chat.cfg");
        try {
            if(!dSurroundChatFile.exists()) {
                if(!dSurroundChatFile.createNewFile()) {
                    RLMixins.LOGGER.log(Level.ERROR, RLMixins.MODID + ": " + "Failed to create new DynamicSurroundings Chat file.");
                    return false;
                }
                else Files.write(dSurroundChatFile.toPath(),
                        (       "# Format:\n" +
                                "#\n" +
                                "# messageId=weight,text\n" +
                                "#\n" +
                                "# messageId: Must be prefixed with \"chat.\" and terminated with a sequential number (e.g. \".0\")  The\n" +
                                "#            stuff in between is the Entity class name registered in EntityList.  (villager.flee is\n" +
                                "#            a special case).\n" +
                                "#\n" +
                                "# weight:    Relative weight of the entry being selected from the weight table.  The higher the number\n" +
                                "#            the greater chance of selection vs. its peers.\n" +
                                "#\n" +
                                "# text:      The stuff that gets displayed in the client.  Though the server side makes the message\n" +
                                "#            selection language translation does not occur until it get's client side.\n" +
                                "#\n" +
                                "chat.villager.0=10,I like living in Dynamic Surroundings.\n" +
                                "chat.villager.1=15,I used to be a miner until I took a creeper to the face.\n" +
                                "chat.villager.2=20,Don't fear the creepers.  Be terrified!\n" +
                                "chat.villager.3=20,Beet soup for dinner.  Again.\n" +
                                "chat.villager.4=10,Villager TV has a decent program on tonight.  It's called Credits.\n" +
                                "chat.villager.5=10,Only one thing you can do about zombies - run!\n" +
                                "chat.villager.6=15,Why doesn't anyone listen to me?\n" +
                                "chat.villager.7=10,Is this really worth the server ticks?\n" +
                                "chat.villager.8=15,A haircut... A Testificate's dream...\n" +
                                "chat.villager.9=15,Cake isn't a lie!  Well, mostly...\n" +
                                "chat.villager.10=20,Of all the modpacks in the world I had to spawn in this one.\n" +
                                "chat.villager.11=20,I could have had hands, but the designers were lazy.\n" +
                                "chat.villager.12=15,Nothing up my sleeves.  No, really.  I mean that.\n" +
                                "chat.villager.13=15,Never spend your diamonds on a hoe.  I read that somewhere.\n" +
                                "chat.villager.14=15,Did you see Villager Number 9 on TV?\n" +
                                "chat.villager.15=20,...and there was this time I ate a poisoned potato...\n" +
                                "chat.villager.16=10,This isn't my nose, it's a false one.\n" +
                                "chat.villager.17=15,I would have had more in stock but a skeleton shot my supplier.\n" +
                                "chat.villager.18=10,When nature calls you have to go, and fast!\n" +
                                "chat.villager.19=10,I got no respect around here.\n" +
                                "chat.villager.20=15,Of all the foods they could have added why did it have to be beets?\n" +
                                "chat.villager.21=20,Tell Mojang that we, the Testificates, need chairs!\n" +
                                "chat.villager.22=15,It could have been worse.  We could be growing brussel sprouts rather than beets.\n" +
                                "chat.villager.23=10,Gazebo!\n" +
                                "chat.villager.24=15,Klaatu... Verata... Necktie!\n" +
                                "chat.villager.25=10,So much rotten flesh! Not so many ideas...\n" +
                                "chat.villager.26=20,I'm sorry.  It's my implementation.\n" +
                                "chat.villager.27=15,I wish I had a voice like Villager Number 4.\n" +
                                "chat.villager.28=20,Yes, it's true.  I use mental powers to harvest crops.\n" +
                                "chat.villager.29=5,Tell OreCruncher that we need better lines.\n" +
                                "chat.villager.30=10,I found a book titled \"FOOBIE BLETCH\".  I wish I knew what that meant.\n" +
                                "chat.villager.31=5,Hey! Eyes up here!\n" +
                                "chat.villager.32=10,On the plus side we don't have flatulating cows.\n" +
                                "chat.villager.33=15,Let's play the quiet game.\n" +
                                "chat.villager.34=15,Who is this Squidward everyone keeps talking about?\n" +
                                "chat.villager.35=20,The union said we had to trade flint for gravel and an emerald.\n" +
                                "chat.villager.36=15,This is, like, the opposite of what I wanted to do today.\n" +
                                "chat.villager.37=15,My torture continues.\n" +
                                "chat.villager.38=15,If it weren't for bad luck I'd have no luck at all.\n" +
                                "chat.villager.39=10,Oh, I'm sorry.  I forgot I only exist when you need something.\n" +
                                "chat.villager.40=15,A day without sunshine is like night.\n" +
                                "chat.villager.41=20,Drawing on my fine command of language, I said nothing.\n" +
                                "chat.villager.42=15,I used to be indecisive. Now I'm not sure.\n" +
                                "chat.villager.43=10,Silence is golden, unless you have kids, then silence is just plain suspicious.\n" +
                                "chat.villager.44=15,Actually, the Nether Star is the Wither's Calcified Ball of Hatred.\n" +
                                "chat.villager.45=5,...and then I found out I could go blind so I stopped.\n" +
                                "chat.villager.46=15,I lost my copy of Better Blocks and Mines.  You wouldn't happen to have a copy?\n" +
                                "chat.villager.47=10,If you have nether warts I can sell you a potion to cure it.\n" +
                                "chat.villager.48=20,You tell the best stories.\n" +
                                "chat.villager.49=15,I was fired because I forgot to put a cover sheet on a TPS report.\n" +
                                "chat.villager.50=10,I always wanted to be somebody, but now I realize I should have been more specific.\n" +
                                "chat.villager.51=15,I never said most of the things I said.\n" +
                                "chat.villager.52=15,Impressive.\n" +
                                "chat.villager.53=15,Any problem can be solved with the suitable application of redstone and pumpkin pie.\n" +
                                "chat.villager.54=10,\"First... cut my son's buns!\"  That was a funny.\n" +
                                "chat.villager.55=10,Yeah.\n" +
                                "chat.villager.56=15,...and I drank this stuff called Fel Slider Cider.  It was a mistake...\n" +
                                "chat.villager.57=10,The axe forgets; the tree remembers.\n" +
                                "chat.villager.58=15,Never dig straight down.\n" +
                                "chat.villager.59=10,Don't Panic.\n" +
                                "chat.villager.60=5,More cow bell!\n" +
                                "chat.villager.61=15,Well that happened.\n" +
                                "chat.villager.62=15,This is perplexing.\n" +
                                "chat.villager.63=15,Yay!  A friend!\n" +
                                "chat.villager.64=10,I'm saying this now so I can have a flashback later.\n" +
                                "chat.villager.65=5,Of course he'd do it.  He had plot armor!\n" +
                                "\n" +
                                "chat.villager.flee.0=20,Run away! Run away!\n" +
                                "chat.villager.flee.1=20,!!! HELP !!!\n" +
                                "chat.villager.flee.2=5,I want my Parental Unit!\n" +
                                "chat.villager.flee.3=15,Testificate Man! We need you!\n" +
                                "chat.villager.flee.4=10,I'm going to need a clothing change!\n" +
                                "chat.villager.flee.5=15,Feet don't fail me now!\n" +
                                "chat.villager.flee.6=10,I wish I was programmed to fight!\n" +
                                "chat.villager.flee.7=15,Too bad I failed that online defense course!\n" +
                                "chat.villager.flee.8=5,If only I had a wheelbarrow and a holocaust cloak!\n" +
                                "chat.villager.flee.9=15,I have no brain! Why are you chasing me?!?\n" +
                                "chat.villager.flee.10=5,Seems like the Benny Hill theme song would be appropriate about now.\n" +
                                "chat.villager.flee.11=10,He's got an arm off!\n" +
                                "chat.villager.flee.12=15,It's every villager for themselves!\n" +
                                "chat.villager.flee.13=10,Wow, these fellas really let themselves go.\n" +
                                "chat.villager.flee.14=15,It seems I was doing this very thing yesterday.\n" +
                                "chat.villager.flee.15=5,And that's our cue to ski-doo!\n" +
                                "chat.villager.flee.16=10,Exit! Stage left!\n" +
                                "chat.villager.flee.17=10,You figure this village would have had walls by now.\n" +
                                "chat.villager.flee.18=10,Somebody set up us the bomb!\n" +
                                "chat.villager.flee.19=10,Shop smart! Shop S-Mart!\n" +
                                "\n" +
                                "chat.zombie.0=25,BBBBBRRRRRAAAAAIIIIIINNNNNNSSSS!\n" +
                                "chat.zombie.1=20,Grrrrrr\n" +
                                "chat.zombie.2=10,I need a better gig.\n" +
                                "chat.zombie.3=5,In short, in matters vegetable, animal, and mineral, I am the very model of a modern Major-General.\n" +
                                "chat.zombie.4=10,Anyone have a throat lozenge?\n" +
                                "chat.zombie.5=10,** Sigh **\n" +
                                "chat.zombie.6=5,Stay away from the light!\n" +
                                "chat.zombie.7=10,Can't resist! Must...chase...villager!\n" +
                                "chat.zombie.8=15,My arms are getting tired.\n" +
                                "chat.zombie.9=10,Oh no!  Not again!\n" +
                                "chat.zombie.10=5,All your base are belong to us\n" +
                                "chat.zombie.11=10,Come get some!\n" +
                                "chat.zombie.12=10,Groovy!\n" +
                                "chat.zombie.13=10,Hey kids you remember i'm a professional, don't try this at home!\n" +
                                "chat.zombie.14=10,So many villagers, so little time.\n" +
                                "chat.zombie.15=10,Everything's better with zombies!\n" +
                                "chat.zombie.16=10,Now that I've introduced my self I should like to know what's going on here?\n" +
                                "\n" +
                                "chat.skeleton.0=10,The short fortune teller who escaped from prison was a small medium at large.\n" +
                                "chat.skeleton.1=10,I don't know where I keep my arrows.  It's a mystery, like villagers having no hands.\n" +
                                "chat.skeleton.2=15,It's not like I had a choice or anything.\n" +
                                "chat.skeleton.3=5,What? You expected intelligent conversation?\n" +
                                "chat.skeleton.4=15,I just got lost in thought. It was unfamiliar territory.\n" +
                                "chat.skeleton.5=15,If everything seems to be going well, you have obviously overlooked something.\n" +
                                "chat.skeleton.6=15,I was trying to daydream, but my mind kept wandering.\n" +
                                "chat.skeleton.7=10,Whoever said nothing was impossible obviously never tried slamming a revolving door.\n" +
                                "chat.skeleton.8=10,Knowledge is realizing that the street is one-way, wisdom is looking both directions anyway.\n" +
                                "chat.skeleton.9=10,You keep using that word.  I don't think it means what you think it means.\n" +
                                "chat.skeleton.10=10,Chicken!  Fight like a skeleton!\n" +
                                "chat.skeleton.11=10,Oh,look. There's some lovely filth over here.\n" +
                                "chat.skeleton.12=5,If you cannot get rid of the family skeleton, you may as well make it dance.\n" +
                                "chat.skeleton.13=5,There is something about a closet that makes a skeleton terribly restless.\n" +
                                "chat.skeleton.14=5,I can be a super model!\n" +
                                "chat.skeleton.15=10,A bazooka is the skeleton key of the impatient.\n" +
                                "chat.skeleton.16=10,Tact is for people who aren't witty enough to use sarcasm.\n" +
                                "chat.skeleton.17=10,On the other hand, you have different fingers.\n" +
                                "chat.skeleton.18=10,There is a strong need for a sarcasm font.\n" +
                                "chat.skeleton.19=10,I am an acquired taste.\n" +
                                "\n" +
                                "chat.witch.0=10,Yes, I can drive a stick.\n" +
                                "chat.witch.1=15,By the pricking of my thumbs, something wicked this way comes.\n" +
                                "chat.witch.2=10,Do not judge my path, you haven't walked in my shoes...or ridden my broom.\n" +
                                "chat.witch.3=15,All trespassers will be used as ingredients in the brew!\n" +
                                "chat.witch.4=10,Fair is foul, and foul is fair.\n" +
                                "chat.witch.5=10,Going so soon? I wouldn't hear of it. Why my little party's just beginning.\n" +
                                "chat.witch.6=15,Why do I have to look like a normal villager?\n" +
                                "chat.witch.7=15,I think I will keep it under my hat for now.\n" +
                                "chat.witch.8=10,I will get you and your little dog, too!  You have a dog, right?\n" +
                                "chat.witch.9=10,Don't ask me where I keep all these potions.  Where do you keep all that dirt?\n" +
                                "chat.witch.10=10,I wish I had a flying monkey.\n" +
                                "chat.witch.11=15,Have you ever had a rock as a familiar?  It was the rage back in the day.\n" +
                                "chat.witch.12=15,It's not a wart! It's a beauty mark!\n" +
                                "chat.witch.13=10,Some days I think my nose has a life of it's own.\n" +
                                "chat.witch.14=15,My work is never done...\n" +
                                "chat.witch.15=10,Tis now the very witching time of night, when churchyards yawn and hell itself breathes out contagion to this world.\n" +
                                "chat.witch.16=10,Double, double toil and trouble; fire burn and cauldron bubble.\n" +
                                "\n" +
                                "# Special token for using splash screen sayings - don't translate!\n" +
                                "chat.squid.0=20,$MINECRAFT$\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            try (Stream<String> stream = Files.lines(dSurroundChatFile.toPath())) {
                List<String> list = stream
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .filter(s -> !(s.startsWith("//") || s.startsWith("#")))
                        .filter(s -> s.contains("="))
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split("=", 2)).map(String::trim).toArray(String[]::new);
                    dSurroundChatMap.put(entryArray[0],entryArray[1]);
                }
            }
            return true;
        }
        catch(Exception ex) {
            RLMixins.LOGGER.log(Level.ERROR, RLMixins.MODID + ": " + "Failed to initialize DynamicSurroundings Chat File: " + ex);
            return false;
        }
    }

    private static boolean initDSurroundChatTimeMap() {
        if(configFolder==null && !initDirectory()) return false;
        dSurroundChatTimeFile = new File(configFolder, "dsurround_chat_time.cfg");
        try {
            if(!dSurroundChatTimeFile.exists()) {
                if(!dSurroundChatTimeFile.createNewFile()) {
                    RLMixins.LOGGER.log(Level.ERROR, RLMixins.MODID + ": " + "Failed to create new DynamicSurroundings Chat Time file.");
                    return false;
                }
                else Files.write(dSurroundChatTimeFile.toPath(),
                        (       "# Format:\n" +
                                "#\n" +
                                "# name,baseTick,randomTick\n" +
                                "#\n" +
                                "# name:        The Entity class name registered in EntityList. (Ex. villager/skeleton/etc.)\n" +
                                "#\n" +
                                "# baseTick:    Minimum amount of ticks before playing a chat message for the entity.\n" +
                                "#\n" +
                                "# randomTick:  Range for the random amount of ticks to be added to the baseTick.\n" +
                                "#              Ticks until next message = baseTick + (Random number between 0 and randomTick)\n" +
                                "#\n" +
                                "# Default values are 400,1200 for all entities except Squids, which are 600,1200\n" +
                                "#\n"
                        ).getBytes(StandardCharsets.UTF_8));
            }
            try (Stream<String> stream = Files.lines(dSurroundChatTimeFile.toPath())) {
                List<String> list = stream
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .filter(s -> !(s.startsWith("//") || s.startsWith("#")))
                        .filter(s -> s.contains(","))
                        .collect(Collectors.toList());
                for(String entry : list) {
                    String[] entryArray = Arrays.stream(entry.split(",", 3)).map(String::trim).toArray(String[]::new);
                    dSurroundChatTimeMap.put(entryArray[0],new int[] {Integer.parseInt(entryArray[1]), Integer.parseInt(entryArray[2])});
                }
            }
            return true;
        }
        catch(Exception ex) {
            RLMixins.LOGGER.log(Level.ERROR, RLMixins.MODID + ": " + "Failed to initialize DynamicSurroundings Chat Time File: " + ex);
            return false;
        }
    }
}
