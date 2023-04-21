package wtchrs.SpringCommunity.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wtchrs.SpringCommunity.common.entity.article.Article;
import wtchrs.SpringCommunity.common.entity.article.ArticleRepository;
import wtchrs.SpringCommunity.common.entity.image.ImageContent;
import wtchrs.SpringCommunity.common.entity.image.ImageContentRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageStoreService {

    private final ImageContentRepository imageContentRepository;
    private final ArticleRepository articleRepository;

    @Value("${file.image.dir}")
    private String imageDir;

    public String getFullPath(String filename) {
        return imageDir + filename;
    }

    @Transactional
    public List<ImageContent> storeArticleImages(Long articleId, List<MultipartFile> images) {
        Optional<Article> findArticle = articleRepository.findArticleById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        return images.stream().map(image -> storeArticleImageProcess(article, image)).toList();
    }

    @Transactional
    public ImageContent storeArticleImage(Long articleId, MultipartFile image) {
        Optional<Article> findArticle = articleRepository.findArticleById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        return storeArticleImageProcess(article, image);
    }

    private ImageContent storeArticleImageProcess(Article article, MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null) return null;

        String storedFilename = UUID.randomUUID().toString();
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        long imageSize = image.getSize();

        try {
            image.transferTo(new File(getFullPath(storedFilename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageContentRepository.save(new ImageContent(article, originalFilename, storedFilename, ext, imageSize));
    }
}
