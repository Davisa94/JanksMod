package janksMod.powers;

import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.sun.org.apache.xpath.internal.operations.Bool;
import janksMod.JanksMod;
import janksMod.util.TextureLoader;

import static janksMod.JanksMod.makePowerPath;

public class GetSwolPower extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;
    //Change The name here And add it to the LanguagePack strings
    public static final String POWER_ID = JanksMod.makeID("GetSwolPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    //Change the path to the texture that you want to use here:
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public GetSwolPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME; //This is pulled from the powerStrings from the languagePack
        this.ID = "GetSwolPower";
        this.source = source; // Who used the power
        this.owner = owner; // Who has the power applied on them
        this.amount = amount; // How much of this power is Currently used;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        //load textures
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Strength"));
        }

        if (this.amount >= 999) {
            this.amount = 999;

        }
        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, NAME));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    @Override
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[1] + tmp + DESCRIPTIONS[2];
            this.type = PowerType.DEBUFF;
        }
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {

    }

    @Override
    public void atStartOfTurn() {

    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        //Put your code here that should run when the card containing this keyword is played
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
    }

    //You don't really need To edit this method
    @Override
    public AbstractPower makeCopy() {
        return new GetSwolPower(owner, source, amount);
    }
}

