package janksMod.powers;

import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import janksMod.JanksMod;
import janksMod.actions.FrostBiteAction;
import janksMod.util.TextureLoader;
import jdk.nashorn.internal.ir.ReturnNode;

import static janksMod.JanksMod.makePowerPath;

public class FrostBitePower extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;
    public static final String POWER_ID = JanksMod.makeID("FrostBite");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = false;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public FrostBitePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = "FrostBite";
        this.source = source;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        if (isMonster(this.source)){this.justApplied = true;}
    }

    public boolean isMonster(AbstractCreature c){
        if (c instanceof AbstractMonster) {
            return true;
        }
        else{
            return false;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer){

    }


    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        }
        else {
            // if the amount is zero then remove this power
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "FrostBite"));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "FrostBite", 1));
            }
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        damage = damage * 0.75F;
        return damage;
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new FrostBiteAction(this.owner, this.amount));
    }


    @Override
    public AbstractPower makeCopy() {
        return new FrostBitePower(owner, source, amount);
    }
}
