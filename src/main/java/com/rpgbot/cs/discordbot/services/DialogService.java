package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.DialogDao;
import com.rpgbot.cs.discordbot.entities.Dialog;
import com.rpgbot.cs.discordbot.exception.DialogNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DialogService {
    private final DialogDao dialogDao;

    public DialogService(DialogDao dialogDao) {
        this.dialogDao = dialogDao;
    }

    @Transactional
    public Dialog getDialog(long id) {
        Dialog dialog = this.dialogDao.findById(id).orElseThrow(DialogNotFoundException::new);
        dialog.getFollowUp().size();
        return dialog;
    }
}
