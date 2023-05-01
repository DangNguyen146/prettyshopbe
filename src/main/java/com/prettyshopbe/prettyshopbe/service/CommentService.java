package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.Comment;
import com.prettyshopbe.prettyshopbe.respository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment getComment(Long id, Integer userId) throws NotFoundException {
        Optional<Comment> optionalComment = commentRepository.findByIdAndUserId(id, userId);
        if (!optionalComment.isPresent()) {
            throw new NotFoundException("Comment not found");
        }
        return optionalComment.get();
    }

    public List<Comment> getCommentsByProductId(Integer productId) {
        return commentRepository.findByProductId(productId);
    }


    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment newComment, Integer userId) throws NotFoundException {
        Optional<Comment> optionalComment = commentRepository.findByIdAndUserId(id, userId);
        if (!optionalComment.isPresent()) {
            throw new NotFoundException("Comment not found");
        }
        Comment comment = optionalComment.get();
        comment.setContent(newComment.getContent());
        comment.setRating(newComment.getRating());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id, Integer userId) {
        commentRepository.deleteByIdAndUserId(id, userId);
    }
}
