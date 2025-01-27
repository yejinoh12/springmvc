package hello.springmvc.mvc2.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    //고객이 업로드한 파일명으로 서버 내부에 파일을 저장하면 안됨
    // 서로 다른 고객이 같은 파일 이름으로 업로드하는 경우 기존 파일 이름과 충돌 날 수 있음
    // 서버에서는 저장할 파일명이 겹치지 않도록 내부에서 관리하는 별도의 파일명이 필요함

    private String uploadFileName; //고객이 업로드한 파일 명
    private String storeFileName;  //서버 내부 관리 이름

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
