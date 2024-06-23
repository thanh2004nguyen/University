package com.aptech.group3.model;

import java.util.Map;

import lombok.Data;

@Data
public class NotificationMessage {
private  String recripientToken;
private String title ;
private String body;
private String image;
private Map<String, String> data;
}
