package com.epi.worldData.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;

/*
 Service to load CSV from provided path. Uses PostConstruct annotation to populate rootPathLocation after
 Bean is initialised.
*/

@Service
@Slf4j
public class CsvLoadingService {

    @Value("${app.storage.path}")
    private String GlobalPath;

    private Path rootPathLocation ;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    private void initPath(){
        this.rootPathLocation = Path.of(this.GlobalPath);
    }

    public Path load(@NonNull String path){
        return rootPathLocation.resolve(path);
    }

    public Resource loadAsResource(@NonNull String path)  throws Exception{
        try {
            Resource resource = resourceLoader.getResource("classpath:" + path);
            if (resource.exists()) {
                return resource;
            }
            resource = resourceLoader.getResource("file:" + path);
            if (resource.exists()) {
                return resource;
            }

            throw new FileNotFoundException("File not found: " + path);

        } catch (Exception e) {
            throw new Exception("Error loading resource: " + path, e);
        }
    }
}
