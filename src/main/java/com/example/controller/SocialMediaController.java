package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.service.AccountService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Optional;
import java.util.List;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService; //Dependency
    private final MessageService messageService; //Dependency
    


    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService=accountService;
        this.messageService=messageService;
    }

    /**
     * Here's an example of how a response can be sent with a custom status code, as well as an informational message
     * in the response body as a String.
     
    @GetMapping("/example401")
    public ResponseEntity example(){
        return ResponseEntity.status(401).body("Unauthorized resource!");
    }
     */

    /**
     * CREATE AN ACCOUNT
     * postAccountHandler(@RequestBody Account account)
     * @param account
     * @return status code & created Account in the response body
     */

    @PostMapping("/register")
    public ResponseEntity postAccountHandler(@RequestBody Account account){
        if(accountService.getAccountByUsername(account)!=null){
            return ResponseEntity.status(409).body(account);//Conflict
        }
        Account createdAccount=accountService.createAccount(account);
        if(createdAccount!=null){
            return ResponseEntity.status(200).body(createdAccount);
        }else{
            return ResponseEntity.status(400).body(createdAccount);//Client Error
        }
    }

    /**
     * VERIFY LOGIN INFO
     * postLoginHandler(@RequestBody Account account)
     * 
     * @param account
     * @return status code & verified Account in the response body
     */
    @PostMapping("/login")
    public ResponseEntity postLoginHandler(@RequestBody Account account){
        Account verifiedAccount=accountService.getAccountByUsernameandPassword(account);
        if(verifiedAccount==null){//&& verifiedAccount.getPassword()==account.getPassword()){
            //handling conditions in the Service layer is better practice
            return ResponseEntity.status(401).body(verifiedAccount);//Unauthorized
        }
        else{
            return ResponseEntity.status(200).body(verifiedAccount);//Default
        }
    }

    /** 
     * CREATE A NEW MESSAGE
     * postMessageHandler(@RequestBody Message message)
     * 
     * @param message
     * @return response body will contain the newly persisted message into the DB
     */
    @PostMapping("/messages")
    public ResponseEntity postMessageHandler(@RequestBody Message message){
        Message createdMessage=messageService.createMessage(message);
        if(createdMessage==null){
            return ResponseEntity.status(400).body(createdMessage);//Client Error
        }else{
            return ResponseEntity.status(200).body(createdMessage);
        }
    }

     /** 
     * GET ALL MESSAGES
     * postMessageHandler()
     * 
     * @return status code & response body will contain List of All Messages
     */
    @GetMapping("/messages")
    public ResponseEntity postMessageHandler(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    /** 
     * GET ONE MESSAGE GIVEN MESSAGE ID
     * postMessagebyMessageIdHandler(@PathVariable Integer message_id)
     * 
     * @param message_id
     * @return response body weill contain the message with the given message_id
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity postMessagebyMessageIdHandler(@PathVariable Integer message_id){
        Message foundMessage =messageService.getMessagebyMessageId(message_id);

        //check whether message belonging to message_id exists in the database
        if(foundMessage !=null){ 
            //Here we cannot use !=null because foundMesage is of type Optional 
            //which will be Empty if message_id does not exist
            return ResponseEntity.status(200).body(foundMessage);
        }else{
            return ResponseEntity.status(200).body(""); //status code 200 by default
        }
    }

    /** 
     * DELETE ONE MESSAGE GIVEN MESSAGE ID
     * deleteMessagebyMessageIdHandler(@PathVariable Integer message_id)
     * 
     * The deletion of an existing message should remove an existing message from the database. 
     * If the message existed, the response body should contain the number of rows updated (1). The response status should be 200, which is the default.
     * If the message did not exist, 
     * the response status should be 200, but the response body should be empty. 
     * This is because the DELETE verb is intended to be idempotent, ie, 
     * multiple calls to the DELETE endpoint should respond with the same type of response.
     * @param message_id
     * @return response body will contain "1" number of succesfully deleted rows or will be empty if unsuccesful
     * 
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessagebyMessageIdHandler(@PathVariable Integer message_id){
        String deletedMessage =messageService.deleteMessagebyMessageId(message_id);
        //This function will return "1" if deletion of the message is successful and
        // an empty string if the deletion is not successful
        return ResponseEntity.status(200).body(deletedMessage); //status code 200 by default
    }

    /** 
     * UPDATE MESSAGE GIVEN MESSAGE ID
     * patchMessagebyMessageIdHandler(@PathVariable Integer message_id, @RequestBody Message message)
     * 
     * The deletion of an existing message should remove an existing message from the database. 
     * If the message existed, the response body should contain the number of rows updated (1). The response status should be 200, which is the default.
     * If the message did not exist, 
     * the response status should be 200, but the response body should be empty. 
     * This is because the DELETE verb is intended to be idempotent, ie, 
     * multiple calls to the DELETE endpoint should respond with the same type of response.
     * 
     * @param message_id, message
     * @return response body will contain "1" number of succesfully updated rows or will be empty if unsuccesful
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity patchMessagebyMessageIdHandler(@PathVariable Integer message_id, @RequestBody Message message){
        String updatedMessage =messageService.updateMessagebyMessageId(message_id,message);
        //This function will return "1" if deletion of the message is successful and
        // an empty string if the deletion is not successful
        if(updatedMessage==""){
            return ResponseEntity.status(400).body(updatedMessage);
        }
        return ResponseEntity.status(200).body(updatedMessage); //status code 200 by default
    }

    /**
     * GET MESSAGES GIVEN ACCOUNT ID
     * getAllMessagesbyAccountIdHandler(@PathVariable Integer account_id)
     * 
     * @param account_id
     * @return status code & response body will contain list of messages posted by person with given account_id
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity getAllMessagesbyAccountIdHandler(@PathVariable Integer account_id){
        List<Message> messages =messageService.getAllMessagesbyAccountId(account_id);
        //This function will return "1" if deletion of the message is successful and
        // an empty string if the deletion is not successful
       
        return ResponseEntity.status(200).body(messages); //status code 200 by default
    }

}
