package org.sopt.yata.yata.vo;

/**
 * Created by SeungKoo on 2017. 6. 20..
 */
public class MockManager {
    private static MockManager ourInstance = new MockManager();

    public static MockManager getInstance() {
        return ourInstance;
    }


    private org.sopt.yata.yata.vo.UserVO user;


    private MockManager() {
        initData();
    }

    private void initData(){
        user = new org.sopt.yata.yata.vo.UserVO();
    }


    public org.sopt.yata.yata.vo.UserVO getUser(){
        return user;
    }

}
