package co.edu.uniquindio.unicine.servicios;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServicio {

    private Cloudinary cloudinary;
    private Map<String, String> config;

    public CloudinaryServicio() {
        config = new HashMap<>();
        config.put("cloud_name", "dkislcnml");
        config.put("api_key", "271924816581446");
        config.put("api_secret", "EZJN7mNhdpcDFphCLPUBSpR7gvQ");


        cloudinary = new Cloudinary(config);
    }

    public Map uploadImg(File file, String folder) throws Exception {
        Map respuesta = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", folder));
        return respuesta;
    }

    public Map deleteImg(String idImg) throws Exception {
        Map respuesta = cloudinary.uploader().destroy(idImg, ObjectUtils.emptyMap());
        return respuesta;
    }
}
