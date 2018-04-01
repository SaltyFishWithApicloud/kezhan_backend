package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.pojos.Moment;
import cn.clate.kezhan.pojos.Notice;
import cn.clate.kezhan.pojos.Resource;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class MomentDomain {
    public static NutMap getMomentByCourseIdUsingPager(int subCourseId, int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        pager.setRecordCount(dao.count(Moment.class, Cnd.where("subCourseId", "=", subCourseId).desc("updateTime")));
        List<Moment> moments;
        moments = dao.query(Moment.class, Cnd.where("subCourseId", "=", subCourseId).
                desc("updateTime"), pager);
        if (moments.size() == 0) {
            return null;
        }
        ArrayList<Moment> momentArrayList = new ArrayList<>(moments);
        NutMap ret = new NutMap();
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        ret.addv("content", momentArrayList);
        return ret;
    }

    public static NutMap getMomentDetailByTypeId(int type, int typeId) {
        Dao dao = DaoFactory.get();
        Object ret;
        switch (type) {
            case 1:
                ret = dao.fetch(Homework.class, typeId);
                break;
            case 2:
                ret = dao.fetch(Notice.class, typeId);
                break;
            case 3:
                ret = dao.fetch(Resource.class, typeId);
                break;
            default:
                ret = null;
                break;
        }
        if (ret == null) {
            return null;
        }
        NutMap nutMap = new NutMap();
        nutMap.addv("type", type);
        nutMap.addv("detail", ret);
        return nutMap;
    }
}
