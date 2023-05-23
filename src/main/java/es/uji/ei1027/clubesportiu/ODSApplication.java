package es.uji.ei1027.clubesportiu;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class ODSApplication {

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

//	private static final Logger log = Logger.getLogger(ODSApplication.class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ODSApplication.class).run(args);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------
	// TEST CODE ACCESSING DB


//	// Plantilla per a executar operacions sobre la connexió
//	private JdbcTemplate jdbcTemplate;
//
//	// Crea el jdbcTemplate a partir del DataSource que hem configurat
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		jdbcTemplate = new JdbcTemplate(dataSource);
//	}
//
//	// using dao
//	@Autowired
//	ODSDao odsDao;
//
//
//	// Funció principal
//	public void run(String... strings) throws Exception {
//		log.info("Ací va el meu codi");
//
//		// manual test - working
//		log.info("Manual access test");
//		ODS n1 = jdbcTemplate.queryForObject(
//				"SELECT * FROM ods",
//				new ODSRowMapper());
//		log.info(n1.toString());
//
//		// initial dao test - working
//		log.info("Dao access test");
//		log.info(odsDao.getODS("hola").toString());
//
//	}

	// -----------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------

}

