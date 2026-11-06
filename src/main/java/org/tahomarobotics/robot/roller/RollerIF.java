package org.tahomarobotics.robot.roller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.Commands;

public interface RollerIF {
default Command intake() {
return Commands.none();
}
default Command stop(){
return Commands.none();
}
default Command eject() {
return Commands.none();
}
default Trigger isSpinning() {
return new Trigger(() -> false);
}
}

