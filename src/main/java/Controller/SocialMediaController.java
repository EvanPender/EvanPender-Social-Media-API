package Controller;

import Model.*;
import Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    private ObjectMapper mapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.get("/messages", this::allMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) throws JsonProcessingException{
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.register(account.username, account.password);
        if(addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
        else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.login(account.username, account.password);
        if(loggedInAccount != null) {
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        }
        else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message postedMessage = messageService.postMessage(message);
        if(postedMessage != null) {
            ctx.json(mapper.writeValueAsString(postedMessage));
        }
        else {
            ctx.status(400);
        }
    }

    private void allMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageHandler(Context ctx) {
        Message message = messageService.getMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if (message != null) {
            ctx.json(message);
        }
    }

    private void getMessageByAccountHandler(Context ctx) {
        ctx.json(messageService.getMessagesByAccount(Integer.parseInt(ctx.pathParam("account_id"))));
    }

    private void deleteMessageHandler(Context ctx) {
        Message deletedMessage = messageService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        String message = mapper.readTree(ctx.body()).path("message_text").asText();
        Message updatedMessage = messageService.updateMessage(id, message);
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        }
        else {
            ctx.status(400);
        }
    }

}