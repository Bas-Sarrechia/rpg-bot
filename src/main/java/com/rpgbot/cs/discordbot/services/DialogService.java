package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.DialogDao;
import com.rpgbot.cs.discordbot.daos.TrackedMessageDao;
import com.rpgbot.cs.discordbot.entities.Dialog;
import com.rpgbot.cs.discordbot.entities.TrackedMessage;
import com.rpgbot.cs.discordbot.exception.DialogNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DialogService {
    private final DialogDao dialogDao;
    private final TrackedMessageDao trackedMessageDao;
    private final UserService userService;

    public DialogService(DialogDao dialogDao, TrackedMessageDao trackedMessageDao, UserService userService) {
        this.dialogDao = dialogDao;
        this.trackedMessageDao = trackedMessageDao;
        this.userService = userService;
    }

    @Transactional
    public Dialog getDialog(long id) {
        Dialog dialog = this.dialogDao.findById(id).orElseThrow(DialogNotFoundException::new);
        dialog.getFollowUp().size();
        return dialog;
    }

    public void track(long messageId, long invokedBy){
        trackedMessageDao.save(new TrackedMessage(messageId, userService.findUserById(invokedBy)));
    }
}
