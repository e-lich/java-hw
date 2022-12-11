package hr.fer.oprpp1.hw05.crypto;

public class Util {
    public static byte[] hexToByte(String keyText) {
        if (keyText.length() % 2 != 0) {
            throw new IllegalArgumentException("Length of key text must be even!");
        }

        byte[] result = new byte[keyText.length() / 2];

        for (int i = 0, j = 0; i < keyText.length(); i += 2, j++) {
            result[j] = (byte) Integer.parseInt(keyText.substring(i, i + 2), 16);
        }

        return result;
    }

    public static String byteToHex(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();

        for (byte b : byteArray) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }

        return sb.toString();
    }
}
