package me.cunzai.bilibilichecker;


import me.cunzai.bilibilichecker.Gui.EditGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.opengl.GL11;
import sun.security.krb5.Config;

import java.awt.*;

public class BiliBiliGUI {
    private String text;
    private String color;
    private boolean rainbow;
    private float scale;
    private double x;
    private double y;

    public BiliBiliGUI(String text, String color, boolean rainbow, double x, double y)
    {
        this.text = text;
        this.color = color;
        this.rainbow = rainbow;
        this.scale = 1.0F;
        this.x = x;
        this.y = y;
    }
    public void draw(){
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        int width = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
        int height = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
        int x = (int) ((width - (fr.getStringWidth(this.getText()) * this.getScale())) * this.getX());
        int y = (int) ((height - (fr.FONT_HEIGHT * this.getScale())) * this.getY());
        x = Math.round(x / this.getScale());
        y = Math.round(y / this.getScale());
        Minecraft mc = Minecraft.getMinecraft();
        Gui.drawRect(60, 80, 80 + fr.getStringWidth(this.getText()), 120 + fr.FONT_HEIGHT, 65536);
        if (rainbow()) {
            int i = Color.HSBtoRGB((float) (System.currentTimeMillis() % 1000L) / 1000.0F, 0.8F, 0.8F);
            mc.fontRendererObj.drawString(text, x, y, i, rainbow);
        }else {
            fr.drawString("§"+this.getColor()+this.getText(), x, y, 1, false);
        }


    }




    public double getX(){
        return this.x;
    }
    public String getText(){
        return this.text;
    }
    public float getScale(){
        return this.scale;
    }
    public double getY(){
        return this.y;
    }
    public String getColor(){
        return this.color;
    }
    public boolean rainbow(){return rainbow;}
    public void update(String text, String color, boolean rainbow, double x, double y){
        this.text=text;
        this.color=color;
        this.rainbow=rainbow;
        this.x=x;
        this.y=y;



    }





}
