package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${student.avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Avatar findAvatar(Long id) {
        logger.info("Searching avatar by id {}", id);
        Avatar answer = avatarRepository.findById(id).get();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        logger.info("Trying to upload avatar for student with id {}", id);
        Student student = studentService.findStudent(id);

        Path filePath = Path.of(avatarsDir, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = getAvatar(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private Avatar getAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }
    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();
            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public List<Avatar> getAvatars(int pageNum, int pageSize) {
        logger.info("Getting pageable list of avatars with page number {} and page size {}", pageNum, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        List<Avatar> answer = avatarRepository.findAll(pageRequest).getContent();
        logger.debug("Getting answer {}", answer);
        return answer;
    }
}
