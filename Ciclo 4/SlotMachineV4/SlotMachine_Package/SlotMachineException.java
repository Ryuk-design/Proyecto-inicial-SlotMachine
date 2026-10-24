package SlotMachine_Package;

/**
 * Excepcion que lanza SlotMachine cuando una operacion no se puede realizar
 * @author (Samuel Ahumada)
 * @version (1.0)
 */
public class SlotMachineException extends Exception
{
    public static final String NO_WHEELS = "No hay ruedas existentes";
    public static final String NO_SYMBOLS = "No hay simbolos existentes";
    public static final String NO_SYMBOLS_TO_WALK = "No hay simbolos sobre los cuales caminar";
    public static final String INVALID_COLOR = "El color ya esta siendo utilizado o el color no existe";
    public static final String SYMBOL_NOT_ASSIGNED = "El color del simbolo que se busca eliminar no ha sido asignado aun";
    public static final String WHEEL_LOCKED = "La rueda esta bloqueada";
    public static final String WHEEL_ALREADY_LOCKED = "La rueda ya esta bloqueada";
    public static final String WHEEL_ALREADY_UNLOCKED = "La rueda ya esta desbloqueada";
    public static final String SOME_WHEEL_LOCKED = "Hay al menos una rueda bloqueada";
    public static final String WHEEL_CANNOT_LOCK = "Esta rueda no se deja bloquear";
    public static final String WHEEL_CANNOT_SWAP = "Una de las ruedas no se deja intercambiar";
    public static final String WHEEL_CANNOT_DELETE = "Esta rueda no se deja eliminar";
    public static final String INVALID_AMOUNT = "La cantidad de ruedas y simbolos debe estar entre 3 y 50";
    public static final String INVALID_CONFIGURATION = "La configuracion enviada no coincide con las ruedas o los simbolos existentes";
    public static final String INVALID_WHEEL_TYPE = "El tipo de rueda no existe";
    public static final String INVALID_SYMBOL_TYPE = "El tipo de simbolo no existe";

    public SlotMachineException(String message)
    {
        super(message);
    }

    public SlotMachineException(String message, Throwable cause)
    {
        super(message, cause);
    }
}