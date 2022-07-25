package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
