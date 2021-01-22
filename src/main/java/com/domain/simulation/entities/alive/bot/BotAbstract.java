package com.domain.simulation.entities.alive.bot;

import com.domain.simulation.WorldConstants;
import com.domain.simulation.entities.alive.EntityAlive;
import com.domain.simulation.entities.alive.bot.single.BotSingle;
import com.domain.simulation.entities.alive.bot.single.energy.EnergyAlive;
import com.domain.simulation.entities.alive.bot.single.energy.EnergyAliveAlive;
import com.domain.simulation.entities.alive.bot.single.genome.Genome;
import com.domain.simulation.entities.alive.qualities.color.ColorEntity;
import com.domain.simulation.entities.alive.qualities.direction.LookDirection;
import com.domain.simulation.entities.alive.qualities.energy.EnergyEntity;
import com.domain.simulation.entities.alive.qualities.position.Position;
import com.domain.simulation.entities.alive.qualities.position.PositionEntity;
import com.domain.simulation.entities.lifeless.LifelessBotBody;
import com.domain.simulation.entities.visitor.Visitor;
import com.domain.simulation.field.Field;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class BotAbstract extends EntityAlive implements Bot {

    protected final Field field;

    protected final LookDirection lookDirection;

    protected final Genome genome;

    protected boolean isAlive = true;

    public BotAbstract(Field field, Position position, EnergyAlive energy, LookDirection lookDirection, Genome genome) {
        super(position, energy, new ColorEntity(0, 0, 0));

        this.field = field;
        energy.subscribeMortal(this);

        this.genome = genome;
        this.lookDirection = lookDirection;
    }

    @Override
    public void makeAMove() {

        var isMoving = isAlive;
        for (int i = 0; isMoving && i < WorldConstants.BOT_MAX_GENES_PER_MOVE; ++i) {

            isMoving = isAlive && genome.runCurrentGene(this);

            energy.incrementEnergyValue(WorldConstants.BOT_RUN_GENE_ENERGY_INCREMENT);
        }
    }

    @Override
    public boolean isAlive() {

        return isAlive;
    }

    @Override
    public void replicate() {

        if (!isAlive) {
            return;
        }

        Position newBotPos;

        var positionBehindOfBot = field.getCellsMatrix().makePositionToBeInside(lookDirection.opposite().getLookingPos(position));

        if (positionBehindOfBot.isPresent() && field.getCellsMatrix().isEmpty(positionBehindOfBot.get())) {
            newBotPos = positionBehindOfBot.get();
        } else {
            var possiblePositions = field.getCellsMatrix().findEmptyPositionsAround(position);
            if (possiblePositions.isEmpty()) {
                destroy();
                return;
            } else {
                newBotPos = possiblePositions.get(ThreadLocalRandom.current().nextInt(possiblePositions.size()));
            }
        }
        energy.setEnergyValue(energy.getEnergyValue() - genome.length() * WorldConstants.GENE_REPLICATION_COST);
        var newBotEnergyValue = energy.getEnergyValue() >> 1;
        var newBotEnergy = new EnergyAliveAlive(newBotEnergyValue);
        energy.setEnergyValue(newBotEnergyValue);

        var newBot = new BotSingle(field, newBotPos, newBotEnergy,
                lookDirection.opposite(), genome.replicate());

        field.putEntity(newBot);
    }

    @Override
    public void destroy() {

        isAlive = false;
        var deadBody = new LifelessBotBody(new PositionEntity(position),
                new EnergyEntity(energy.getEnergyValue() + WorldConstants.DRIED_BODY_ENERGY_VALUE));
        field.getCellsMatrix().put(deadBody);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isFriendly(Bot bot) {
        return genome.isFriendly(bot.getGenome());
    }

    @Override
    public Field getField() {

        return field;
    }

    @Override
    public Genome getGenome() {

        return genome;
    }

    @Override
    public LookDirection getLookDirection() {

        return lookDirection;
    }

    @Override
    public Optional<Position> getObservedPos() {
        return field.getCellsMatrix().makePositionToBeInside(lookDirection.getLookingPos(position));
    }
}