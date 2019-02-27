/**
 * 
 */
package blockChain;

import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author guozixua
 *
 */
public class BlockChain {
  private Node head;
  private Node tail;

  private static class Node {
    Block item;
    Node next;

    /**
     * 
     * @param block A block
     * @param next next node of the link list if there is a node
     */
    public Node(Block block, Node next) {
      this.item = block;
      this.next = next;
    }
  }

  /**
   * 
   * @param intial the starting amount of Anna
   * @throws NoSuchAlgorithmException
   */
  public BlockChain(int intial) throws NoSuchAlgorithmException {
    Block blocks = new Block(0, intial, null);
    this.head = new Node(blocks, null);
    this.tail = this.head;
  }

  /**
   * mines a new candidate block to be added to the end of the chain.
   * 
   * @param amount the amount of money that is transfer
   * @return a valid block with the given amount
   * @throws NoSuchAlgorithmException
   */
  public Block mine(int amount) throws NoSuchAlgorithmException {
    int[] balance = getBalance();
    if (amount > balance[1] || amount < -balance[0]) {
      throw new IllegalArgumentException("invalid amount: " + amount);
    }
    Block block = new Block(this.tail.item.getNum() + 1, amount, this.tail.item.getHash());
    return block;
  }

  /**
   * 
   * @return get the amount of blocks in the block chain
   */
  public int getSize() {
    return this.tail.item.getNum() + 1;
  }

  /**
   * adds this block to the list
   * 
   * @param blk a block
   */
  public void append(Block blk) {
    int[] balance = getBalance();
    if (blk.getNum() != this.tail.item.getNum() + 1) {
      throw new IllegalArgumentException("invalid block number: " + blk.getNum());
    }
    if (blk.amount > balance[1] || blk.amount < -balance[0]) {
      throw new IllegalArgumentException("invalid amount: " + blk.amount);
    }
    if (!blk.getPrevHash().equals(this.tail.item.getHash())) {
      throw new IllegalArgumentException("invlaid prevhash: " + blk.prevHash);
    }
    Node myNode = new Node(blk, null);
    this.tail.next = myNode;
    this.tail = myNode;
  }

  /**
   * get the current balance of Anna and Bob First element of the array represent Anna and the
   * second element represent Bob
   * 
   * @return the current balance of Anna and Bob
   */
  private int[] getBalance() {
    int[] arr = new int[2];
    if (this.head == null) {
      return arr;
    }
    arr[0] = this.head.item.getAmount();
    Node myNode = this.head.next;

    while (myNode != null) {
      arr[0] += myNode.item.getAmount(); // add amount to anna
      arr[1] -= myNode.item.getAmount();
      myNode = myNode.next;
    }
    return arr;
  }

  /**
   * 
   * @return false if failed to remove the last block vice versa
   */
  public boolean removeLast() {
    if (this.head == this.tail) {
      return false;
    }
    Node currentNode = this.head;
    while (currentNode.next != this.tail) {
      currentNode = currentNode.next;
    }
    this.tail = currentNode;
    this.tail.next = null;
    return true;
  }

  /**
   * 
   * @return the hash of the last block
   */
  public Hash getHash() {
    return this.tail.item.getHash();
  }

  /**
   * 
   * @return if the block chain is vaild
   */
  public boolean isValidBlockChain() {
    Node currentNode = this.head;
    Node prevNode = null;
    int currentAmount = 0;

    while (currentNode != null) {
      currentAmount += currentNode.item.amount;

      if (currentAmount < 0) {
        return false;
      }

      if (prevNode != null) {
        if (!prevNode.item.getHash().equals(currentNode.item.getPrevHash())) {
          return false;
        }

      }

      prevNode = currentNode;
      currentNode = currentNode.next;
    }
    return true;
  }

  /**
   * print out the balance of Alice and Bob
   */
  public void printBalances() {
    int[] balances = getBalance();
    System.out.println("Alice: " + balances[0] + ", Bob: " + balances[1]);
  }

  /**
   * @return string of the current block chain
   */
  public String toString() {
    String myString = "";
    Node myNode = this.head;
    while (myNode != null) {
      if (myNode.next == null) {
        myString += myNode.item.toString();
      } else {
        myString += myNode.item.toString() + "\n";
      }
      myNode = myNode.next;
    }
    return myString;
  }
}
