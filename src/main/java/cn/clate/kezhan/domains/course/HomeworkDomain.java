package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeworkDomain {

    public static NutMap getHomeworkListBySubCourseId(int subCourseId, int pageNumber, int pageSize, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            Pager pager = dao.createPager(pageNumber, pageSize);
            List<Homework> homeworkList = dao.query(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)
                    .desc("update_time"), pager);
            if (homeworkList == null) {
                return null;
            }
            pager.setRecordCount(dao.count(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)));
            for (Homework it : homeworkList) {
                dao.fetchLinks(it, "poster");
                it.getPoster().removeCriticalInfo();
                it.setUpdateTime(Tools.dateTimeTodate(it.getUpdateTime()));
                it.setDeadline(Tools.dateTimeTodate(it.getDeadline()));
            }
            NutMap ret = new NutMap();
            ret.addv("now_page", pager.getPageNumber());
            ret.addv("per_page_size", pager.getPageSize());
            ret.addv("page_count", pager.getPageCount());
            ret.addv("homeworkList", new ArrayList<Homework>(homeworkList));
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static Homework getHomeworkByHomeworkId(int homeworkId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            Homework homework = dao.fetch(Homework.class, homeworkId);
            if (homework == null) {
                return null;
            }
            dao.fetchLinks(homework, "poster");
            dao.fetchLinks(homework, "subCourse");
            homework.setUpdateTime(Tools.dateTimeTodate(homework.getUpdateTime()));
            homework.setDeadline(Tools.dateTimeTodate(homework.getDeadline()));
            synchronized (HomeworkDomain.class) {
                dao.update(Homework.class, Chain.makeSpecial("viewerCount", "+1"), Cnd.where("id", "=", homeworkId));
            }
            return homework;
        } finally {

        }
    }

    public static NutMap addHomework(int uid, String title, String desc, String ddl, int scid, int yid, int sid) {
        try {
            NutMap ret = new NutMap();
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            User poster = dao.fetch(User.class, uid);
            if (poster == null) {
                ret.addv("ok?", false);
                return ret;
            }
            Homework homework = new Homework();
            homework.setPosterId(uid).setDeadline(ddl).setDescription(desc).setTitle(title).setSubCourseId(scid).setUpdateTime(Tools.dateTimeTodate(Tools.getDateStr(new Date())));
            dao.insert(homework);
            if (homework.getId() == 0) {
                ret.addv("ok?", false);
                return ret;
            }
            ret.addv("ok?", true);
            ret.addv("hm", homework);
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static NutMap updateHomework(int hmid, String title, String desc, String ddl, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            NutMap ret = new NutMap();
            Dao dao = DaoFactory.get();
            Homework hm = dao.fetch(Homework.class, hmid);
            if (hm == null) {
                ret.addv("ok?", false);
                return ret;
            }
            hm.setUpdateTime(Tools.dateTimeTodate(Tools.getDateStr(new Date()))).setTitle(title).setDescription(desc).setDeadline(ddl);
            dao.update(hm);
            ret.addv("hm", hm);
            ret.addv("ok?", true);
            return ret;
        } finally {
            TableName.clear();
        }
    }
}
