/**
 *
 */
package blockChain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author guozixua
 *
 */
public class Hash {

  private byte[] hash;

  /**
   * Constructor of Hash
   * @param data byte array to hash
   * @throws NoSuchAlgorithmException
   */
  public Hash(byte[] data) {
      this.hash = data;
  }

  public static byte[] calculateHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(ByteBuffer.allocate(4).putInt(num).array());
    md.update(ByteBuffer.allocate(4).putInt(num).array());
    if (prevHash != null) {
      md.update(prevHash.getData());
    }
    md.update(ByteBuffer.allocate(8).putLong(nonce).array());
    return md.digest();
  }

  /**
   * Get hash value of data
   * @return hash value of data
   */
  public byte[] getData() {
    return hash;
  }

  /**
   * Determine if hash value is valid
   * @return true if hash start with three zero, vice versa
   */
  public boolean isValid() {
    return hash[0] == 0 && hash[1] == 0 && hash[2] == 0;
  }

  /**
   * @return String description of object
   */
  public String toString() {
    String myString = "";
    for (int i = 0; i < hash.length; i++) {
      myString += String.format("%02X", Byte.toUnsignedInt(this.hash[i]));
    }
    return myString;
  }

  /**
   * Determine if current hash object equal to other object
   */
  public boolean equals(Object other) {
    if (other instanceof Hash) {
      Hash otherHash = (Hash) other;
      return Arrays.equals(this.hash, otherHash.hash);
    }
    return false;
  }
}
