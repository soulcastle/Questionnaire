package com.yanzhen.controller;

import com.yanzhen.entity.Admin;
import com.yanzhen.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Controller
public class IndexController {

    @Value("classpath:init.json")
    private Resource resource;

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Admin admin1 = (Admin) session.getAttribute("admin");
        Admin admin = adminService.detail(admin1.getId());
        model.addAttribute("name",
                admin.getName());
        return "index";
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/menu")
    @ResponseBody
    public void menu(HttpServletResponse response) {
        try {
            File file = resource.getFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }
            bufferedReader.close();
            fileReader.close();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
