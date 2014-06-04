/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.hygj.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import sun.awt.EmbeddedFrame;

import com.hygj.bean.EmailBean;
import com.hygj.bean.UsersBean;
import com.hygj.dao.SendedDAOINF;
import com.hygj.dao.impl.SendedDAOImpl;
import com.hygj.email.EmailService;

import com.hygj.service.InfoServiceINF;
import com.hygj.service.impl.InfoServiceImpl;

/** 
 * MyEclipse Struts
 * Creation date: 04-30-2009
 * 
 * XDoclet definition:
 * @struts.action parameter="method" validate="true"
 */
public class InfoAction extends DispatchAction {
	/*
	 * Generated Methods
	 */

	/** 
	 * ���ܣ���֤�û�
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward checkLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm dn = (DynaActionForm) form;

		UsersBean users = new UsersBean();
		
		users.setUsername(dn.get("username").toString());
		users.setPassword(dn.get("password").toString());
		
		InfoServiceINF info = new InfoServiceImpl();
		
		UsersBean user = info.checkLogin(users);
		
		if(user.getId()>0)
		{
			request.getSession().setAttribute("user", user);
			return mapping.findForward("main");
		}
		else
		{
			return new ActionForward("/index.jsp");
		}
	}
	/******
	 * ���ܣ�����Email
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sendedMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DynaActionForm dn = (DynaActionForm) form;
		
		Calendar   cl=Calendar.getInstance();     //��ȡ����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");  
		String   thetime   =   sdf.format(cl.getTime()); 
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		EmailBean email = new EmailBean();
		
		email.setHost("smtp.sohu.com");
		email.setUsername(ub.getUsername());
		email.setPassword(ub.getPassword());
		email.setSender(dn.get("fajianren").toString());
		email.setRecipients(dn.get("shoujianren").toString());
		email.setTitle(dn.get("title").toString());
		email.setContent(dn.get("content").toString());
		email.setThetime(thetime);
		email.setType(3);
		email.setStatus(0);
		
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int flag = info.sendEmail(email);//����ݿ�
	
		new EmailService().sendEmail(email);//���ʼ�
		
		return mapping.findForward("ok");

	}
	/** 
	 * ���ܣ���ѯ�ѽ����ʼ�
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectGetsEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		info.deleteShou(ub.getUsername(), "1");//ɾ���ռ����ʼ�
		
		
		EmailBean email = new EmailBean();
		
		email.setHost("pop3.sohu.com");
		email.setUsername(ub.getUsername());
		email.setPassword(ub.getPassword());
		email.setRecipients(ub.getUsername()+"@sohu.com");
		
		new EmailService().receiveMail(email);//�����ʼ�
		

		List<EmailBean> list = info.selectGetsEmail(ub.getUsername());//��ѯ��ݿ�
		
		System.out.println(list.size()+"================");
		request.setAttribute("list", list);
		
		return mapping.findForward("yijie");
	}

	
	/** 
	 * ���ܣ���ѯ�ѽ����ʼ�(����,δ��)
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectGetCout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int itt = info.selectGetCount(ub.getUsername());//����
		
		System.out.println(itt+"����");
		
		int iss = info.selectGetSCount(ub.getUsername());//δ��
		
		request.setAttribute("itt", itt);
		request.setAttribute("iss", iss);

		return mapping.findForward("yijiecount");
	}
	
	/** 
	 * ���ܣ���ѯ�ѷ����ʼ�
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectSendedEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		List<EmailBean> list = info.selectsendedEmail(ub.getUsername());
		
		request.setAttribute("list", list);
		
		return mapping.findForward("yifa");
	}
	/** 
	 * ���ܣ���ѯ��ɾ���ʼ�
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectDeleteEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		List<EmailBean> list = info.selectdeleteEmail(ub.getUsername());
		
		request.setAttribute("list", list);
		
		
		return mapping.findForward("yishan");
	}
	/** 
	 * ���ܣ���ѯ�ѷ����ʼ���ϸ
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectMoreEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		List<EmailBean> list = info.selectMoreEmail(id); //���ID��ѯ�ʼ���ϸ��Ϣ
		
	
		int flag = info.updateDeleteEmail(id); //������ɾ���ʼ�״̬
		
		request.setAttribute("list", list);

		return mapping.findForward("emaillist");
	}
	
	/** 
	 * ���ܣ���ѯ�ѷ����ʼ�(����)
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectSeEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int it = info.selectSendedCount(ub.getUsername());
		
		request.setAttribute("it", it);

		return mapping.findForward("yifacout");
	}
	
	/** 
	 * ���ܣ���ѯ��ɾ���ʼ�(����)
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectDeleteCout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int it = info.selectDeleteCount(ub.getUsername());//����
		
		int is = info.selectDeleteUCount(ub.getUsername());//δ��
		
		request.setAttribute("it", it);
		request.setAttribute("is", is);

		return mapping.findForward("yishancout");
	}

	/** 
	 * ���ܣ���ѯ�ݸ��ʼ�
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectCao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		List<EmailBean> list = info.selectCaoEmail(ub.getUsername());
		
		request.setAttribute("list", list);

		return mapping.findForward("caogao");
	}
	/** 
	 * ���ܣ���ѯ�ݸ��ʼ�(����)
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward selectCaoCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int io = info.selectCaoCount(ub.getUsername());//����
		
		System.out.println(io+"---!");
		
		request.setAttribute("io", io);
	
		return mapping.findForward("caogaocount");
	}
	
	/****
	 *  ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward selectCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int shoujian = info.selectGetCount(ub.getUsername());//�ռ�
		
		int caogao = info.selectCaoCount(ub.getUsername());//�ݸ�
		
		int yifa = info.selectSendedCount(ub.getUsername());//�ѷ�
		
		int delete = info.selectDeleteCount(ub.getUsername());//ɾ��
		
		request.setAttribute("shoujian", shoujian);
		request.setAttribute("caogao", caogao);
		request.setAttribute("yifa", yifa);
		request.setAttribute("delete", delete);
	
		return mapping.findForward("left");
	}
	
	/****
	 *  ��ӭҳ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward welcome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		InfoServiceINF info = new InfoServiceImpl();
		
		int wel = info.selectGetCount(ub.getUsername());//����
		
		int come = info.selectGetSCount(ub.getUsername());//δ��
		
		String user = ub.getUsername();
		
		request.setAttribute("user", user);
		request.setAttribute("wel", wel);
		request.setAttribute("come", come);
		
	
		return mapping.findForward("welcome");
	}

	/** 
	 * �ƶ���������
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward moveAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String[] arg = request.getParameterValues("del_id");
		
		InfoServiceINF ss = new InfoServiceImpl();
		
		for (String str : arg) 
		{
			ss.moveBox(str);
		}
		
		
		return mapping.findForward("oki");
	}
	
	/** 
	 * ɾ���ʼ�
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String[] arg = request.getParameterValues("del_id");
		
		InfoServiceINF ss = new InfoServiceImpl();
		
		for (String str : arg) 
		{
			ss.deleteEmail(str);
		}
		
		UsersBean ub = (UsersBean)(request.getSession().getAttribute("user") != null ? request.getSession().getAttribute("user") : "");
		
		EmailBean email = new EmailBean();
		
		email.setHost("pop3.sohu.com");
		email.setUsername(ub.getUsername());
		email.setPassword(ub.getPassword());
		
		new EmailService().deleteMail(email);
		
		return mapping.findForward("oks");
	}
}