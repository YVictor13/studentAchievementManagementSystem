@[TOC](成绩管理系统)
# 需求分析
## 1、需求背景
随着计算机以及网络的普及，教师与学生对成绩方式的要求也越来越高，在成绩管理方式上追求质量及效率。作为教学核心组成之一的成绩管理系统也趋向更加便捷快速的方式。在现代化的教育技术模式下，计算机已经广泛应用于学校的教育管理，给传统的成绩管理带来了重大的革命。
数字化成绩管理系统是在积累了丰富业务经验的基础上进行开发的，在需求上，充分考虑了具体用户的实际情况。主要完成了教师管理、学生管理、成绩管理等业务，也可作为学校学生与教务管理系统的一个子模块。
## 2、功能需求
- 系统可设置一名管理员。
- 系统管理员可添加学生、教师（单独添加、Excel批量添加）。
- 学生、教师可直接根据系统管理员上传个人信息，并登录系统。
- 学生、教师可直接修改个人信息。
- 学生（根据年级）、教师可查成绩（根据年级、课程）。
- 系统管理员可添加、修改、删除课程。
- 系统管理员可查看成绩（根据学院、专业、年级）。
- 教师为课程添加成绩（单独添加、批量（Excel）添加）
## 3、功能简述
- 内容全面:系统全面覆盖了所有课程成绩信息,可为学校、教师和学生提供全面准确的信息。
- 层次分明:系统采用模块化程序设计结构。各模块之间既相互独立,又具有一定的联系,各模块可独立编制、调试、查错、修改和执行,结构严谨,便于扩展和维护。

## 4、性能需求
### 1、数据精确度
要求保证能及时正确保存相关信息,能够查询到所要查询的相关信息并保证其正确率。
### 2、时间特性
要求保证一般操作的响应时间应在1一2秒内。
### 3、适应性
要求满足运行环境在允许操作系统之间的安全转换和与其它应用软件的独立运行	要求，与一般软件没冲突情况。

# 技术选型
## 1、框架
- 前端：Bootstrap
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/bootstrap.png)

<br>
- 后端：Springboot

![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/springboot.png)
<br>
## 2、编程语言
- 前端：HTML、CSS、JS
- 后端：Java、SQL、XML、Mybatis
- 前后端交互插件：Thymeleaf
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/thmeleaf.png)
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/mybatis.png)
- 开发工具：IDEA-2020、Navicat Premium 15、Google
- 运行环境：Windows 10 + Maven + JDK 11 + IE9以上
# 系统设计
## 功能设计
- 教师：查询个人信息、修改个人信息、课程查询、成绩录入和修改、学生成绩查询
- 系统管理员：查询教师信息、进行成绩修改
- 学生：查询个人信息、查看成绩
## 数据库设计
### 1、成绩管理系统ER图
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E6%88%90%E7%BB%A9%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9FER%E5%9B%BE.png)
### 2、学生-教师-管理员-课程
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E5%AD%A6%E7%94%9F-%E6%95%99%E5%B8%88-%E8%AF%BE%E7%A8%8BER%E5%9B%BE.png)

### 3、学生-教师-管理员-成绩
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E5%AD%A6%E7%94%9F-%E6%95%99%E5%B8%88-%E6%88%90%E7%BB%A9ER%E5%9B%BE.png)

## 2、数据字典
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8.png)

## 3、系统设计
### 1、功能设计
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E5%8A%9F%E8%83%BD%E8%AE%BE%E8%AE%A1%E5%9B%BE.png)

### 2、用例设计
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E7%94%A8%E4%BE%8B%E8%AE%BE%E8%AE%A1%E5%9B%BE.png)

# 系统界面
- 登录
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E7%99%BB%E5%BD%95.png)

- 首页
![image](https://github.com/YVictor13/studentAchievementManagementSystem/blob/main/%E6%96%87%E6%A1%A3%E5%9B%BE%E7%89%87/%E9%A6%96%E9%A1%B5.png)


