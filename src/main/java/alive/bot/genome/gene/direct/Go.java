package alive.bot.genome.gene.direct;

import alive.bot.genome.gene.Gene;
import alive.bot.model.Bot;

public class Go extends DirectGene {

    @Override
    public Boolean run(Bot bot) {

        var lookingPos = bot.getLookingPos();
        var cells = bot.getField().getCellsMatrix();
        lookingPos.ifPresent(pos -> {
            if (cells.isEmpty(pos)) {
                cells.addEmpty(bot.getPosition());
                bot.setPosition(pos);
                cells.addEntity(bot);
            }
        });

        bot.getEnergy().incrementEnergyValue(-2);
        bot.getGenome().incrementGeneIdx(1);
        return false;
    }

    @Override
    public Gene replicate() {

        return new Go();
    }
}
