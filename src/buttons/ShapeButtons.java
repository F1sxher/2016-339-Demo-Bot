package buttons;

/**
 * Functions for all of the shape buttons.
 *
 * @author Fisher
 */
public abstract class ShapeButtons {
    public static final byte TRIANGLE = client.Controller.TRIANGLE; // the Triangle button
	public static final byte CIRCLE = client.Controller.CIRCLE; // the Circle button
	public static final byte CROSS = client.Controller.CROSS; // the Cross button
	public static final byte SQUARE = client.Controller.SQUARE; // the Square button

    /**
     * Sets the controller to the drive controller
     * @button {@link ShapeButtons#CIRCLE}
     * @return The Set controler Type 
     * @param controller The controller to set
     */
    public client.Controller.controllerTypes setDriveController(client.Controller controller)
    {
        return controller.setControllerType(client.Controller.controllerTypes.DriverController);
    }

    /**
     * Sets the controller to the operator controller
     * @button {@link ShapeButtons#TRIANGLE}
     * @return The Set controller Type
     * @param controller The controller to set
    */
    public client.Controller.controllerTypes setOperatorController(client.Controller controller)
    {
        return controller.setControllerType(client.Controller.controllerTypes.OperatorController);
    }
}
