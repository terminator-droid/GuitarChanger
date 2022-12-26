package com.dudev.jdbc.starter.dto;

import lombok.Builder;

@Builder
public record UserFilter (String username, String password, String phoneNumber){}
