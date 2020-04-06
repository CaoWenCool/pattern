package com.demo.decorator.login;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 18:22
 * @version: V1.0
 * @detail:
 **/
@Data
public class User {
    private String username;
    private String password;
    private String mid;
    private String info;
}
