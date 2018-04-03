package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.CourseTimeSlot;
import cn.clate.kezhan.pojos.CourseUserTake;
import cn.clate.kezhan.pojos.Representative;
import cn.clate.kezhan.pojos.Teacher;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

@At("/course")
public class CourseModule {
    @At("/getAllCourseByUserId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getAllCourseByUserId(@Param("uid") String id) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "用户id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap courseUserTakeList = CourseDomain.getSubCourseTermIdListByUserId(Integer.parseInt(id));
        if (courseUserTakeList == null)
            return Ret.e("用户没有课程安排");
        NutMap timeSlots = CourseDomain.getTimeSlotsByCourseSubIdList(courseUserTakeList);
        if (timeSlots == null)
            return Ret.e("课程开课时间未安排");
        List<CourseTimeSlot> courseTimeSlots = (List<CourseTimeSlot>) timeSlots.get("time_slots");
        ArrayList<NutMap> courseList = new ArrayList<>();
        for (CourseTimeSlot timeSlot : courseTimeSlots) {
            NutMap courseItem = new NutMap();
            NutMap courseSub = CourseDomain.getCourseSubBySubId(timeSlot.getSubCourseTermId());
            NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"));
            NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
            courseItem.addv("course_name", course.get("name"));
            courseItem.addv("classroom", courseSub.get("classroom"));
            courseItem.addv("subCourseTermId", timeSlot.getSubCourseTermId());
            courseItem.addv("startDate", timeSlot.getStartDate());
            courseItem.addv("endDate", timeSlot.getEndDate());
            courseItem.addv("day", timeSlot.getDay());
            courseItem.addv("startTime", timeSlot.getStartTime());
            courseItem.addv("endTime", timeSlot.getEndTime());
            courseItem.addv("isOdd", timeSlot.getIsOdd());
            courseList.add(courseItem);
        }
        return Ret.s("courseList", courseList);
    }

    @At("/getCourseBySubId")
    @Ok("json")
    public NutMap getCourseBySubId(@Param("sub_id") String id) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "班级课程id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap courseSub = CourseDomain.getCourseSubBySubId(Integer.parseInt(id));
        NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"));
        NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
        NutMap teacher = TeacherDomain.getTeacherById((int) course.get("teacher_id"));
        NutMap timeSlots = CourseDomain.getTimeSlotsByCourseSubid(Integer.parseInt(id));
        NutMap ret = new NutMap();
        ret.addv("course_name", course.get("name"));
        ret.addv("course_name_en", course.get("nameEn"));
        ret.addv("teacher", teacher.get("name"));
        ret.addv("type", course.get("type"));
        ret.addv("credit", course.get("credits"));
        ret.addv("classroom", courseSub.get("classroom"));
        ret.addv("count_thumbs_up", courseTerm.get("countThumbsUp"));
        ret.addv("count_learning", course.get("countLearning"));
        ret.attach(timeSlots);
        return Ret.s(ret);
    }

    @At("/getMembersBySubId")
    @Ok("json")
    public NutMap getMembersBySubId(@Param("sub_id") String id) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "班级课程id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap studentList = CourseDomain.getStudentListByCourseSubId(Integer.parseInt(id));
        NutMap representiceList = CourseDomain.getRepresentiveByCourseSubId(Integer.parseInt(id));
        NutMap teacher = TeacherDomain.getTeacheInforByCourseSubId(Integer.parseInt(id));
        if (studentList == null)
            return Ret.e("用户列表获取错误");
        if (representiceList == null)
            return Ret.e("课代表列表获取错误");
        if (teacher == null)
            return Ret.e("老师信息获取错误");
        List<CourseUserTake> courseUserTakes = (List<CourseUserTake>) studentList.get("student_list");
        List<Representative> representatives = (List<Representative>) representiceList.get("representative_list");
        ArrayList<NutMap> ret = new ArrayList<>();
        NutMap teacherInfo = new NutMap();
        teacherInfo.addv("id", teacher.get("id"));
        teacherInfo.addv("name", teacher.get("username"));
        teacherInfo.addv("real_name", teacher.get("name"));
        teacherInfo.addv("avatar", teacher.get("avatar"));
        teacherInfo.addv("identity", 2);
        ret.add(teacherInfo);
        for (CourseUserTake courseUserTake : courseUserTakes) {
            NutMap item = new NutMap();
            NutMap user = UserInfoDomain.getUserById(courseUserTake.getUserId());
            item.addv("id", user.get("id"));
            item.addv("name", user.get("username"));
            item.addv("real_name", user.get("real_name"));
            item.addv("avatar", user.get("avatar"));
            int flag = 1;
            for (Representative representative : representatives) {
                if ((int)user.get("id")==representative.getUserId())
                {
                    item.addv("identity", 1);
                    flag = 0;
                }
            }
            if (flag == 1) {
                item.addv("identity", 0);
            }
            //identity 为0 表示普通学生 1表示该课课代表 2表示该课老师
            ret.add(item);
        }
        return Ret.s("student_list", ret);
    }

}
