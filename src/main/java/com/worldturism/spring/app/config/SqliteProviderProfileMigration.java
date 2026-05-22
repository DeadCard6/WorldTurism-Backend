package com.worldturism.spring.app.config;

import java.util.List;
import java.util.Map;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqliteProviderProfileMigration implements ApplicationRunner {

	private final JdbcTemplate jdbcTemplate;

	public SqliteProviderProfileMigration(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!hasUniqueUserIdConstraint()) {
			return;
		}

		jdbcTemplate.execute("PRAGMA foreign_keys=OFF");
		try {
			jdbcTemplate.execute("""
					CREATE TABLE provider_profiles_new (
						id integer primary key autoincrement,
						address varchar(200),
						business_name varchar(150) not null,
						category varchar(80),
						city varchar(100),
						description varchar(1000),
						logo_url varchar(300),
						tax_id varchar(60),
						user_id bigint not null,
						website varchar(200),
						foreign key(user_id) references users(id)
					)
					""");
			jdbcTemplate.execute("""
					INSERT INTO provider_profiles_new (
						id,
						address,
						business_name,
						category,
						city,
						description,
						logo_url,
						tax_id,
						user_id,
						website
					)
					SELECT
						id,
						address,
						business_name,
						category,
						city,
						description,
						logo_url,
						tax_id,
						user_id,
						website
					FROM provider_profiles
					""");
			jdbcTemplate.execute("DROP TABLE provider_profiles");
			jdbcTemplate.execute("ALTER TABLE provider_profiles_new RENAME TO provider_profiles");
		} finally {
			jdbcTemplate.execute("PRAGMA foreign_keys=ON");
		}
	}

	private boolean hasUniqueUserIdConstraint() {
		List<Map<String, Object>> indexes;
		try {
			indexes = jdbcTemplate.queryForList("PRAGMA index_list(provider_profiles)");
		} catch (RuntimeException exception) {
			return false;
		}

		for (Map<String, Object> index : indexes) {
			if (!isUnique(index)) {
				continue;
			}

			String indexName = String.valueOf(index.get("name"));
			List<Map<String, Object>> columns = jdbcTemplate.queryForList("PRAGMA index_info(\"" + escapeIdentifier(indexName) + "\")");
			if (columns.size() == 1 && "user_id".equals(columns.get(0).get("name"))) {
				return true;
			}
		}

		return false;
	}

	private boolean isUnique(Map<String, Object> index) {
		Object unique = index.get("unique");
		return unique instanceof Number number && number.intValue() == 1;
	}

	private String escapeIdentifier(String identifier) {
		return identifier.replace("\"", "\"\"");
	}
}
