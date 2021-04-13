package labs.pm.exceptions;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: CommodityManagerException.java</p>
 * <p>Date/time: 14 апрель 2021 in 1:02</p>
 * @author Andrei G. Pastushenko
 * <p>Class Castom Exception</p>
 * @see Exception
 * @see java.util.NoSuchElementException
 */

public class CommodityManagerException extends Exception {

    public CommodityManagerException() {
        super();
    }

    public CommodityManagerException(String message) {
        super(message);
    }

    public CommodityManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
