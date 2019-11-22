/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Moment {
    private String content;
    private AbstractAccount owner;
    private List<AbstractAccount> likeList;

    public Moment(String content, AbstractAccount owner) {
        this.content = content;
        this.owner = owner;
        likeList = new ArrayList<>();
    }

    public void addLike(AbstractAccount abstractAccount) {
        if (!likeList.contains(abstractAccount))
            likeList.add(abstractAccount);
    }

    public void cancelLike(AbstractAccount abstractAccount) {
        if (likeList.contains(abstractAccount))
            likeList.remove(abstractAccount);
    }

    public String getContent() {
        return content;
    }

    public AbstractAccount getOwner() {
        return owner;
    }

    public List<AbstractAccount> getLikeList() {
        return Collections.unmodifiableList(likeList);
    }

    public String toString() {
        return "user: " + owner.getNickname() + " content: " + content + " " + likeList.size() + " like(s)";
    }
}
