package cn.KiesPro.utils;

import java.util.ArrayList;
import java.util.List;

public class FairySoulUtil {
    public static boolean debug = false;
    public static boolean enabled = false;
    public static boolean showGUI = false;
    public static int currentSetting = 0;
    
    public static List<String> hub = new ArrayList<String>();
    public static List<String> islands = new ArrayList<String>();
    public static List<String> den = new ArrayList<String>();
    public static List<String> fortress = new ArrayList<String>();
    public static List<String> mine = new ArrayList<String>();
    public static List<String> caverns = new ArrayList<String>();
    public static List<String> barn = new ArrayList<String>();
    public static List<String> desert = new ArrayList<String>();
    public static List<String> end = new ArrayList<String>();
    public static List<String> darwen = new ArrayList<String>();
    public static List<String> dungeons = new ArrayList<String>();
    public static List<String> hubfound = new ArrayList<String>();
    public static List<String> islandsfound = new ArrayList<String>();
    public static List<String> denfound = new ArrayList<String>();
    public static List<String> fortressfound = new ArrayList<String>();
    public static List<String> minefound = new ArrayList<String>();
    public static List<String> cavernsfound = new ArrayList<String>();
    public static List<String> barnfound = new ArrayList<String>();
    public static List<String> desertfound = new ArrayList<String>();
    public static List<String> endfound = new ArrayList<String>();
    public static List<String> darwenfound = new ArrayList<String>();
    public static List<String> dungeonsfound = new ArrayList<String>();

    public static void setup() {
        FairySoulUtil.hub();
        FairySoulUtil.islands();
        FairySoulUtil.den();
        FairySoulUtil.fortress();
        FairySoulUtil.mine();
        FairySoulUtil.caverns();
        FairySoulUtil.barn();
        FairySoulUtil.desert();
        FairySoulUtil.end();
        FairySoulUtil.darwen();
        FairySoulUtil.dungeons();
    }

    public static void setCurrent(int i) {
        currentSetting = i;
    }

    private static void hub() {
        hub.add("-81,70,-88");
        hub.add("72,70,-99");
        hub.add("-49,90,-72");
        hub.add("-53,70,-100");
        hub.add("40,68,-65");
        hub.add("-24,88,-63");
        hub.add("-20,90,-12");
        hub.add("23,79,-134");
        hub.add("-16,66,-110");
        hub.add("26,80,-65");
        hub.add("44,68,-34");
        hub.add("154,98,-71");
        hub.add("168,60,-36");
        hub.add("110,67,58");
        hub.add("138,66,129");
        hub.add("132,144,114");
        hub.add("111,120,127");
        hub.add("148,112,88");
        hub.add("149,116,115");
        hub.add("113,102,106");
        hub.add("96,106,121");
        hub.add("87,77,43");
        hub.add("86,89,66");
        hub.add("82,61,196");
        hub.add("180,63,-15");
        hub.add("169,60,129");
        hub.add("-142,77,-31");
        hub.add("-225,72,-21");
        hub.add("-208,70,80");
        hub.add("-32,71,21");
        hub.add("8,75,13");
        hub.add("-48,76,49");
        hub.add("-60,108,3");
        hub.add("-56,115,28");
        hub.add("-50,132,32");
        hub.add("22,132,25");
        hub.add("-52,161,43");
        hub.add("10,179,22");
        hub.add("2,181,31");
        hub.add("-39,191,34");
        hub.add("-3,193,32");
        hub.add("57,90,79");
        hub.add("48,78,81");
        hub.add("43,120,70");
        hub.add("49,121,69");
        hub.add("40,108,78");
        hub.add("43,152,73");
        hub.add("-94,72,-129");
        hub.add("-187,92,-104");
        hub.add("-33,76,-213");
        hub.add("155,62,28");
        hub.add("176,64,42");
        hub.add("162,46,69");
        hub.add("147,53,88");
        hub.add("72,71,-190");
        hub.add("104,77,-133");
        hub.add("70,90,-149");
        hub.add("34,72,-162");
        hub.add("-229,123,84");
        hub.add("-207,100,66");
        hub.add("-259,114,85");
        hub.add("-262,102,67");
        hub.add("-252,132,51");
        hub.add("-260,96,48");
        hub.add("-233,86,84");
        hub.add("-214,103,89");
        hub.add("-195,55,153");
        hub.add("-245,75,149");
        hub.add("-152,67,123");
        hub.add("-261,56,115");
        hub.add("-133,74,133");
        hub.add("-191,102,109");
        hub.add("-183,80,29");
        hub.add("-166,79,93");
        hub.add("-248,74,125");
        hub.add("-6,66,-179");
        hub.add("-21,79,-166");
        hub.add("-34,67,-150");
        hub.add("-92,59,-138");
    }

