package com.example.shopappbackend.services.comment;

import com.example.shopappbackend.dtos.CommentDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.models.Comment;
import com.example.shopappbackend.responses.comment.CommentResponse;

import java.util.List;

public interface ICommentService {
    Comment insertComment(CommentDTO comment);

    void deleteComment(Integer commentId);
    void updateComment(Integer id, CommentDTO commentDTO) throws DataNotFoundException;

    List<CommentResponse> getCommentsByUserAndProduct(Integer userId, Integer productId);
    List<CommentResponse> getCommentsByProduct(Integer productId);
}
