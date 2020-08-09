package com.kyle.demo.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kyle.demo.common.PageBean;
import com.kyle.demo.entity.DUR;
import com.kyle.demo.entity.User;
import com.kyle.demo.service.UserService;
import com.kyle.demo.util.ConvertUtil;
import com.kyle.demo.util.RedisTool;

@RestController
@RequestMapping("user")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private RestTemplate restTemplate;
    
//    @Autowired
//    private Validator validator;
    
    
    @GetMapping("/hello")
    public User getUser(String name ,int age,HttpServletRequest request) {
        return new User();
    }
    
    @PostMapping("/get")
    public User getUser(@RequestBody/* @Validated({DUR.class})*/User user) {
//    	Set<ConstraintViolation<User>> validate = validator.validate(user, CUD.class);
//    	validate.forEach((t) -> System.out.println(t.getMessage()));
        return userService.getUser(user);
    }
    
    @PostMapping(value="/get2",produces = {"application/xml;charset=UTF-8"})
    public User getUser2(@RequestBody @Valid User user) {
    	return userService.getUser(user);
    }
    
    @PostMapping(value="/get3")
    public User getUser3() {
    	String url = "http://127.0.0.1:8080/demo/user/hello";
    	User user =restTemplate.getForObject(url, User.class);
    	return userService.getUser(user);
    }
    
    @PostMapping(value="/get4")
    public User getUser4(HttpServletRequest request) {
    	Map<String, Object> xml = ConvertUtil.parseXml(request);
    	System.out.println(xml);
    	return null;
    }
    
    
    @PostMapping(value="/get5")
    public User getUser5() {
    	return new User();
    }
    
    
    @PostMapping(value="/list")
    public PageBean<User> list(@RequestBody Map<String,Object> param) {
    	System.out.println(param);
    	return userService.list(param);
    }
    
    
    @PostMapping(value="/update")
    public boolean update(@RequestBody User user) {
    	return userService.update(user);
    }
    
    @PostMapping(value="/update2")
    public boolean update2(@RequestBody User user) {
    	return userService.uupdate(user);
    }
    
    @PostMapping("/setUser")
    public String setUserInfo(@RequestBody User user,HttpServletRequest request) {
    	request.getSession().setAttribute("user", user);
    	return "success";
    }
    
    @GetMapping("/getUser")
    public User getUser(HttpServletRequest request) {
    	User user = (User)request.getSession().getAttribute("user");
    	System.out.println(user);
    	return user;
    }
    
    //获取图形验证码
    @GetMapping(value="/get7")
    public void get7(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	BufferedImage image = getVerifyCodePic();
    	ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/png");
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
    	
    }
    
    public BufferedImage getVerifyCodePic() {
        Random ran = new Random();
        int width = 120;
        int height = 35;
        
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect(0, 0, width-1, height-1);
        g.setColor(Color.YELLOW);
        g.fillRect(1, 1, width-2, height-2);
        
        String code = genNumberStr(g, ran);
        
        for(int i=0;i<20;i++){
            int x1 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int x2 = ran.nextInt(width);
            int y2 = ran.nextInt(height);
            g.setColor(genColor());
            g.drawLine(x1, y1, x2, y2);
        }
        
        return image;
    }
    
    
    private String genNumberStr(Graphics g, Random ran) {
        String str = "";
        for(int i=1;i<=4;i++){
            str += ran.nextInt(10);
        }
        //颜色
        g.setColor(Color.BLUE);
        //字体
        g.setFont(new Font("黑体",Font.ITALIC,35));
        //画文字
        g.drawString(str, 25, 30);
        return str;
    }
    
    //随机生成颜色
    private Color genColor(){
        Random ran = new Random();
        int r = ran.nextInt(256);
        int g = ran.nextInt(256);
        int b = ran.nextInt(256);
        return new Color(r,g,b);
    }
    
    
}