/**
* I declare that this assignment is my own work and that all 
* material previously written or published in any source by any 
* other person has been duly acknowledged in the assignment. I 
* have not submitted this work, or a significant part thereof, 
* previously as part of any academic program. In submitting this 
* assignment I give permission to copy it for assessment purposes 
* only.
* 
* title: GetMail.java
* description: A java program that will connect to your preferred email server
*              and retrieve all unread messages, printing them line by line in the terminal to the user.
*              Can also retrieve the contents of a selected email with a second inovcation of the program with
*              one additional argument, an integer representing the number of the email. 
* @author craigleech
* @version 1.0
* @since 2018-06-28
*/

/**
 * DOCUMENTATION...
 */
/**                                                                               
 *
 *<P>
 * This program is run via the command prompt. It retirieves email from the desired server.
 * Host, Username, and password will be passed in a command line arguments.
 * An additional integer arugment can be passed in via command prompt. This is used an an index
 * for the messages and will return the content of that selected message
 * 
 *</P>
 *<P>
 * This program uses Java(TM) SE Runtime Environment (build 9.0.4+11),
 * JavaMail API (availavle here: http://www.oracle.com/technetwork/java/javamail/index.html) and,
 * JavaBeans Activation Frameworks (available here: http://www.oracle.com/technetwork/articles/java/index-135046.html).
 * Download JavaMail and JAF and unzip them. Search through the unzipped files for mail.jar and activation.jar.
 * You will need to add mail.jar and activation.jar to your classpath
 *</P>
 *                                                                              
 *<DL>
 *<DT> Compiling and running instructions</DT>
 *<DT> Dowload and install javaMail and JAF </DT>
 *<DT> Add mail.jar and activation.jar to your classpath </DT>
 *<DT> Change to the directory containing the source code.</DT>
 *<DD> Compile:    javac GetMail.java</DD>
 *<DD> Run:        java GetMail host username password</DD>
 *<DD> Document:   javadoc GetMail.java</DD>
 *</DL>
 */

/**                                                                               
 *
 *<P> 
 * public class GetMail{<BR>
 * Private inner class
 *</P>
 *<P>
 * public static void main (String args[]){ <BR>
 * Main method used to execute the program
 *</P>  
 *<P>
 * public void getUnread throws NoSuchProviderException, MessagingException{ <BR>
 * retirves all unread email message from the selected server
 *</P> 
 *<P>
 * public void getMail throws NoSuchProviderException, MessagingException{ <BR>
 * displays the selected email to the user
 *</P>
 */

/**
 *
 * <H3>Test Plan</H3>
 *
 *<P>
 * 1. Run the application from command prompt with arguments host username password.
 *    Or run application from command prompt with arguments host username password index, where index
 *    is some integer.
 * EXPECTED:
 *   Program will retrieve all unread emails, or if run with additional argument will display the
 *   message contents to the user
 *<P>
 * 2. Good data cases:
 * EXPECTED:
 *    Run the following test cases by running the program with the following parameters:
 *
 *   1. java GetMail (the desired host server) (username) (password) 
 *          Program will retierve all unread emails if given proper host, username, and password.
 *          
 *           1 Subject: Craig, welcome to your new Google AccountFrom: (Andy from Google <andy-noreply@google.com>)
 *           2 Subject: Account registration confirmationFrom: (PlayStation <Sony@email.sonyentertainmentnetwork.com>)
 *
 * ACTUAL:
 *    Results displayed as expected. All unread emails printed to the terminal
 * 
 *   2. java GetMail (desired host) (username) (password) (1)
 *          Program will display the contents of the selected message to the user
 *          
 *           
 *
 * ACTUAL:
 *    Results displayed as expected. Contents of message are displayed to user. Cannot display html pages.
 *</P>
 *<P>
 * 3. Bad data cases:
 * EXPECTED:
 *    Run the following test cases by running the program with the following parameters:
 *
 *    1.  (No Such Provider) to run: java GetMail some.host.com username password
 *          output should be:
 *
 *          com.sun.mail.util.MailConnectException: Couldn't connect to host, port: some.host.com, 995; timeout -1;
 *          nested exception is:
 *          java.net.UnknownHostException: some.host.com
 *
 *    2. (Incorrect username or password) to run: java GetMail host username password
 *          output should be:
 *          
 *          javax.mail.AuthenticationFailedException: [AUTH] Username and password not accepted.
 *
 *    3. (Attempting to access an email that does not exist) to run: java GetMail host username password 10000
 *          output should be:
 *
 *          java.lang.ArrayIndexOutOfBoundsException: 9999  
 *          
 * ACTUAL: 
 *    Results displayed as expected.
 *
 *
 * 4. ***Note. Program will run and send email whether attachment exists or not. User does not have to attatch a file.
 *</P>
 */ 


