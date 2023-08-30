package com.zhoupb.mysite.file.controller.v1;

import com.github.yitter.idgen.YitIdHelper;
import com.zhoupb.file.service.FileStorageService;
import com.zhoupb.mysite.common.JSONResponse;
import com.zhoupb.mysite.common.util.StringUtils;
import com.zhoupb.mysite.file.mapper.ImageMapper;
import com.zhoupb.mysite.model.file.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1/image")
public record ImageController(FileStorageService fileStorageService, ImageMapper imageMapper, @Value("${app.file.allow-type}") Set<String> allowType) {

    private static final DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @PostMapping
    public JSONResponse<String> post(@RequestHeader long accountId, MultipartFile file) throws IOException {
        if ( file == null || file.isEmpty() )
            return JSONResponse.fail(HttpStatus.BAD_REQUEST, "图片不能为空");
        String fileType = StringUtils.getFilenameExtension(file.getOriginalFilename());
        // 判断文件类型
        if ( !allowType.contains(fileType) )
            return JSONResponse.fail(HttpStatus.BAD_REQUEST, "不支持的文件类型");
        // 判断是不是一张图片
        if ( ImageIO.read(file.getInputStream()) == null )
            return JSONResponse.fail(HttpStatus.BAD_REQUEST, "不是一张图片");
        // 目录就用日期，格式化日期 yyyy/MM/dd
        OffsetDateTime now = OffsetDateTime.now();
        String directory = now.format(formatPattern);
        long id = YitIdHelper.nextId();
        String name = id + "." + fileType;
        String path = directory + "/" + name;
        String url = fileStorageService.uploadImage(path, file.getInputStream());
        Image image = new Image(id, directory, name, accountId, now, now, fileStorageService.getBucket());
        // 保存到数据库
        imageMapper.insert(image);
        return JSONResponse.ok(HttpStatus.OK, url);
    }

    @DeleteMapping("/{id}")
    public JSONResponse<Void> delete(@RequestHeader long accountId, @PathVariable long id) {
        Optional<Image> imageOptional = imageMapper.wrapper().eq(Image::getId, id).eq(Image::getAccountId, accountId).first();
        if ( imageOptional.isEmpty() )
            return JSONResponse.fail(HttpStatus.NOT_FOUND, "图片不存在");
        imageMapper.deleteByPrimaryKey(imageOptional.get().getId());
        fileStorageService.delete(imageOptional.get().getPath());
        // 要从数据库删除
        return JSONResponse.ok(HttpStatus.OK, null, "删除成功");
    }
}
