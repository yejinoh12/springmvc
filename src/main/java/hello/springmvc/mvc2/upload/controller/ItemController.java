package hello.springmvc.mvc2.upload.controller;

import hello.springmvc.mvc2.upload.domain.FileItem;
import hello.springmvc.mvc2.upload.domain.FileItemRepository;
import hello.springmvc.mvc2.upload.domain.UploadFile;
import hello.springmvc.mvc2.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final FileItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form){
        return "mvc2/upload/item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        //데이터베이스에 저장
        FileItem item = new FileItem();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        FileItem item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "mvc2/upload/item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        FileItem item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); //한글 깨짐 방지
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        //다운로드를 위해 헤더를 추가해야함
        //추가하지 않으면 브라우저가 파일을 열어서 보기만 함
        //브라우저가 CONTENT_DISPOSITION 헤더를 인식하고 브라우저가 다운로드 결정
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