/**
 * CODE...
 */

/** import java packages */
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.Flags;
import javax.mail.search.FlagTerm;

/** primary class for GetMail*/
public class GetMail {

   /**
   * Method to retrieve all of the Unread messages from a selected email folder.
   * Constructs an arrays of of messages and prints out the message number, subject, and from line by line to the user
   * @param host  The server to connect to
   * @param storeType  The type of sever you are connecting to
   * @param user  The username/email address of the email account you are trying to access
   * @param password  The password of the email account
   * @throws NoSuchProviderException 
   * @throws MessagingException
   * @throws Exception
   */
   public static void getUnread(String host, String storeType, String user,String password){
      try {
      //set pop3 server properties
      Properties properties = new Properties();
      properties.put("mail.pop3.host", host);
      properties.put("mail.pop3.port", "995");
      properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create POP3 store object and connect
      Store store = emailSession.getStore("pop3s");
      store.connect(host, user, password);

      //create and open the emailFolder
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      // retrieve only the unread messages from the email folder
      Message[] messages = emailFolder.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false)); 

      // print out all of the unread emails for the user with subject and from field
      for (int i = 0, n = messages.length; i < n; i++) {
         Message message = messages[i];
         System.out.println((i + 1) + " Subject: "+message.getSubject()+"From: ("+message.getFrom()[0]+")");
      }

      //close the store and emailFolder
      emailFolder.close(false);
      store.close();

      //catch Exceptions
      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
   * Method to retrieve the selected email.
   * Used when the program is executed with an extra integer argument. Prints the content of the desired email in the terminal.
   * @param host  The server to connect to
   * @param storeType  The type of sever you are connecting to
   * @param user  The username/email address of the email account you are trying to access
   * @param password  The password of the email account
   * @param index  index used to locate the desired email. Must be passed in as args[3]
   * @throws NoSuchProviderException 
   * @throws MessagingException
   * @throws Exception
   */
   public static void getEmail(String host, String storeType, String user,String password,String index){
         try {
         //set pop3 server properties
         Properties properties = new Properties();
         properties.put("mail.pop3.host", host);
         properties.put("mail.pop3.port", "995");
         properties.put("mail.pop3.starttls.enable", "true");
         Session emailSession = Session.getDefaultInstance(properties);
     
         //create POP3 store object and connect
         Store store = emailSession.getStore("pop3s");
         store.connect(host, user, password);

         //create and open the emailFolder
         Folder emailFolder = store.getFolder("INBOX");
         emailFolder.open(Folder.READ_ONLY);

         // retrieve only the unread messages from the email folder
         Message[] messages = emailFolder.search(new FlagTerm(new Flags(
                       Flags.Flag.SEEN), false)); 

         int tempIndex = new Integer(index);
         Message message = messages[tempIndex-1];
         System.out.println((tempIndex + 1) + " Subject: "+message.getContent());
      
         //close the store and emailFolder
         emailFolder.close(false);
         store.close();

         //catch Exceptions
         } catch (NoSuchProviderException e) {
            e.printStackTrace();
         } catch (MessagingException e) {
            e.printStackTrace();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   /**
   *    Main method for GetEmail
   */
   public static void main(String[] args) {
      // set host, username, password from args
      String host = args[0];
      String mailStoreType = "pop3";
      String username = args[1];
      String password = args[2];

      //if run with only 3 arguments call getUnread()
      if(args.length == 3){
         getUnread(host, mailStoreType, username, password);
      }
      //else...
      else{
         // create a string index from args[3]
         String index = args[3];
         // call getEmail()
         getEmail(host, mailStoreType, username, password, index);
      }
   }
}