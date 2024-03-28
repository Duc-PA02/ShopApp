package com.example.shopappbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orderdetails")
@RequiredArgsConstructor
public class OrderDetailController {
}
