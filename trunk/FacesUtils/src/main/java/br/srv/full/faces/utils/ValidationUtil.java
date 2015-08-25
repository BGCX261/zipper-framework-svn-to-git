package br.srv.full.faces.utils;

  

/**
 * Utility class for validation. This class contains commonly used validation logic which are been
 * refactored in single static methods.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public final class ValidationUtil {

    // Constructors -------------------------------------------------------------------------------

    private ValidationUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns true if the given string is null or is empty after trimming all whitespace.
     * @param string The string to be checked on emptiness.
     * @return True if the given string is null or is empty after trimming all whitespace.
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * Returns true if the given string is a valid email address.
     * @param string The string to be checked on being a vaild email address.
     * @return True if the given string is a valid email address.
     */
    public static boolean isEmail(String string) {
        return string.matches("^[\\w-~#&]+(\\.[\\w-~#&]+)*@([\\w-]+\\.)+[a-zA-Z]{2,5}$");
    }

    /**
     * Returns true if the given string is a valid number.
     * @param string The string to be checked on being a vaild number.
     * @return True if the given string is a valid number.
     */
    public static boolean isNumber(String string) {
        return string.matches("^\\d+$");
    }

}