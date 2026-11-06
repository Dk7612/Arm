//THIS IS ASWANTH's TERRITORY  
package org.tahomarobotics.robot.arm;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.Commands;

public interface ArmIF {
default Command forward() {
return Commands.none();
}
default Command stop(){
return Commands.none();
}
default Command backward() {
return Commands.none();
}
default Trigger isDeployed() {
return new Trigger(() -> false);
}
}

