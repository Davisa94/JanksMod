package janksMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import janksMod.powers.CommonPower;
import janksMod.powers.FrozenPower;

public class FrostBiteAction extends AbstractGameAction {
    private int magicNumber;
    private AbstractCreature target;


    public FrostBiteAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.magicNumber = amount;
        this.actionType = ActionType.DAMAGE;
        this.source = source
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new FrozenPower(p, p, magicNumber), magicNumber,
                AttackEffect.BLUNT_LIGHT));
    }
}


