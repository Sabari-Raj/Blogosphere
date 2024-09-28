package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Schema(
        description = "POSTDTO model class"
)
public class PostDTO {
    private Long id;
    @Schema(
            description = "Blog Post title"
    )
    @NotEmpty
    @Size(min=2,message = "Post title should have atleast 2 characters")
    private String title;
    @Schema(
            description = "Blog Post description"
    )
    @NotEmpty
    @Size(min=10,message = "Post title should have atleast 10 characters")
    private String description;
    @Schema(
            description = "Blog Post content"
    )
    @NotEmpty
    private String content;
    @Schema(
            description = "Blog Post Comments"
    )
    private List<CommentDTO> comments;
}
