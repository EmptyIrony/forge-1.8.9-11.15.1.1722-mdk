package me.cunzai.bilibilichecker;

import me.cunzai.bilibilichecker.Gui.EditGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand implements ICommand {

    @Override
    public String getCommandName(){
        return "BiliBili";
    }
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "其实你只要输入/BiliBili Reload一下就好了";
    }
    @Override
    public void processCommand(ICommandSender sender, String[] args)throws CommandException{
        if (args.length==0){
            sendHelpMessage(sender);
        }else {
            if (args.length==1){
                if (args[0].equalsIgnoreCase("reload")){
                    sender.addChatMessage( new ChatComponentText("§b加载配置文件中"));
                    ConfigLoader.load();
                    int fans;
                    Thread thread = new Thread(new Checker());
                    thread.start();
                    try {
                        Checker.lockGetFans.lock();
                        fans = Checker.fans;
                    }finally {
                        Checker.lockGetFans.unlock();
                    }
                    if (fans==-1){
                        BiliBiliChecker.gui.update("无法获取粉丝数，有可能你被B站封了，请尝试/bilibili reload，请等待解封",ConfigLoader.color,ConfigLoader.rainbow,ConfigLoader.x,ConfigLoader.y);
                    }else { BiliBiliChecker.gui.update(ConfigLoader.text,ConfigLoader.color,ConfigLoader.rainbow,ConfigLoader.x,ConfigLoader.y);}
                    sender.addChatMessage( new ChatComponentText("§b加载完成"));
                }else if (args[0].equalsIgnoreCase("edit")){
                    Minecraft.getMinecraft().displayGuiScreen(new EditGui());
                }
            }
        }
    }
    private void sendHelpMessage(ICommandSender player) {
        player.addChatMessage(new ChatComponentText("§7§m----------------------------"));
        player.addChatMessage(new ChatComponentText("§b§lBiliBiliChecker"));
        player.addChatMessage(new ChatComponentText(" "));
        player.addChatMessage(new ChatComponentText("  §bReload §7- §f重载配置文件."));
        player.addChatMessage(new ChatComponentText(" "));
        player.addChatMessage(new ChatComponentText("§7§m----------------------------"));
    }
    public List getCommandAliases(){
        ArrayList<String> list =new ArrayList<String>();
        list.add("bilibili");
        return list;
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("bilibili");
        return list;
    }
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }
    @Override
    public int compareTo(ICommand arg0) {
        return 0;
    }

}
