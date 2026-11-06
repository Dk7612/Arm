package org.tahomarobotics.robot.roller;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Roller implements RollerIF {
    private final RollerSubsystem subsystem;

    public Roller() {
        this(new RollerSubsystem());
    }

    
    Roller(RollerSubsystem subsystem) {
        this.subsystem = subsystem;
    }
    @Override
    public Command intake() {
        return subsystem.run(() -> subsystem.setVelocity(RollerConstants.ROLLER_INTAKE_RPS))
        .finallyDo(subsystem::setNeutralmode)
        .withName("Roller Intake Command");

    }
        @Override
        public Trigger isSpinning() {
            return new Trigger(subsystem::isSpinning);
        }

    @Override
    public Command eject() {
        return subsystem.run(() -> subsystem.setVelocity(RollerConstants.ROLLER_EJECT_RPS))
        .finallyDo(subsystem::setNeutralmode)
        .withName("Roller Eject Command");
    }

    @Override
    public Command stop() {
        return subsystem.run(subsystem::setNeutralmode)
        .withName("Roller Stop Command");
    }
}


