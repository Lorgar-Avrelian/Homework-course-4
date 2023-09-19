package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    Avatar findAvatar(Long id);

    void uploadAvatar(Long id, MultipartFile avatar) throws IOException;

    List<Avatar> getAvatars(int pageNum, int pageSize);
}
