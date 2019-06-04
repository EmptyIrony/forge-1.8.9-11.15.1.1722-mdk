package me.cunzai.bilibilichecker.Gui;

import me.cunzai.bilibilichecker.ConfigLoader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class EditGui extends GuiScreen {
    public static final String[] colors;
    private GuiButton rainbow;
    private GuiButton color;

    public void initGui(){
        this.buttonList.add(this.rainbow = new GuiButton(0,this.width/2 - 70,this.height/2 - 28,140,20,"彩色字体 :"+(ConfigLoader.rainbow ? "on" :"off")));
        this.buttonList.add(this.color = new GuiButton(1,width/2-70,height/2-6,140,20,"字体颜色:"+"§"+ConfigLoader.color+colors[ConfigLoader.getColorNum(ConfigLoader.color)]));
    }
    protected void actionPerformed(GuiButton button){
        switch (button.id){
            case 0: {
                ConfigLoader.rainbow=!ConfigLoader.rainbow;
                ConfigLoader.save();
            }
            case 1:{
                if (ConfigLoader.getColorNum(ConfigLoader.color)==15){
                    ConfigLoader.color=colors[0];
                    ConfigLoader.save();
                }
                ConfigLoader.color=colors[ConfigLoader.getColorNum(ConfigLoader.color)+1];
                ConfigLoader.save();
            }
        }
    }
    public boolean doseGuiPauseGame(){
        return false;
    }





    static {
        colors = new String[]{"1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    }


}
