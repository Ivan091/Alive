package alive.bot.genome.gene.direct;

import alive.bot.genome.gene.Gene;
import alive.bot.model.Bot;

public class Eat extends DirectGene {

    @Override
    public Boolean run(Bot bot) {

        var lookingPos = bot.getLookingPos();
        var cells = bot.getField().getCellsMatrix();

        lookingPos.ifPresent(pos -> {
            var eatingContent = cells.getEntity(pos);
            cells.addEmpty(pos);
            bot.getEnergy().incrementEnergyValue(eatingContent.getEnergy().getEnergyValue() >> 1);
        });

        bot.getEnergy().incrementEnergyValue(-2);
        bot.getGenome().incrementGeneIdx(1);
        return false;
    }

    @Override
    public Gene replicate() {

        return new Eat();
    }
}
