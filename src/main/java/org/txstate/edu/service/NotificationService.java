package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.txstate.edu.model.Accounts;
import org.txstate.edu.model.NotificationPolicy;
import org.txstate.edu.model.Transaction;
import org.txstate.edu.model.UsersProfile;
import org.txstate.edu.repository.AccountRepository;
import org.txstate.edu.repository.NotificationPolicyRepository;
import org.txstate.edu.repository.UserProfileRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

/**
 * Created by jyoti on 4/22/18.
 */
@Service
public class NotificationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NotificationPolicyRepository notificationPolicyRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private static StringBuilder htmlTemplate = new StringBuilder();

    @PostConstruct
    public void afterPropertiesSet() {

        //Gets the XML file under src/main/resources folder
        Resource resource = new ClassPathResource("templates/email_template.html");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()));
            String reader = "";
            while ((reader = bufferedReader.readLine()) != null) {
                htmlTemplate.append(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyOnTransaction(Transaction transaction, Accounts fromAccount, Accounts toAccount) {

        UsersProfile toUserProfile = userProfileRepository.findByUsername(toAccount.getUsername());
        UsersProfile fromUserProfile = userProfileRepository.findByUsername(fromAccount.getUsername());

        NotificationPolicy toNotificationPolicy = notificationPolicyRepository.findByUsername(toAccount.getUsername());
        NotificationPolicy fromNotificationPolicy = notificationPolicyRepository.findByUsername(fromAccount.getUsername());

        // If notification is enabled or not for TO
        // To check credit amount is equal to or more than transaction amount

        if (toNotificationPolicy.getEnable()) {
            if (transaction.getAmount() >= toNotificationPolicy.getCreditAmount()) {
                // send email
                String message = "A recent $" + transaction.getAmount() + " is Credited on " +
                        new Date() + " to your account was more than " + toNotificationPolicy.getCreditAmount()
                        + " limit in your Alerts settings.";
                this.sendMail(toUserProfile.getEmail(), "Alert for Transaction",
                        toUserProfile.getFirstName(), message);
            }
        }

        // If notification is enabled or not for FROM
        // To check debit amount is equal to or more than transaction amount
        if (fromNotificationPolicy.getEnable()) {
            if (transaction.getAmount() >= fromNotificationPolicy.getDebitAmount()) {
                //send email;
                String message = "A recent $" + transaction.getAmount() + " is Debited on " +
                        new Date() + " to your account was more than " + fromNotificationPolicy.getDebitAmount()
                        + " limit in your Alerts settings.";
                this.sendMail(fromUserProfile.getEmail(), "Alert for Transaction",
                        fromUserProfile.getFirstName(), message);
            }
        }
    }

    public void notifyOnProfileUpdate(String username) {

    }

    public void notifyOnChangePassword(String username) {

    }

    private void sendMail(String recipient, String subject, String recipientName, String message) {
        String htmlBody = htmlTemplate.toString().replace("${name}", recipientName);
        htmlBody = htmlBody.replace("${message}", message);
        try {
            mailService.sendMail(recipient, "Texas State Bank : " + subject, htmlBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NotificationPolicy getNotificationPolicy(String username) {
        NotificationPolicy notificationPolicy = notificationPolicyRepository.findByUsername(username);
        return notificationPolicy;
    }

    public void updateNotificationPolicy(NotificationPolicy notificationPolicy){
        NotificationPolicy dbNotificationPolicy = notificationPolicyRepository.findByUsername(notificationPolicy.getUsername());
        dbNotificationPolicy.setProfileUpdate(notificationPolicy.getProfileUpdate());
        dbNotificationPolicy.setPasswordUpdate(notificationPolicy.getPasswordUpdate());
        dbNotificationPolicy.setEnable(notificationPolicy.getEnable());
        dbNotificationPolicy.setDebitAmount(notificationPolicy.getDebitAmount());
        dbNotificationPolicy.setCreditAmount(notificationPolicy.getCreditAmount());
        notificationPolicyRepository.save(dbNotificationPolicy);
    }
}
