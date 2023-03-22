package com.example.backstage.vo;

import lombok.Data;

/**
 * @author ajie
 * @createTime 2021年06月20日 19:43:00
 */
@Data
public class WxVo {
    private String encryptedData;

    private String iv;

    private String sessionKey;

    private String beginTime;

    private String endTime;
}
