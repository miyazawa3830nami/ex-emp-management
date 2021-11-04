package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {

	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER
	= (rs,i) -> {
		Administrator administrator 
		= new Administrator(rs.getInt("id"),rs.getString("name"),rs.getString("mail_address"),rs.getString("password"));
		return administrator;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 管理者情報を挿入
	 * @param administrator
	 * @return
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		if(administrator.getId() == null) {
			String insertSql = "INSERT INTO administrators(name,mail_address,password)"
					+ " VALUES(:name,:mailAddress,:password);";
			template.update(insertSql,param);
		} 
	}
	/**
	 * メールアドレスとパスワードから管理者情報を取得
	 * @param mailAddress
	 * @param password
	 * @return
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String findBySql = "SELECT * FROM administrators WHERE mail_address=:mailAddress OR password=:password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
		List<Administrator> administratorList = template.query(findBySql, param, ADMINISTRATOR_ROW_MAPPER);
		if(administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}
}
