package com.company;

import java.lang.reflect.Array;

public class Creature extends Card {
    private int atk;
    private int def;
    private String glyph;

    public int getAtk() {
        return(atk);
    }

    public String getGlyph() {
        if(glyph == null){
            return("Null");
        }
        return glyph;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getDef() {
        return def;
    }

    public void setGlyph(String glyph) {
        this.glyph = glyph;
    }
    public String print(){
        return("Atk: " + this.getAtk() + ", Def:" + this.getDef() + ", Glyph:" + this.getGlyph());
    }
}
