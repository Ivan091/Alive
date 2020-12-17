package alive.entities.alive.bot.genome.mutator.fabric.conditional;

import alive.entities.alive.bot.genome.gene.Gene;
import alive.entities.alive.bot.genome.gene.conditional.GenomeJump;
import alive.entities.alive.bot.genome.mutator.fabric.GeneFabric;

public class GenomeJumpFabric implements GeneFabric {
    @Override
    public Gene create(int key, int genomeLength) {
        if (genomeLength < 4) {
            key = 1;
        } else {
            key = key % (genomeLength / 4);
        }
        return new GenomeJump(key);
    }
}