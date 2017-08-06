package cn.hejinyo.core.FreeMarker;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/4 21:37
 * @Description :
 */
public class RichFreeMarkerView extends FreeMarkerView {
    /**
     * 部署路径属性名称
     */
    public static final String CONTEXT_PATH = "basePath";

    /**
     * 在model中增加部署路径base，方便处理部署路径问题。
     */
    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        model.put(CONTEXT_PATH, basePath);
    }
}
