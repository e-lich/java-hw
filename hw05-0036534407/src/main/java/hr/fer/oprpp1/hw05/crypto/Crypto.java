package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * Program that can encrypt/decrypt given file using the AES crypto algorithm and check and calculate sha-256 digest.
 */
public class Crypto {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }

        if (args[0].equals("checksha")) {
            checkSha(args[1]);
        } else if (args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments!");
        } else if (args[0].equals("encrypt")) {
            crypt(true, args[1], args[2]);
        } else if (args[0].equals("decrypt")) {
            crypt(false, args[1], args[2]);
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }

    }

    /**
     * @param file path to file
     * @throws NoSuchAlgorithmException if SHA-256 algorithm is not available
     * @throws IOException if an I/O error occurs
     */
    private static void checkSha(String file) throws NoSuchAlgorithmException, IOException {
        System.out.println("Please provide expected sha-256 digest for " + file + ":");

        Scanner sc = new Scanner(System.in);
        String expected = sc.nextLine();
        sc.close();

        byte[] expectedBytes = Util.hexToByte(expected);

        Path filePath = Path.of(file);
        byte[] actual = digest(filePath);

        if (actual.length != expectedBytes.length) {
            System.out.println("Digesting completed. Digest of " + file +
                    " does not match the expected digest. Digest was: " + Util.byteToHex(actual));
        } else {
            for (int i = 0; i < actual.length; i++) {
                if (actual[i] != expectedBytes[i]) {
                    System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest." +
                            "\nDigest was: " + Util.byteToHex(actual));
                    return;
                }
            }

            System.out.println("Digesting completed. Digest of " + file + " matches the expected digest.");
        }
    }

    /**
     * @param filePath path to file
     * @return byte array of sha-256 digest
     * @throws NoSuchAlgorithmException if SHA-256 algorithm is not available
     * @throws IOException if an I/O error occurs
     */
    private static byte[] digest(Path filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        InputStream inputStream = new BufferedInputStream(Files.newInputStream(filePath));
        byte[] inputLine;

        while ((inputLine = inputStream.readNBytes(4000)).length != 0) {
            md.update(inputLine);
        }

        inputStream.close();

        return md.digest();
    }

    /**
     * @param encrypt true if encrypting, false if decrypting
     * @param src path to source file
     * @param dest path to destination file
     * @throws NoSuchPaddingException if the transformation contains a padding scheme that is not available
     * @throws NoSuchAlgorithmException if the transformation contains a particular data encryption algorithm that is not available
     * @throws InvalidAlgorithmParameterException if the given algorithm parameters are inappropriate for this cipher
     * @throws InvalidKeyException if the given key is inappropriate for initializing this cipher
     * @throws IOException if an I/O error occurs
     * @throws IllegalBlockSizeException if this cipher is a block cipher, no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size
     * @throws BadPaddingException if this cipher is in decryption mode, and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
     */
    private static void crypt(boolean encrypt, String src, String dest) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        // getting password and initialization vector from standard input
        Scanner sc = new Scanner(System.in);

        System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
        String keyText = sc.nextLine();

        System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
        String ivText = sc.nextLine();

        sc.close();

        // initialize Cipher object
        SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

        // set up streams
        Path srcPath =  Path.of(src);
        Path destPath = Path.of(dest);

        InputStream inputStream = new BufferedInputStream(Files.newInputStream(srcPath));
        OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(new File(destPath.toUri()).toPath()));

        // encrypt/decrypt
        byte[] inputLine;

        while ((inputLine = inputStream.readNBytes(4000)).length != 0) {
            outputStream.write(cipher.update(inputLine));
        }

        outputStream.write(cipher.doFinal());

        inputStream.close();
        outputStream.close();

        System.out.println((encrypt ? "Encryption" : "Decryption") + " completed." +
                "Generated file " + dest + " based on file " + src +".");

    }
}
