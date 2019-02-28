package blockChain;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public class Block {
  int num;
  int amount;
  Hash prevHash;
  Hash currHash;
  long nonce;

  /**
   * Generate a new block with num, amount, and preHash. Find nonce by mining.
   * 
   * @param num the number that new block have
   * @param amount the amount of money transfer
   * @param prevHash the hash of previous block. null if it is first block.
   * @throws NoSuchAlgorithmException
   */
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;

    long nonce = 0;
    Hash currHash = new Hash(new byte[0]);
    for (nonce = 0; nonce < Long.MAX_VALUE; nonce++) {
      byte[] tmpByte;
      /*
      if (prevHash == null) {
        tmpByte = ByteBuffer.allocate(64).putInt(num).putInt(amount).putLong(nonce).array();
      } else {
        tmpByte = ByteBuffer.allocate(64).putInt(num).putInt(amount).putLong(nonce)
            .put(prevHash.getData()).array();
      }
      */
      tmpByte = Hash.calculateHash(num, amount, prevHash, nonce);
      currHash = new Hash(tmpByte);
      if (currHash.isValid())
        break;
    }

    this.nonce = nonce;
    this.currHash = currHash;
    this.prevHash = prevHash;
  }

  /**
   * Generate new block by given num, amount, preHash, and nonce.
   * 
   * @param num the number that new block have
   * @param amount the amount of money transfer
   * @param prevHash the hash of previous block. null if it is first block.
   * @param nonce the nonce of new block
   * @throws NoSuchAlgorithmException
   * @throws IllegalArgumentException if nonce is invalid
   */
  public Block(int num, int amount, Hash prevHash, long nonce)
      throws IllegalArgumentException, NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    /*
    if (prevHash == null) {
      this.currHash =
          new Hash(ByteBuffer.allocate(64).putInt(num).putInt(amount).putLong(nonce).array());
    } else {
      this.currHash = new Hash(ByteBuffer.allocate(64).putInt(num).putInt(amount).putLong(nonce)
          .put(prevHash.getData()).array());
    }
    */
    currHash = new Hash(Hash.calculateHash(num, amount, prevHash, nonce));
    if (!currHash.isValid()) {
      throw new IllegalArgumentException("invalid nonce: " + nonce);
    }
  }

  /**
   * 
   * @return the number of blocks
   */
  public int getNum() {
    return this.num;
  }

  /**
   * 
   * @return the amount of money that is transfer
   */
  public int getAmount() {
    return this.amount;
  }

  /**
   * 
   * @return the nonce from the block
   */
  public long getNonce() {
    return this.nonce;
  }

  /**
   * 
   * @return the previous Hash of the block
   */
  public Hash getPrevHash() {
    return this.prevHash;
  }

  /**
   * 
   * @return get the current Hash
   */
  public Hash getHash() {
    return this.currHash;
  }

  /**
   * @return the string description of the block
   */
  public String toString() {
    return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
        + ", preHash: " + (this.prevHash == null ? "null" : prevHash.toString()) + ", hash: "
        + this.currHash.toString() + ")";
  }
}
