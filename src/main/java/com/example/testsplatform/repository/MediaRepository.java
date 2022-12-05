package com.example.testsplatform.repository;

import com.example.testsplatform.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "media", path = "media")
public interface MediaRepository extends JpaRepository<Media, Long> {

    Media findMediaById(Long id);
}