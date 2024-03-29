package org.crashmax.TwitchEmotes;

import org.crashmax.TwitchEmotes.Fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.rpapi.ResourcePackAPI;

import java.util.*;

public class Emotes extends JavaPlugin implements Listener {

    public static Server serv;
    public static PluginDescriptionFile pdf;
    public static PluginManager pm;
    public static Emotes plugin;
    public static MEConfig config;
    static HashMap<List<String>, String> emojis = new HashMap<>();
    static List<String> DeclinedPlayers = new ArrayList<>();
    private static final List<String> installing = new ArrayList<>();
    private final HashMap<String, HashMap<Integer, String>> signPlayers = new HashMap<>();

    public static boolean isInstalling(Player p) {
        return installing.contains(p.getName());
    }

    public static void delInstalling(Player p) {
        installing.remove(p.getName());
    }

    public void onEnable() {
        try {

            Plugin p = Bukkit.getPluginManager().getPlugin("ResourcePackApi");
            boolean RPAPI = p != null && p.isEnabled();
            plugin = this;
            serv = getServer();
            pdf = getDescription();
            pm = serv.getPluginManager();
            AddEmojis();
            config = new MEConfig(plugin);

            if (getBukkitVersion() >= 188) {
                if (RPAPI) {
                    MELogger.warning("ResourcePackApi detected but after version 1.8.8 is not necessary. You can remove securely!");
                }
                pm.registerEvents(new MEListener188(), this);
            } else {
                if (!RPAPI) {
                    MELogger.severe("Not found the dependency ResourcePackAPI required for version < 1.8.8!");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                pm.registerEvents(new MEListenerRPA(), this);
            }

            MELogger.sucess(pdf.getFullName() + " enabled. (" + getBukkitVersion() + ")");

            Bukkit.getPluginManager().registerEvents(this, this);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void AddEmojis() {
		// http://www.tamasoft.co.jp/en/general-info/unicode.html 4C00
		// 1
        emojis.put(new ArrayList<>(Collections.singletonList("4Head")), "䰀");
        emojis.put(new ArrayList<>(Collections.singletonList("ANELE")), "䰁");
        emojis.put(new ArrayList<>(Collections.singletonList("BloodTrail")), "䰂");
        emojis.put(new ArrayList<>(Collections.singletonList("BibleThump")), "䰃");
        emojis.put(new ArrayList<>(Collections.singletonList("CoolStoryBob")), "䰄");
        emojis.put(new ArrayList<>(Collections.singletonList("HYPERDG")), "䰅");
        emojis.put(new ArrayList<>(Collections.singletonList("CFailFish")), "䰆");
        emojis.put(new ArrayList<>(Collections.singletonList("Kappa")), "䰇");
        emojis.put(new ArrayList<>(Collections.singletonList("KPride")), "䰈");
        emojis.put(new ArrayList<>(Collections.singletonList("Keepo")), "䰉");
        emojis.put(new ArrayList<>(Collections.singletonList("NotLikeThis")), "䰊");
        emojis.put(new ArrayList<>(Collections.singletonList("PogChamp")), "䰋");
        emojis.put(new ArrayList<>(Collections.singletonList("SeemsGood")), "䰌");
        emojis.put(new ArrayList<>(Collections.singletonList("SMOrc")), "䰍");
        emojis.put(new ArrayList<>(Collections.singletonList("TriHard")), "䰎");
        emojis.put(new ArrayList<>(Collections.singletonList("Kreygasm")), "䰏");
		// 2
        emojis.put(new ArrayList<>(Collections.singletonList("roflanEbalo")), "䰐");
        emojis.put(new ArrayList<>(Collections.singletonList("ebaloRoflan")), "䰑");
        emojis.put(new ArrayList<>(Collections.singletonList("roflanBuldiga")), "䰒");
        emojis.put(new ArrayList<>(Collections.singletonList("roflanGorit")), "䰓");
        emojis.put(new ArrayList<>(Collections.singletonList("gachiGASM")), "䰔");
        emojis.put(new ArrayList<>(Collections.singletonList("OMEGALUL")), "䰕");
        emojis.put(new ArrayList<>(Collections.singletonList("5Head")), "䰖");
        emojis.put(new ArrayList<>(Collections.singletonList("KEKW")), "䰗");
        emojis.put(new ArrayList<>(Collections.singletonList("KWait")), "䰘");
        emojis.put(new ArrayList<>(Collections.singletonList("monkaS")), "䰙");
        emojis.put(new ArrayList<>(Collections.singletonList("pepe3Head")), "䰚");
        emojis.put(new ArrayList<>(Collections.singletonList("FeelsThinkingMan")), "䰛");
        emojis.put(new ArrayList<>(Collections.singletonList("PepoG")), "䰜");
        emojis.put(new ArrayList<>(Collections.singletonList("monkaOMEGA")), "䰝");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeLaugh")), "䰞");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeCringe")), "䰟");
		// 3
        emojis.put(new ArrayList<>(Collections.singletonList("lehaHaha")), "䰠");
        emojis.put(new ArrayList<>(Collections.singletonList("lehaTrail")), "䰡");
        emojis.put(new ArrayList<>(Collections.singletonList("Qq")), "䰢");
        emojis.put(new ArrayList<>(Collections.singletonList("goblinWut")), "䰣");
        emojis.put(new ArrayList<>(Collections.singletonList("goblinKavo")), "䰤");
        emojis.put(new ArrayList<>(Collections.singletonList("goblinOgo")), "䰥");
        emojis.put(new ArrayList<>(Collections.singletonList("pogW")), "䰦");
        emojis.put(new ArrayList<>(Collections.singletonList("D:")), "䰧");
        emojis.put(new ArrayList<>(Collections.singletonList("KEKL")), "䰨");
        emojis.put(new ArrayList<>(Collections.singletonList("KEKYou")), "䰩");
        emojis.put(new ArrayList<>(Collections.singletonList("FeelsRageMan")), "䰪");
        emojis.put(new ArrayList<>(Collections.singletonList("pressF")), "䰫");
        emojis.put(new ArrayList<>(Collections.singletonList("Durka1")), "䰬");
        emojis.put(new ArrayList<>(Collections.singletonList("melW")), "䰭");
        emojis.put(new ArrayList<>(Collections.singletonList("melYoba")), "䰮");
        emojis.put(new ArrayList<>(Collections.singletonList("sosiska")), "䰯");
		// 4
        emojis.put(new ArrayList<>(Collections.singletonList("CrashMax")), "䰰");
        emojis.put(new ArrayList<>(Collections.singletonList("BarsokuN")), "䰱");
        emojis.put(new ArrayList<>(Collections.singletonList("le_xot")), "䰲");
        emojis.put(new ArrayList<>(Collections.singletonList("BAN")), "䰳");
        emojis.put(new ArrayList<>(Collections.singletonList("NED")), "䰴");
        emojis.put(new ArrayList<>(Collections.singletonList("AYAYA")), "䰵");
        emojis.put(new ArrayList<>(Collections.singletonList("LULW")), "䰶");
        emojis.put(new ArrayList<>(Collections.singletonList("Thonk")), "䰷");
        emojis.put(new ArrayList<>(Collections.singletonList("HAha")), "䰸");
        emojis.put(new ArrayList<>(Collections.singletonList("sadCat")), "䰹");
        emojis.put(new ArrayList<>(Collections.singletonList("LULM")), "䰺");
        emojis.put(new ArrayList<>(Collections.singletonList("Clap")), "䰻");
        emojis.put(new ArrayList<>(Collections.singletonList("koronavirus")), "䰼");
        emojis.put(new ArrayList<>(Collections.singletonList("pogO")), "䰽");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoNoob")), "䰾");
        emojis.put(new ArrayList<>(Collections.singletonList("Loading")), "䰿");
		// 5
        emojis.put(new ArrayList<>(Collections.singletonList("SSSsss")), "䱀");
        emojis.put(new ArrayList<>(Collections.singletonList("Pepega")), "䱁");
        emojis.put(new ArrayList<>(Collections.singletonList("weSmart")), "䱂");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeS")), "䱃");
        emojis.put(new ArrayList<>(Collections.singletonList("2Head")), "䱄");
        emojis.put(new ArrayList<>(Collections.singletonList("KRoss")), "䱅");
        emojis.put(new ArrayList<>(Collections.singletonList("Petrosyan")), "䱆");
        emojis.put(new ArrayList<>(Collections.singletonList("EZ")), "䱇");
        emojis.put(new ArrayList<>(Collections.singletonList("MingLee")), "䱈");
        emojis.put(new ArrayList<>(Collections.singletonList("OpieOP")), "䱉");
        emojis.put(new ArrayList<>(Collections.singletonList("imGlitch")), "䱊");
        emojis.put(new ArrayList<>(Collections.singletonList("imAT")), "䱋");
        emojis.put(new ArrayList<>(Collections.singletonList("EleGiggle")), "䱌");
        emojis.put(new ArrayList<>(Collections.singletonList("roflanHmm")), "䱍");
        emojis.put(new ArrayList<>(Collections.singletonList("roflanHater")), "䱎");
        emojis.put(new ArrayList<>(Collections.singletonList("roflanPominki")), "䱏");
		// 6
        emojis.put(new ArrayList<>(Collections.singletonList("peepoSleeper")), "䱐");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeSleeper")), "䱑");
        emojis.put(new ArrayList<>(Collections.singletonList("PEPEGAHACKER")), "䱒");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeHappy")), "䱓");
        emojis.put(new ArrayList<>(Collections.singletonList("melATMTA")), "䱔");
        emojis.put(new ArrayList<>(Collections.singletonList("Klass")), "䱕");
        emojis.put(new ArrayList<>(Collections.singletonList("AnimeHappy")), "䱖");
        emojis.put(new ArrayList<>(Collections.singletonList("spongeO")), "䱗");
        emojis.put(new ArrayList<>(Collections.singletonList("spongeW")), "䱘");
        emojis.put(new ArrayList<>(Collections.singletonList("FeelsMinecraftMan")), "䱙");
        emojis.put(new ArrayList<>(Collections.singletonList("HeyGuys")), "䱚");
        emojis.put(new ArrayList<>(Collections.singletonList("POOPEGA")), "䱛");
        emojis.put(new ArrayList<>(Collections.singletonList("Durka2")), "䱜");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeDen")), "䱝");
        emojis.put(new ArrayList<>(Collections.singletonList("elRisitas")), "䱞");
        emojis.put(new ArrayList<>(Collections.singletonList("LULY")), "䱟");
		// 7
        emojis.put(new ArrayList<>(Collections.singletonList("ricardoFlick")), "䱠");
        emojis.put(new ArrayList<>(Collections.singletonList("ZULUL")), "䱡");
        emojis.put(new ArrayList<>(Collections.singletonList("KKomrade")), "䱢");
        emojis.put(new ArrayList<>(Collections.singletonList("HYPERKEK")), "䱣");
        emojis.put(new ArrayList<>(Collections.singletonList("KEKKK")), "䱤");
        emojis.put(new ArrayList<>(Collections.singletonList("gachiHands")), "䱥");
        emojis.put(new ArrayList<>(Collections.singletonList("KEKHands")), "䱦");
        emojis.put(new ArrayList<>(Collections.singletonList("DiamondOre")), "䱧");
        emojis.put(new ArrayList<>(Collections.singletonList("Areyawinning")), "䱨");
        emojis.put(new ArrayList<>(Collections.singletonList("shrekT")), "䱩");
        emojis.put(new ArrayList<>(Collections.singletonList("shrekW")), "䱪");
        emojis.put(new ArrayList<>(Collections.singletonList("shrekS")), "䱫");
        emojis.put(new ArrayList<>(Collections.singletonList("Slowpoke")), "䱬");
        emojis.put(new ArrayList<>(Collections.singletonList("GabeN")), "䱭");
        emojis.put(new ArrayList<>(Collections.singletonList("forsenT")), "䱮");
        emojis.put(new ArrayList<>(Collections.singletonList("ThanosDaddy")), "䱯");
        // 8
        emojis.put(new ArrayList<>(Collections.singletonList("SPog")), "䱰");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoZalupa")), "䱱");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoWTF")), "䱲");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoSad")), "䱳");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoPoopoo")), "䱴");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoPat")), "䱵");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoKnife")), "䱶");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoidk")), "䱷");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoHazmat")), "䱸");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoFull")), "䱹");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoCool")), "䱺");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoClown")), "䱻");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoNO")), "䱼");
        emojis.put(new ArrayList<>(Collections.singletonList("okgj")), "䱽");
        emojis.put(new ArrayList<>(Collections.singletonList("monkaXW")), "䱾");
        emojis.put(new ArrayList<>(Collections.singletonList("monkaSSS")), "䱿");
        // 9
        emojis.put(new ArrayList<>(Collections.singletonList("monkaGun")), "䲀");
        emojis.put(new ArrayList<>(Collections.singletonList("hsgj")), "䲁");
        emojis.put(new ArrayList<>(Collections.singletonList("pedobear")), "䲂");
        emojis.put(new ArrayList<>(Collections.singletonList("hyperthink")), "䲃");
        emojis.put(new ArrayList<>(Collections.singletonList("icantbreathe")), "䲄");
        emojis.put(new ArrayList<>(Collections.singletonList("ESC")), "䲅");
        emojis.put(new ArrayList<>(Collections.singletonList("ResidentSleeper")), "䲆");
        emojis.put(new ArrayList<>(Collections.singletonList("NO")), "䲇");
        emojis.put(new ArrayList<>(Collections.singletonList("gotcha")), "䲈");
        emojis.put(new ArrayList<>(Collections.singletonList("weirdflexbutokay")), "䲉");
        emojis.put(new ArrayList<>(Collections.singletonList("kys")), "䲊");
        emojis.put(new ArrayList<>(Collections.singletonList("howdy_hattip")), "䲋");
        emojis.put(new ArrayList<>(Collections.singletonList("midas")), "䲌");
        emojis.put(new ArrayList<>(Collections.singletonList("budustrelat")), "䲍");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoDead")), "䲎");
        emojis.put(new ArrayList<>(Collections.singletonList("RussianDOG")), "䲏");
        // 10
        emojis.put(new ArrayList<>(Collections.singletonList("melVasyan")), "䲐");
        emojis.put(new ArrayList<>(Collections.singletonList("melLove")), "䲑");
        emojis.put(new ArrayList<>(Collections.singletonList("sadbutgood")), "䲒");
        emojis.put(new ArrayList<>(Collections.singletonList("Puten")), "䲓");
        emojis.put(new ArrayList<>(Collections.singletonList("POGGERS")), "䲔");
        emojis.put(new ArrayList<>(Collections.singletonList("hsgj2")), "䲕");
        emojis.put(new ArrayList<>(Collections.singletonList("roflanEbashu")), "䲖");
        emojis.put(new ArrayList<>(Collections.singletonList("DverZapili")), "䲗");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoBizniz")), "䲘");
        emojis.put(new ArrayList<>(Collections.singletonList("succ")), "䲙");
        emojis.put(new ArrayList<>(Collections.singletonList("GoodNight")), "䲚");
        emojis.put(new ArrayList<>(Collections.singletonList("jerryWhat")), "䲛");
        emojis.put(new ArrayList<>(Collections.singletonList("Woah")), "䲜");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoCuptea")), "䲝");
        emojis.put(new ArrayList<>(Collections.singletonList("redGasm")), "䲞");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoLove")), "䲟");
        // 11
        emojis.put(new ArrayList<>(Collections.singletonList("flunched")), "䲠");
        emojis.put(new ArrayList<>(Collections.singletonList("wannafight")), "䲡");
        emojis.put(new ArrayList<>(Collections.singletonList("THONKERS")), "䲢");
        emojis.put(new ArrayList<>(Collections.singletonList("WaitWhat")), "䲣");
        emojis.put(new ArrayList<>(Collections.singletonList("SUCAAAAT")), "䲤");
        emojis.put(new ArrayList<>(Collections.singletonList("guncock")), "䲥");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoGroove")), "䲦");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoCry")), "䲧");
        emojis.put(new ArrayList<>(Collections.singletonList("TomWut")), "䲨");
        emojis.put(new ArrayList<>(Collections.singletonList("cmonBRUG")), "䲩");
        emojis.put(new ArrayList<>(Collections.singletonList("WUTFACE")), "䲪");
        emojis.put(new ArrayList<>(Collections.singletonList("vitasW")), "䲫");
        emojis.put(new ArrayList<>(Collections.singletonList("SmileW")), "䲬");
        emojis.put(new ArrayList<>(Collections.singletonList("KonCha")), "䲭");
        emojis.put(new ArrayList<>(Collections.singletonList("CarlSmile")), "䲮");
        emojis.put(new ArrayList<>(Collections.singletonList("GoodMorning")), "䲯");
        // 12
        emojis.put(new ArrayList<>(Collections.singletonList("4House")), "䲰");
        emojis.put(new ArrayList<>(Collections.singletonList("iamtired")), "䲱");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeREEEE")), "䲲");
        emojis.put(new ArrayList<>(Collections.singletonList("nenadoG")), "䲳");
        emojis.put(new ArrayList<>(Collections.singletonList("comfytag")), "䲴");
        emojis.put(new ArrayList<>(Collections.singletonList("degW")), "䲵");
        emojis.put(new ArrayList<>(Collections.singletonList("PHAHAA")), "䲶");
        emojis.put(new ArrayList<>(Collections.singletonList("AAAAAAA")), "䲷");
        emojis.put(new ArrayList<>(Collections.singletonList("MmmYea")), "䲸");
        emojis.put(new ArrayList<>(Collections.singletonList("VoHiYo")), "䲹");
        emojis.put(new ArrayList<>(Collections.singletonList("WeirdChamp")), "䲺");
        emojis.put(new ArrayList<>(Collections.singletonList("Angery")), "䲻");
        emojis.put(new ArrayList<>(Collections.singletonList("RIP")), "䲼");
        emojis.put(new ArrayList<>(Collections.singletonList("EYES")), "䲽");
        emojis.put(new ArrayList<>(Collections.singletonList("griffin")), "䲾");
        emojis.put(new ArrayList<>(Collections.singletonList("vseochenploho")), "䲿");
        // 13
        emojis.put(new ArrayList<>(Collections.singletonList("SexyDog")), "䳀");
        emojis.put(new ArrayList<>(Collections.singletonList("shluha")), "䳁");
        emojis.put(new ArrayList<>(Collections.singletonList("Wazosky")), "䳂");
        emojis.put(new ArrayList<>(Collections.singletonList("voobrajenie")), "䳃");
        emojis.put(new ArrayList<>(Collections.singletonList("gunRight")), "䳄");
        emojis.put(new ArrayList<>(Collections.singletonList("gunLeft")), "䳅");
        emojis.put(new ArrayList<>(Collections.singletonList("RNG")), "䳆");
        emojis.put(new ArrayList<>(Collections.singletonList("pepegaLUL")), "䳇");
        emojis.put(new ArrayList<>(Collections.singletonList("GG")), "䳈");
        emojis.put(new ArrayList<>(Collections.singletonList("CosmicBrain")), "䳉");
        emojis.put(new ArrayList<>(Collections.singletonList("y1")), "䳊");
        emojis.put(new ArrayList<>(Collections.singletonList("y2")), "䳋");
        emojis.put(new ArrayList<>(Collections.singletonList("y3")), "䳌");
        emojis.put(new ArrayList<>(Collections.singletonList("y4")), "䳍");
        emojis.put(new ArrayList<>(Collections.singletonList("y5")), "䳎");
        emojis.put(new ArrayList<>(Collections.singletonList("y6")), "䳏");
        // 14
        emojis.put(new ArrayList<>(Collections.singletonList("n1")), "䳐");
        emojis.put(new ArrayList<>(Collections.singletonList("n2")), "䳑");
        emojis.put(new ArrayList<>(Collections.singletonList("n3")), "䳒");
        emojis.put(new ArrayList<>(Collections.singletonList("K1")), "䳓");
        emojis.put(new ArrayList<>(Collections.singletonList("K2")), "䳔");
        emojis.put(new ArrayList<>(Collections.singletonList("K3")), "䳕");
        emojis.put(new ArrayList<>(Collections.singletonList("HappyPepe")), "䳖");
        emojis.put(new ArrayList<>(Collections.singletonList("vraixAh")), "䳗");
        emojis.put(new ArrayList<>(Collections.singletonList("BLELELE")), "䳘");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeRage")), "䳙");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeThicc")), "䳚");
        emojis.put(new ArrayList<>(Collections.singletonList("p1")), "䳛");
        emojis.put(new ArrayList<>(Collections.singletonList("p2")), "䳜");
        emojis.put(new ArrayList<>(Collections.singletonList("p3")), "䳝");
        emojis.put(new ArrayList<>(Collections.singletonList("p4")), "䳞");
        emojis.put(new ArrayList<>(Collections.singletonList("pepeSit")), "䳟");
        // 15
        emojis.put(new ArrayList<>(Collections.singletonList("n4")), "䳠");
        emojis.put(new ArrayList<>(Collections.singletonList("n5")), "䳡");
        emojis.put(new ArrayList<>(Collections.singletonList("n6")), "䳢");
        emojis.put(new ArrayList<>(Collections.singletonList("K4")), "䳣");
        emojis.put(new ArrayList<>(Collections.singletonList("K5")), "䳤");
        emojis.put(new ArrayList<>(Collections.singletonList("K6")), "䳥");
        emojis.put(new ArrayList<>(Collections.singletonList("W1")), "䳦");
        emojis.put(new ArrayList<>(Collections.singletonList("W2")), "䳧");
        emojis.put(new ArrayList<>(Collections.singletonList("PotatoTriggered")), "䳨");
        emojis.put(new ArrayList<>(Collections.singletonList("4weirdW")), "䳩");
        emojis.put(new ArrayList<>(Collections.singletonList("MinecraftPick")), "䳪");
        emojis.put(new ArrayList<>(Collections.singletonList("c1")), "䳫");
        emojis.put(new ArrayList<>(Collections.singletonList("c2")), "䳬");
        emojis.put(new ArrayList<>(Collections.singletonList("c3")), "䳭");
        emojis.put(new ArrayList<>(Collections.singletonList("c4")), "䳮");
        emojis.put(new ArrayList<>(Collections.singletonList("JustRight")), "䳯");
        // 16
        emojis.put(new ArrayList<>(Collections.singletonList("n7")), "䳰");
        emojis.put(new ArrayList<>(Collections.singletonList("n8")), "䳱");
        emojis.put(new ArrayList<>(Collections.singletonList("n9")), "䳲");
        emojis.put(new ArrayList<>(Collections.singletonList("K7")), "䳳");
        emojis.put(new ArrayList<>(Collections.singletonList("K8")), "䳴");
        emojis.put(new ArrayList<>(Collections.singletonList("K9")), "䳵");
        emojis.put(new ArrayList<>(Collections.singletonList("W3")), "䳶");
        emojis.put(new ArrayList<>(Collections.singletonList("W4")), "䳷");
        emojis.put(new ArrayList<>(Collections.singletonList("Happicloud")), "䳸");
        emojis.put(new ArrayList<>(Collections.singletonList("SMILERS")), "䳹");
        emojis.put(new ArrayList<>(Collections.singletonList("peepoAwn")), "䳺");
        emojis.put(new ArrayList<>(Collections.singletonList("RAGE")), "䳻");
        emojis.put(new ArrayList<>(Collections.singletonList("mute")), "䳼");
        emojis.put(new ArrayList<>(Collections.singletonList("LOLW")), "䳽");
        emojis.put(new ArrayList<>(Collections.singletonList("Hmm")), "䳾");
        emojis.put(new ArrayList<>(Collections.singletonList("MEGALEE")), "䳿");
    }

    public void onDisable() {
        config.saveAll();
        MELogger.severe(pdf.getFullName() + " disabled.");
    }

    private int getBukkitVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String v = name.substring(name.lastIndexOf('.') + 1) + ".";
        String[] version = v.replace('_', '.').split("\\.");

        int lesserVersion = 0;
        try {
            lesserVersion = Integer.parseInt(version[2]);
        } catch (NumberFormatException ignored) {
        }
        return Integer.parseInt((version[0] + version[1]).substring(1) + lesserVersion);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> tab = new ArrayList<>();
        for (List<String> emojis : Emotes.emojis.keySet()) {
            for (String emoji : emojis) {
                tab.add(emoji);
            }
        }
        return tab;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(config.getLangString("plugin-tag"));
            if (sender.hasPermission("twitchemotes.install")) {
                sender.sendMessage(config.getLangString("commands.install").replace("{cmd}", lbl));
            }
            if (sender.hasPermission("twitchemotes.enable")) {
                sender.sendMessage(config.getLangString("commands.enable").replace("{cmd}", lbl));
            }
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + Emotes.pdf.getFullName() + ", developed by CrashMax!");
        }
        if (args.length == 1) {
            if ((args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")) && sender.hasPermission("twitchemotes.enable")) {
                DeclinedPlayers.remove(sender.getName());
                sender.sendMessage(config.getLangString("plugin-tag") + config.getLangString("emojis.enabled"));
            }
            if ((args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) && sender.hasPermission("twitchemotes.enable")) {
                if (!DeclinedPlayers.contains(sender.getName())) {
                    DeclinedPlayers.add(sender.getName());
                }
                sender.sendMessage(config.getLangString("plugin-tag") + config.getLangString("emojis.disabled"));
            }
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("twitchemotes.reload")) {
                config.reload();
                sender.sendMessage(ChatColor.AQUA + Emotes.pdf.getFullName() + " reloaded!");
            }
            if (args[0].equalsIgnoreCase("download") && sender instanceof Player && sender.hasPermission("twitchemotes.download")) {
                Player p = (Player) sender;
                if (getBukkitVersion() >= 188) {
                    p.setResourcePack("https://atestacraft.ru/download/resourcepacks/TwitchEmotes.zip");
                } else {
                    ResourcePackAPI.setResourcepack(p, "https://atestacraft.ru/download/resourcepacks/TwitchEmotes.zip");
                }
                installing.add(p.getName());
            }
            if (args[0].equalsIgnoreCase("install") && sender instanceof Player && sender.hasPermission("twitchemotes.install")) {
                Player p = (Player) sender;
                if (config.getList("download-help-lines") != null && config.getList("download-help-lines").size() > 0) {
                    for (String line : config.getList("download-help-lines")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                    }
                }
            }
            if (args[0].equalsIgnoreCase("list") && sender instanceof Player && sender.hasPermission("twitchemotes.list")) {
                Iterator<List<String>> emojits = Emotes.emojis.keySet().iterator();
                sender.sendMessage(config.getLangString("plugin-tag") + config.getLangString("default-color") + " Emojis:");
                try {
                    Class.forName("com.google.gson.JsonParser");
                    FancyMessage message = new FancyMessage();

                    while (emojits.hasNext()) {
                        List<String> shortcuts = emojits.next();
                        String[] emoji = shortcuts.toArray(new String[0]);
                        StringBuilder shortcut = new StringBuilder();
                        for (String shortc : emoji) {
                            shortcut.append(" ").append(config.getLangString("default-color")).append("|§r ").append(shortc);
                        }
                        shortcut = new StringBuilder(shortcut.substring(4));
                        message.text(config.getLangString("default-color") + "|§r" + Emotes.emojis.get(shortcuts) + config.getLangString("default-color") + "|§r")
                                .tooltip(Emotes.config.getLangString("shortcut") + shortcut)
                                .then(" ");
                    }
                    message.send(sender);
                } catch (ClassNotFoundException e) {
                    StringBuilder send = new StringBuilder();
                    while (emojits.hasNext()) {
                        List<String> shortcuts = emojits.next();
                        String[] emoji = shortcuts.toArray(new String[0]);
                        send.append("|").append(Emotes.emojis.get(shortcuts)).append(" = ").append(Arrays.toString(emoji)).append("|");
                    }
                    sender.sendMessage(send.toString());
                }
            }
            for (List<String> emojis : Emotes.emojis.keySet()) {
                if (emojis.contains(args[0]) && (sender.hasPermission("twitchemotes." + args[0]) || sender.hasPermission("twitchemotes.all"))) {
                    sender.sendMessage(config.getLangString("plugin-tag") + " " + args[0] + "§r = " + Emotes.emojis.get(emojis));
                }
            }
        }

        if (args.length >= 3) {
            //emoji setsign <line> <text>
            if (args[0].equalsIgnoreCase("setsign") && sender instanceof Player && sender.hasPermission("twitchemotes.setsign")) {
                Player p = (Player) sender;
                int line;
                try {
                    line = Integer.parseInt(args[1]);
                    if (line < 1 || line > 4) {
                        sender.sendMessage(config.getLangString("plugin-tag") + config.getLangString("setsign.nolines"));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(config.getLangString("plugin-tag") + config.getLangString("setsign.usage").replace("{cmd}", lbl));
                    return true;
                }
                StringBuilder message = new StringBuilder();
                for (String arg : args) {
                    if (arg.equals(args[0]) || arg.equals(args[1])) {
                        continue;
                    }
                    message.append(arg).append(" ");
                }
                String msg = formatEmoji(p, message.toString().substring(0, message.toString().length() - 1), true);
                HashMap<Integer, String> pmsg = new HashMap<>();
                if (signPlayers.containsKey(p.getName())) {
                    pmsg = signPlayers.get(p.getName());
                }
                pmsg.put(line, msg);
                signPlayers.put(p.getName(), pmsg);
                sender.sendMessage(config.getLangString("plugin-tag") + config.getLangString("setsign.setline-to").replace("{line}", "" + line).replace("{text}", ChatColor.translateAlternateColorCodes('&', msg)));
            }
        }
        return true;
    }

    private String formatEmoji(Player p, String msg, boolean sign) {
        for (List<String> emojis : Emotes.emojis.keySet()) {
            for (String emoji : emojis) {
                if (msg.contains(emoji) && (p.hasPermission("twitchemotes.emoji.all"))) {
                    String emof = Emotes.emojis.get(emojis);
                    msg = msg.replace(emoji, sign ? "&f" + emof + "&r" : emof);
                }
            }
        }
        return msg;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if (config.getBool("config.resourcepack-onplayerjoin")) {
            if (getBukkitVersion() >= 188) {
                p.setResourcePack("https://atestacraft.ru/download/resourcepacks/TwitchEmotes.zip");
            } else {
                ResourcePackAPI.setResourcepack(p, "https://atestacraft.ru/download/resourcepacks/TwitchEmotes.zip");
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInteractSign(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if (signPlayers.containsKey(p.getName())) {
            if (b != null && b.getState() instanceof Sign) {
                Sign s = (Sign) b.getState();
                for (Integer line : signPlayers.get(p.getName()).keySet()) {
                    s.setLine(line - 1, ChatColor.translateAlternateColorCodes('&', signPlayers.get(p.getName()).get(line)));
                }
                s.update();
            } else {
                p.sendMessage(config.getLangString("plugin-tag") + config.getLangString("setsign.no-sign"));
            }
            signPlayers.remove(p.getName());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("twitchemotes.placesign") && !Emotes.DeclinedPlayers.contains(p.getName())) {
            for (int i = 0; i < e.getLines().length; i++) {
                e.setLine(i, ChatColor.translateAlternateColorCodes('&', formatEmoji(p, e.getLine(i), true)));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String chat = e.getMessage();

        if (Emotes.DeclinedPlayers.contains(p.getName())) {
            return;
        }
        e.setMessage(formatEmoji(p, chat, false));
    }
}
