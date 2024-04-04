package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.haedream.haedream.dto.SignUpDTO;
import com.haedream.haedream.service.AdminService;

import java.util.List;

@Controller
public class AdminController {

  @Autowired
  private AdminService adminService;

  @GetMapping("/admin")
  public String admin(Model model) {
    List<SignUpDTO> allUsers = adminService.getAllUsers();
    model.addAttribute("users", allUsers);
    return "admin";
  }

  // 사용자 삭제를 위한 POST 요청 처리
  @PostMapping("/admin/delete/{username}")
  public String deleteUser(@PathVariable String username) {
    adminService.deleteUserByUsername(username);
    return "redirect:/admin"; // 삭제 후 관리자 페이지로 리디렉션
  }

}
