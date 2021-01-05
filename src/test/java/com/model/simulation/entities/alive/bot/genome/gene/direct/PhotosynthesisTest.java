package com.model.simulation.entities.alive.bot.genome.gene.direct;

import com.model.simulation.entities.alive.bot.genome.gene.GeneTest;
import com.model.simulation.entities.alive.bot.genome.mutator.factory.direct.PhotosynthesisGeneFactory;

class PhotosynthesisTest extends GeneTest {

    public PhotosynthesisTest() {
        super(new PhotosynthesisGeneFactory().create(0, 0));
    }
}