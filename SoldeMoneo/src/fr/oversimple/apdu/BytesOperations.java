package fr.oversimple.apdu;

public class BytesOperations {

    /**
     *
     * Convert a byte array to a string in the hexadecimal representation
     *
     * @param byteIn
     *            the byte array to convert
     * @return A string hexadecimal representation of this byte array
     */
    public static String convert(byte[] byteArray) {
        String str = "";

        for (int i = 0; i < byteArray.length; i++) {
            str += convert(byteArray[i]);
        }
        return str;
    }

    /**
     *
     * Convert a byte to a string in the hexadecimal representation
     *
     * @param byteIn
     *            the byte to convert
     * @return A string hexadecimal representation of this byte
     */
    public static String convert(byte byteIn) {
        return Integer.toString((byteIn & 0xff) + 0x100, 16).substring(1);
    }

    /**
     * Convert a string to a byte array
     *
     * @param s
     *            the string hexadecimal representation of a byte array
     * @return the corresponding byte array
     */
    public static byte[] convert(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     *
     * Concatenate two byte in a byte array
     *
     * @param a the left most byte
     * @param b the right most byte
     * @return a byte array byteArray=a|b
     */
    public static byte[] concat(byte a, byte b) {
        byte[] result = new byte[2];
        result[0] = a;
        result[1] = b;
        return result;
    }

    /**
     *
     * Concatenate a byte on the left of a byte array
     *
     * @param a the left most byte
     * @param b the byte array on the right
     * @return a new byte array with a|b[]
     */
    public static byte[] concat(byte a, byte[] b) {
        byte[] result = new byte[1 + b.length];
        result[0] = a;
        System.arraycopy(b, 0, result, 1, b.length);
        return result;
    }

    /**
     * Concatenate a byte array on the left of a byte
     * @param a the byte array on the left
     * @param b the byte on the right
     * @return a[]|b
     */
    public static byte[] concat(byte[] a, byte b) {
        byte[] result = new byte[a.length + 1];
        System.arraycopy(a, 0, result, 0, a.length);
        result[a.length] = b;
        return result;
    }

    /**
     * Concatenante two byte array
     * @param a the left most byte array
     * @param b the right most byte array
     * @return a[]|b[]
     */
    public static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    /**
     * Convert a byte[] to an int value
     * @param a the byte[] array
     * @return the int value of a
     */
    public static int toInt(byte[] a) {
        int result = 0;
        int pow = 1;
        for (int i = 0; i < a.length; i++) {
            result = result + toInt(a[i]) * pow;
            pow *= 16;
        }
        return result;
    }

    /**
     * Cast intellignetly a byte to an int avoiding sign errors
     * @param b the byte to cast in int
     * @return the int value without casting mistake
     */
    public static int toInt(byte b) {
        ;
        int result = (b & 0x000000FF);
        return result;
    }

    /**
     * Convert an int to a byte[]
     * @param value the int to convert into int
     * @return the byte[] representation of the int value
     */
    public static final byte[] intToByteArray(int value) {
        return new byte[] { (byte) ((value & 0xFF) >>> 24), (byte) ((value & 0xFF) >>> 16),
                (byte) ((value & 0xFF) >>> 8), (byte) (value & 0xFF) };
    }
}