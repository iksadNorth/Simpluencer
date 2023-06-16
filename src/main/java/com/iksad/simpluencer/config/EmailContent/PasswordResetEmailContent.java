package com.iksad.simpluencer.config.EmailContent;

import com.iksad.simpluencer.entity.Agent;

public interface PasswordResetEmailContent {
    String getTitle(Agent agent);
    String getContent(Agent agent, String newPassword);
}
