package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.DialogDao;
import com.rpgbot.cs.discordbot.daos.TrackedMessageDao;
import com.rpgbot.cs.discordbot.entities.Dialog;
import com.rpgbot.cs.discordbot.entities.TrackedMessage;
import com.rpgbot.cs.discordbot.exception.DialogNotFoundException;
import com.rpgbot.cs.discordbot.exception.IllegalTrackedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

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

    public void track(long messageId, long invokedBy, Dialog trackedDialog) {
        trackedMessageDao.save(new TrackedMessage(messageId, userService.findUserById(invokedBy), trackedDialog, false));
    }

    @Transactional
    public Map<String, Dialog> followUpTrackedMessage(long trackedMessageId, long userId) {
        TrackedMessage trackedMessage = trackedMessageDao.findById(trackedMessageId).orElseThrow(DialogNotFoundException::new);
        if(!trackedMessage.isResolved()) {
            if (trackedMessage.getCalledBy().getId() == userId) {
                Dialog dialog = trackedMessage.getTrackedDialog();
                //region lazy-initialization
                dialog.getFollowUp().size();
                for (Dialog deepDialog : dialog.getFollowUp().values()) {
                    deepDialog.getFollowUp().size();
                }
                //endregion
                resolveTrackedMessage(trackedMessageId);
                return dialog.getFollowUp();
            }
        } else {
            return new HashMap<>();
        }
        throw new IllegalTrackedException();
    }

    public void resolveTrackedMessage(long id) {
        trackedMessageDao.findById(id).ifPresentOrElse(trackedMessage -> {
            trackedMessage.setResolved(true);
            trackedMessageDao.save(trackedMessage);
        }, () -> {
            throw new DialogNotFoundException();
        });
    }
}
