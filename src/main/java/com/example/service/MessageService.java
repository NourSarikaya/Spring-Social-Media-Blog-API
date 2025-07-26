package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,AccountRepository accountRepository){
        this.messageRepository=messageRepository;
        this.accountRepository=accountRepository;
    }

    /**
     * Given a brand new transient message (meaning, no such message exists yet in the database),
     * persist the message to the database (create a new database record for the message entity.)
     * 
     * When message_text is longer in length than 255 chars return null
     * When message_text is empty return null
     * When an account associated with the person who posted the message does not exist return null
     * @param  Message entity to be saved
     * @return the saved message entity
     */
    public Message createMessage(Message message){
        if(message.getMessageText()==""){
            return null;
        }else if(message.getMessageText().length()>255){
            //had these condition checks in the controller but failed the test cases
            return null;
        }else if(!accountRepository.findById(message.getPostedBy()).isPresent()){
            // findById returns an Optional thus the need for isPresent() method
            return null;
        }
        return messageRepository.save(message);
        // .save method comes from JpaRepository that we included in the Repository layer, 
        // which provides built-in database operations like .save(), .findAll() and etc
    }

    /**
     * getAllMessages()
     * findAll() will return all message entities stored in the DB table.
     * @return all Message entities.
     * copied from previous project and changed the messageaDAO method
     * to one of the default repository methods
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * getMessagebyMessageId
     *  Because fingById() returns a type of Optional<Message>,
     *  we also need to apply .get() to convert the
     *  For some reason if Message with the given message_id does not exist return null
     * @param message_id
     * @return message with the given message id.
     */
    public Message getMessagebyMessageId(Integer message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }else{
            return null;
        }
    }

    /**
     * deleteMessagebyMessageId
     * Delete Message Entity with certain message_id
     * @return string representing whether string is deleted or not
     * (Because the message body will contain the number of rows deleted if deletion is successful and 
     * the message body will be empty if unsuccesful)
     */
    public String deleteMessagebyMessageId(Integer message_id){
        if(!messageRepository.findById(message_id).isPresent()){
            return "";
        }
        messageRepository.deleteById(message_id);
        return "1";
        //In the response body we only need to put the number of rows updated
        //which in this case will always be 1 since there is only one message with a specific message_id
    }

    /**
     * updateMessagebyMessageId
     * The update of a message should be successful 
     * if and only if the message id already exists and the new message_text is not blank 
     * and is not over 255 characters. If the update is successful, 
     * 
     * the response body should contain the number of rows updated (1), and the response status should be 200, which is the default. 
     * The message existing on the database should have the updated message_text.
     * If the update of the message is not successful for any reason, 
     * the response status should be 400. (Client error)
     * @return string representing whether message is updated or not
     */
    public String updateMessagebyMessageId(Integer message_id, Message message){
        
        if(!messageRepository.findById(message_id).isPresent()){//message_id is not already present in the database
            return "";
        }else if(message.getMessageText().length()>255){//text is over 255 characters in length
            return "";
        }else if(message.getMessageText()==""){//text in the request body is empty
            return "";
        }
        message.setMessageId(message_id);//since the message obtained from the request body does not contain a message_id
        messageRepository.save(message);//update message
        return "1";
    }

    /**
     * getAllMessagesGivenAccountId()
     * @return all messages with the given posted_by account_id.
     * copied from previous project and changed the messageaDAO method
     * to one of the default repository methods
     */
    public List<Message> getAllMessagesbyAccountId(Integer account_id) {
        return messageRepository.findAllByPostedBy(account_id);
    }
}



