package bean;

/**
 * @author Alex_Cheng
 * @date 2019/5/28 22:49
 * @Description TODO
 */
public class Product {

    private String pid;
    private String pname;
    private double estoreprice;
    double markprice;
    int pnum;
    int cid;
    String imgurl;
    String description;

    Category category;

    public Product() {
    }

    public Product(String pid, String pname, double estoreprice, double markprice, int pnum, int cid, String imgurl, String description) {
        this.pid = pid;
        this.pname = pname;
        this.estoreprice = estoreprice;
        this.markprice = markprice;
        this.pnum = pnum;
        this.cid = cid;
        this.imgurl = imgurl;
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getEstoreprice() {
        return estoreprice;
    }

    public void setEstoreprice(double estoreprice) {
        this.estoreprice = estoreprice;
    }

    public double getMarkprice() {
        return markprice;
    }

    public void setMarkprice(double markprice) {
        this.markprice = markprice;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", estoreprice=" + estoreprice +
                ", markprice=" + markprice +
                ", pnum=" + pnum +
                ", cid=" + cid +
                ", imgurl='" + imgurl + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }


}
