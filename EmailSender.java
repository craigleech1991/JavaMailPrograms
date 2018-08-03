/**
* I declare that this assignment is my own work and that all 
* material previously written or published in any source by any 
* other person has been duly acknowledged in the assignment. I 
* have not submitted this work, or a significant part thereof, 
* previously as part of any academic program. In submitting this 
* assignment I give permission to copy it for assessment purposes 
* only.
* 
* title: EmailSender.java
* description: A java email program that will connect to your preferred email server
*              and send an email to a primary recipient, a list of at least three 
*              secondary recipients (cc) and at least three tertiary recipients (bcc). 
* @author craigleech
* @version 1.0
* @since 2018-06-27
*/

/**
 * DOCUMENTATION...
 */
/**                                                                               
 *
 *<P>
 * This program is run via the command prompt. It sends an email using information stored in emailFile.txt
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
 *<DT> Ensure emailFile.txt exists within current working directory and is in the correct format.</DT>
 *<DD> Compile:    javac emailSender.java</DD>
 *<DD> Run:        java emailSender emailFile.txt</DD>
 *<DD> Document:   javadoc emailSender.java</DD>
 *</DL>
 */

/**                                                                               
 *
 *<P> 
 * public class EmailSender {<BR>
 * Private inner class
 *</P>
 *<P>
 * public static void main (String args[]){ <BR>
 * Main method used to execute the program
 *</P>  
 *<P>
 * public void sendEmail throws AddressException, MessagingException{ <BR>
 * construct and sends an email
 *</P> 
 */ 

/**
 *
 * <H3>Test Plan</H3>
 *
 *<P>
 * 1. Run the application from command prompt with arguments emailFile.txt
 * EXPECTED:
 *   Program will construct an email from the given information provided in emailFile.txt
 *</P>
 *<P>
 * 2. Good data cases:
 * EXPECTED:
 *    Run the following test cases by running the program with the following parameters:
 *
 *   1. java EmailSender emailFile.txt
 *          If email is created successfully, emailFile.txt will be printed to the terminal.
 *          Success message will be printed to terminal if the email was succesfully sent.
 *          
 *           some.email.server
 *           someguy1234
 *           pssword1234
 *           craigleech777@gmail.com
 *           brad@gmail.com,sssssss@gmail.com
 *           bob@gmail.com,sergeantmeowenstein@hotmail.com
 *           subject
 *           body blah blah blah blah blah.........
 *           Email Sent.
 *
 * ACTUAL:
 *    Results displayed as expected. Email is sent to all recipients
 *</P>
 *<P>
 * 3. Bad data cases:
 * EXPECTED:
 *    Run the following test cases by running the program with the following parameters:
 *
 *    1.  (emailFile.txt does not exists) to run: java EmailSender emailFile.txt
 *          output should be:
 *          Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 1
 *             at EmailSender.main(EmailSender.java:205)
 *    2. (emailFile.txt is not in correct format) to run: java EmailSender emailFile.txt
 *          output should be:
 *          cannot read email file
 * ACTUAL: 
 *    Results displayed as expected.
 *</P>
 */ 

/**
 * CODE...
 */

/** import java packages*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
/**primary class for EmailSender*/ 
public class EmailSender {
/**
 * Method to construct the contents of an email and send the Email.
 * @param  user  the users email, also the from email
 * @param  password  the users password
 * @param to the  recipient of the email
 * @param ccList  a string of email recipients separated with a comma to be cc'd
 * @param bccList  a string of email recipients seaparated with a comma to be bcc'd
 * @param subject  the subject of the email
 * @param message  the body of the email
 * @throws AddressException
 * @throws MessagingException
 */ 
    public void sendEmail(String server, String port,
            final String user, final String password, String to, String ccList, String bccList,
            String subject, String message) throws AddressException,
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", server);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // Authenticatorn & password authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };

        // create a new session with server properties and  authentication
        Session session = Session.getInstance(properties, auth);
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        // set the from address
        msg.setFrom(new InternetAddress(user));
        // set the to address
        InternetAddress[] toAddresses = { new InternetAddress(to) };
        // set the ccAddresses, will parse through the string ccList
        InternetAddress[] ccAddresses = InternetAddress.parse(ccList);
        // set the bccAddresses, will parse though the string bccList
        InternetAddress[] bccAddresses = InternetAddress.parse(bccList);
        //Add all the email recipients
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.addRecipients(Message.RecipientType.CC,ccAddresses);
        msg.addRecipients(Message.RecipientType.BCC,bccAddresses);
        // set subject
        msg.setSubject(subject);
        // set date
        msg.setSentDate(new Date());
        // set plain text message
        msg.setContent(message, "text/html");
        // Now with the message all created...send the e-mail
        Transport.send(msg);
    }
 
    /* Main Method for EmailSender */
    public static void main(String[] args) {
        // SMTP server information
        File emailFile = new File(args[0]);
        String port = "587"; // port will remain 587
        String server = "";
        String user = "";
        String password = "";
        String to = "";
        String subject = "";
        String message = "";
        String ccList = "";
        String bccList = "";
        
        /// try to read the contents of the email file
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(emailFile), "UTF-8"));) {
            // for every line, String entry equals the contents of that line
            for (String entry = in.readLine(); entry != null; entry = in.readLine()) {

                // if, else if statements to separate out server information
                if(entry.contains("Server")){server = entry.split(": ")[1];
                    System.out.println(server);
                }
                else if(entry.contains("User")){user = entry.split(": ")[1];
                    System.out.println(user);
                }
                else if(entry.contains("Password")){password = entry.split(": ")[1];
                    System.out.println(password);
                }
                else if(entry.contains("To")){to = entry.split(": ")[1];
                    System.out.println(to);
                }
                else if(entry.contains("CC")){ccList = entry.split(": ")[1];
                    System.out.println(ccList);
                }
                else if(entry.contains("BCC")){bccList = entry.split(": ")[1];
                    System.out.println(bccList);
                }
                else if(entry.contains("Subject")){subject = entry.split(": ")[1];
                    System.out.println(subject);
                }
                // the remaining contents of the message will be the body of the email. 
                else{
                    message += entry;
                }
            }
            
            //System.out.println(message);
            // creatrea new HtmlEmailSender mailer
            EmailSender mailer = new EmailSender();
            // try to send email
            try {
                mailer.sendEmail(server, port, user, password, to, ccList, bccList, subject, message);
                System.out.println("Email sent.");
            } 
            // catch exception
            catch (Exception ex) {
                System.out.println("Failed to sent email.");
                ex.printStackTrace();
            }
        }
        // catch IOException, ensure emailFile.txt exists in current directory
        catch (IOException e){
            System.out.println("cannot read email file "+e);
        }
    }
}