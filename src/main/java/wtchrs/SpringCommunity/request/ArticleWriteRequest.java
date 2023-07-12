package wtchrs.SpringCommunity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleWriteRequest {

    @NotBlank
    @Size(min = 5)
    private String title;

    @NotBlank
    private String content;

    private List<MultipartFile> images;
}
