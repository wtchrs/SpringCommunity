package wtchrs.SpringCommunity.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleWriteRequest {

    @NotBlank
    @Size(min = 5)
    private String title;

    @NotBlank
    private String content;

}
