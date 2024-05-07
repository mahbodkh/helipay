package app.helipay.se.service;

import app.helipay.se.domain.PostEntity;
import app.helipay.se.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostService
{

  private final PostRepository postRepository;
//  private final PostMapper postMapper;

//  public PostReply createPost(PostRequest request) {
//    var postEntity = PostEntity.builder()
//                               .title(request.getTitle())
//                               .content(request.getContent())
//                               .build();
//    var postEntitySaved = postRepository.save(postEntity);
//    return postMapper.toDto(postEntitySaved);
//  }

//  public Optional<PostReply> getPost(Long postId) {
//    return Optional.of(postRepository.findById(postId))
//                   .filter(Optional::isPresent)
//                   .map(Optional::get)
//                   .map(postMapper::toDto);
//  }

//  public List<PostReply> getAllPosts() {
//    final var postEntities = postRepository.findAll();
//    return postMapper.toDto(postEntities);
//  }

  public void deletePost(Long postId) {
    postRepository.findById(postId)
                  .ifPresent(postEntity -> {
                    postRepository.deleteById(postEntity.getId());
                    log.debug("Deleted User: {}", postEntity);
                  });
  }

//  public List<PostReply> getPostByPageName(String name) {
//    return null;
//  }
}