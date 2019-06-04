package me.cunzai.bilibilichecker;

import com.google.common.eventbus.Subscribe;
import me.cunzai.bilibilichecker.Proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentScore;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.Configuration;

import static me.cunzai.bilibilichecker.Checker.*;

@Mod(name=BiliBiliChecker.MODID,modid = BiliBiliChecker.NAME,version=BiliBiliChecker.VERSION,acceptableRemoteVersions = "[1.7.10,1.8.9]")
public class BiliBiliChecker {
    public static final String MODID = "BiliBiliChecker";
    public static final String NAME= "BiliBili Checker";
    public static final String VERSION = "1.0";
    public static final Logger logger;
    public static BiliBiliGUI gui;
    private int timer;

    @Mod.Instance(BiliBiliChecker.MODID)
    public static  BiliBiliChecker ins;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ins = this;
        new ConfigLoader(event);
        ConfigLoader.load();
        Checker.refresh();
        ConfigLoader.replace();
        ConfigLoader.save();
        int fans;
        fans = Checker.fans;
        logger.info(fans);
        logger.info(fans);
        if (fans==-1){
            this.gui = new BiliBiliGUI("无法获取粉丝数，有可能你被B站封了，请尝试/bilibili reload，或请等待解封",ConfigLoader.color,ConfigLoader.rainbow,ConfigLoader.x,ConfigLoader.y);
        }else {
            this.gui = new BiliBiliGUI(ConfigLoader.text, ConfigLoader.color, ConfigLoader.rainbow, ConfigLoader.x, ConfigLoader.y);
        }
        ClientCommandHandler.instance.registerCommand(new ReloadCommand());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);

    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent e){
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT) {
            gui.draw();
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e){
        if (timer>=ConfigLoader.time*40) {
            int fans;
            timer=0;
            Thread thread = new Thread(new Checker());
            thread.start();
            try{
                lockGetFans.lock();
                fans=Checker.fans;
            }finally {
                lockGetFans.unlock();
            }

            if (fans == -1) {
                gui.update("无法获取粉丝数，有可能你被B站封了，请尝试/bilibili reload，或请等待解封", ConfigLoader.color, ConfigLoader.rainbow, ConfigLoader.x, ConfigLoader.y);
            } else {
                gui.update(ConfigLoader.unText.replace("[fans]", fans + ""), ConfigLoader.color, ConfigLoader.rainbow, ConfigLoader.x, ConfigLoader.y);
                logger.info("成功刷新粉丝数！");
            }
        }else { timer++; }
    }
    @SubscribeEvent
    public void onTickCheckUpdate(TickEvent.ClientTickEvent e){
        if (timer>=120000){
            timer=0;
            boolean hasNewVersion;
            try{
                UpdateCheck.lockNewVersion.lock();
                hasNewVersion=UpdateCheck.haveNewVersion;
            }finally {
                UpdateCheck.lockNewVersion.unlock();
            }
            if (hasNewVersion) {
                try {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b[BiliBiliChecker]发现新版本！请及时更新！群;422286334"));
                }catch (Exception ex){
                    logger.info("发现新版本，但无法发送更新提示");
                }
            }

        }else {timer++;}
    }


    static {
        logger = LogManager.getLogger("BiliBili");
    }





}
