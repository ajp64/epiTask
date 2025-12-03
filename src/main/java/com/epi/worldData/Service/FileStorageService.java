package com.epi.worldData.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;

/*
 Service to load CSV from provided path. Uses PostConstruct annotation to populate rootPathLocation after
 Bean is initialised.
*/

@Service
@Slf4j
public class FileStorageService {

    @Value("${app.storage.path}")
    private String GlobalPath;

    private Path rootPathLocation ;

    @PostConstruct
    private void initPath(){
        this.rootPathLocation = Path.of(this.GlobalPath);
    }

    public Path load(@NonNull String path){
        return rootPathLocation.resolve(path);
    }

    public Resource loadAsResource(@NonNull String path)  throws Exception{
        try{
            Path resourcePath = load(path);
            UrlResource resource = new UrlResource(resourcePath.toUri());
            if(!resource.exists()){
                throw new Exception("File Not Found");
            }
            return resource;
        }
        catch(Exception e){
            try {
                return new UrlResource("");
            } catch (MalformedURLException e1) {
                throw new Exception("File Not Found");
            }
        }
    }
}
