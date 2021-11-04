package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;


@Repository
public class EmployeeRepository {

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER
	= (rs,i) -> {
		Employee employee 
		= new Employee(
				rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("gender"),
				rs.getDate("hireDate"), rs.getString("mailAddress"), rs.getString("zipCode"),
				rs.getString("address"), rs.getString("telephone"), rs.getInt("salary"), 
				rs.getString("characteristics"), rs.getInt("dependentsCount"));
		return employee;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 従業員一覧情報を入社日順（降順）で取得する
	 * @return
	 */
	public List<Employee> findAll(){
		String sql = "SELECT * FROM employees ORDER BY hireDate;";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	/**
	 * 主キーから従業員情報を取得する
	 * @param id
	 * @return
	 */
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	
	/**
	 * 従業員情報を変更する
	 * @param employee
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String updateSql = "UPDATE employees SET name=:name, image=:image, gender=:gender, hireDate=:hireDate, "
				+ "mailAddress=:mailAddress, zipCode=:zipCode, address=:address, telephone=:telephone,"
				+ "characteristics=:characteristics, dependentsCount=:dependentsCount WHERE id=:id;";
		template.update(updateSql, param);
	}
}
