package wtchrs.SpringCommunity.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageUploadResponse {

    private String location;

    public ImageUploadResponse(String location) {
        this.location = location;
    }

}
