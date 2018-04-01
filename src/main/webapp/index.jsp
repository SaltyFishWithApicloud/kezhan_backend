<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<h4>用户登录：<br>
    http://95.163.194.157:8080/kezhan/user/login<br>
    params: <br>
    username<br>
    password<br>
    <br>
    用户根据id获得个人信息<br>
    http://95.163.194.157:8080/kezhan/user/getById<br>
    params:<br>
    uid<br>
    utoken<br>
    <br>
    用户根据姓名获得个人信息<br>
    http://95.163.194.157:8080/kezhan/user/getById<br>
    params:<br>
    uid<br>
    utoken<br>
    uname<br>
    <br>
    用户发送手机验证码：<br>
    http://95.163.194.157:8080/kezhan/user/phone<br>
    params:<br>
    phone<br>
    <br>
    用户注册：<br>
    http://95.163.194.157:8080/kezhan/user/register<br>
    params:<br>
    username<br>
    password<br>
    phone<br>
    verification_code<br>
    <br>
    用户忘记密码发送手机验证码：<br>
    http://clate.cn:8080/kezhan/user/resetSendMsg<br>
    params:<br>
    uid<br>
    phone<br>
    <br>
    获得所有圈子<br>
    http://clate.cn:8080/kezhan/circle/getAll<br>
    无参数<br>
    <br>
    按圈子类型和页获得圈子（其中type_id为0默认返回所有类型）<br>
    http://clate.cn:8080/kezhan/circle/getByTypePage<br>
    params:<br>
    type_id<br>
    page_number<br>
    page_size<br>
    <br>
    根据帖子id获取评论（type=1表示是圈子类型的评论）<br>
    http://clate.cn:8080/kezhan/circle/comments<br>
    params:<br>
    topic_id<br>
    page_number<<br>
    page_size<<br>
    <br>
    发布评论<br>
    http://clate.cn:8080/kezhan/circle/submitComment<br>
    params:<br>
    topic_type=1<br>
    topic_id=1<br>
    from_id=8<br>
    from_name=zhaoning<br>
    to_id=-1<br>
    to_name<br>
    content=喵喵喵喵喵喵<br>
    uid=8<br>
    utoken=ziTk48xKFrdkRUoG<br>
    <br>
    根据用户id查询用户的课表<br>
    http://clate.cn:8080/kezhan/course/getAllCourseByUserId<br>
    params:<br>
    uid=8<br>
    utoken=ziTk48xKFrdkRUoG<br>
    <br>
    <br>
    根据班级ID取得作业信息<br>
    localhost:8080/homework/getBySubCourse?sub_course_id=1<br>
    <br>
    <br>
    根据作业ID取得作业信息<br>
    localhost:8080/homework/getByHomeworkId?homework_id=1<br>
    <br>
    根据班级的课程id（小表的id）查询该课程相关信息，用于显示课程详情<br>
    http://clate.cn:8080/kezhan/course/getCourseBySubId<br>
    params:<br>
    sub_id=1<br>
    <br>
    获取圈子的所有type<br>
    http://localhost:8080/circle/allCircleTypes<br>
    no params<br>
    <br>
    根据班级ID取得公告信息<br>
    localhost:8080/notice/getBySubCourse?uid=8&&sub_course_id=1&&utoken=wVAAT2EdF9mTPU92<br>
    <br>
    根据公告ID取得公告信息<br>
    localhost:8080/notice/getByNoticeId?uid=8&&notice_id=1&&utoken=wVAAT2EdF9mTPU92<br>
    <br>
    根据圈子id获取圈子详情<br>
    http://localhost:8080/circle/getById<br>
    params<br>
    id=1<br>
    <br>
    根据老师id获取老师信息<br>
    http://localhost:8080/teacher/getById<br>
    params<br>
    teacher_id=1<br>
    <br>
    搜索框模糊查询老师或课程<br>
    http://localhost:8080/search/getByString<br>
    params<br>
    string=编译<br>
    <br>
    根据小表课程id分页查看公告并刷新已读／未读<br>
    http://localhost:8080/notice/getBySubCourse <br>
    params<br>
    uid=8 <br>
    utoken=ThWm4t9BQCSyd5wR <br>
    sub_course_id=1 <br>
    page_number=1 <br>
    page_size=1 <br>
    <br>
    根据小表课程id分页查看作业<br>
    http://localhost:8080/homework/getBySubCourse<br>
    params<br>
    sub_course_id=1<br>
    page_number=1<br>
    page_size=1
</h4>
</body>
</html>
