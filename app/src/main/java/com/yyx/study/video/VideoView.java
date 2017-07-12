package com.yyx.study.video;

import java.util.List;
import java.util.Objects;


/**
 * @author laohu
 * @site http://ittiger.cn
 */
public interface VideoView{

    void showLoadMoreErrorView();

    void showLoadMoreView();

    void setLoadMoreData(List<Objects> videos);
}
