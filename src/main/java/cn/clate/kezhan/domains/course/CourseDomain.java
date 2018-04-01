package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Course;
import cn.clate.kezhan.pojos.CourseSub;
import cn.clate.kezhan.pojos.CourseTimeSlot;
import cn.clate.kezhan.pojos.Couse2018;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class CourseDomain {
    public static NutMap getCourseSubBySubId(int subId) {
        Dao dao = DaoFactory.get();
        CourseSub courseSub = dao.fetch(CourseSub.class, Cnd.where("id", "=", subId).and("status", "!=", -1));
        PojoSerializer pjsr = new PojoSerializer(courseSub);
        NutMap ret = pjsr.get();
        return ret;
    }

    public static NutMap getCourseTermByCourseTermId(int courseTermId) {
        Dao dao = DaoFactory.get();
        Couse2018 couseTerm = dao.fetch(Couse2018.class, Cnd.where("id", "=", courseTermId).and("status", "!=", -1));
        //该课程开设count个班级
        int count = dao.count(CourseSub.class, Cnd.where("course_term_id", "=", courseTermId).and("status", "!=", -1));
        PojoSerializer pjsr = new PojoSerializer(couseTerm);
        NutMap ret = pjsr.get();
        ret.addv("class_num", count);
        return ret;
    }

    public static NutMap getCourseByCourseId(int courseId) {
        Dao dao = DaoFactory.get();
        Course course = dao.fetch(Course.class, Cnd.where("id", "=", courseId).and("status", "!=", -1));
        PojoSerializer pjsr = new PojoSerializer(course);
        NutMap ret = pjsr.get();
        return ret;
    }

    public static NutMap getTimeSlotsByCourseSubid(int id) {
        Dao dao = DaoFactory.get();
        List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, Cnd.where("sub_course_term_id", "=", id));
        ArrayList<CourseTimeSlot> timeSlots = new ArrayList<>(courseTimeSlots);
        NutMap ret = new NutMap();
        ret.addv("time_slots", timeSlots);
        return ret;
    }

    public static NutMap getCourseNumByTeacherId(int teacherId) {
        Dao dao = DaoFactory.get();
        int count = dao.count(Course.class, Cnd.where("teacher_id", "=", teacherId).and("status", "!=", -1));
        NutMap ret = new NutMap();
        ret.addv("teacher_course_num", count);
        return ret;
    }

    public static NutMap getCourseListByTeacherId(int teacherId) {
        Dao dao = DaoFactory.get();
        List<Course> courses = dao.query(Course.class, Cnd.where("teacher_id", "=", teacherId).and("status", "!=", -1));
        if (courses == null)
            return null;
        ArrayList<Course> courseArrayList = new ArrayList<>(courses);
        NutMap ret = new NutMap();
        ret.addv("teacher_courses", courseArrayList);
        return ret;
    }

    public static NutMap getCoursesByCourseNameFuzzy(String courseName) {
        Dao dao = DaoFactory.get();
        List<Course> courses = dao.query(Course.class, Cnd.where("name", "LIKE", "%" + courseName + "%").and("status", "!=", -1));
        if (courses == null || courses.size() == 0)
            return null;
//        ArrayList<Course> courseArrayList = new ArrayList<>(courses);
        NutMap ret = new NutMap();
        ret.addv("course_num", courses.size());
        List<NutMap> courseArrayList = new ArrayList<>();
        for (Course circle : courses) {
            PojoSerializer pjsr = new PojoSerializer(circle);
            NutMap courseNutmap = pjsr.get();
            courseNutmap.remove("desc");
        }
        ret.addv("courses", courseArrayList);
        return ret;
    }

    public static NutMap getCoursesByCourseName(String courseName) {
        Dao dao = DaoFactory.get();
        List<Course> courses = dao.query(Course.class, Cnd.where("name", "=", courseName).and("status", "!=", -1));
        if (courses == null || courses.size() == 0)
            return null;
        ArrayList<Course> courseArrayList = new ArrayList<>(courses);
        NutMap ret = new NutMap();
        ret.addv("courses", courseArrayList);
        return ret;
    }

}
