package br.com.bonnepet.service;

import br.com.bonnepet.service.exception.FileException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URISyntaxException;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    private static final String BUCKET_NAME = "bonnepet";

    public String uploadFile(InputStream is, String fileName) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image");
            LOG.info("Iniciando upload");
            s3client.putObject(BUCKET_NAME, fileName+".jpg", is, meta);
            LOG.info("Upload finalizado");
            return s3client.getUrl(BUCKET_NAME, fileName).toURI().toString() + ".jpg";
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}
