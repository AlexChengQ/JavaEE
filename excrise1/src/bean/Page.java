package bean;

import java.util.List;

public class Page<T> {

    //总页数
    private int totalPageNum;
    //总记录数
    private int totalRecordNum;
    //当前页面数
    private int currentPageNum;
    //前一页数
    private int previousPageNum;
    //后一页数
    private int nextPageNum;

    //private List<Category> list;
    //应该使用泛型实用性更广泛
    private List<T> list;

    public static final int PAGE_SIZE = 5;


    public Page() {
    }

    //当前页数，总记录数，一页显示数
    //计算总页数，前一页数，后一页数
    public Page(int currentPageNum, int totalRecordNum, int NumberPage) {

        setTotalRecordNum(totalRecordNum);
        setCurrentPageNum(currentPageNum);

        int i = totalRecordNum / NumberPage ;
        setTotalPageNum((totalRecordNum % NumberPage == 0 ? i : i + 1));

        int prevPageNum = currentPageNum - 1 == 0 ? currentPageNum : currentPageNum - 1;
        setPreviousPageNum(prevPageNum);

        int nextPageNum = currentPageNum + 1 > totalPageNum ? currentPageNum : currentPageNum + 1;
        setNextPageNum(nextPageNum);
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalRecordNum() {
        return totalRecordNum;
    }

    public void setTotalRecordNum(int totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getPreviousPageNum() {
        return previousPageNum;
    }

    public void setPreviousPageNum(int previousPageNum) {
        this.previousPageNum = previousPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
