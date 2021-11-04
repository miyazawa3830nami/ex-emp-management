package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 従業員一覧を取得する
	 * @param model
	 * @return
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		model.addAttribute("employeeList",employeeService.showList());
		return "employee/list";
	}
}