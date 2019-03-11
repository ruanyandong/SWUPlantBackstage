package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class characterFilter implements Filter {

	//�ַ�����
    String encoding=null;
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if(encoding!=null){
        	System.out.println("���벻Ϊ��");
        //����request�ַ�����
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
         //����response�ַ�����
            response.setContentType("text/html;charset="+encoding);
        }
     //���ݸ���һ��������
        chain.doFilter(request, response);
    }
    public void init(FilterConfig filterConfig) throws ServletException {
      //��ȡ��ʼ������
        encoding=filterConfig.getInitParameter("CharsetEncoding");
        System.out.println("����=======��"+encoding);
    }
    public void destroy() {
        // TODO Auto-generated method stub
        encoding=null;
    }

}
