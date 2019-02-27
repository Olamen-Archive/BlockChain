/**
 * 
 */
package blockChain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * @author guozixua
 *
 */
public class BlockChainDriver {

  /**
   * @param args
   * @throws NoSuchAlgorithmException 
   */
  public static void main(String[] args) throws IllegalArgumentException, NoSuchAlgorithmException {
    int initialAmount = 0;
    if (args.length != 1) {
      throw new IllegalArgumentException("Invalid number of arguments");
    }
    initialAmount = Integer.valueOf(args[0]); //convert arg to initial amount
    Scanner in = new Scanner(System.in);
    BlockChain chain = new BlockChain(initialAmount);
    
    boolean isQuit = false;
    // Keep looping unless isQuit is true
    while (!isQuit) {
      System.out.println(chain.toString());
      System.out.print("Command? ");
      String command = in.next();
      
      switch(command) {
        case "help":
          System.out.println("mine: discovers the nonce for a given transaction");
          System.out.println("append: appends a new block onto the end of the chain");
          System.out.println("remove: removes the last block from the end of the chain");
          System.out.println("check: checks that the block chain is valid");
          System.out.println("report: reports the balances of Alice and Bob");
          System.out.println("help: prints this list of commands");
          System.out.println("quit: quits the program");
          break;
        case "mine": 
          System.out.print("Amount transferred? ");
          int amountTransfer = in.nextInt();
          Block newBlock = chain.mine(amountTransfer);
          System.out.printf("amount = %d, nonce = %d\n", newBlock.amount, newBlock.nonce);
          break;
        case "append": 
          System.out.print("Amount transferred? ");
          int transfer = in.nextInt();
          System.out.print("Nonce? ");
          long nonce = in.nextLong();
          try {
            chain.append(new Block(chain.getSize(), transfer, chain.getHash(), nonce));
          } catch (IllegalArgumentException err) {
            err.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case "remove":
          chain.removeLast();
          break;
        case "check":
          boolean isValid = chain.isValidBlockChain();
          if (isValid) {
            System.out.println("Chain is valid!");
          } else {
            System.out.println("Chain is invalid!");
          }
          break;
        case "report": 
          chain.printBalances();
          break;
        case "quit":
          isQuit = true;
          break;
        default: 
          System.out.println("Invalid command!");
      }
      System.out.println();
    }
    in.close();
  }
}
