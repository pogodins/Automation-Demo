package common.automation.framework.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.testng.Reporter;

public class EmailHelper {
	private static Store store = null;
	public static Message getEmail(String email, String password, String folderName, String subject){
		URLName url = new URLName("imaps", "imap.gmail.com", -1, folderName, email, password);
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		
		Message lastMessage = null;
				
		try{
			store = session.getStore(url);
			store.connect();
			Folder folder = store.getFolder(url);
			folder.open(Folder.READ_WRITE);
			
			SearchTerm term = new SubjectTerm(subject);
			
			Message[] messages = folder.search(term);
			lastMessage = messages[messages.length - 1];
			Reporter.log("Getting email with subject: " + lastMessage.getSubject(), true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lastMessage;
	}
	
	public static void deleteEmail(Message email){
		try {
			Reporter.log("Removing email with subject: " + email.getSubject(), true);
			email.setFlag(Flags.Flag.DELETED, true);
			email.getFolder().close(true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAllEmails(String email, String password, String folderName){
		Reporter.log("REMOVING ALL EMAILS IN FOLDER: " + folderName, true);
		URLName url = new URLName("imaps", "imap.gmail.com", -1, folderName, email, password);
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
				
		try{
			store = session.getStore(url);
			store.connect();
			Folder folder = store.getFolder(url);
			folder.open(Folder.READ_WRITE);
			
			Message[] messages = folder.getMessages();
			for (Message message : messages) {
				message.setFlag(Flags.Flag.DELETED, true);
				Reporter.log("Removing email with subject: " + message.getSubject(), true);
			}
			folder.close(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(){
		try {
			store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isEmailPresent(String email, String password, String folderName, String subject){
		URLName url = new URLName("imaps", "imap.gmail.com", -1, folderName, email, password);
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		
		try {
			store = session.getStore(url);
			store.connect();
			Folder folder = store.getFolder(url);
		
			folder.open(Folder.READ_WRITE);
			
			SearchTerm term = new SubjectTerm(subject);
			
			Message[] messages = folder.search(term);
			int messagesCount = messages.length;
			closeConnection();
			if(messagesCount > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
     * Return the primary text content of the message.
     */
    public static String getText(Part p) throws
                MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
}
