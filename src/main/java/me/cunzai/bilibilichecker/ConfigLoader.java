package me.cunzai.bilibilichecker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
    private static Configuration config;
    private static Logger logger;
    public static int uid;
    public static String text;
    public static Double x;
    public static Double y;
    public static boolean rainbow;
    public static String color;
    public static int time;
    public static String unText;
    public ConfigLoader (FMLPreInitializationEvent e){
        logger = e.getModLog();
        config = new Configuration(e.getSuggestedConfigurationFile());
        load();


    }
    public static void load(){
        config.load();
        String comment;
        comment = "你的BiliBili UID，如果喜欢这个mod，建议关注我https://space.bilibili.com/5832391/";
        uid = config.get(Configuration.CATEGORY_GENERAL,"uid",5832391,comment).getInt();
        String comment1 = "显示的文字，不需要加颜色符号 [fans] 为变量，注意变量大小写";
        unText = config.get(Configuration.CATEGORY_GENERAL,"text","你的粉丝数为: [fans]",comment1).getString();
        String comment2 = "显示的x坐标";
        x = config.get(Configuration.CATEGORY_GENERAL,"x",0.25D,comment2).getDouble();
        String comment3 = "显示的y坐标";
        y = config.get(Configuration.CATEGORY_GENERAL,"y",0.25D,comment3).getDouble();
        String comment4 = "字体是否为彩色，如果是，那么无视下面的字体颜色";
        rainbow = config.get(Configuration.CATEGORY_GENERAL,"Rainbow",false,comment4).getBoolean();
        String comment5 = "字体的颜色";
        color = config.get(Configuration.CATEGORY_GENERAL,"color","b",comment5).getString();
        String comment6 = "刷新时间，建议不要太频繁，否则有可能被B站API拉黑，单位秒，默认为30秒，建议10秒以上，稳健~具体我也没测试过，你们可以自己测试";
        time = config.get(Configuration.CATEGORY_GENERAL,"time",30,comment6).getInt();
    }
    public static void save(){
        config.save();
    }
    public static int getColorNum(String color){
        if (color.equalsIgnoreCase("1")){
            return 0;
        }else if (color.equalsIgnoreCase("2")){
            return 1;
        }else if (color.equalsIgnoreCase("3")){
            return 2;
        }else if (color.equalsIgnoreCase("4")){
            return 3;
        }else if (color.equalsIgnoreCase("5")){
            return 4;
        }else if (color.equalsIgnoreCase("6")){
            return 5;
        }else if (color.equalsIgnoreCase("7")){
            return 6;
        }else if (color.equalsIgnoreCase("8")){
            return 7;
        }else if (color.equalsIgnoreCase("9")){
            return 8;
        }else if (color.equalsIgnoreCase("a")){
            return 9;
        }else if (color.equalsIgnoreCase("b")){
            return 10;
        }else if (color.equalsIgnoreCase("c")){
            return 11;
        }else if (color.equalsIgnoreCase("d")){
            return 12;
        }else if (color.equalsIgnoreCase("e")){
            return 13;
        }else if (color.equalsIgnoreCase("f")){
            return 14;
        }else return 15;
    }
    public static void replace(){
        int fans;
        try{
            Checker.lockGetFans.lock();
            fans = Checker.fans;
        }finally {
            Checker.lockGetFans.unlock();
        }
        ConfigLoader.text = ConfigLoader.unText.replace("[fans]",fans+"");
    }
}
