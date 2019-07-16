package utils;

import bean.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Alex_Cheng
 * @date 2019/5/29 20:02
 * @Description 分类处理表单上传项，如果是图片就封装处理
 */
public class FileUploadUtils {
    public static Product creatProduct(HttpServletRequest request) {
        //返回product对象
        Product product = new Product();
        try {
            //创建工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //配置存储库
            ServletContext servletContext = request.getServletContext();
            //设置文件存放目录
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            //解析http请求报文
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //防止中文乱码
            fileUpload.setHeaderEncoding("utf-8");
            String upload = request.getServletContext().getRealPath("upload");
            //如果用户什么都不填然后点击增加
            // the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is null
            //要么加个判断或者在jsp设置不为空
            List<FileItem> list = fileUpload.parseRequest(request);
            if (list != null) {
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()){
                        //普通表单内的name值
                        String fieldName = fileItem.getFieldName();
                        String str = fileItem.getString("utf-8");
                        switch (fieldName) {
                            case "cid": {
                                product.setCid(Integer.parseInt(str));
                                break;
                            }
                            case "pid": {
                                product.setPid(str);
                                break;
                            }
                            case "pname": {
                                product.setPname(str);
                                break;
                            }
                            case "pnum": {
                                product.setPnum(Integer.parseInt(str));
                                break;
                            }
                            case "estoreprice": {
                                product.setEstoreprice(Double.parseDouble(str));
                                break;
                            }
                            case "markprice": {
                                product.setMarkprice(Double.parseDouble(str));
                                break;
                            }
                            case "description": {
                                product.setDescription(str);
                            }
                        }
                    }
                    else {
                        String fileName = fileItem.getName();
                        fileName = UUID.randomUUID().toString() + "-" + fileName;
                        File uploadFile = new File(upload + "/" + fileName);
                        if( ! uploadFile.getParentFile().exists()){
                            uploadFile.getParentFile().mkdirs();
                        }
                        fileItem.write(uploadFile);
                        String imgurl =  "/upload/" + fileName;
                        product.setImgurl(imgurl);
                    }
                }
            }
            System.out.println(product.toString());
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}

