package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min=10, message = "Comment should have atleast 10 characters")
    private String body;

}
