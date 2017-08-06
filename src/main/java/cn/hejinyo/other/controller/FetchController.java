package cn.hejinyo.other.controller;

import cn.hejinyo.other.service.ITableIpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class FetchController {

	@Resource(name = "tableIpServiceImpl")
    ITableIpService service;

	@RequestMapping(value = "fetch")
	public String fetch() {
		return "page/fetch";
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public void get(HttpServletRequest request, HttpServletResponse response) {
		String number = request.getParameter("num");
		String country = request.getParameter("country");
		String isp = request.getParameter("isp");
		String txt = service.printIp(number, country, isp);
		PrintWriter writer;
		try {
			response.setCharacterEncoding("GBK");
			writer = response.getWriter();
			writer.write(txt);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