    private static void islands() {
        islands.add("-294,85,31");
        islands.add("-315,89,-72");
        islands.add("-390,60,-5");
        islands.add("-357,99,79");
        islands.add("-386,108,-69");
        islands.add("-404,136,6");
        islands.add("-454,120,-58");
        islands.add("-408,122,-92");
        islands.add("-450,113,-87");
        islands.add("-370,112,-118");
        islands.add("-471,132,-125");
    }

    private static void den() {
        den.add("-198,160,-331");
        den.add("-185,135,-290");
        den.add("-203,169,-320");
        den.add("-301,92,-171");
        den.add("-297,90,-169");
        den.add("-294,36,-274");
        den.add("-309,63,-185");
        den.add("-309,66,-245");
        den.add("-204,94,-241");
        den.add("-279,127,-177");
        den.add("-336,82,-153");
        den.add("-422,106,-206");
        den.add("-336,111,-253");
        den.add("-322,95,-281");
        den.add("-222,74,-361");
        den.add("-140,85,-335");
        den.add("-147,78,-299");
        den.add("-169,62,-289");
        den.add("-160,62,-275");
    }

    private static void fortress() {
        fortress.add("-314,96,-402");
        fortress.add("-309,146,-427");
        fortress.add("-373,136,-398");
        fortress.add("-336,91,-447");
        fortress.add("-323,139,-443");
        fortress.add("-389,105,-462");
        fortress.add("-379,119,-478");
        fortress.add("-216,62,-481");
        fortress.add("-320,77,-503");
        fortress.add("-300,75,-545");
        fortress.add("-431,79,-564");
        fortress.add("-433,208,-579");
        fortress.add("-431,79,-564");
        fortress.add("-236,84,-592");
        fortress.add("-313,39,-677");
        fortress.add("-374,172,-683");
        fortress.add("-306,107,-691");
        fortress.add("-347,133,-694");
        fortress.add("-181,90,-608");
        fortress.add("-317,203,-739");
    }

    private static void mine() {
        mine.add("-47,75,-298");
        mine.add("-62,104,-289");
        mine.add("-37,78,-308");
        mine.add("17,86,-312");
        mine.add("21,36,-320");
        mine.add("-44,100,-344");
        mine.add("-26,94,-340");
        mine.add("-1,80,-337");
        mine.add("19,57,-341");
        mine.add("-19,142,-364");
        mine.add("-23,113,-353");
        mine.add("-11,76,-395");
    }

    private static void caverns() {
        caverns.add("3,152,85");
        caverns.add("18,74,74");
        caverns.add("-21,25,72");
        caverns.add("3,182,50");
        caverns.add("0,65,57");
        caverns.add("3,14,51");
        caverns.add("9,170,44");
        caverns.add("-60,37,52");
        caverns.add("-35,127,28");
        caverns.add("-18,163,26");
        caverns.add("44,98,23");
        caverns.add("57,161,19");
        caverns.add("29,149,14");
        caverns.add("-2,255,-1");
        caverns.add("-40,72,0");
        caverns.add("-11,102,-16");
        caverns.add("-71,13,5");
        caverns.add("-76,13,24");
        caverns.add("-8,74,-44");
        caverns.add("71,167,-12");
    }

    private static void barn() {
        barn.add("155,23,-204");
        barn.add("143,90,-243");
        barn.add("182,99,-235");
        barn.add("96,96,-287");
        barn.add("99,112,-275");
        barn.add("126,91,-304");
        barn.add("183,99,-305");
    }

    private static void desert() {
        desert.add("152,67,-361");
        desert.add("145,77,-374");
        desert.add("111,63,-447");
        desert.add("150,60,-448");
        desert.add("138,72,-587");
        desert.add("279,112,-541");
        desert.add("387,77,-365");
        desert.add("261,133,-348");
        desert.add("273,141,-467");
        desert.add("263,177,-565");
        desert.add("254,69,-493");
        desert.add("193,66,-468");
        desert.add("271,56,-361");
    }

    private static void end() {
        end.add("-517,100,-295");
        end.add("-545,92,-257");
        end.add("-492,81,-275");
        end.add("-587,122,-276");
        end.add("-696,116,-256");
        end.add("-748,106,-284");
        end.add("-587,48,-293");
        end.add("-723,75,-222");
        end.add("-657,36,-201");
        end.add("-609,85,-230");
        end.add("-583,208,-272");
        end.add("-492,21,-175");
    }

    private static void darwen() {
        darwen.add("-9,230,-135");
        darwen.add("22,127,184");
        darwen.add("-204,131,199");
        darwen.add("-108,141,143");
        darwen.add("-115,141,154");
        darwen.add("133,104,104");
        darwen.add("-21,208,-59");
        darwen.add("155,189,123");
        darwen.add("-139,220,-88");
        darwen.add("34,102,86");
        darwen.add("-52,205,48");
    }

    private static void dungeons() {
        dungeons.add("17,124,-58");
        dungeons.add("1,134,75");
        dungeons.add("10,164,-10");
        dungeons.add("-139,47,-1");
        dungeons.add("-55,82,-10");
        dungeons.add("-4,21,-17");
        dungeons.add("13,60,99");
    }
}